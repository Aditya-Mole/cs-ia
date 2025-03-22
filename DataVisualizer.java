import java.util.Map;

interface DataVisualizer {
    void generateChart(String title, Map<String, Double> data);
    void exportReport(String reportType);
}
