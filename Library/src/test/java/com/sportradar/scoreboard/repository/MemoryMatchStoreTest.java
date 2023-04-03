/*
 * Copyright (C) 2023 enrodpre
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.sportradar.scoreboard.repository;

import com.sportradar.scoreboard.comparators.OrderComparators;
import com.sportradar.scoreboard.entity.Match;
import com.sportradar.scoreboard.exceptions.NotFoundException;
import com.sportradar.scoreboard.repository.impl.MemoryMatchRepository;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;

/**
 *
 * @author enrodpre
 */
public class MemoryMatchStoreTest {

    private static MemoryMatchRepository store;

    public MemoryMatchStoreTest() {
    }

    @BeforeEach
    public void setUp() {
        store = new MemoryMatchRepository();
    }

    @Test
    public void testCreate() {
        int id = store.create("Spain", "Portugal");

        Match expected = new Match(0, "Spain", 1, "Portugal", 1, null);
        Match actual = assertDoesNotThrow(() -> store.read(id));

        assertEquals(expected, actual);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.Standard.class)
    public class TestsWithFilledData {

        @BeforeEach
        public void setUp() {
            store.create("USA", "Brazil");
        }

        @Test
        public void testReadAll() {
            store.create("Spain", "Portugal");

            List<Match> expected = List.of(new Match(0, "USA", 1, "Brazil", 1, null),
                    new Match(1, "Spain", 1, "Portugal", 1, null)
            );
            List<Match> actual = store.list();

            assertIterableEquals(expected, actual);
        }

        @Test
        public void testDelete() {
            // Ensure the list is filled
            List<Match> before = store.list();
            assertEquals(1, before.size());

            // Delete element
            assertDoesNotThrow(() -> store.delete(0));

            // Check if the list is now empty
            List<Match> after = store.list();
            assertEquals(Collections.EMPTY_LIST, after);
        }

        @Test
        public void testUpdate() {
            assertDoesNotThrow(() -> store.update(0, 43, 65));

            Match m = assertDoesNotThrow(() -> store.read(0));
            assertEquals(43, m.getHomeTeamScore());
            assertEquals(65, m.getAwayTeamScore());
        }

    }

    @Test
    public void testReadNotFound() {
        Throwable exception = assertThrows(NotFoundException.class, () -> store.read(1));
        assertEquals("Id 1 not found when reading", exception.getMessage());
    }

    @Test
    public void testDeleteNotFound() {
        Throwable exception = assertThrows(NotFoundException.class, () -> store.delete(0));
        assertEquals("Id 0 not found when deleting", exception.getMessage());
    }

    @Test
    public void testUpdateNotFound() {
        Throwable exception = assertThrows(NotFoundException.class, () -> store.update(0, 1, 2));
        assertEquals("Id 0 not found when updating", exception.getMessage());
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.Standard.class)
    public class TestsWithBigNumberOfData {

        @BeforeEach
        public void setUp() {
            for (int i = 0; i < 100; i++) {
                store.create("USA", "Brazil");
            }
        }

        @Test
        public void testNoDuplicatedId() {
            List<Match> matches = store.list();

            int s = matches.stream()
                    // Group them by id
                    .collect(Collectors.groupingBy(Match::getId))
                    .keySet()
                    .size();

            if (s != 100) {
                fail("There are repeated ids");
            }

        }

        @Test
        @RepeatedTest(5)
        public void testOrderIsEnsured() {
            List<Match> m1 = store.list(),
                    m2 = store.list();

            assertIterableEquals(m1, m2);
        }
    }

    @Test
    public void testSimpleOrderedList() {
        store.create("a", "b");
        store.create("c", "d");

        List<Match> result = store.orderedList(OrderComparators.RECENTLY_ADDED);

        assertEquals("c 1 - d 1", result.get(0).toString());
        assertEquals("a 1 - b 1", result.get(1).toString());

    }

    @Test
    public void testComposedOrderedList() {

        int id = store.create("c", "d");
        store.create("a", "b");
        store.create("e", "f");

        store.update(id, 3, 4);

        List<Match> result = store.orderedList(OrderComparators.TOTAL_SCORE, OrderComparators.RECENTLY_ADDED);

        assertEquals("c 3 - d 4", result.get(0).toString());
        assertEquals("e 1 - f 1", result.get(1).toString());
        assertEquals("a 1 - b 1", result.get(2).toString());

    }

    @Test
    public void testOrderedList() {
        // Create the data
        int id1 = store.create("Mexico", "Canada");
        int id2 = store.create("Spain", "Brazil");
        int id3 = store.create("Germany", "France");
        int id4 = store.create("Uruguay", "Italy");
        int id5 = store.create("Argentina", "Australia");

        // Update them
        store.update(id1, 0, 5);
        store.update(id2, 10, 2);
        store.update(id3, 2, 2);
        store.update(id4, 6, 6);
        store.update(id5, 3, 1);

        // Fetch the ordered data
        List<Match> result = store.orderedList(OrderComparators.TOTAL_SCORE, OrderComparators.RECENTLY_ADDED);

        // Test
        assertEquals("Uruguay 6 - Italy 6", result.get(0).toString());
        assertEquals("Spain 10 - Brazil 2", result.get(1).toString());
        assertEquals("Mexico 0 - Canada 5", result.get(2).toString());
        assertEquals("Argentina 3 - Australia 1", result.get(3).toString());
        assertEquals("Germany 2 - France 2", result.get(4).toString());

    }

}
