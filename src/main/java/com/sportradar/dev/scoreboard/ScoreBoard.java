package com.sportradar.dev.scoreboard;

import java.util.List;

public interface ScoreBoard {
    Integer startMatch(String homeTeam, String awayTeam) throws Exception;

    void updateScore(Integer matchNumber, int homeTeamScore, int awayTeamScore) throws Exception;

    void finishMatch(Integer matchNumber) throws Exception;

    List<?> getScoreBoardSummary() throws Exception;

    List<?> getMatches() throws Exception;
}
