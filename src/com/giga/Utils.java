package com.giga;

/*
  ********************************************************************
  File:      Utils.java  (Class)
  Author:    Chamupathi Gigara Hettige
  Contents:  6SENG002W CWK
  This provides all the utility functions.
  Date:      22/11/20
  Version:   1.0
  ***********************************************************************
 */

public class Utils {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static int randomTime() {
        return (int) (Math.random() * 1000);
    }

    public static void print(String message) {
        System.out.println(message);
    }

    public static void printInfo(String message) {
        System.out.println(ANSI_CYAN + message + ANSI_RESET);
    }

    public static void printSuccess(String message) {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
    }

    public static void printWarn(String message) {
        System.out.println(ANSI_YELLOW + message + ANSI_RESET);
    }

    public static void printError(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }
}
