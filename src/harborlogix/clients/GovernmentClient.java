package harborlogix.clients;

public class GovernmentClient extends Client {

    public static final double STATUTORY_DISCOUNT = 25.0;
    private final String agencyCode;

    public GovernmentClient(String clientId, String name, String agencyCode) {
        super(clientId, name);
        if (agencyCode == null || agencyCode.isBlank()) {
            throw new IllegalArgumentException("Agency code cannot be null or blank");
        }
        this.agencyCode = agencyCode;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    @Override
    public double discountPercent() {
        return STATUTORY_DISCOUNT;
    }

    @Override
    public String clientTier() {
        return "Government";
    }

    @Override
    public boolean priorityHandling() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + ", agency=" + agencyCode + ", discount=" + STATUTORY_DISCOUNT + "%";    }
}