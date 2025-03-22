import java.util.Date;

class Loan extends FinancialAsset {
    private double interestRate;
    private int term; // in months
    private int paymentsMade;

    public Loan(String name, double value, Date purchaseDate, double interestRate, int term, int paymentsMade) {
        super(name, value, purchaseDate);
        this.interestRate = interestRate;
        this.term = term;
        this.paymentsMade = paymentsMade;
        setType("Loan");
    }

    @Override
    public double calculateInterest() {
        return getValue() * (interestRate / 100) / 12; // Monthly interest
    }

    public double calculateMonthlyPayment() {
        double monthlyRate = interestRate / 100 / 12;
        double payment = getValue() * monthlyRate * Math.pow(1 + monthlyRate, term)
                / (Math.pow(1 + monthlyRate, term) - 1);
        return payment;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getPaymentsMade() {
        return paymentsMade;
    }

    public void setPaymentsMade(int paymentsMade) {
        this.paymentsMade = paymentsMade;
    }
}
