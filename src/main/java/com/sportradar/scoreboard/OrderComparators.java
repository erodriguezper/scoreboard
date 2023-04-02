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

import com.sportradar.scoreboard.entity.Match;
import java.util.Comparator;

/**
 *
 * @author enrodpre
 */
public enum OrderComparators {
    TOTAL_SCORE {
        @Override
        Comparator<Match> makeComparison() {
            return (Match m1, Match m2) -> {
                int m1Score = m1.getAwayTeamScore() + m1.getHomeTeamScore();
                int m2Score = m2.getAwayTeamScore() + m2.getHomeTeamScore();

                return Integer.compare(m1Score, m2Score);
            };
        }
    },
    MAX_SCORER{
        @Override
        Comparator<Match> makeComparison() {
            return (Match m1, Match m2) -> {
                int m1Score = m1.getAwayTeamScore() + m1.getHomeTeamScore();
                int m2Score = m2.getAwayTeamScore() + m2.getHomeTeamScore();

                return Integer.compare(m1Score, m2Score);
            };
        }
    },
    RECENTLY_ADDED {
        @Override
        Comparator<Match> makeComparison() {
            return (Match m1, Match m2) -> {
                int m1Score = m1.getAwayTeamScore() + m1.getHomeTeamScore();
                int m2Score = m2.getAwayTeamScore() + m2.getHomeTeamScore();

                return Integer.compare(m1Score, m2Score);
            };
        }
    };

    
    abstract Comparator<Match> makeComparison();
}
