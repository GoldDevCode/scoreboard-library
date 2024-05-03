package com.sportsradar.dev.test;

import com.sportsradar.dev.scoreboard.ScoreBoard;
import com.sportsradar.dev.scoreboard.impl.ScoreBoardImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class ScoreBoardTests {

    private ScoreBoard scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = new ScoreBoardImpl();
    }

    /**
     * Initial test to check if the test framework is working
     */
    @Test
    @DisplayName("Check if the test framework is working")
    void testSetupOK() {
        assertTrue(true);
    }

    @Test
    @DisplayName("Test if the match is started successfully")
    void testStartMatch_OK() throws Exception {
        assertEquals(1, scoreboard.startMatch("Germany", "France"));
        assertEquals(2, scoreboard.startMatch("France", "Saudi Arabia"));
        assertEquals(2, scoreboard.getMatches().size());
    }

    @Test
    @DisplayName("Test match not started when home team or away team is already playing")
    void testStartMatch_Fails_When_HomeTeam_Or_AwayTeam_Is_Already_Playing() throws Exception {
        assertEquals(1, scoreboard.startMatch("Germany", "France"));
        assertThrows(IllegalStateException.class, () -> scoreboard.startMatch("France", "Italy"));
    }

    @Test
    @DisplayName("Test match not started when home team or away team name is not valid")
    void testStartMatch_Fails_When_HomeTeam_Or_AwayTeam_Name_Is_Invalid() throws Exception {
        assertEquals(-1, scoreboard.startMatch("Germany", null));
        assertEquals(-1, scoreboard.startMatch("", "Italy"));
        assertEquals(-1, scoreboard.startMatch("Germany", "ygrfbej"));
        assertEquals(-1, scoreboard.startMatch("ABVGAVGVA", "ehrifhr"));
    }

    @Test
    void testUpdateScore() throws Exception {
        assertThrows(UnsupportedOperationException.class, () -> scoreboard.updateScore(1, 3, 1));
    }

    @Test
    void testDeleteMatch() throws Exception {
        assertThrows(UnsupportedOperationException.class, () -> scoreboard.finishMatch(1));
    }

    @Test
    void testGetScoreBoardSummary() throws Exception {
        assertThrows(UnsupportedOperationException.class, () -> scoreboard.getScoreBoardSummary());
    }
}
