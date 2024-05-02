package com.sportsradar.dev.scoreboard;

import java.util.List;

public interface ScoreBoard {
    Integer addMatch(String homeTeam, String awayTeam) throws Exception;

    void updateScore(Integer matchNumber, int homeTeamScore, int awayTeamScore) throws Exception;

    void deleteMatch(Integer matchNumber) throws Exception;

    List<?> getScoreBoardSummary() throws Exception;
}
