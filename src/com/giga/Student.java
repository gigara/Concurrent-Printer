package com.giga;

import static com.giga.Utils.print;
import static com.giga.Utils.randomTime;

public class Student extends Thread {
    private String studentName;
    private LaserPrinter printer;

    public Student(ThreadGroup group, String studentName, LaserPrinter printer) {
        super(group, "Thread of student " + studentName);
        this.studentName = studentName;
        this.printer = printer;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public LaserPrinter getPrinter() {
        return printer;
    }

    public void setPrinter(LaserPrinter printer) {
        this.printer = printer;
    }

    @Override
    public void run() {
        Document CWK1 = new Document(studentName, "cwk1", 10);
        Document CWK2 = new Document(studentName, "cwk2", 16);
        Document CWK3 = new Document(studentName, "cwk3", 5);
        Document CWK4 = new Document(studentName, "cwk4", 20);
        Document CWK5 = new Document(studentName, "cwk5", 22);

        // print documents
        try {
            print("Sending " + CWK1.getDocumentName() + " of " + CWK1.getUserID());
            printer.printDocument(CWK1);
            sleep(randomTime());

            print("Sending " + CWK2.getDocumentName() + " of " + CWK2.getUserID());
            printer.printDocument(CWK2);
            sleep(randomTime());

            print("Sending " + CWK3.getDocumentName() + " of " + CWK3.getUserID());
            printer.printDocument(CWK3);
            sleep(randomTime());

            print("Sending " + CWK4.getDocumentName() + " of " + CWK4.getUserID());
            printer.printDocument(CWK4);
            sleep(randomTime());

            print("Sending " + CWK5.getDocumentName() + " of " + CWK5.getUserID());
            printer.printDocument(CWK5);
            sleep(randomTime());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
