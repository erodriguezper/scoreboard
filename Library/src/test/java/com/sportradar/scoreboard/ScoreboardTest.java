package com.sportradar.scoreboard;

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
import com.sportradar.scoreboard.comparators.OrderComparators;
import com.sportradar.scoreboard.repository.MatchRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 *
 * @author enrodpre
 */
public class ScoreboardTest {

    private final MatchRepository repository;
    private final Scoreboard scoreboard;

    public ScoreboardTest() {
        repository = Mockito.mock(MatchRepository.class);
        scoreboard = new Scoreboard(repository);
    }

    /**
     * Test of startGame method, of class Scoreboard.
     */
    @Test
    public void testStartGame() {
        Mockito.when(repository.create("team1", "team2")).thenReturn(45);

        assertEquals(45, scoreboard.startGame("team1", "team2"));
    }

    /**
     * Test of finishGame method, of class Scoreboard.
     */
    @Test
    public void testFinishGame() {
        scoreboard.finishGame(3);

        Mockito.verify(repository, Mockito.times(1)).delete(3);
    }

    /**
     * Test of updateGame method, of class Scoreboard.
     */
    @Test
    public void testUpdateGame() {
        scoreboard.updateGame(3, 3, 4);

        Mockito.verify(repository, Mockito.times(1)).update(3, 3, 4);
    }

    /**
     * Test of getSummaryByTotalScore method, of class Scoreboard.
     */
    @Test
    public void testGetSummaryByTotalScore() {
        scoreboard.getSummaryByTotalScore();
        
        Mockito.verify(repository, Mockito.times(1))
                .orderedList(OrderComparators.TOTAL_SCORE, OrderComparators.RECENTLY_ADDED);
    }
}
