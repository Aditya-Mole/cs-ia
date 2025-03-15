// Real Estate implementation
public class RealEstate extends FinancialAsset {
    private String address;
    private double squareFeet;
    private double appreciationRate;

    /**
     * Constructor for Real Estate asset
     * @param name Property name/description
     * @param value Current market value
     * @param purchaseDate Date of purchase
     * @param address Property address
     * @param squareFeet Size in square feet
     * @param appreciationRate Annual appreciation rate (%)
     */
    public RealEstate(String name, double value, String purchaseDate,
                      String address, double squareFeet, double appreciationRate) {
        super(name, value, purchaseDate, "Real Estate");
        this.address = address;
        this.squareFeet = squareFeet;
        this.appreciationRate = appreciationRate;
    }

    @Override
    public double calculateInterest() {
        // For real estate, "interest" is appreciation
        return value * (appreciationRate / 100);
    }

    @Override
    public void displayDetails() {
        System.out.println("Property: " + name);
        System.out.println("Address: " + address);
        System.out.println("Size: " + squareFeet + " sq ft");
        System.out.println("Current Value: $" + String.format("%.2f", value));
        System.out.println("Annual Appreciation: " + appreciationRate + "%");
        System.out.println("Purchase date: " + purchaseDate);
    }

    @Override
    public double calculateFutureValue(int years, double customAppreciationRate) {
        // If a custom rate is provided (not -1), use it; otherwise use the property's rate
        double rate = (customAppreciationRate >= 0) ? customAppreciationRate : appreciationRate;
        // Future Value = Present Value * (1 + annual rate)^years
        return value * Math.pow(1 + (rate / 100), years);
    }

    // Getters
    public String getAddress() {
        return address;
    }

    public double getSquareFeet() {
        return squareFeet;
    }

    public double getAppreciationRate() {
        return appreciationRate;
    }

    /**
     * Updates the property value
     * @param newValue The new property value
     */
    public void updateValue(double newValue) {
        // Calculate implied appreciation rate based on new value
        if (this.value > 0) {
            this.appreciationRate = ((newValue / this.value) - 1) * 100;
        }
        this.value = newValue;
    }
}
