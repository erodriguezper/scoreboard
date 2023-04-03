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
import java.util.Comparator;

/**
 *
 * @author enrodpre
 */
public enum OrderComparators {
    // Comparator that orders by the total amount of goals that happened in the match
    TOTAL_SCORE {
        @Override
        protected Comparator<Match> generateComparator() {
            return (Match m1, Match m2) -> {
                int m1Score = m1.getAwayTeamScore() + m1.getHomeTeamScore();
                int m2Score = m2.getAwayTeamScore() + m2.getHomeTeamScore();

                return Integer.compare(m2Score, m1Score);
            };
        }
    },
    // Comparator that orders by the team that has scored more goals
    MAX_SCORER {
        @Override
        protected Comparator<Match> generateComparator() {
            return (Match m1, Match m2)
                    -> Integer.compare(
                            Math.max(m2.getHomeTeamScore(), m2.getAwayTeamScore()),
                            Math.max(m1.getHomeTeamScore(), m1.getAwayTeamScore())
                    );
        }
    },
    // Comparator that orders by the latter that has been created
    RECENTLY_ADDED {
        @Override
        protected Comparator<Match> generateComparator() {
            return (Match m1, Match m2) -> m2.getStartDate().compareTo(m1.getStartDate());
        }
    };

    // Marks if the comparator should be reversed
    private boolean reversed = false;

    public OrderComparators reversed() {
        reversed = true;
        return this;
    }

    protected abstract Comparator<Match> generateComparator();

    public Comparator<Match> getComparator() {
        return reversed ? generateComparator().reversed() : generateComparator();
    }
}
