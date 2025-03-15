// Bond implementation - extends FinancialAsset
public class Bond extends FinancialAsset {
    private double faceValue;
    private double couponRate;
    private int term; // Years until maturity
    private String issuer;

    /**
     * Constructor for Bond asset
     * @param name Bond name
     * @param faceValue Face value of the bond
     * @param couponRate Annual interest rate
     * @param term Years to maturity
     * @param purchaseDate Date of purchase
     * @param issuer Bond issuer
     */
    public Bond(String name, double faceValue, double couponRate, int term, String purchaseDate, String issuer) {
        super(name, faceValue, purchaseDate, "Bond");
        this.faceValue = faceValue;
        this.couponRate = couponRate;
        this.term = term;
        this.issuer = issuer;
    }

    @Override
    public double calculateInterest() {
        // Annual interest payment = face value * coupon rate
        return faceValue * (couponRate / 100);
    }

    @Override
    public void displayDetails() {
        System.out.println("Bond: " + name);
        System.out.println("Issuer: " + issuer);
        System.out.println("Face Value: $" + String.format("%.2f", faceValue));
        System.out.println("Coupon Rate: " + couponRate + "%");
        System.out.println("Term: " + term + " years");
        System.out.println("Annual Interest: $" + String.format("%.2f", calculateInterest()));
        System.out.println("Purchase date: " + purchaseDate);
    }

    @Override
    public double calculateFutureValue(int years, double marketRate) {
        // For bonds, we use the present value of future cash flows
        // This is a simplified calculation
        double totalValue = 0;

        // Calculate present value of coupon payments
        for (int i = 1; i <= Math.min(years, term); i++) {
            double couponPayment = faceValue * (couponRate / 100);
            totalValue += couponPayment / Math.pow(1 + (marketRate / 100), i);
        }

        // If years exceeds term, the bond has matured and face value is returned
        if (years >= term) {
            totalValue += faceValue / Math.pow(1 + (marketRate / 100), term);
        }

        return totalValue;
    }

    // Getters
    public double getFaceValue() {
        return faceValue;
    }

    public double getCouponRate() {
        return couponRate;
    }

    public int getTerm() {
        return term;
    }

    public String getIssuer() {
        return issuer;
    }
}

