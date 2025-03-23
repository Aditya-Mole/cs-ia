import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

class StockTest {
    private Stock stock;

    @BeforeEach
    void setUp() {
        stock = new Stock("Apple", 10000, new Date(), "AAPL", 100, 100);
    }

    @Test
    void testCalculateInterest() {
        assertEquals(700, stock.calculateInterest(), 0.01, "Stock interest calculation failed");
    }

    @Test
    void testSetShares() {
        stock.setShares(150);
        assertEquals(15000, stock.getValue(), "Stock value update failed after setting shares");
    }

    @Test
    void testSetPricePerShare() {
        stock.setPricePerShare(120);
        assertEquals(12000, stock.getValue(), "Stock value update failed after changing price per share");
    }
}

