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
package com.sportradar.scoreboard.comparators;

import com.sportradar.scoreboard.entity.Match;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author enrodpre
 */
public class OrderComparatorsTest {

    /**
     * Test of reversed method, of class OrderComparators.
     */
    @Test
    public void testReversed() {
        OrderComparators ex = OrderComparators.MAX_SCORER;
        assertEquals(ex, ex.reversed().reversed());
    }

    /**
     * Test of getComparator method, of class OrderComparators.
     */
    @Test
    public void testComparators() {
        Match m1 = new Match(1, "", 3, "", 5, Instant.ofEpochMilli(1234)),
                m2 = new Match(1, "", 4, "", 3, Instant.ofEpochMilli(4321));
        
        assertEquals(-1, OrderComparators.TOTAL_SCORE.getComparator().compare(m1, m2));
        assertEquals(1, OrderComparators.TOTAL_SCORE.getComparator().reversed().compare(m1, m2));
        
        assertEquals(-1, OrderComparators.MAX_SCORER.getComparator().compare(m1, m2));
        assertEquals(1, OrderComparators.MAX_SCORER.getComparator().reversed().compare(m1, m2));
        
        assertEquals(1, OrderComparators.RECENTLY_ADDED.getComparator().compare(m1, m2));
        assertEquals(-1, OrderComparators.RECENTLY_ADDED.getComparator().reversed().compare(m1, m2));
    }
}
