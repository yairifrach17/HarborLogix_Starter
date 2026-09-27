package harborlogix.app;

import harborlogix.cargo.*;
import harborlogix.clients.*;
import harborlogix.ops.*;

public class TerminalApp {

    public static void main(String[] args) {
        System.out.println("HarborLogix terminal - student " + TariffPolicy.STUDENT_ID);

        // 1. Initial Yard & Manifest
        System.out.println("\n=== 1. YARD MANIFEST ===");
        Yard yard = new Yard("Main-Yard", TariffPolicy.YARD_CAPACITY);

        Client standard = new Client("C-01", "Standard Client");
        ContractClient contract = new ContractClient("C-02", "Contract Client", 15.0);
        GovernmentClient gov = new GovernmentClient("C-03", "Government Client", "SEC-99");

        yard.receive(new StandardContainer("SC-1", standard, 12000, 5, 33.0));
        yard.receive(new RefrigeratedContainer("RC-2", contract, 14000, 4, 30.0, -18.0, 5.0));
        yard.receive(new HazmatContainer("HC-3", contract, 18000, 3, 28.0, 3, true));
        yard.receive(new LiquidTank("LT-4", gov, 20000, 6, 25000, 80.0));

        yard.printManifest();

        // 2. Invoices
        System.out.println("\n=== 2. INVOICES ===");
        Client[] clients = {standard, contract, gov};
        for (Client c : clients) {
            System.out.println(c.getName() + " (Discount: " + c.discountPercent() + "%): " + yard.invoiceFor(c));
        }

        // 3. Drainage Round
        System.out.println("\n=== 3. DRAINAGE ROUND ===");
        for (CargoUnit unit : yard.getUnits()) {
            if (unit instanceof LiquidTank tank) {
                System.out.println("Before pump: " + tank);
                tank.transferOut(3000.0);
                System.out.println("After pumping 3000L: " + tank);
            }
        }

// 4. Oversized Cargo (Part D)
        System.out.println("\n=== 4. OVERSIZED CARGO (PART D) ===");
        yard.receive(new OversizedCargo("OC-5", gov, 35000, 2, 14.5, true));
        yard.printManifest();
    }
}