
import java.util.*;

// ==========================================
// 1. SINGLETON PATTERN
// Central Urban Database
// ==========================================
class UrbanDatabase {
    private static UrbanDatabase instance;

    private UrbanDatabase() {}

    public static UrbanDatabase getInstance() {
        if (instance == null)
            instance = new UrbanDatabase();

        return instance;
    }

    public void connect() {
        System.out.println("Connected to Central Urban Database");
    }
}

// ==========================================
// 2. FACTORY PATTERN
// Urban Service Creation
// ==========================================
interface UrbanService {
    void execute();
}

class TrafficService implements UrbanService {
    public void execute() {
        System.out.println("Managing city traffic");
    }
}

class WasteService implements UrbanService {
    public void execute() {
        System.out.println("Managing waste collection");
    }
}

class EmergencyService implements UrbanService {
    public void execute() {
        System.out.println("Handling emergency requests");
    }
}

class ServiceFactory {
    public static UrbanService createService(String type) {

        if (type.equalsIgnoreCase("traffic"))
            return new TrafficService();

        if (type.equalsIgnoreCase("waste"))
            return new WasteService();

        if (type.equalsIgnoreCase("emergency"))
            return new EmergencyService();

        throw new IllegalArgumentException("Unknown service");
    }
}

// ==========================================
// 3. OBSERVER PATTERN
// Complaint Status Notification
// ==========================================
interface Observer {
    void update(String message);
}

class Citizen implements Observer {
    private String name;

    Citizen(String name) {
        this.name = name;
    }

    public void update(String message) {
        System.out.println(name + " Notification: " + message);
    }
}

class ComplaintManager {
    private List<Observer> citizens = new ArrayList<>();

    public void addObserver(Observer citizen) {
        citizens.add(citizen);
    }

    public void notifyCitizens(String message) {
        for (Observer citizen : citizens)
            citizen.update(message);
    }

    public void updateComplaintStatus(String status) {
        System.out.println("Complaint Status: " + status);
        notifyCitizens("Your complaint is " + status);
    }
}

// ==========================================
// 4. STRATEGY PATTERN
// Route Optimization
// ==========================================
interface RouteStrategy {
    void calculateRoute();
}

class ShortestRoute implements RouteStrategy {
    public void calculateRoute() {
        System.out.println("Calculating shortest route");
    }
}

class FastestRoute implements RouteStrategy {
    public void calculateRoute() {
        System.out.println("Calculating fastest route");
    }
}

class LeastTrafficRoute implements RouteStrategy {
    public void calculateRoute() {
        System.out.println("Calculating least congested route");
    }
}

class TrafficController {
    private RouteStrategy strategy;

    public void setStrategy(RouteStrategy strategy) {
        this.strategy = strategy;
    }

    public void optimizeRoute() {
        if (strategy != null)
            strategy.calculateRoute();
    }
}

// ==========================================
// 5. ADAPTER PATTERN
// External Traffic API
// ==========================================
class ExternalTrafficAPI {
    public String getTrafficData() {
        return "Heavy Traffic";
    }
}

interface TrafficData {
    void getData();
}

class TrafficAdapter implements TrafficData {
    private ExternalTrafficAPI api;

    TrafficAdapter(ExternalTrafficAPI api) {
        this.api = api;
    }

    public void getData() {
        System.out.println("External API Data: "
                + api.getTrafficData());
    }
}

// ==========================================
// MAIN CLASS
// ==========================================
public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("===== DHAKA URBAN MANAGEMENT SYSTEM =====");

        // 1. SINGLETON
        System.out.println("\n1. SINGLETON PATTERN");

        UrbanDatabase db = UrbanDatabase.getInstance();
        db.connect();

        // 2. FACTORY
        System.out.println("\n2. FACTORY PATTERN");

        UrbanService service =
                ServiceFactory.createService("traffic");
        service.execute();

        service = ServiceFactory.createService("waste");
        service.execute();

        service = ServiceFactory.createService("emergency");
        service.execute();

        // 3. OBSERVER
        System.out.println("\n3. OBSERVER PATTERN");

        ComplaintManager manager = new ComplaintManager();

        manager.addObserver(new Citizen("Alvee"));
        manager.addObserver(new Citizen("Alice"));

        manager.updateComplaintStatus("Resolved");

        // 4. STRATEGY
        System.out.println("\n4. STRATEGY PATTERN");

        TrafficController controller = new TrafficController();

        System.out.println("Select Route:");
        System.out.println("1. Shortest Route");
        System.out.println("2. Fastest Route");
        System.out.println("3. Least Traffic Route");

        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                controller.setStrategy(new ShortestRoute());
                break;

            case 2:
                controller.setStrategy(new FastestRoute());
                break;

            case 3:
                controller.setStrategy(new LeastTrafficRoute());
                break;

            default:
                System.out.println("Invalid choice");
                sc.close();
                return;
        }

        controller.optimizeRoute();

        // 5. ADAPTER
        System.out.println("\n5. ADAPTER PATTERN");

        ExternalTrafficAPI externalAPI =
                new ExternalTrafficAPI();

        TrafficData adapter =
                new TrafficAdapter(externalAPI);

        adapter.getData();

        System.out.println("\n===== SYSTEM COMPLETED =====");

        sc.close();
    }
}
