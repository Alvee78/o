
import java.util.*;

// ==========================================
// 1. SINGLETON PATTERN
// University Configuration Manager
// ==========================================

class UniversityConfig {
    private static UniversityConfig instance;

    private UniversityConfig() {
        System.out.println("University Configuration Initialized.");
    }

    public static UniversityConfig getInstance() {
        if (instance == null) {
            instance = new UniversityConfig();
        }
        return instance;
    }

    public void showConfig() {
        System.out.println("University: Rajshahi University");
        System.out.println("System: Campus Management System");
    }
}

// ==========================================
// 2. FACTORY PATTERN
// User Account Creation
// ==========================================

interface User {
    void showRole();
}

class StudentAccount implements User {
    public void showRole() {
        System.out.println("Student Account Created.");
    }
}

class FacultyAccount implements User {
    public void showRole() {
        System.out.println("Faculty Account Created.");
    }
}

class AdminAccount implements User {
    public void showRole() {
        System.out.println("Administrator Account Created.");
    }
}

class UserFactory {
    public static User createUser(String type) {
        if (type.equalsIgnoreCase("student")) {
            return new StudentAccount();
        } else if (type.equalsIgnoreCase("faculty")) {
            return new FacultyAccount();
        } else if (type.equalsIgnoreCase("admin")) {
            return new AdminAccount();
        }

        return null;
    }
}

// ==========================================
// 3. OBSERVER PATTERN
// Notice and Result Notification
// ==========================================

interface Observer {
    void update(String message);
}

class StudentSubscriber implements Observer {
    private String name;

    StudentSubscriber(String name) {
        this.name = name;
    }

    public void update(String message) {
        System.out.println(name + " received: " + message);
    }
}

class NotificationManager {
    private List<Observer> students = new ArrayList<>();

    public void subscribe(Observer student) {
        students.add(student);
    }

    public void publishNotice(String notice) {
        System.out.println("\nNew Notice Published: " + notice);

        for (Observer student : students) {
            student.update(notice);
        }
    }

    public void publishResult(String result) {
        System.out.println("\nResult Published: " + result);

        for (Observer student : students) {
            student.update(result);
        }
    }
}

// ==========================================
// 4. STRATEGY PATTERN
// Student Evaluation Strategy
// ==========================================

interface EvaluationStrategy {
    void evaluate(double marks);
}

class GPAEvaluation implements EvaluationStrategy {
    public void evaluate(double marks) {
        double gpa;

        if (marks >= 80) gpa = 4.00;
        else if (marks >= 70) gpa = 3.50;
        else if (marks >= 60) gpa = 3.00;
        else if (marks >= 50) gpa = 2.50;
        else gpa = 0.00;

        System.out.println("GPA: " + gpa);
    }
}

class PercentageEvaluation implements EvaluationStrategy {
    public void evaluate(double marks) {
        System.out.println("Percentage: " + marks + "%");
    }
}

class GradeEvaluation implements EvaluationStrategy {
    public void evaluate(double marks) {
        String grade;

        if (marks >= 80) grade = "A+";
        else if (marks >= 70) grade = "A";
        else if (marks >= 60) grade = "B";
        else if (marks >= 50) grade = "C";
        else grade = "F";

        System.out.println("Grade: " + grade);
    }
}

class StudentEvaluator {
    private EvaluationStrategy strategy;

    public void setStrategy(EvaluationStrategy strategy) {
        this.strategy = strategy;
    }

    public void evaluateStudent(double marks) {
        if (strategy != null) {
            strategy.evaluate(marks);
        }
    }
}

// ==========================================
// 5. ADAPTER PATTERN
// External Library System Integration
// ==========================================

// Expected interface of our campus system
interface LibrarySystem {
    void searchBook(String bookName);
}

// Existing external/legacy library system
class ExternalLibrarySystem {
    public String searchBookLegacy(String bookName) {
        return "Book found in external library: " + bookName;
    }
}

// Adapter connects external system to campus system
class LibraryAdapter implements LibrarySystem {
    private ExternalLibrarySystem externalLibrary;

    LibraryAdapter(ExternalLibrarySystem externalLibrary) {
        this.externalLibrary = externalLibrary;
    }

    public void searchBook(String bookName) {
        String result = externalLibrary.searchBookLegacy(bookName);
        System.out.println(result);
    }
}

// ==========================================
// MAIN CLASS
// ==========================================

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // 1. Singleton
        System.out.println("===== SINGLETON PATTERN =====");

        UniversityConfig config = UniversityConfig.getInstance();
        config.showConfig();

        // 2. Factory
        System.out.println("\n===== FACTORY PATTERN =====");

        User student = UserFactory.createUser("student");
        User faculty = UserFactory.createUser("faculty");
        User admin = UserFactory.createUser("admin");

        student.showRole();
        faculty.showRole();
        admin.showRole();

        // 3. Observer
        System.out.println("\n===== OBSERVER PATTERN =====");

        NotificationManager manager = new NotificationManager();

        manager.subscribe(new StudentSubscriber("Alvi"));
        manager.subscribe(new StudentSubscriber("Faria"));

        manager.publishNotice("Course Registration Starts Tomorrow");
        manager.publishResult("Semester Result Published");

        // 4. Strategy
        System.out.println("\n===== STRATEGY PATTERN =====");

        StudentEvaluator evaluator = new StudentEvaluator();

        System.out.print("Enter student marks: ");
        double marks = sc.nextDouble();

        System.out.println("1. GPA Evaluation");
        System.out.println("2. Percentage Evaluation");
        System.out.println("3. Grade Evaluation");

        System.out.print("Choose evaluation method: ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                evaluator.setStrategy(new GPAEvaluation());
                break;

            case 2:
                evaluator.setStrategy(new PercentageEvaluation());
                break;

            case 3:
                evaluator.setStrategy(new GradeEvaluation());
                break;

            default:
                System.out.println("Invalid choice!");
                sc.close();
                return;
        }

        evaluator.evaluateStudent(marks);

        // 5. Adapter
        System.out.println("\n===== ADAPTER PATTERN =====");

        ExternalLibrarySystem externalLibrary =
                new ExternalLibrarySystem();

        LibrarySystem library =
                new LibraryAdapter(externalLibrary);

        library.searchBook("Object Oriented Design");

        sc.close();
    }
}
