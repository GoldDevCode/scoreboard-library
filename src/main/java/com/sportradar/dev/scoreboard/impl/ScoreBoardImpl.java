package com.sportradar.dev.scoreboard.impl;

import com.sportradar.dev.match.Match;
import com.sportradar.dev.scoreboard.ScoreBoard;
import com.sportradar.dev.team.Teams;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public final class ScoreBoardImpl implements ScoreBoard {

    private static final Logger logger = Logger.getLogger(ScoreBoardImpl.class.getName());
    private final AtomicInteger nextMatchId = new AtomicInteger(0);
    private final Map<Integer, Match> matches = new ConcurrentHashMap<>();
    private final Map<String, Integer> teamToMatchNumber = new ConcurrentHashMap<>();

    /**
     * @param homeTeam
     * @param awayTeam
     * @return matchNumber
     * @throws Exception
     * This method starts a new match between two teams, accepting the names of the home team and the away team.
     */
    @Override
    public Integer startMatch(String homeTeam, String awayTeam) throws Exception {
        try {
            if (!Teams.isValidTeam(homeTeam) || !Teams.isValidTeam(awayTeam)) {
                throw new IllegalArgumentException("Invalid team name. " +
                        "Home team name and/or Away team name is not a valid participating team." +
                        "HomeTeam=" + homeTeam + ", AwayTeam=" + awayTeam);
            }

            if (homeTeam.equals(awayTeam)) {
                throw new IllegalArgumentException("Duplicate team names. " +
                        "Home team name and Away team name are same" +
                        "HomeTeam=" + homeTeam + ", AwayTeam=" + awayTeam);
            }

            final int matchId = nextMatchId.incrementAndGet();
            boolean homeTeamAdded = teamToMatchNumber.computeIfAbsent(homeTeam, k -> matchId) == matchId;
            boolean awayTeamAdded = teamToMatchNumber.computeIfAbsent(awayTeam, k -> matchId) == matchId;

            if (!homeTeamAdded) {
                throw new IllegalStateException("Home team already playing a match." +
                        "Cannot start a new match." +
                        "HomeTeam=" + homeTeam + ", AwayTeam=" + awayTeam);
            }

            if (!awayTeamAdded) {
                teamToMatchNumber.remove(homeTeam);
                throw new IllegalStateException("Away team already playing a match." +
                        "Cannot start a new match." +
                        "HomeTeam=" + homeTeam + ", AwayTeam=" + awayTeam);
            }

            Match match = new Match(homeTeam, awayTeam);
            final int matchNumber = match.getId();
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
     * This method updates the score of a match, accepting the match number, home team score, and away team score.
     * Also validates the input parameters.
     */
    @Override
    public void updateScore(Integer matchNumber, int homeTeamScore, int awayTeamScore) throws Exception {
        Match match = matches.get(matchNumber);
        if (match == null) {
            logger.severe("Match not found for match number=" + matchNumber);
            throw new IllegalArgumentException("Match not found for match number=" + matchNumber);
        } else if (homeTeamScore < 0 || awayTeamScore < 0) {
            logger.severe("Invalid score values for update. " +
                    "Home team score and/or Away team score is negative. Received " +
                    "HomeTeamScore=" + homeTeamScore + ", AwayTeamScore=" + awayTeamScore);
            throw new IllegalArgumentException("Invalid score values for update. " +
                    "Home team score and/or Away team score is negative." +
                    "HomeTeamScore=" + homeTeamScore + ", AwayTeamScore=" + awayTeamScore);
        } else if (homeTeamScore < match.getHomeTeamScore() || awayTeamScore < match.getAwayTeamScore()) {
            logger.severe("Invalid score values for update. " +
                    "Home team score and/or Away team score is less than current score." +
                    "Current Scores HomeTeamScore=" + match.getHomeTeamScore() + ", AwayTeamScore=" + match.getAwayTeamScore() +
                    "Received HomeTeamScore=" + homeTeamScore + ", AwayTeamScore=" + awayTeamScore);
            throw new IllegalArgumentException("Invalid score values for update. " +
                    "Home team score and/or Away team score is less than current score." +
                    "HomeTeamScore=" + homeTeamScore + ", AwayTeamScore=" + awayTeamScore);
        } else if (homeTeamScore == match.getHomeTeamScore() && awayTeamScore == match.getAwayTeamScore()) {
            logger.severe("No change in score values. " +
                    "Home team score and Away team score are same as current score." +
                    "Current score=" + match.getHomeTeamScore() + " - " + match.getAwayTeamScore() + ", " +
                    "HomeTeamScore=" + homeTeamScore + ", AwayTeamScore=" + awayTeamScore);
            throw new IllegalArgumentException("No change in score values. " + "Home team score and Away team score are same as current score." +
                    "HomeTeamScore=" + homeTeamScore + ", AwayTeamScore=" + awayTeamScore);
        } else {
            match.setHomeTeamScore(homeTeamScore);
            match.setAwayTeamScore(awayTeamScore);
        }
    }

    /**
     * @param matchNumber
     * @throws Exception
     * This method finishes a match, accepting the match number.
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
     * This method returns the summary of the scoreboard.
     * In order to get the summary, the matches are sorted based on the total score.
     * If the total score is the same, the most recently started match is placed first.
     */
    @Override
    public List<String> getScoreBoardSummary() throws Exception {
        final List<String> matchSummary = matches.values()
                .stream()
                .sorted((m1, m2) -> {
                    if (m1.getTotalScore() == m2.getTotalScore()) {
                        return Long.compare(m2.getId(), m1.getId());
                    }
                    return Integer.compare(m2.getTotalScore(), m1.getTotalScore());
                }).map(Match::toString).toList();

        return matchSummary;
    }

    /**
     * @return List<Match>
     * @throws Exception
     */
    @Override
    public List<Match> getMatches() throws Exception {
        final List<Match> matchList = Collections.unmodifiableList(matches.values().stream().toList());
        return matchList;
    }
}
