package com.example.ufceventer.models;

import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
@Entity(tableName = "pickAndScores")
public class PickAndScore {
    @PrimaryKey (autoGenerate = true)
    public int id;

    private String fighter1Name;
    private String fighter1Record;
    private String  fighter1Pic;
    private String fighter2Name;
    private String fighter2Record;
    private String fighter2Pic;
    private boolean mainEvent;

    private int f1R1, f2R1, f1R2, f2R2, f1R3, f2R3, f1R4, f2R4, f1R5, f2R5;
    private int outcome;

    public String getFighter1Name() {
        return fighter1Name;
    }

    public void setFighter1Name(String fighter1Name) {
        this.fighter1Name = fighter1Name;
    }

    public String getFighter1Record() {
        return fighter1Record;
    }

    public void setFighter1Record(String fighter1Record) {
        this.fighter1Record = fighter1Record;
    }

    public String getFighter1Pic() {
        return fighter1Pic;
    }

    public void setFighter1Pic(String fighter1Pic) {
        this.fighter1Pic = fighter1Pic;
    }

    public String getFighter2Name() {
        return fighter2Name;
    }

    public void setFighter2Name(String fighter2Name) {
        this.fighter2Name = fighter2Name;
    }

    public String getFighter2Record() {
        return fighter2Record;
    }

    public void setFighter2Record(String fighter2Record) {
        this.fighter2Record = fighter2Record;
    }

    public String getFighter2Pic() {
        return fighter2Pic;
    }

    public void setFighter2Pic(String fighter2Pic) {
        this.fighter2Pic = fighter2Pic;
    }

    public boolean isMainEvent() {
        return mainEvent;
    }

    public void setMainEvent(boolean mainEvent) {
        this.mainEvent = mainEvent;
    }

    public int getF1R1() {
        return f1R1;
    }

    public void setF1R1(int f1R1) {
        this.f1R1 = f1R1;
    }

    public int getF2R1() {
        return f2R1;
    }

    public void setF2R1(int f2R1) {
        this.f2R1 = f2R1;
    }

    public int getF1R2() {
        return f1R2;
    }

    public void setF1R2(int f1R2) {
        this.f1R2 = f1R2;
    }

    public int getF2R2() {
        return f2R2;
    }

    public void setF2R2(int f2R2) {
        this.f2R2 = f2R2;
    }

    public int getF1R3() {
        return f1R3;
    }

    public void setF1R3(int f1R3) {
        this.f1R3 = f1R3;
    }

    public int getF2R3() {
        return f2R3;
    }

    public void setF2R3(int f2R3) {
        this.f2R3 = f2R3;
    }

    public int getF1R4() {
        return f1R4;
    }

    public void setF1R4(int f1R4) {
        this.f1R4 = f1R4;
    }

    public int getF2R4() {
        return f2R4;
    }

    public void setF2R4(int f2R4) {
        this.f2R4 = f2R4;
    }

    public int getF1R5() {
        return f1R5;
    }

    public void setF1R5(int f1R5) {
        this.f1R5 = f1R5;
    }

    public int getF2R5() {
        return f2R5;
    }

    public void setF2R5(int f2R5) {
        this.f2R5 = f2R5;
    }

    public int getOutcome() {
        return outcome;
    }

    public void setOutcome(int outcome) {
        this.outcome = outcome;
    }



    public int getId() {
        return id;
    }
}