/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Time;
import java.sql.Date;

/**
 *
 * @author Saminda Permauna
 */
public class DeflectionHeader {

    private long headerId;
    private int engineId;
    private Date date;
    private Time time;
    private double cracnkCaseTemp;
    private double bottomError;
    private double checkError;

    public long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(long headerId) {
        this.headerId = headerId;
    }

    public int getEngineId() {
        return engineId;
    }

    public void setEngineId(int engineId) {
        this.engineId = engineId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public double getCracnkCaseTemp() {
        return cracnkCaseTemp;
    }

    public void setCracnkCaseTemp(double cracnkCaseTemp) {
        this.cracnkCaseTemp = cracnkCaseTemp;
    }

    public double getBottomError() {
        return bottomError;
    }

    public void setBottomError(double bottomError) {
        this.bottomError = bottomError;
    }

    public double getCheckError() {
        return checkError;
    }

    public void setCheckError(double checkError) {
        this.checkError = checkError;
    }
}
