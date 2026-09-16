package harborlogix.cargo;

import harborlogix.clients.Client;
import harborlogix.ops.TariffPolicy;

public class LiquidTank extends CargoUnit {

    private final double capacityLitres;
    private double fillPercent;

    public LiquidTank(String unitId, Client owner, double weightKg,
                      int daysStored, double capacityLitres, double fillPercent) {
        super(unitId, owner, weightKg, daysStored);
        if (capacityLitres <= 0) {
            throw new IllegalArgumentException("Capacity must be positive, got: " + capacityLitres);
        }
        if (fillPercent < 0.0 || fillPercent > 100.0) {
            throw new IllegalArgumentException("Fill percentage must be between 0 and 100, got: " + fillPercent);
        }
        this.capacityLitres = capacityLitres;
        this.fillPercent = fillPercent;
    }

    public double getCapacityLitres() {
        return capacityLitres;
    }

    public double getFillPercent() {
        return fillPercent;
    }

    public double currentLitres() {
        return capacityLitres * (fillPercent / 100.0);
    }

    public double transferOut(double litres) {
        if (litres <= 0) {
            throw new IllegalArgumentException("Litres to transfer must be positive, got: " + litres);
        }
        double presentLitres = currentLitres();
        double transferred = Math.min(litres, presentLitres);
        double remainingLitres = presentLitres - transferred;

        this.fillPercent = (remainingLitres / capacityLitres) * 100.0;
        return transferred;
    }

    @Override
    public double dailyStorageFee() {
        return TariffPolicy.LIQUID_RATE * currentLitres();
    }

    @Override
    public String handlingCategory() {
        return "Tank";
    }

    @Override
    public String safetyBriefing() {
        return "Liquid tank: inspect valves, check pressure seals, and verify electrical grounding.";
    }

    @Override
    public String toString() {
        return String.format("%s, %.1f/%.1f L (%.1f%%)", super.toString(), currentLitres(), capacityLitres, fillPercent);
    }
}