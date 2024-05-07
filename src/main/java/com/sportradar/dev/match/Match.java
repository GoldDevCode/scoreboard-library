package com.sportradar.dev.match;

import java.util.concurrent.atomic.AtomicInteger;

public final class Match {
    private static final AtomicInteger idGenerator = new AtomicInteger(0);
    private final int id;
    private final String homeTeam;
    private final String awayTeam;
    private final AtomicInteger homeTeamScore = new AtomicInteger(0);
    private final AtomicInteger awayTeamScore = new AtomicInteger(0);

    public Match(String homeTeam, String awayTeam) {
        this.id = idGenerator.incrementAndGet();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public int getId() {
        return id;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeTeamScore() {
        return homeTeamScore.get();
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore.set(homeTeamScore);
    }

    public int getAwayTeamScore() {
        return awayTeamScore.get();
    }

    public void setAwayTeamScore(int awayTeamScore) {
        this.awayTeamScore.set(awayTeamScore);
    }

    public int getTotalScore() {
        return homeTeamScore.get() + awayTeamScore.get();
    }

    @Override
    public String toString() {
        return homeTeam + " " + homeTeamScore.get() +
                " - " +
                awayTeam + " " + awayTeamScore.get();
    }
}
