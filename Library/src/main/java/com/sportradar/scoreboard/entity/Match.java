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

import java.time.Instant;
import java.util.Objects;

/**
 *
 * @author enrodpre
 */
public class Match {

    private final int id;
    private String homeTeam;
    private int homeTeamScore;
    private String awayTeam;
    private int awayTeamScore;
    private Instant startDate;

    // Full constructor
    public Match(int id, String homeTeam, int homeTeamScore, String awayTeam, int awayTeamScore, Instant startDate) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.homeTeamScore = homeTeamScore;
        this.awayTeam = awayTeam;
        this.awayTeamScore = awayTeamScore;
        this.startDate = startDate;
    }

    // Initial match constructor
    public Match(int id, String homeTeam, String awayTeam, Instant startDate) {
        this(id, homeTeam, 0, awayTeam, 0, startDate);
    }

    // Copy constructor
    public Match(Match other) {
        this(other.getId(), other.getHomeTeam(), other.getHomeTeamScore(), other.getAwayTeam(), other.getAwayTeamScore(), other.getStartDate());
    }

    /**
     * Textual representation of the object
     *
     * @return the string that represents the match
     */
    @Override
    public String toString() {
        return String.format("%s %d - %s %d",
                this.homeTeam,
                this.homeTeamScore,
                this.awayTeam,
                this.awayTeamScore);
    }

    /**
     * Compare if two matches are identical value wise
     *
     * @param o the other object
     * @return true if are identical value wise, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        // Same object
        if (o == this) {
            return true;
        }

        // Not instance of same class
        if (!(o instanceof Match)) {
            return false;
        }

        // Cast to Match
        Match other = (Match) o;

        return (this.getHomeTeam() == null ? other.getHomeTeam() == null : this.getHomeTeam().equals(other.getHomeTeam()))
                && this.getHomeTeamScore() == other.getHomeTeamScore()
                && (this.getAwayTeam() == null ? other.getAwayTeam() == null : this.getAwayTeam().equals(other.getAwayTeam()))
                && this.getAwayTeamScore() == other.getAwayTeamScore();
    }

    /**
     * Create unique hash
     *
     * @return int hash that represents the object
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.homeTeam);
        hash = 11 * hash + this.homeTeamScore;
        hash = 11 * hash + Objects.hashCode(this.awayTeam);
        hash = 11 * hash + this.awayTeamScore;
        return hash;
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Get the value of homeTeam
     *
     * @return the value of homeTeam
     */
    public String getHomeTeam() {
        return homeTeam;
    }

    /**
     * Set the value of homeTeam
     *
     * @param homeTeam new value of homeTeam
     */
    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    /**
     * Get the value of homeTeamScore
     *
     * @return the value of homeTeamScore
     */
    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    /**
     * Set the value of homeTeamScore
     *
     * @param homeTeamScore new value of homeTeamScore
     */
    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    /**
     * Get the value of awayTeam
     *
     * @return the value of awayTeam
     */
    public String getAwayTeam() {
        return awayTeam;
    }

    /**
     * Set the value of awayTeam
     *
     * @param awayTeam new value of awayTeam
     */
    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    /**
     * Get the value of awayTeamScore
     *
     * @return the value of awayTeamScore
     */
    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    /**
     * Set the value of awayTeamScore
     *
     * @param awayTeamScore new value of awayTeamScore
     */
    public void setAwayTeamScore(int awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    /**
     * Get the value of startDate
     *
     * @return the value of startDate
     */
    public Instant getStartDate() {
        return startDate;
    }
}
