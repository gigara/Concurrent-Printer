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
        try {
            while (tonerLevel > ServicePrinter.Minimum_Toner_Level) {
                if (studentsGroup.activeCount() == 0) {
                    break;
                }
                System.out.println("Waiting to replace toner");
                System.out.println("Printer already have enough toner level. Trying again in 5 seconds.");
                wait(5000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (studentsGroup.activeCount() != 0) {
            print(toString());
            System.out.println("Replace toner");
            tonerLevel = ServicePrinter.Full_Toner_Level;
            print(toString());
            print("");
        } else {
            System.out.println("Exiting toner replace since there are no more printing jobs.");
        }

        // notify others
        notifyAll();
    }

    /**
     * Refill papers.
     */
    @Override
    public synchronized void refillPaper() {
        try {
            while ((paperLevel + ServicePrinter.SheetsPerPack) > ServicePrinter.Full_Paper_Tray) {
                if (studentsGroup.activeCount() == 0) {
                    break;
                }
                System.out.println("Waiting to refill paper");
                System.out.println("Printer already have enough papers. Trying again in 5 seconds.");
                wait(5000);
            }

            if (studentsGroup.activeCount() != 0) {
                print(toString());
                System.out.println("Refill paper");
                paperLevel += ServicePrinter.SheetsPerPack;
                print(toString());
                print("");
            } else {
                System.out.println("Exiting toner replace since there are no more printing jobs.");
            }

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

        try {
            print(toString());
            System.out.println("Printing " + document.getDocumentName() + " of " + document.getUserID());
            // print if pre condition passed
            for (int i = 0; i < pages; i++) {
                if (paperLevel > 0 && tonerLevel > 0) {
                    paperLevel--;
                    tonerLevel--;
                } else {
                    System.out.println("No paper or toner");
                    wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        documentsPrinted++;
        print(toString());
        print("");

        // notify others
        notifyAll();
    }

}
