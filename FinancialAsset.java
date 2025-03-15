// Base abstract class for all financial assets
public abstract class FinancialAsset {
    protected String name;
    protected double value;
    protected String purchaseDate;
    protected String type;

    /**
     * Constructor for the base financial asset
     * @param name The name of the asset
     * @param value The current value of the asset
     * @param purchaseDate When the asset was acquired
     * @param type The type of financial asset
     */
    public FinancialAsset(String name, double value, String purchaseDate, String type) {
        this.name = name;
        this.value = value;
        this.purchaseDate = purchaseDate;
        this.type = type;
    }

    // Abstract methods that all financial assets must implement
    public abstract double calculateInterest();
    public abstract void displayDetails();
    public abstract double calculateFutureValue(int years, double rateOfReturn);

    // Common getters and setters
    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getType() {
        return type;
    }
}
