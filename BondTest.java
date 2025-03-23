import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

class BondTest {
    private Bond bond;

    @BeforeEach
    void setUp() {
        bond = new Bond("Gov Bond", 5000, new Date(), 5, new Date());
    }

    @Test
    void testCalculateInterest() {
        assertEquals(250, bond.calculateInterest(), 0.01, "Bond interest calculation failed");
    }
}
