import org.w3c.dom.Node;

// Interface for financial data visualization
public interface DataVisualizer {
    /**
     * Generates a chart showing net worth over time
     * @param assets List of assets
     * @param debts List of debts/loans
     * @return The chart component
     */
    Node generateNetWorthChart(List<FinancialAsset> assets, List<Loan> loans);

    /**
     * Generates a chart showing asset allocation
     * @param assets List of assets
     * @return The chart component
     */
    Node generateAssetAllocationChart(List<FinancialAsset> assets);

    /**
     * Generates a chart showing debt repayment progress
     * @param loans List of loans
     * @return The chart component
     */
    Node generateDebtRepaymentChart(List<Loan> loans);

    /**
     * Generates a historical trend chart for a specific asset
     * @param asset The asset to visualize
     * @param historicalData The historical value data
     * @return The chart component
     */
    Node generateAssetHistoryChart(FinancialAsset asset, Map<LocalDate, Double> historicalData);
}
