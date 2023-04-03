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
package com.sportradar.scoreboard.entity;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.sportradar.scoreboard.entity.Match;
import java.time.Instant;

/**
 *
 * @author enrodpre
 */
public class MatchTest {

    private static Match match;
    private static final int ID = 12345;
    private static final String HOME_TEAM_NAME = "Australia";
    private static final int HOME_TEAM_SCORE = 32;
    private static final String AWAY_TEAM_NAME = "Canada";
    private static final int AWAY_TEAM_SCORE = 21;

    public MatchTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        System.out.println("Starting Match class tests");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("Done Match class testing");
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.Standard.class)
    public class TestsWithEmptyValuesInConstructors {

        @BeforeEach
        public void setUp() {
            match = new Match(32, "", 0, "", 0, Instant.ofEpochMilli(1234));
        }

        @Test
        public void testGetId() {
            assertEquals(32, match.getId());
        }

        @Test
        public void testHomeTeamProp() {
            match.setHomeTeam(HOME_TEAM_NAME);

            assertEquals(HOME_TEAM_NAME, match.getHomeTeam());
        }

        @Test
        public void testHomeTeamScoreProp() {
            match.setHomeTeamScore(HOME_TEAM_SCORE);

            assertEquals(HOME_TEAM_SCORE, match.getHomeTeamScore());
        }

        @Test
        public void testAwayTeamProp() {
            match.setAwayTeam(AWAY_TEAM_NAME);

            assertEquals(AWAY_TEAM_NAME, match.getAwayTeam());
        }

        @Test
        public void testAwayTeamScoreProp() {
            match.setAwayTeamScore(AWAY_TEAM_SCORE);

            assertEquals(AWAY_TEAM_SCORE, match.getAwayTeamScore());
        }

        @Test
        public void testGetStartDate() {
            assertEquals(Instant.ofEpochMilli(1234), match.getStartDate());
        }
    }

    @Test
    public void testInitialConstructor() {
        match = new Match(ID, HOME_TEAM_NAME, AWAY_TEAM_NAME, Instant.ofEpochMilli(1234));

        assertEquals(HOME_TEAM_NAME, match.getHomeTeam());
        assertEquals(0, match.getHomeTeamScore());
        assertEquals(AWAY_TEAM_NAME, match.getAwayTeam());
        assertEquals(0, match.getAwayTeamScore());
        assertEquals(Instant.ofEpochMilli(1234), Instant.ofEpochMilli(1234));
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.Standard.class)
    public class FullValueInConstructors {

        @BeforeEach
        public void setUp() {
            match = new Match(ID, HOME_TEAM_NAME, HOME_TEAM_SCORE, AWAY_TEAM_NAME, AWAY_TEAM_SCORE, null);
        }

        @Test
        public void testToString() {
            assertEquals("Australia 32 - Canada 21", match.toString());
        }

        @Test
        public void testEquals() {
            // With itself
            assertTrue(match.equals(match));

            // With other identical match
            Match otherMatch = new Match(ID, HOME_TEAM_NAME, HOME_TEAM_SCORE, AWAY_TEAM_NAME, AWAY_TEAM_SCORE, Instant.ofEpochMilli(1234));

            assertTrue(match.equals(otherMatch));

            // With other different match
            otherMatch = new Match(ID, HOME_TEAM_NAME, HOME_TEAM_SCORE, AWAY_TEAM_NAME, 2, Instant.now());

            assertFalse(match.equals(otherMatch));
        }

        @Test
        public void testCopyConstructor() {
            assertTrue(match.equals(new Match(match)));
        }
    }

}
