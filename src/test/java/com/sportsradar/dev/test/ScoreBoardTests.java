package com.sportsradar.dev.test;

import com.sportsradar.dev.scoreboard.ScoreBoard;
import com.sportsradar.dev.scoreboard.impl.ScoreBoardImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @DisplayName("Test if the test framework is working")
    void testSetupOK() {
        assertTrue(true);
    }

    @Test
    void testAddMatch() throws Exception {
        assertThrows(UnsupportedOperationException.class, () -> scoreboard.addMatch("Team A", "Team B"));
    }

    @Test
    void testUpdateScore() throws Exception {
        assertThrows(UnsupportedOperationException.class, () -> scoreboard.updateScore(1, 3, 1));
    }

    @Test
    void testDeleteMatch() throws Exception {
        assertThrows(UnsupportedOperationException.class, () -> scoreboard.deleteMatch(1));
    }

    @Test
    void testGetScoreBoardSummary() throws Exception {
        assertThrows(UnsupportedOperationException.class, () -> scoreboard.getScoreBoardSummary());
    }
}
