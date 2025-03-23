import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

class FinancialCalculationsTest {
    private Stock stock;
    private Loan loan;

    @BeforeEach
    void setUp() {
        // Initialize stock: 100 shares at $100 each = $10,000
        stock = new Stock("TechStock", 10000, new Date(), "TSLA", 100, 100);

        // Initialize loan: $50,000 principal, 5% interest rate, 60-month term
        loan = new Loan("Car Loan", 50000, new Date(), 5.0, 60, 0);
    }

    @Test
    void testStockInterestCalculation() {
        double expectedInterest = 700; // 7% of $10,000
        double actualInterest = stock.calculateInterest();

        System.out.println("Stock Interest Expected: " + expectedInterest);
        System.out.println("Stock Interest Actual: " + actualInterest);

        assertEquals(expectedInterest, actualInterest, 0.01, "Stock interest calculation is incorrect");
    }

    @Test
    void testLoanMonthlyPaymentCalculation() {
        double expectedPayment = 943.56; // Expected monthly payment (approx.)
        double actualPayment = loan.calculateMonthlyPayment();

        System.out.println("Loan Monthly Payment Expected: " + expectedPayment);
        System.out.println("Loan Monthly Payment Actual: " + actualPayment);

        assertEquals(expectedPayment, actualPayment, 0.5, "Loan monthly payment calculation is incorrect");
    }
}
