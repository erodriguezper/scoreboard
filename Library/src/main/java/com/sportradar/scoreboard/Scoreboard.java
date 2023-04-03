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
package com.sportradar.scoreboard;

import com.sportradar.scoreboard.comparators.OrderComparators;
import com.sportradar.scoreboard.entity.Match;
import com.sportradar.scoreboard.repository.MatchRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author enrodpre
 */
@Component
public class Scoreboard {

    private final MatchRepository repository;

    @Autowired
    public Scoreboard(MatchRepository repository) {
        this.repository = repository;
    }

    /**
     * Starts a game with initial score of 0 - 0
     *
     * @param homeTeamName String that represents the home team
     * @param awayTeamName String that represents the away team
     * @return the id of the created match
     */
    public int startGame(String homeTeamName, String awayTeamName) {
        return repository.create(homeTeamName, awayTeamName);
    }

    /**
     * Finishes a game, i.e. it deletes it from the storage
     *
     * @param id Integer that represents the game
     */
    public void finishGame(int id) {
        repository.delete(id);
    }

    /**
     * Updates scores from a game
     *
     * @param id Integer that represents the match
     * @param homeTeamScore Goals that home team has scored
     * @param awayTeamScore Goals that away team has scored
     */
    public void updateGame(int id, int homeTeamScore, int awayTeamScore) {
        repository.update(id, homeTeamScore, awayTeamScore);
    }

    /**
     * Lists the game ordered by total score and recently added
     *
     * @return the list of games ordered
     */
    public List<Match> getSummaryByTotalScore() {
        return repository.orderedList(OrderComparators.TOTAL_SCORE, OrderComparators.RECENTLY_ADDED);
    }
}
