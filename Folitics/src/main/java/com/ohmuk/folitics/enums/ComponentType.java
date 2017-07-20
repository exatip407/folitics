package com.ohmuk.folitics.enums;

public enum ComponentType {

    SENTIMENT("Sentiment"), OPINION("Opinion"), RESPONSE("Response"), LINK("Link"),  GLOBALVERDICT(
            "GlobalVerdict"),IMAGE("Image"),VIDEO("Video"),GRAPH("Graph"), LOCALVERDICT("LocalVerdict"),BUMPER("Bumper"),MEGA("Mega"),MINI("Mini"),
    GOVTSCHEMEDATA("GovtSchemeData"),LOCALINDICATORGRAPH("LocalIndicatorGraph"),COMPARISIONGRAPH("ComparisionGraph"),
    GLOBALVERDICTREPORT("GlobalVerdictReport"),LOCALVERDICTREPORT("LocalVerdictReport"),CHART("Chart");

    private String value;

    private ComponentType(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final ComponentType getComponentType(String value) {
        if (SENTIMENT.getValue().equals(value)) {
            return SENTIMENT;
        }
        if (OPINION.getValue().equals(value)) {
            return OPINION;
        }
        if (RESPONSE.getValue().equals(value)) {
            return RESPONSE;
        }
        if (LINK.getValue().equals(value)) {
            return LINK;
        }
        if (GRAPH.getValue().equals(value)) {
            return GRAPH;
        }
       
        if (GLOBALVERDICT.getValue().equals(value)) {
            return GLOBALVERDICT;
        }
        if (LOCALVERDICT.getValue().equals(value)) {
            return LOCALVERDICT;
        }
        if (BUMPER.getValue().equals(value)) {
            return BUMPER;
        }
        if (IMAGE.getValue().equals(value)) {
            return IMAGE;
        }
        if (VIDEO.getValue().equals(value)) {
            return VIDEO;
        }
        if (MEGA.getValue().equals(value)) {
            return MEGA;
        }
        if (MINI.getValue().equals(value)) {
            return MINI;
        }
        if (GOVTSCHEMEDATA.getValue().equals(value)) {
            return GOVTSCHEMEDATA;
        }
        if (LOCALINDICATORGRAPH.getValue().equals(value)) {
            return LOCALINDICATORGRAPH;
        }
        if (COMPARISIONGRAPH.getValue().equals(value)) {
            return COMPARISIONGRAPH;
        }
        if (GLOBALVERDICTREPORT.getValue().equals(value)) {
            return GLOBALVERDICTREPORT;
        }
        if (LOCALVERDICTREPORT.getValue().equals(value)) {
            return LOCALVERDICTREPORT;
        }
        if (CHART.getValue().equals(value)) {
            return CHART;
        }

        return null;
    }
}
