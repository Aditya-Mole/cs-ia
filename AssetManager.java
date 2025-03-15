import java.util.List;

// Interface for Asset Management functionality
public interface AssetManager {
    /**
     * Adds a new asset to the portfolio
     * @param asset The financial asset to add
     * @return true if successfully added
     */
    boolean addAsset(FinancialAsset asset);

    /**
     * Removes an asset from the portfolio
     * @param assetName Name of the asset to remove
     * @return true if successfully removed
     */
    boolean removeAsset(String assetName);

    /**
     * Retrieves an asset by name
     * @param assetName Name of the asset to retrieve
     * @return The financial asset or null if not found
     */
    FinancialAsset getAsset(String assetName);

    /**
     * Calculates the total value of all assets
     * @return Total value of assets
     */
    double calculateTotalAssetValue();

    /**
     * Returns all assets in the portfolio
     * @return List of all financial assets
     */
    List<FinancialAsset> getAllAssets();

    /**
     * Gets assets filtered by type
     * @param type The type of assets to retrieve
     * @return List of matching assets
     */
    List<FinancialAsset> getAssetsByType(String type);
}
