package com.giga;

/*
  ********************************************************************
  File:      LaserPrinter.java  (Class)
  Author:    Chamupathi Gigara Hettige
  Contents:  6SENG002W CWK
  This provides all the printing system functions.
  Date:      22/11/20
  Version:   1.0
  ***********************************************************************
 */

import static com.giga.Utils.printInfo;

public class PrintingSystem {
    public static void main(String[] args) {
        // initialize
        ThreadGroup studentsGroup = new ThreadGroup("Student Group");
        ThreadGroup techniciansGroup = new ThreadGroup("Technician Group");

        LaserPrinter laserPrinter = new LaserPrinter("lp-CG.24", studentsGroup, techniciansGroup);

        Student student1 = new Student(studentsGroup, "Chamupathi Gigara", laserPrinter);
        Student student2 = new Student(studentsGroup, "Charith Jayasanka", laserPrinter);
        Student student3 = new Student(studentsGroup, "Vikum Sanjeewa", laserPrinter);
        Student student4 = new Student(studentsGroup, "Manishi Kumari", laserPrinter);

        PaperTechnician paperTechnician = new PaperTechnician(techniciansGroup, "Sirisena", laserPrinter);
        TonerTechnician tonerTechnician = new TonerTechnician(techniciansGroup, "Sajith", laserPrinter);

        // Start threads.
        // wait for all the threads to terminate.
        try {
            paperTechnician.start();
            tonerTechnician.start();

            student1.start();
            student1.join();

            student2.start();
            student2.join();

            student3.start();
            student3.join();

            student4.start();
            student4.join();

            paperTechnician.join();
            tonerTechnician.join();

            // final status after terminating all the threads.
            printInfo(laserPrinter.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
