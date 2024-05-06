package com.sportradar.dev.test;

import com.sportradar.dev.match.Match;
import com.sportradar.dev.scoreboard.ScoreBoard;
import com.sportradar.dev.scoreboard.impl.ScoreBoardImpl;
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
    public void setUp() {
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
        scoreboard.startMatch("Germany", "France");
        scoreboard.startMatch("Costa Rica", "Saudi Arabia");
        assertEquals(2, scoreboard.getMatches().size());
    }

    @Test
    @DisplayName("Test match not started when home team or away team is already playing")
    void testStartMatch_Fails_When_HomeTeam_Or_AwayTeam_Is_Already_Playing() throws Exception {
        scoreboard.startMatch("Germany", "France");
        assertEquals(-1, scoreboard.startMatch("Germany", "Costa Rica"));
        assertEquals(-1, scoreboard.startMatch("France", "USA"));
        assertEquals(-1, scoreboard.startMatch("Germany", "France"));
        assertEquals(1, scoreboard.getMatches().size());
    }

    @Test
    @DisplayName("Test match not started when home team or away team name is not valid")
    void testStartMatch_Fails_When_HomeTeam_Or_AwayTeam_Name_Is_Invalid() throws Exception {
        assertEquals(-1, scoreboard.startMatch("Germany", null));
        assertEquals(-1, scoreboard.startMatch("", "Italy"));
        assertEquals(-1, scoreboard.startMatch("Germany", "ygrfbej"));
        assertEquals(-1, scoreboard.startMatch("ABVGAVGVA", "ehrifhr"));
        assertEquals(0, scoreboard.getMatches().size());
    }

    @Test
    void testUpdateScore_OK() throws Exception {
        int matchNumber = scoreboard.startMatch("Germany", "France");
        scoreboard.updateScore(matchNumber, 3, 1);
        Match match = (Match) scoreboard.getMatches().getFirst();
        assertEquals(3, match.getHomeTeamScore());
        assertEquals(1, match.getAwayTeamScore());
    }

    @Test
    void testFinishMatch_OK() throws Exception {
        int matchNumber = scoreboard.startMatch("Germany", "France");
        assertEquals(1, scoreboard.getMatches().size());
        scoreboard.finishMatch(matchNumber);
        assertEquals(0, scoreboard.getMatches().size());
        //Testing via assertion that you can start a match again with the same teams.
        matchNumber = scoreboard.startMatch("Germany", "France");
        assertEquals(1, scoreboard.getMatches().size());
    }

    @Test
    void testFinishMatch_MatchNotFound() throws Exception {
        int matchNumber = scoreboard.startMatch("Germany", "France");
        assertEquals(1, scoreboard.getMatches().size());
        assertThrows(IllegalArgumentException.class, () -> scoreboard.finishMatch(-1));
        assertEquals(1, scoreboard.getMatches().size());
        //Testing via assertion that you cannot start a match again with the same teams, if not deleted.
        assertEquals(-1, scoreboard.startMatch("Germany", "France"));
    }

    @Test
    void testGetScoreBoardSummary() throws Exception {
        assertThrows(UnsupportedOperationException.class, () -> scoreboard.getScoreBoardSummary());
    }

}
