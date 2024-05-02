package com.sportsradar.dev.scoreboard.impl;

import com.sportsradar.dev.scoreboard.ScoreBoard;

import java.util.List;

public class ScoreBoardImpl implements ScoreBoard {
    /**
     * @param homeTeam
     * @param awayTeam
     * @return
     * @throws Exception
     */
    @Override
    public Integer addMatch(String homeTeam, String awayTeam) throws Exception {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * @param matchNumber
     * @param homeTeamScore
     * @param awayTeamScore
     * @throws Exception
     */
    @Override
    public void updateScore(Integer matchNumber, int homeTeamScore, int awayTeamScore) throws Exception {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * @param matchNumber
     * @throws Exception
     */
    @Override
    public void deleteMatch(Integer matchNumber) throws Exception {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public List<?> getScoreBoardSummary() throws Exception {
        throw new UnsupportedOperationException("Not implemented yet");
    }


}
