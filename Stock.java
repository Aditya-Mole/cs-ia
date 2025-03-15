// Stock implementation - extends FinancialAsset
public class Stock extends FinancialAsset {
    private int shares;
    private double pricePerShare;
    private String tickerSymbol;

    /**
     * Constructor for Stock asset
     * @param name Company name
     * @param shares Number of shares owned
     * @param pricePerShare Current price per share
     * @param purchaseDate Date of purchase
     * @param tickerSymbol Stock ticker symbol
     */
    public Stock(String name, int shares, double pricePerShare, String purchaseDate, String tickerSymbol) {
        super(name, shares * pricePerShare, purchaseDate, "Stock");
        this.shares = shares;
        this.pricePerShare = pricePerShare;
        this.tickerSymbol = tickerSymbol;
    }

    @Override
    public double calculateInterest() {
        // Stocks don't have interest, but could calculate dividend yield
        // This is a placeholder for potential dividend calculation
        return 0.0;
    }

    @Override
    public void displayDetails() {
        System.out.println("Stock: " + name + " (" + tickerSymbol + ")");
        System.out.println("Shares: " + shares);
        System.out.println("Price per share: $" + String.format("%.2f", pricePerShare));
        System.out.println("Total value: $" + String.format("%.2f", value));
        System.out.println("Purchase date: " + purchaseDate);
    }

    @Override
    public double calculateFutureValue(int years, double annualGrowthRate) {
        // Using compound growth formula: FV = PV * (1 + r)^t
        return value * Math.pow(1 + (annualGrowthRate / 100), years);
    }

    /**
     * Updates stock price and recalculates total value
     * @param newPrice The new price per share
     */
    public void updateStockPrice(double newPrice) {
        this.pricePerShare = newPrice;
        this.value = shares * pricePerShare;
    }

    // Getters and setters
    public int getShares() {
        return shares;
    }

    public double getPricePerShare() {
        return pricePerShare;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }
}
