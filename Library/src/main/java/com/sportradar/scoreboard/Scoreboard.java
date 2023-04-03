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

    private MatchRepository repository;
    
    @Autowired
    public Scoreboard(MatchRepository repository) {
            this.repository = repository;
    }

    public int startGame(String homeTeamName, String awayTeamName) {
        return repository.create(homeTeamName, awayTeamName);
    }

    public void finishGame(Match match) {
        repository.delete(match.getId());
    }

    public void updateGame(Match match, int homeTeamScore, int awayTeamScore) {
        repository.update(match.getId(), homeTeamScore, awayTeamScore);
    }

    public List<Match> getSummaryByTotalScore() {
        return repository.orderedList(OrderComparators.MAX_SCORER, OrderComparators.RECENTLY_ADDED);
    }
}
