
import java.util.*;


// ==========================================
// 1. SINGLETON PATTERN
// ==========================================
class SystemConfig {
    private static SystemConfig instance;

    private SystemConfig() {}

    public static SystemConfig getInstance() {
        if (instance == null)
            instance = new SystemConfig();

        return instance;
    }

    public void showConfig() {
        System.out.println("System Configuration Loaded");
    }
}

// ==========================================
// 2. FACTORY PATTERN
// ==========================================
interface Sensor {
    double readData();
}

class SoilSensor implements Sensor {
    public double readData() {
        return 20.0; // Soil moisture percentage
    }
}

class TemperatureSensor implements Sensor {
    public double readData() {
        return 32.5; // Temperature in Celsius
    }
}

class SensorFactory {
    public static Sensor createSensor(String type) {
        if (type.equalsIgnoreCase("soil"))
            return new SoilSensor();

        if (type.equalsIgnoreCase("temperature"))
            return new TemperatureSensor();

        throw new IllegalArgumentException("Unknown sensor");
    }
}

// ==========================================
// 3. OBSERVER PATTERN
// ==========================================
interface Observer {
    void update(String message);
}

class Farmer implements Observer {
    private String name;

    Farmer(String name) {
        this.name = name;
    }

    public void update(String message) {
        System.out.println("Alert to " + name + ": " + message);
    }
}

class FarmMonitor {
    private List<Observer> farmers = new ArrayList<>();

    public void addObserver(Observer farmer) {
        farmers.add(farmer);
    }

    public void notifyFarmers(String message) {
        for (Observer farmer : farmers)
            farmer.update(message);
    }
}

// ==========================================
// 4. STRATEGY PATTERN
// ==========================================
interface IrrigationStrategy {
    void irrigate();
}

class AutomaticIrrigation implements IrrigationStrategy {
    public void irrigate() {
        System.out.println("Automatic Pump: ON");
    }
}

class ManualIrrigation implements IrrigationStrategy {
    public void irrigate() {
        System.out.println("Manual Irrigation Started");
    }
}

class WeatherBasedIrrigation implements IrrigationStrategy {
    public void irrigate() {
        System.out.println("Weather-based Irrigation Started");
    }
}

class IrrigationController {
    private IrrigationStrategy strategy;

    public void setStrategy(IrrigationStrategy strategy) {
        this.strategy = strategy;
    }

    public void startIrrigation() {
        if (strategy != null)
            strategy.irrigate();
    }
}

// ==========================================
// 5. ADAPTER PATTERN
// ==========================================
class OldSoilSensor {
    public double getOldReading() {
        return 25.0;
    }
}

class SoilSensorAdapter implements Sensor {
    private OldSoilSensor oldSensor;

    SoilSensorAdapter(OldSoilSensor sensor) {
        oldSensor = sensor;
    }

    public double readData() {
        return oldSensor.getOldReading();
    }
}

// ==========================================
// MAIN SYSTEM
// ==========================================
public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Singleton
        System.out.println("=== SMART AGRICULTURE SYSTEM ===");
        SystemConfig config = SystemConfig.getInstance();
        config.showConfig();

        // Factory: Create sensors
        Sensor soil = SensorFactory.createSensor("soil");
        Sensor temperature =
                SensorFactory.createSensor("temperature");

        // Adapter: Connect old sensor
        Sensor oldSensor =
                new SoilSensorAdapter(new OldSoilSensor());

        // Observer: Register farmers
        FarmMonitor monitor = new FarmMonitor();
        monitor.addObserver(new Farmer("Alvee"));
        monitor.addObserver(new Farmer("Alice"));

        // Read sensor data
        double moisture = soil.readData();
        double temp = temperature.readData();

        System.out.println("\n--- SENSOR DATA ---");
        System.out.println("Soil Moisture: " + moisture + "%");
        System.out.println("Temperature: " + temp + " C");
        System.out.println("Old Sensor Reading: "
                + oldSensor.readData() + "%");

        // Check soil moisture
        if (moisture < 30) {
            System.out.println("\nLow Soil Moisture!");
            monitor.notifyFarmers("Soil moisture is low!");

            // Strategy: Select irrigation method
            IrrigationController controller =
                    new IrrigationController();

            System.out.println("\nSelect Irrigation:");
            System.out.println("1. Automatic");
            System.out.println("2. Manual");
            System.out.println("3. Weather Based");

            System.out.print("Enter choice: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    controller.setStrategy(
                            new AutomaticIrrigation());
                    break;

                case 2:
                    controller.setStrategy(
                            new ManualIrrigation());
                    break;

                case 3:
                    controller.setStrategy(
                            new WeatherBasedIrrigation());
                    break;

                default:
                    System.out.println("Invalid choice");
                    input.close();
                    return;
            }

            controller.startIrrigation();

        } else {
            System.out.println("\nSoil moisture is sufficient.");
            System.out.println("Irrigation not required.");
        }

        System.out.println("\n=== SYSTEM COMPLETED ===");

        input.close();
    }
}
