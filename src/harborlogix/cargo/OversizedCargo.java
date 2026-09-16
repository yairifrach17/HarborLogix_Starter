package harborlogix.cargo;

import harborlogix.clients.Client;
import harborlogix.ops.TariffPolicy;

public class OversizedCargo extends CargoUnit {

    private final double lengthM;
    private final boolean needsHeavyCrane;

    public OversizedCargo(String unitId, Client owner, double weightKg,
                          int daysStored, double lengthM, boolean needsHeavyCrane) {
        super(unitId, owner, weightKg, daysStored);
        if (lengthM <= 12.0) {
            throw new IllegalArgumentException("Length must exceed 12.0 meters");
        }
        this.lengthM = lengthM;
        this.needsHeavyCrane = needsHeavyCrane;
    }

    public double getLengthM() {
        return lengthM;
    }

    public boolean isNeedsHeavyCrane() {
        return needsHeavyCrane;
    }

    @Override
    public double dailyStorageFee() {
        return TariffPolicy.OVERSIZE_DAILY_FLAT + (lengthM * 5.0);
    }

    @Override
    public String handlingCategory() {
        return "Oversized";
    }

    @Override
    public String safetyBriefing() {
        return "Oversized cargo: wide load escort required";
    }

    @Override
    public String toString() {
        return super.toString() + ", length=" + lengthM + "m, heavyCrane=" + needsHeavyCrane;
    }
}