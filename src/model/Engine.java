/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Saminda Permauna
 */
public class Engine {
    private int engineId;
    private int shipId;
    private String engineName;
    private String engineType;
    private int noOfCylinders;
    private int noOfBearings;
    private int firingOrder;
    private String inchargeName;
    private HODRank inchargeRank;
    private String workerName;
    private Rank workerRank;

    public int getEngineId() {
        return engineId;
    }

    public void setEngineId(int engineId) {
        this.engineId = engineId;
    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public int getNoOfCylinders() {
        return noOfCylinders;
    }

    public void setNoOfCylinders(int noOfCylinders) {
        this.noOfCylinders = noOfCylinders;
    }

    public int getNoOfBearings() {
        return noOfBearings;
    }

    public void setNoOfBearings(int noOfBearings) {
        this.noOfBearings = noOfBearings;
    }

    public int getFiringOrder() {
        return firingOrder;
    }

    public void setFiringOrder(int firingOrder) {
        this.firingOrder = firingOrder;
    }

    public String getInchargeName() {
        return inchargeName;
    }

    public void setInchargeName(String inchargeName) {
        this.inchargeName = inchargeName;
    }

    public HODRank getInchargeRank() {
        return inchargeRank;
    }

    public void setInchargeRank(HODRank inchargeRank) {
        this.inchargeRank = inchargeRank;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public Rank getWorkerRank() {
        return workerRank;
    }

    public void setWorkerRank(Rank workerRank) {
        this.workerRank = workerRank;
    }
}
