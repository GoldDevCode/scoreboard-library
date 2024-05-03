package com.sportsradar.dev.team;

public enum Teams {
    /*
    QATAR,
    ECUADOR,
    SENEGAL,
    NETHERLANDS,
    ENGLAND,
    IRAN,
    USA,
    WALES,
    ARGENTINA,
    SAUDI_ARABIA,
    MEXICO,
    POLAND,
    FRANCE,
    AUSTRALIA,
    DENMARK,
    TUNISIA,
    SPAIN,
    COSTA_RICA,
    GERMANY,
    JAPAN,
    BELGIUM,
    CANADA,
    MOROCCO,
    CROATIA,
    BRAZIL,
    SERBIA,
    SWITZERLAND,
    CAMEROON,
    PORTUGAL,
    GHANA,
    URUGUAY,
    KOREA_REPUBLIC;

     */
    QATAR("Qatar"),
    ECUADOR("Ecuador"),
    SENEGAL("Senegal"),
    NETHERLANDS("Netherlands"),
    ENGLAND("England"),
    IRAN("Iran"),
    USA("USA"),
    WALES("Wales"),
    ARGENTINA("Argentina"),
    SAUDI_ARABIA("Saudi Arabia"),
    MEXICO("Mexico"),
    POLAND("Poland"),
    FRANCE("France"),
    AUSTRALIA("Australia"),
    DENMARK("Denmark"),
    TUNISIA("Tunisia"),
    SPAIN("Spain"),
    COSTA_RICA("Costa Rica"),
    GERMANY("Germany"),
    JAPAN("Japan"),
    BELGIUM("Belgium"),
    CANADA("Canada"),
    MOROCCO("Morocco"),
    CROATIA("Croatia"),
    BRAZIL("Brazil"),
    SERBIA("Serbia"),
    SWITZERLAND("Switzerland"),
    CAMEROON("Cameroon"),
    PORTUGAL("Portugal"),
    GHANA("Ghana"),
    URUGUAY("Uruguay"),
    KOREA_REPUBLIC("Korea Republic");

    private final String label;

    Teams(String label) {
        this.label = label;
    }

    public static boolean isValidTeam(String teamName) {
        for (Teams team : Teams.values()) {
            if (team.getLabel().equalsIgnoreCase(teamName)) {
                return true;
            }
        }
        return false;
    }

    public String getLabel() {
        return label;
    }
}
