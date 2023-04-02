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
package com.sportradar.scoreboard.persistence.impl;

import com.sportradar.scoreboard.exceptions.NotFoundException;
import com.sportradar.scoreboard.entity.Match;
import com.sportradar.scoreboard.factory.MatchFactory;
import com.sportradar.scoreboard.persistence.MatchStore;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *
 * @author enrodpre
 */
public class MemoryMatchStore implements MatchStore {

    private final List<Match> store;

    public MemoryMatchStore() {
        // Implicit generic type
        this.store = new ArrayList<>();
    }

    private int idCounter = 0;

    @Override
    public int create(String homeTeam, String awayTeam) {
        Match newMatch = MatchFactory.createMatch(
                idCounter++,
                homeTeam,
                awayTeam
        );

        store.add(newMatch);

        return newMatch.getId();
    }

    @Override
    public Match read(int id) throws NotFoundException {
        return store.stream()
                .filter((Match m) -> m.getId() == id)
                .findAny()
                .map(MatchFactory::cloneMatch) // It is important to not return the actual object so it may not be modified
                .orElseThrow(exceptionSupplier("Id %d not found when reading", id));
    }

    @Override
    public List<Match> read() {
        return store.stream()
                .map(MatchFactory::cloneMatch) // It is important to not return the actual object so it may not be modified
                .collect(Collectors.toList());
    }

    @Override
    public void update(int id, int homeTeamScore, int awayTeamScore) throws NotFoundException {
        Match toUpdate = store.stream()
                .filter(m -> m.getId() == id)
                .findAny()
                .orElseThrow(exceptionSupplier("Id %d not found when updating", id));
        
        toUpdate.setHomeTeamScore(homeTeamScore);
        toUpdate.setAwayTeamScore(awayTeamScore);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        boolean removed = store.removeIf(m -> m.getId() == id);

        Optional.of(removed)
                .filter(Boolean::booleanValue)
                .orElseThrow(exceptionSupplier("Id %d not found when deleting", id));

    }

    private Supplier<NotFoundException> exceptionSupplier(String msg, int id) {
        return () -> new NotFoundException(String.format(msg, id));
    }
}
