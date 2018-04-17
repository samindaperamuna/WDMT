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
public interface MessageSubject {

    public void addObserver(MessageObserver messageObserver);

    public void removeObserver(MessageObserver messageObserver);

    public void notifyObservers();
}