package com.giga;

import static com.giga.Utils.print;

public class LaserPrinter implements ServicePrinter {
    private String printerID;
    private int paperLevel = ServicePrinter.Full_Paper_Tray;
    private int tonerLevel = ServicePrinter.Full_Toner_Level;
    private int documentsPrinted;
    private ThreadGroup studentsGroup;
    private ThreadGroup techniciansGroup;

    public LaserPrinter(String printerID, ThreadGroup studentsGroup, ThreadGroup techniciansGroup) {
        this.printerID = printerID;
        this.studentsGroup = studentsGroup;
        this.techniciansGroup = techniciansGroup;
    }

    public String getPrinterID() {
        return printerID;
    }

    public void setPrinterID(String printerID) {
        this.printerID = printerID;
    }

    public int getPaperLevel() {
        return paperLevel;
    }

    public void setPaperLevel(int paperLevel) {
        this.paperLevel = paperLevel;
    }

    public int getTonerLevel() {
        return tonerLevel;
    }

    public void setTonerLevel(int tonerLevel) {
        this.tonerLevel = tonerLevel;
    }

    public int getDocumentsPrinted() {
        return documentsPrinted;
    }

    public void setDocumentsPrinted(int documentsPrinted) {
        this.documentsPrinted = documentsPrinted;
    }

    public ThreadGroup getStudentsGroup() {
        return studentsGroup;
    }

    public void setStudentsGroup(ThreadGroup studentsGroup) {
        this.studentsGroup = studentsGroup;
    }

    public ThreadGroup getTechniciansGroup() {
        return techniciansGroup;
    }

    public void setTechniciansGroup(ThreadGroup techniciansGroup) {
        this.techniciansGroup = techniciansGroup;
    }

    @Override
    public String toString() {
        return "[PrinterID: " + printerID +
                ", Paper Level: " + paperLevel +
                ", Toner Level: " + tonerLevel +
                ", Documents Printed: " + documentsPrinted + "]";
    }

    /**
     * Replace toner.
     */
    @Override
    public synchronized void replaceTonerCartridge() {
        // pre condition before refilling papers
        // check for maximum number of papers
        // obligatory-guarded action
        boolean isExceeding = tonerLevel > ServicePrinter.Minimum_Toner_Level;
        while (isExceeding) {
            try {
                if (!isUsing()) {
                    break;
                }
                print("Waiting to replace toner");
                wait(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!isExceeding) {
            print("Replace toner");
            tonerLevel = ServicePrinter.Full_Toner_Level;
            print("");
        }

        // notify others
        notifyAll();
    }

    /**
     * Refill papers.
     */
    @Override
    public synchronized void refillPaper() {
        // pre condition before refilling papers
        // check for maximum number of papers
        // obligatory-guarded action
        boolean isExceeding = paperLevel + ServicePrinter.SheetsPerPack > ServicePrinter.Full_Paper_Tray;
        while (isExceeding) {
            try {
                if (!isUsing()) {
                    break;
                }
                print("Waiting to refill paper");
                wait(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!isExceeding) {
            print("Refill paper");
            paperLevel += ServicePrinter.SheetsPerPack;
            print(toString());
            print("");
        }
        // notify others
        notifyAll();
    }

    /**
     * Print the document.
     *
     * @param document document
     */
    @Override
    public synchronized void printDocument(Document document) {
        int pages = document.getNumberOfPages();

        // pre condition before printing
        // check for enough pages & toner
        // obligatory-guarded action
        while (paperLevel < pages || tonerLevel < pages) {
            try {
                print("Waiting - No enough papers / toner");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        print("Printing " + document.getDocumentName() + " of " + document.getUserID());
        // print if pre condition passed
        paperLevel -= pages;
        tonerLevel -= pages;
        documentsPrinted++;
        print(toString());
        print("");

        // notify others
        notifyAll();
    }

    private boolean isUsing() {
        return studentsGroup.activeCount() == 0;
    }

}
