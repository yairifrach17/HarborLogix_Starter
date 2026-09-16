package harborlogix.ops;

import harborlogix.cargo.CargoUnit;
import harborlogix.clients.Client;
import java.util.ArrayList;

public class Yard {

    private final String yardName;
    private final int capacity;
    private final ArrayList<CargoUnit> units;

    public Yard(String yardName, int capacity) {
        if (yardName == null || yardName.isBlank()) {
            throw new IllegalArgumentException("Yard name cannot be null or blank");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive, got: " + capacity);
        }
        this.yardName = yardName;
        this.capacity = capacity;
        this.units = new ArrayList<>();
    }

    public String getYardName() {
        return yardName;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getUnitCount() {
        return units.size();
    }

    public ArrayList<CargoUnit> getUnits() {
        return new ArrayList<>(units);
    }

    public void receive(CargoUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Cannot receive null cargo unit");
        }
        if (units.size() >= capacity) {
            throw new IllegalStateException("Yard capacity reached: " + capacity);
        }
        for (CargoUnit existing : units) {
            if (existing.getUnitId().equals(unit.getUnitId())) {
                throw new IllegalArgumentException("Duplicate unit ID: " + unit.getUnitId());
            }
        }
        units.add(unit);
    }

    public double totalDailyRevenue() {
        double total = 0.0;
        for (CargoUnit unit : units) {
            total += unit.dailyStorageFee();
        }
        return total;
    }

    public double invoiceFor(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        double clientGrossCharge = 0.0;
        for (CargoUnit unit : units) {
            if (unit.getOwner().getClientId().equals(client.getClientId())) {
                clientGrossCharge += unit.totalStorageCharge();
            }
        }
        double discount = client.discountPercent();
        return clientGrossCharge * (1.0 - (discount / 100.0));
    }

    public CargoUnit heaviestUnit() {
        if (units.isEmpty()) {
            return null;
        }
        CargoUnit heaviest = units.get(0);
        for (int i = 1; i < units.size(); i++) {
            CargoUnit current = units.get(i);
            if (current.getWeightKg() > heaviest.getWeightKg()) {
                heaviest = current;
            }
        }
        return heaviest;
    }

    public void printManifest() {
        System.out.println("=== Yard Manifest: " + yardName + " ===");
        for (CargoUnit unit : units) {
            System.out.println(unit);
            System.out.println("  Safety Briefing: " + unit.safetyBriefing());
        }
        System.out.printf("Total Daily Revenue: %.2f%n", totalDailyRevenue());
    }
}