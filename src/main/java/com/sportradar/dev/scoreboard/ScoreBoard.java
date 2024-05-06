package com.sportradar.dev.scoreboard;

import com.sportradar.dev.match.Match;

import java.util.List;

public interface ScoreBoard {

    Integer startMatch(String homeTeam, String awayTeam) throws Exception;

    void updateScore(Integer matchNumber, int homeTeamScore, int awayTeamScore) throws Exception;

    void finishMatch(Integer matchNumber) throws Exception;

    List<String> getScoreBoardSummary() throws Exception;

    List<Match> getMatches() throws Exception;
}
