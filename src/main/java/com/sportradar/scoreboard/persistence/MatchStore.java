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
package com.sportradar.scoreboard.persistence;

import com.sportradar.scoreboard.exceptions.NotFoundException;
import com.sportradar.scoreboard.entity.Match;
import java.util.List;

/**
 * Interface that defines the basic CRUD operations that hold the store
 * @author enrodpre
 */
public interface MatchStore {
    public int create(String homeTeam, String awayTeam);
    public Match read(int id) throws NotFoundException;
    public List<Match> read();
    public void update(int id, int homeTeamScore, int awayTeamScore) throws NotFoundException;
    public void delete(int id) throws NotFoundException;
}
