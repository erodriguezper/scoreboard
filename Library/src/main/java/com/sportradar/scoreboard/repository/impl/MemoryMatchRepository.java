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
package com.sportradar.scoreboard.repository.impl;

import com.sportradar.scoreboard.comparators.OrderComparators;
import com.sportradar.scoreboard.exceptions.NotFoundException;
import com.sportradar.scoreboard.entity.Match;
import com.sportradar.scoreboard.factory.MatchFactory;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import com.sportradar.scoreboard.repository.MatchRepository;
import java.util.Comparator;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

/**
 *
 * @author enrodpre
 */
@Repository
public class MemoryMatchRepository implements MatchRepository {

    private final List<Match> store;

    public MemoryMatchRepository() {
        // Implicit generic type
        this.store = new ArrayList<>();
    }

    private int idCounter = 0;

    @Override
    public int create(String homeTeam, String awayTeam) {
        // Create the object calling the factory
        Match newMatch = MatchFactory.createMatch(
                idCounter++,
                homeTeam,
                awayTeam
        );

        // Add it to the store
        store.add(newMatch);

        // Return the id
        return newMatch.getId();
    }

    @Override
    public Match read(int id) throws NotFoundException {
        // Take the matches we have stored
        return store.stream()
                // Search for the one that has the id
                .filter((Match m) -> m.getId() == id)
                // If there is any
                .findAny()
                // Clone it so it may not be modified
                .map(MatchFactory::cloneMatch) 
                // Else throw an exception
                .orElseThrow(exceptionSupplier("Id %d not found when reading", id));
    }

    @Override
    public List<Match> list() {
        // Take the matches we have stored
        return store.stream()
                // Clone them so they may not be modified
                .map(MatchFactory::cloneMatch)
                // As a list
                .collect(Collectors.toList());
    }

    @Override
    public void update(int id, int homeTeamScore, int awayTeamScore) throws NotFoundException {
        // Take the matches we have stored
        Match toUpdate = store.stream()
                // Look for the one that has the id
                .filter(m -> m.getId() == id)
                // If there is any, return it
                .findAny()
                // Else throw an exception
                .orElseThrow(exceptionSupplier("Id %d not found when updating", id));

        // Update the values
        toUpdate.setHomeTeamScore(homeTeamScore);
        toUpdate.setAwayTeamScore(awayTeamScore);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        // Remove the one that has the id
        boolean removed = store.removeIf(m -> m.getId() == id);

        // If there was not 
        Optional.of(removed)
                .filter(Boolean::booleanValue)
                // Throw an exception
                .orElseThrow(exceptionSupplier("Id %d not found when deleting", id));

    }

    @Override
    public List<Match> orderedList(OrderComparators... orderTypes) {
        Stream<Match> matchList = list().stream();
        
        // Convert the array into a list
        return List.of(orderTypes)
                // Make a parallel stream so we can ensure order when applying reduce operation
                .parallelStream()
                // Extract the comparator of each OrderComparator
                .map(OrderComparators::getComparator)
                // Reduce the list applying them in order
                .reduce(Comparator::thenComparing)
                // Apply the sorted function of the list to be ordered
                .map(matchList::sorted)
                // If there is not any comparator return the list unaltered
                .orElse(matchList)
                // As a list
                .collect(Collectors.toList());

    }

    private Supplier<NotFoundException> exceptionSupplier(String msg, int id) {
        // Supplier that throws a NotFoundException with the given msg and the id
        return () -> new NotFoundException(String.format(msg, id));
    }

}
