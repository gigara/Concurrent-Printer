package com.giga;

import java.util.concurrent.Semaphore;

import static com.giga.Utils.*;

public class LaserPrinter implements ServicePrinter {
    private String printerID;
    private int paperLevel = ServicePrinter.Full_Paper_Tray;
    private int tonerLevel = ServicePrinter.Full_Toner_Level;
    private int documentsPrinted;
    private ThreadGroup studentsGroup;
    private ThreadGroup techniciansGroup;
    private Semaphore printer = new Semaphore(1);

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
        try {
            while (tonerLevel > ServicePrinter.Minimum_Toner_Level) {
                printWarn("Waiting to replace toner");
                if (!isUsing()) {
                    printError("Skip the toner replace job since no more printing jobs are in the queue.");
                    break;
                }
                printError("Printer already have enough toner level. Trying again in 5 seconds.");
                wait(5000);
            }
            printer.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (isUsing()) {
            print(toString());
            printSuccess("Replace toner");
            tonerLevel = ServicePrinter.Full_Toner_Level;
            print(toString());
            print("");
        }

        printer.release();
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
        try {
            while ((paperLevel + ServicePrinter.SheetsPerPack) > ServicePrinter.Full_Paper_Tray) {
                printWarn("Waiting to refill paper");
                if (!isUsing()) {
                    printError("Skip the paper replace job since no more printing jobs are in the queue.");
                    break;
                }
                printError("Printer already have enough papers. Trying again in 5 seconds.");
                wait(5000);
            }

            printer.acquire();
            if (isUsing()) {
                print(toString());
                printSuccess("Refill paper");
                paperLevel += ServicePrinter.SheetsPerPack;
                print(toString());
                print("");
            }
            printer.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
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

        try {
            print(toString());
            printInfo("Printing " + document.getDocumentName() + " of " + document.getUserID());
            // print if pre condition passed
            for (int i = 0; i < pages; i++) {
                if (paperLevel > 0 && tonerLevel > 0) {
                    printer.acquire();
                    paperLevel--;
                    tonerLevel--;
                    printer.release();
                } else {
                    if (paperLevel == 0) {
                        printError("No enough papers!\n");
                    }
                    if (tonerLevel == 0) {
                        printError("No enough toner!\n");
                    }
                    wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        documentsPrinted++;
        printInfo("Print job completed: " + document.getDocumentName() + " of " + document.getUserID());
        print(toString());
        print("");

        // notify others
        notifyAll();
    }

    private boolean isUsing() {
        return studentsGroup.activeCount() > 0;
    }

}