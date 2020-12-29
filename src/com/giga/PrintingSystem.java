package com.giga;

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
        student1.start();
        student2.start();
        student3.start();
        student4.start();
        paperTechnician.start();
        tonerTechnician.start();

        // wait for all the threads to terminate.
        try {
            student1.join();
            student2.join();
            student3.join();
            student4.join();
            paperTechnician.join();
            tonerTechnician.join();

            // final status after terminating all the threads.
            System.out.println(laserPrinter.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
