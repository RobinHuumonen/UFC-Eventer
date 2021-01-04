package com.example.ufceventer;

public class FightListItem {
    private String fighter1Name;
    private String fighter1Record;
    private String  fighter1Pic;
    private String fighter2Name;
    private String fighter2Record;
    private String fighter2Pic;
    private boolean mainEvent;
    private int dbId;
    private int outcome;

    public FightListItem(String fighter1Name, String fighter1Record, String fighter1Pic, String fighter2Name, String fighter2Record, String fighter2Pic, boolean mainEvent, int dbId, int outcome) {
        this.fighter1Name = fighter1Name;
        this.fighter1Record = fighter1Record;
        this.fighter1Pic = fighter1Pic;
        this.fighter2Name = fighter2Name;
        this.fighter2Record = fighter2Record;
        this.fighter2Pic = fighter2Pic;
        this.mainEvent = mainEvent;
        this.dbId = dbId;
        this.outcome = outcome;
    }

    public String getFighter1Name() {
        return fighter1Name;
    }

    public String getFighter1Record() {
        return fighter1Record;
    }

    public String getFighter1Pic() {
        return fighter1Pic;
    }

    public String getFighter2Name() {
        return fighter2Name;
    }

    public String getFighter2Record() {
        return fighter2Record;
    }

    public String getFighter2Pic() {
        return fighter2Pic;
    }

    public boolean isMainEvent() {
        return mainEvent;
    }

    public int getDbId() {
        return dbId;
    }

    public int getOutcome() {
        return outcome;
    }
}
