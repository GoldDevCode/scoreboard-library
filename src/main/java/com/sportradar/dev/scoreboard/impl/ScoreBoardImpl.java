package com.sportradar.dev.scoreboard.impl;

import com.sportradar.dev.match.Match;
import com.sportradar.dev.scoreboard.ScoreBoard;
import com.sportradar.dev.team.Teams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ScoreBoardImpl implements ScoreBoard {

    private static final Logger logger = Logger.getLogger(ScoreBoardImpl.class.getName());
    private Map<Integer, Match> matches = new HashMap<>();
    private Map<String, Integer> teamToMatchNumber = new HashMap<>();

    /**
     * @param homeTeam
     * @param awayTeam
     * @return
     * @throws Exception
     */
    @Override
    public Integer startMatch(String homeTeam, String awayTeam) throws Exception {
        try {
            if (!Teams.isValidTeam(homeTeam) || !Teams.isValidTeam(awayTeam)) {
                throw new IllegalArgumentException("Invalid team name. " +
                        "Home team name and/or Away team name is not a valid participating team." +
                        "HomeTeam=" + homeTeam + ", AwayTeam=" + awayTeam);
            }

            if (teamToMatchNumber.containsKey(homeTeam) || teamToMatchNumber.containsKey(awayTeam)) {
                throw new IllegalStateException("One or both of the teams already playing a match." +
                        "Cannot start a new match." +
                        "HomeTeam=" + homeTeam + ", AwayTeam=" + awayTeam);
            }

            Match match = new Match(homeTeam, awayTeam);
            final int matchNumber = match.getId();

            teamToMatchNumber.put(homeTeam, matchNumber);
            teamToMatchNumber.put(awayTeam, matchNumber);
            matches.put(matchNumber, match);
            return matchNumber;
        } catch (IllegalArgumentException | IllegalStateException e) {
            logger.severe(e.getMessage());
        }
        return -1;
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
        Match match = matches.get(matchNumber);
        if (match == null) {
            logger.severe("Match not found for match number=" + matchNumber);
            throw new IllegalArgumentException("Match not found for match number=" + matchNumber);
        } else {
            matches.remove(matchNumber);
            teamToMatchNumber.remove(match.getHomeTeam());
            teamToMatchNumber.remove(match.getAwayTeam());
        }
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
