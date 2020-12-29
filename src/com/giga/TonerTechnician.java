package com.giga;

/*
  ********************************************************************
  File:      TonerTechnician.java  (Class)
  Author:    Chamupathi Gigara Hettige
  Contents:  6SENG002W CWK
  This provides all the toner technician functions.
  Date:      22/11/20
  Version:   1.0
  ***********************************************************************
 */

import static com.giga.Utils.print;
import static com.giga.Utils.randomTime;

public class TonerTechnician extends Thread {
    private String tonerTechnicianName;
    private LaserPrinter printer;

    public TonerTechnician(ThreadGroup group, String tonerTechnicianName, LaserPrinter printer) {
        super(group, "Thread of student " + tonerTechnicianName);
        this.tonerTechnicianName = tonerTechnicianName;
        this.printer = printer;
    }

    public String getTonerTechnicianName() {
        return tonerTechnicianName;
    }

    public void setTonerTechnicianName(String tonerTechnicianName) {
        this.tonerTechnicianName = tonerTechnicianName;
    }

    public LaserPrinter getPrinter() {
        return printer;
    }

    public void setPrinter(LaserPrinter printer) {
        this.printer = printer;
    }

    @Override
    public void run() {
        try {
            sleep(randomTime());
            print("Replacing toner by " + getTonerTechnicianName());
            printer.replaceTonerCartridge();

            sleep(randomTime());
            print("Replacing toner by " + getTonerTechnicianName());
            printer.replaceTonerCartridge();

            sleep(randomTime());
            print("Replacing toner by " + getTonerTechnicianName());
            printer.replaceTonerCartridge();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
