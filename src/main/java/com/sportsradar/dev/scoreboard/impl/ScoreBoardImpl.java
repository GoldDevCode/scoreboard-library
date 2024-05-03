package com.sportsradar.dev.scoreboard.impl;

import com.sportsradar.dev.match.Match;
import com.sportsradar.dev.scoreboard.ScoreBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreBoardImpl implements ScoreBoard {

    private Map<Integer, Match> matches = new HashMap<>();
    /**
     * @param homeTeam
     * @param awayTeam
     * @return
     * @throws Exception
     */
    @Override
    public Integer startMatch(String homeTeam, String awayTeam) throws Exception {
        Match match = new Match(homeTeam, awayTeam);
        final int matchNumber = match.getId();
        matches.put(matchNumber, match);
        return matchNumber;
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
    public void finishMatch(Integer matchNumber) throws Exception {
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

    /**
     * @return
     * @throws Exception
     */
    @Override
    public List<?> getMatches() throws Exception {
        return new ArrayList<>(matches.values());
    }

}
