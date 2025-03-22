import java.util.Date;

class Stock extends FinancialAsset {
    private String ticker;
    private int shares;
    private double pricePerShare;

    public Stock(String name, double value, Date purchaseDate, String ticker, int shares, double pricePerShare) {
        super(name, value, purchaseDate);
        this.ticker = ticker;
        this.shares = shares;
        this.pricePerShare = pricePerShare;
        setType("Stock");
    }

    @Override
    public double calculateInterest() {
        // For stocks, we'll use an expected annual return
        return getValue() * 0.07; // 7% estimated annual return
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
        // Update value based on shares and price
        setValue(shares * pricePerShare);
    }

    public double getPricePerShare() {
        return pricePerShare;
    }

    public void setPricePerShare(double pricePerShare) {
        this.pricePerShare = pricePerShare;
        // Update value based on shares and price
        setValue(shares * pricePerShare);
    }
}
