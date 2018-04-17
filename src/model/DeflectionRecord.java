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
public class DeflectionRecord {

    private long headerId;
    private int cylinderId;
    private int nearBdcStbd;
    private int starboard;
    private int top;
    private int port;
    private int nearBdcPort;

    public int getCylinderId() {
        return cylinderId;
    }

    public void setCylinderId(int cylinderId) {
        this.cylinderId = cylinderId;
    }

    public void setHeaderId(long headerId) {
        this.headerId = headerId;
    }

    public long getHeaderId() {
        return headerId;
    }

    public int getNearBdcStbd() {
        return nearBdcStbd;
    }

    public void setNearBdcStbd(int nearBdcStbd) {
        this.nearBdcStbd = nearBdcStbd;
    }

    public int getStarboard() {
        return starboard;
    }

    public void setStarboard(int starboard) {
        this.starboard = starboard;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getNearBdcPort() {
        return nearBdcPort;
    }

    public void setNearBdcPort(int nearBdcPort) {
        this.nearBdcPort = nearBdcPort;
    }
}
