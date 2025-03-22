import java.util.Date;

class Bond extends FinancialAsset {
    private double interestRate;
    private Date maturityDate;

    public Bond(String name, double value, Date purchaseDate, double interestRate, Date maturityDate) {
        super(name, value, purchaseDate);
        this.interestRate = interestRate;
        this.maturityDate = maturityDate;
        setType("Bond");
    }

    @Override
    public double calculateInterest() {
        return getValue() * (interestRate / 100);
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }
}
