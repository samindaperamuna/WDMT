/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Saminda Permauna
 */
public class Calc {

    // Bottom.
    public static double getB(double X, double Y) {
        return (X + Y) / 2.0;
    }

    // Top plus Bottom.
    public static double getC(double T, double X, double Y) {
        return T + getB(X, Y);
    }

    // Starboard plus Port.
    public static double getD(double S, double P) {
        return S + P;
    }

    // Top minus Bottom
    public static double getV(double T, double X, double Y) {
        return T - getB(X, Y);
    }

    // Starboard minus Port
    public static double getH(double S, double P) {
        return S - P;
    }
}
