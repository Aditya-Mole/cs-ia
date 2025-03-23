import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

class LoanTest {
    private Loan loan;

    @BeforeEach
    void setUp() {
        loan = new Loan("Home Loan", 50000, new Date(), 5, 60, 0);
    }

    @Test
    void testCalculateInterest() {
        assertEquals(208.33, loan.calculateInterest(), 0.01, "Loan interest calculation failed");
    }

    @Test
    void testCalculateMonthlyPayment() {
        assertEquals(943.56, loan.calculateMonthlyPayment(), 0.5, "Loan monthly payment calculation failed");
    }
}

