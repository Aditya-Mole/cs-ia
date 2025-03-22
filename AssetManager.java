import java.util.List;

// Interface definitions as mentioned in the requirements
interface AssetManager {
    void addAsset(FinancialAsset asset);
    void removeAsset(FinancialAsset asset);
    void updateAsset(FinancialAsset asset);
    List<FinancialAsset> getAllAssets();
}