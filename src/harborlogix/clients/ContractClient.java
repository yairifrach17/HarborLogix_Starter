package harborlogix.clients;

public class ContractClient extends Client {

    private final double contractDiscount;

    public ContractClient(String clientId, String name, double contractDiscount) {
        super(clientId, name);
        if (contractDiscount < 0.0 || contractDiscount > 40.0) {
            throw new IllegalArgumentException("Contract discount must be between 0 and 40 percent, got: " + contractDiscount);
        }
        this.contractDiscount = contractDiscount;
    }

    @Override
    public double discountPercent() {
        return contractDiscount;
    }

    @Override
    public String clientTier() {
        return "Contract";
    }

    @Override
    public String toString() {
        return String.format("%s, discount=%.1f%%", super.toString(), contractDiscount);
    }
}