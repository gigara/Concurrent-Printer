package com.giga;

import static com.giga.Utils.print;
import static com.giga.Utils.randomTime;

public class PaperTechnician extends Thread {
    private String paperTechnicianName;
    private LaserPrinter printer;

    public PaperTechnician(ThreadGroup group, String paperTechnicianName, LaserPrinter printer) {
        super(group, "Thread of student " + paperTechnicianName);
        this.paperTechnicianName = paperTechnicianName;
        this.printer = printer;
    }

    public String getPaperTechnicianName() {
        return paperTechnicianName;
    }

    public void setPaperTechnicianName(String paperTechnicianName) {
        this.paperTechnicianName = paperTechnicianName;
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
            print("Replacing paper by " + getPaperTechnicianName());
            printer.refillPaper();
            sleep(randomTime());

            print("Replacing paper by " + getPaperTechnicianName());
            printer.refillPaper();
            sleep(randomTime());

            print("Replacing paper by " + getPaperTechnicianName());
            printer.refillPaper();
            sleep(randomTime());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}