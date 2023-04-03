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
package com.sportradar.scoreboard.factory;

import com.sportradar.scoreboard.factory.MatchFactory;
import com.sportradar.scoreboard.entity.Match;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author enrodpre
 */
public class MatchFactoryTest {
    
    private static final int ID = 12345;
    private static final String HOME_TEAM_NAME = "Australia";
    private static final int HOME_TEAM_SCORE = 32;
    private static final String AWAY_TEAM_NAME  = "Canada";
    private static final int AWAY_TEAM_SCORE = 21;   
    
    @Test
    public void testCreate(){
        Match match = new Match(ID, HOME_TEAM_NAME, AWAY_TEAM_NAME, null);
        assertEquals(match, MatchFactory.createMatch(ID, HOME_TEAM_NAME, AWAY_TEAM_NAME));
    }
    
    @Test
    public void testCreateWithScore(){
        Match match = new Match(ID, HOME_TEAM_NAME, HOME_TEAM_SCORE, AWAY_TEAM_NAME, AWAY_TEAM_SCORE, null);
        assertEquals(match, MatchFactory.createMatch(ID, HOME_TEAM_NAME, HOME_TEAM_SCORE, AWAY_TEAM_NAME, AWAY_TEAM_SCORE));
    }
    
    @Test
    public void testClone(){
        Match match = new Match(ID, HOME_TEAM_NAME, HOME_TEAM_SCORE, AWAY_TEAM_NAME, AWAY_TEAM_SCORE, null);
        assertEquals(match, MatchFactory.cloneMatch(match));
    }
}
