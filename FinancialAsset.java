import java.util.Date;

// Financial Asset Classes
abstract class FinancialAsset {
    private String name;
    private double value;
    private Date purchaseDate;
    private String type;

    public FinancialAsset(String name, double value, Date purchaseDate) {
        this.name = name;
        this.value = value;
        this.purchaseDate = purchaseDate;
    }

    public abstract double calculateInterest();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getType() {
        return type;
    }

    protected void setType(String type) {
        this.type = type;
    }
}
