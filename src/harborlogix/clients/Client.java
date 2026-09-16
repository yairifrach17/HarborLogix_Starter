package harborlogix.clients;

public class Client {

    private final String clientId;
    private final String name;

    public Client(String clientId, String name) {
        if (clientId == null || clientId.isBlank()) {
            throw new IllegalArgumentException("Client ID cannot be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        this.clientId = clientId;
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public double discountPercent() {
        return 0.0;
    }

    public String clientTier() {
        return "Standard";
    }

    public boolean priorityHandling() {
        return false;
    }

    @Override
    public String toString() {
        return String.format("Client[id=%s, name=%s, tier=%s]", clientId, name, clientTier());
    }
}