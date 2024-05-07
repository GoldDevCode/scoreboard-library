package com.sportradar.dev.test;

import com.sportradar.dev.match.Match;
import com.sportradar.dev.scoreboard.ScoreBoard;
import com.sportradar.dev.scoreboard.impl.ScoreBoardImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ScoreBoard Tests")
public class ScoreBoardTests {
    private static final Logger logger = Logger.getLogger(ScoreBoardTests.class.getName());
    private ScoreBoard scoreboard;

    @BeforeEach
    public void setUp() {
        scoreboard = new ScoreBoardImpl();
    }

    /**
     * Initial test to check if the test framework is setup correctly and working
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
        assertEquals(-1, scoreboard.startMatch("USA", "USA"));
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
    void testUpdateScore_Fail_When_Negative_Score_Value() throws Exception {
        int matchNumber = scoreboard.startMatch("Germany", "France");
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore(matchNumber, 3, -1));
        Match match = (Match) scoreboard.getMatches().getFirst();
        assertEquals(0, match.getHomeTeamScore());
        assertEquals(0, match.getAwayTeamScore());
    }

    @Test
    void testUpdateScore_Fail_When_Score_Value_Less_than_Current_Score() throws Exception {
        int matchNumber = scoreboard.startMatch("Germany", "France");
        scoreboard.updateScore(matchNumber, 3, 2);
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore(matchNumber, 2, 4));
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore(matchNumber, 1, 1));
        Match match = (Match) scoreboard.getMatches().getFirst();
        assertEquals(3, match.getHomeTeamScore());
        assertEquals(2, match.getAwayTeamScore());
    }

    @Test
    void testUpdateScore_Fail_When_Both_Score_Value_Equal_To_Current_Score() throws Exception {
        int matchNumber = scoreboard.startMatch("Germany", "France");
        scoreboard.updateScore(matchNumber, 3, 2);
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore(matchNumber, 3, 2));
        Match match = (Match) scoreboard.getMatches().getFirst();
        assertEquals(3, match.getHomeTeamScore());
        assertEquals(2, match.getAwayTeamScore());
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
    void testGetScoreBoardSummary_OK() throws Exception {
        int match1 = scoreboard.startMatch("Germany", "France");
        int match2 = scoreboard.startMatch("Costa Rica", "Saudi Arabia");
        int match3 = scoreboard.startMatch("Japan", "Spain");
        int match4 = scoreboard.startMatch("Ghana", "USA");

        scoreboard.updateScore(match1, 3, 2);
        scoreboard.updateScore(match2, 1, 1);
        scoreboard.updateScore(match3, 2, 3);
        scoreboard.updateScore(match4, 1, 0);

        assertEquals(4, scoreboard.getScoreBoardSummary().size());
        assertEquals("Japan 2 - Spain 3", scoreboard.getScoreBoardSummary().getFirst());
        assertEquals("Germany 3 - France 2", scoreboard.getScoreBoardSummary().get(1));
        assertEquals("Costa Rica 1 - Saudi Arabia 1", scoreboard.getScoreBoardSummary().get(2));
        assertEquals("Ghana 1 - USA 0", scoreboard.getScoreBoardSummary().getLast());
    }

    @Test
    void testStartMatch_Race_Condition_With_Multiple_Threads_OK() throws Exception {
        Thread thread1 = new Thread(() -> {
            try {
                scoreboard.startMatch("Germany", "France");
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                scoreboard.startMatch("France", "Saudi Arabia");
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals(1, scoreboard.getMatches().size());
    }

    @Test
    void testScoreUpdate_In_Case_Of_Multiple_Threads_OK() throws Exception {
        int matchNumber = scoreboard.startMatch("Germany", "France");
        Thread thread1 = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    scoreboard.updateScore(matchNumber, i, i);
                }
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                for (int i = 6; i <= 10; i++) {
                    scoreboard.updateScore(matchNumber, i, i);
                }
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        int expectedScore = 10;
        Match match = scoreboard.getMatches().getFirst();
        assertTrue(match.getHomeTeamScore() == expectedScore && match.getAwayTeamScore() == expectedScore, "Scores are correct and did not experience race conditions.");
        assertEquals("Germany 10 - France 10", scoreboard.getScoreBoardSummary().getFirst());
    }

    @Test
    void testIncorrectScoreUpdate_In_Case_Of_Multiple_Threads_With_Sleep_OK() throws Exception {
        int matchNumber = scoreboard.startMatch("Germany", "France");
        Thread thread1 = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    scoreboard.updateScore(matchNumber, i, i);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                for (int i = 6; i <= 10; i++) {
                    scoreboard.updateScore(matchNumber, i, i);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        int expectedScore = 10;
        Match match = scoreboard.getMatches().getFirst();
        assertTrue(match.getHomeTeamScore() == expectedScore && match.getAwayTeamScore() == expectedScore, "Scores are correct and did not experience race conditions.");
    }

    @Test
    void testMultipleConcurrentOperations_OK() throws Exception {
        int matchNumber1 = scoreboard.startMatch("Germany", "France");
        int matchNumber2 = scoreboard.startMatch("Costa Rica", "Saudi Arabia");
        Thread thread1 = new Thread(() -> {
            try {
                scoreboard.startMatch("Germany", "France");
                scoreboard.updateScore(matchNumber1, 3, 2);
                scoreboard.finishMatch(matchNumber1);
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                scoreboard.startMatch("Costa Rica", "Saudi Arabia");
                scoreboard.updateScore(matchNumber2, 1, 1);
                scoreboard.finishMatch(matchNumber2);
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals(0, scoreboard.getMatches().size());
    }
}
