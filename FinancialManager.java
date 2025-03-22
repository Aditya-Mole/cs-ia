// Main Application Class
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.io.*;

public class FinancialManager {
    // Singleton instance for database connection
    private static FinancialManager instance;
    private JFrame frame;
    private List<FinancialAsset> assets;
    private List<Loan> liabilities;
    private JPanel dashboardPanel;
    private JPanel assetsPanel;
    private JPanel liabilitiesPanel;
    private JPanel reportsPanel;
    private JPanel settingsPanel;
    private JPanel currentPanel;

    // Get singleton instance
    public static FinancialManager getInstance() {
        if (instance == null) {
            instance = new FinancialManager();
        }
        return instance;
    }

    private FinancialManager() {
        assets = new ArrayList<>();
        liabilities = new ArrayList<>();

        // Load sample data for demonstration
        loadSampleData();

        // Set up the UI
        initializeUI();
    }

    private void loadSampleData() {
        // Add sample assets
        assets.add(new Stock("Apple Stock", 55000, new Date(), "AAPL", 100, 550.0));
        assets.add(new Stock("Amazon Stock", 320000, new Date(), "AMZN", 80, 4000.0));
        assets.add(new Bond("Government Bond", 25000, new Date(), 4.8, addMonths(new Date(), 60)));
        assets.add(new Bond("Corporate Bond", 35000, new Date(), 5.2, addMonths(new Date(), 36)));

        // Add sample liabilities
        liabilities.add(new Loan("Mortgage", 350000, new Date(), 3.5, 360, 24));
        liabilities.add(new Loan("Car Loan", 25000, new Date(), 4.2, 60, 12));
        liabilities.add(new Loan("Personal Loan", 10000, new Date(), 6.5, 36, 6));
    }

    private Date addMonths(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    private void initializeUI() {
        // Create main window
        frame = new JFrame("Financial Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLayout(new BorderLayout());

        // Create sidebar
        JPanel sidebar = createSidebar();
        frame.add(sidebar, BorderLayout.WEST);

        // Create main content panels
        dashboardPanel = createDashboardPanel();
        assetsPanel = createAssetsPanel();
        liabilitiesPanel = createLiabilitiesPanel();
        reportsPanel = createReportsPanel();
        settingsPanel = createSettingsPanel();

        // Set dashboard as the default view
        currentPanel = dashboardPanel;
        frame.add(currentPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(50, 50, 50));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBorder(new EmptyBorder(20, 20, 20, 20));

        // App title
        JLabel titleLabel = new JLabel("Financial Manager");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(titleLabel);
        sidebar.add(Box.createVerticalStrut(30));

        // Navigation buttons
        String[] navItems = {"Dashboard", "Assets", "Liabilities", "Reports", "Settings"};
        for (String item : navItems) {
            JButton navButton = new JButton(item);
            navButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            navButton.setMaximumSize(new Dimension(160, 40));
            navButton.setFont(new Font("Arial", Font.PLAIN, 14));
            navButton.setForeground(Color.WHITE);
            navButton.setBackground(new Color(70, 70, 70));
            navButton.setBorderPainted(false);
            navButton.setFocusPainted(false);
            navButton.addActionListener(new NavigationListener(item));
            sidebar.add(navButton);
            sidebar.add(Box.createVerticalStrut(10));
        }

        return sidebar;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 20, 20));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 240, 240));

        // Net Worth Panel
        JPanel netWorthPanel = createCardPanel("Net Worth");
        double netWorth = calculateNetWorth();
        JLabel netWorthValueLabel = new JLabel("$" + formatCurrency(netWorth));
        netWorthValueLabel.setFont(new Font("Arial", Font.BOLD, 32));
        netWorthValueLabel.setHorizontalAlignment(JLabel.CENTER);

        // Monthly change
        JLabel changeLabel = new JLabel("+2.5% from last month");
        changeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        changeLabel.setForeground(new Color(0, 150, 0));
        changeLabel.setHorizontalAlignment(JLabel.CENTER);

        netWorthPanel.add(netWorthValueLabel);
        netWorthPanel.add(changeLabel);

        // Asset Distribution Panel
        JPanel assetDistributionPanel = createCardPanel("Asset Distribution");
        // In a real application, this would be a chart
        // For now, we'll just add a placeholder
        JLabel chartPlaceholder = new JLabel("Chart would be here");
        chartPlaceholder.setHorizontalAlignment(JLabel.CENTER);
        assetDistributionPanel.add(chartPlaceholder);

        // Recent Transactions Panel
        JPanel transactionsPanel = createCardPanel("Recent Transactions");
        transactionsPanel.setLayout(new BoxLayout(transactionsPanel, BoxLayout.Y_AXIS));

        // Sample transactions
        String[][] transactions = {
                {"Stock Purchase - AAPL", "-$5,000"},
                {"Bond Interest Payment", "+$1,200"},
                {"Mortgage Payment", "-$2,500"}
        };

        for (String[] transaction : transactions) {
            JPanel transactionRow = new JPanel(new BorderLayout());
            transactionRow.setBackground(Color.WHITE);

            JLabel descLabel = new JLabel(transaction[0]);
            JLabel amountLabel = new JLabel(transaction[1]);

            if (transaction[1].startsWith("+")) {
                amountLabel.setForeground(new Color(0, 150, 0));
            } else {
                amountLabel.setForeground(new Color(150, 0, 0));
            }

            transactionRow.add(descLabel, BorderLayout.WEST);
            transactionRow.add(amountLabel, BorderLayout.EAST);
            transactionRow.setBorder(new EmptyBorder(10, 10, 10, 10));

            transactionsPanel.add(transactionRow);
            transactionsPanel.add(Box.createVerticalStrut(5));
        }

        // Actions Panel
        JPanel actionsPanel = createCardPanel("Quick Actions");
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        String[] actions = {"Add Asset", "Add Liability", "Make Payment", "Generate Report"};
        for (String action : actions) {
            JButton actionButton = new JButton(action);
            actionButton.setBackground(new Color(66, 139, 202));
            actionButton.setForeground(Color.WHITE);
            actionButton.setFocusPainted(false);
            actionButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, action + " feature would open here"));
            buttonPanel.add(actionButton);
        }

        actionsPanel.add(buttonPanel);

        // Add all panels to the dashboard
        panel.add(netWorthPanel);
        panel.add(assetDistributionPanel);
        panel.add(transactionsPanel);
        panel.add(actionsPanel);

        return panel;
    }

    private JPanel createCardPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD,
                16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(15));

        return panel;
    }

    private JPanel createAssetsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 240, 240));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("Assets");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton addButton = new JButton("Add New Asset");
        addButton.setBackground(new Color(66, 139, 202));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> showAddAssetDialog());

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(addButton, BorderLayout.EAST);

        // Assets Table
        String[] columnNames = {"Name", "Type", "Value", "Purchase Date", "Details", "Actions"};
        Object[][] data = new Object[assets.size()][6];

        for (int i = 0; i < assets.size(); i++) {
            FinancialAsset asset = assets.get(i);
            data[i][0] = asset.getName();
            data[i][1] = asset.getType();
            data[i][2] = "$" + formatCurrency(asset.getValue());
            data[i][3] = formatDate(asset.getPurchaseDate());
            data[i][4] = "View";
            data[i][5] = "Edit";
        }

        JTable table = new JTable(data, columnNames);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(230, 230, 250));

        // Make the "View" and "Edit" columns clickable
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                if (row >= 0 && col == 4) {  // View details
                    showAssetDetails(assets.get(row));
                } else if (row >= 0 && col == 5) {  // Edit asset
                    JOptionPane.showMessageDialog(frame, "Edit feature would open here");
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        // Summary Panel
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        summaryPanel.setBackground(new Color(240, 240, 240));
        summaryPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        double totalValue = assets.stream().mapToDouble(FinancialAsset::getValue).sum();
        int stocksCount = (int) assets.stream().filter(a -> a instanceof Stock).count();
        int bondsCount = (int) assets.stream().filter(a -> a instanceof Bond).count();

        summaryPanel.add(createSummaryCard("Total Assets Value", "$" + formatCurrency(totalValue)));
        summaryPanel.add(createSummaryCard("Stocks", stocksCount + " items"));
        summaryPanel.add(createSummaryCard("Bonds", bondsCount + " items"));

        // Combine all panels
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(summaryPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createSummaryCard(String title, String value) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 18));
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(valueLabel);

        return card;
    }

    private JPanel createLiabilitiesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 240, 240));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("Liabilities");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton addButton = new JButton("Add New Liability");
        addButton.setBackground(new Color(66, 139, 202));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> showAddLoanDialog());

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(addButton, BorderLayout.EAST);

        // Liabilities Table
        String[] columnNames = {"Name", "Balance", "Interest Rate", "Monthly Payment", "Term", "Payments Made", "Actions"};
        Object[][] data = new Object[liabilities.size()][7];

        for (int i = 0; i < liabilities.size(); i++) {
            Loan loan = liabilities.get(i);
            data[i][0] = loan.getName();
            data[i][1] = "$" + formatCurrency(loan.getValue());
            data[i][2] = loan.getInterestRate() + "%";
            data[i][3] = "$" + formatCurrency(loan.calculateMonthlyPayment());
            data[i][4] = loan.getTerm() + " months";
            data[i][5] = loan.getPaymentsMade() + " / " + loan.getTerm();
            data[i][6] = "Make Payment";
        }

        JTable table = new JTable(data, columnNames);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(230, 230, 250));

        // Make the "Make Payment" column clickable
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                if (row >= 0 && col == 6) {  // Make payment
                    showMakePaymentDialog(liabilities.get(row));
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        // Summary Panel
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        summaryPanel.setBackground(new Color(240, 240, 240));
        summaryPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        double totalDebt = liabilities.stream().mapToDouble(Loan::getValue).sum();
        double totalMonthlyPayments = liabilities.stream().mapToDouble(Loan::calculateMonthlyPayment).sum();

        summaryPanel.add(createSummaryCard("Total Debt", "$" + formatCurrency(totalDebt)));
        summaryPanel.add(createSummaryCard("Monthly Payments", "$" + formatCurrency(totalMonthlyPayments)));
        summaryPanel.add(createSummaryCard("Debt-to-Asset Ratio", String.format("%.2f", totalDebt / calculateTotalAssets())));

        // Combine all panels
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(summaryPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 240, 240));

        // Header
        JLabel titleLabel = new JLabel("Financial Reports");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Report options
        JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        optionsPanel.setBackground(new Color(240, 240, 240));

        String[] reports = {
                "Net Worth Summary",
                "Asset Allocation",
                "Debt Overview",
                "Cash Flow Analysis"
        };

        for (String report : reports) {
            JPanel reportCard = new JPanel();
            reportCard.setLayout(new BorderLayout());
            reportCard.setBackground(Color.WHITE);
            reportCard.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(220, 220, 220), 1),
                    new EmptyBorder(15, 15, 15, 15)
            ));

            JLabel reportLabel = new JLabel(report);
            reportLabel.setFont(new Font("Arial", Font.BOLD, 16));

            JButton generateButton = new JButton("Generate");
            generateButton.setBackground(new Color(66, 139, 202));
            generateButton.setForeground(Color.WHITE);
            generateButton.setFocusPainted(false);
            generateButton.addActionListener(e -> generateReport(report));

            reportCard.add(reportLabel, BorderLayout.CENTER);
            reportCard.add(generateButton, BorderLayout.SOUTH);

            optionsPanel.add(reportCard);
        }

        // Combine all panels
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(optionsPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 240, 240));

        // Header
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Settings form
        JPanel formPanel = new JPanel(new GridLayout(5, 1, 0, 20));
        formPanel.setBackground(new Color(240, 240, 240));

        // Currency setting
        JPanel currencyPanel = new JPanel(new BorderLayout());
        currencyPanel.setBackground(new Color(240, 240, 240));

        JLabel currencyLabel = new JLabel("Currency:");
        currencyLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        String[] currencies = {"USD ($)", "EUR (€)", "GBP (£)", "JPY (¥)", "CAD ($)"};
        JComboBox<String> currencyComboBox = new JComboBox<>(currencies);

        currencyPanel.add(currencyLabel, BorderLayout.WEST);
        currencyPanel.add(currencyComboBox, BorderLayout.EAST);

        // Date format setting
        JPanel dateFormatPanel = new JPanel(new BorderLayout());
        dateFormatPanel.setBackground(new Color(240, 240, 240));

        JLabel dateFormatLabel = new JLabel("Date Format:");
        dateFormatLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        String[] dateFormats = {"MM/dd/yyyy", "dd/MM/yyyy", "yyyy-MM-dd"};
        JComboBox<String> dateFormatComboBox = new JComboBox<>(dateFormats);

        dateFormatPanel.add(dateFormatLabel, BorderLayout.WEST);
        dateFormatPanel.add(dateFormatComboBox, BorderLayout.EAST);

        // Theme setting
        JPanel themePanel = new JPanel(new BorderLayout());
        themePanel.setBackground(new Color(240, 240, 240));

        JLabel themeLabel = new JLabel("Theme:");
        themeLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        String[] themes = {"Light", "Dark", "System Default"};
        JComboBox<String> themeComboBox = new JComboBox<>(themes);

        themePanel.add(themeLabel, BorderLayout.WEST);
        themePanel.add(themeComboBox, BorderLayout.EAST);

        // Data backup setting
        JPanel backupPanel = new JPanel(new BorderLayout());
        backupPanel.setBackground(new Color(240, 240, 240));

        JLabel backupLabel = new JLabel("Auto Backup:");
        backupLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JCheckBox backupCheckBox = new JCheckBox();
        backupCheckBox.setBackground(new Color(240, 240, 240));

        backupPanel.add(backupLabel, BorderLayout.WEST);
        backupPanel.add(backupCheckBox, BorderLayout.EAST);

        // Save button
        JButton saveButton = new JButton("Save Settings");
        saveButton.setBackground(new Color(66, 139, 202));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Settings saved successfully!"));

        // Add components to form
        formPanel.add(currencyPanel);
        formPanel.add(dateFormatPanel);
        formPanel.add(themePanel);
        formPanel.add(backupPanel);
        formPanel.add(saveButton);

        // Combine all panels
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);

        return panel;
    }

    private void showAddAssetDialog() {
        JDialog dialog = new JDialog(frame, "Add New Asset", true);
        dialog.setSize(400, 400);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Asset type selection
        formPanel.add(new JLabel("Asset Type:"));
        String[] assetTypes = {"Stock", "Bond"};
        JComboBox<String> typeComboBox = new JComboBox<>(assetTypes);
        formPanel.add(typeComboBox);

        // Common fields
        formPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Value:"));
        JTextField valueField = new JTextField();
        formPanel.add(valueField);

        // Type-specific fields (initially for Stock)
        JPanel stockFields = new JPanel(new GridLayout(0, 2, 10, 10));
        JPanel bondFields = new JPanel(new GridLayout(0, 2, 10, 10));

        // Stock fields
        stockFields.add(new JLabel("Ticker Symbol:"));
        JTextField tickerField = new JTextField();
        stockFields.add(tickerField);

        stockFields.add(new JLabel("Number of Shares:"));
        JTextField sharesField = new JTextField();
        stockFields.add(sharesField);

        stockFields.add(new JLabel("Price per Share:"));
        JTextField priceField = new JTextField();
        stockFields.add(priceField);

        // Bond fields
        bondFields.add(new JLabel("Interest Rate (%):"));
        JTextField interestField = new JTextField();
        bondFields.add(interestField);

        bondFields.add(new JLabel("Maturity (months):"));
        JTextField maturityField = new JTextField();
        bondFields.add(maturityField);

        // Initially show stock fields
        JPanel specificFieldsPanel = new JPanel(new BorderLayout());
        specificFieldsPanel.add(stockFields, BorderLayout.CENTER);

        // Switch between stock and bond fields
        typeComboBox.addActionListener(e -> {
            specificFieldsPanel.removeAll();
            if (typeComboBox.getSelectedItem().equals("Stock")) {
                specificFieldsPanel.add(stockFields, BorderLayout.CENTER);
            } else {
                specificFieldsPanel.add(bondFields, BorderLayout.CENTER);
            }
            specificFieldsPanel.revalidate();
            specificFieldsPanel.repaint();
        });

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancel");
        JButton saveButton = new JButton("Save");

        cancelButton.addActionListener(e -> dialog.dispose());
        saveButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                double value = Double.parseDouble(valueField.getText());

                if (typeComboBox.getSelectedItem().equals("Stock")) {
                    String ticker = tickerField.getText();
                    int shares = Integer.parseInt(sharesField.getText());
                    double price = Double.parseDouble(priceField.getText());

                    assets.add(new Stock(name, value, new Date(), ticker, shares, price));
                } else {
                    double interestRate = Double.parseDouble(interestField.getText());
                    int maturity = Integer.parseInt(maturityField.getText());

                    assets.add(new Bond(name, value, new Date(), interestRate, addMonths(new Date(), maturity)));
                }

                JOptionPane.showMessageDialog(dialog, "Asset added successfully!");
                dialog.dispose();
                refreshUI();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numeric values.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        // Assemble dialog
        dialog.add(formPanel, BorderLayout.NORTH);
        dialog.add(specificFieldsPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void showAddLoanDialog() {
        JDialog dialog = new JDialog(frame, "Add New Loan", true);
        dialog.setSize(400, 350);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Loan fields
        formPanel.add(new JLabel("Loan Name:"));
        JTextField nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Principal Amount:"));
        JTextField principalField = new JTextField();
        formPanel.add(principalField);

        formPanel.add(new JLabel("Interest Rate (%):"));
        JTextField interestField = new JTextField();
        formPanel.add(interestField);

        formPanel.add(new JLabel("Term (months):"));
        JTextField termField = new JTextField();
        formPanel.add(termField);

        formPanel.add(new JLabel("Payments Made:"));
        JTextField paymentsField = new JTextField("0");
        formPanel.add(paymentsField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancel");
        JButton saveButton = new JButton("Save");

        cancelButton.addActionListener(e -> dialog.dispose());
        saveButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                double principal = Double.parseDouble(principalField.getText());
                double interestRate = Double.parseDouble(interestField.getText());
                int term = Integer.parseInt(termField.getText());
                int paymentsMade = Integer.parseInt(paymentsField.getText());

                liabilities.add(new Loan(name, principal, new Date(), interestRate, term, paymentsMade));

                JOptionPane.showMessageDialog(dialog, "Loan added successfully!");
                dialog.dispose();
                refreshUI();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numeric values.", "Input Error", JOptionPane.ERROR_MESSAGE);
            ;}
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        // Assemble dialog
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void showMakePaymentDialog(Loan loan) {
        JDialog dialog = new JDialog(frame, "Make Payment", true);
        dialog.setSize(400, 250);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Payment info
        formPanel.add(new JLabel("Loan:"));
        formPanel.add(new JLabel(loan.getName()));

        formPanel.add(new JLabel("Current Balance:"));
        formPanel.add(new JLabel("$" + formatCurrency(loan.getValue())));

        formPanel.add(new JLabel("Monthly Payment:"));
        formPanel.add(new JLabel("$" + formatCurrency(loan.calculateMonthlyPayment())));

        formPanel.add(new JLabel("Payment Amount:"));
        JTextField paymentField = new JTextField(String.valueOf(loan.calculateMonthlyPayment()));
        formPanel.add(paymentField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancel");
        JButton payButton = new JButton("Make Payment");

        cancelButton.addActionListener(e -> dialog.dispose());
        payButton.addActionListener(e -> {
            try {
                double payment = Double.parseDouble(paymentField.getText());

                if (payment <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Payment must be greater than zero.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Apply payment
                double newBalance = loan.getValue() - payment;
                loan.setValue(Math.max(0, newBalance));
                loan.setPaymentsMade(loan.getPaymentsMade() + 1);

                JOptionPane.showMessageDialog(dialog, "Payment of $" + formatCurrency(payment) + " processed successfully!");
                dialog.dispose();
                refreshUI();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid payment amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(payButton);

        // Assemble dialog
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void showAssetDetails(FinancialAsset asset) {
        JDialog dialog = new JDialog(frame, "Asset Details", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        detailsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Common details
        detailsPanel.add(new JLabel("Name:"));
        detailsPanel.add(new JLabel(asset.getName()));

        detailsPanel.add(new JLabel("Type:"));
        detailsPanel.add(new JLabel(asset.getType()));

        detailsPanel.add(new JLabel("Value:"));
        detailsPanel.add(new JLabel("$" + formatCurrency(asset.getValue())));

        detailsPanel.add(new JLabel("Purchase Date:"));
        detailsPanel.add(new JLabel(formatDate(asset.getPurchaseDate())));

        // Type-specific details
        if (asset instanceof Stock) {
            Stock stock = (Stock) asset;

            detailsPanel.add(new JLabel("Ticker:"));
            detailsPanel.add(new JLabel(stock.getTicker()));

            detailsPanel.add(new JLabel("Shares:"));
            detailsPanel.add(new JLabel(String.valueOf(stock.getShares())));

            detailsPanel.add(new JLabel("Price per Share:"));
            detailsPanel.add(new JLabel("$" + formatCurrency(stock.getPricePerShare())));
        } else if (asset instanceof Bond) {
            Bond bond = (Bond) asset;

            detailsPanel.add(new JLabel("Interest Rate:"));
            detailsPanel.add(new JLabel(bond.getInterestRate() + "%"));

            detailsPanel.add(new JLabel("Maturity Date:"));
            detailsPanel.add(new JLabel(formatDate(bond.getMaturityDate())));

            detailsPanel.add(new JLabel("Annual Interest:"));
            detailsPanel.add(new JLabel("$" + formatCurrency(bond.calculateInterest())));
        }

        // Button to close
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);

        // Assemble dialog
        dialog.add(detailsPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void generateReport(String reportType) {
        JDialog dialog = new JDialog(frame, reportType, true);
        dialog.setSize(700, 500);
        dialog.setLayout(new BorderLayout());

        JTextArea reportText = new JTextArea();
        reportText.setEditable(false);
        reportText.setFont(new Font("Monospaced", Font.PLAIN, 14));
        reportText.setBorder(new EmptyBorder(10, 10, 10, 10));

        StringBuilder report = new StringBuilder();
        report.append("=== ").append(reportType).append(" ===\n\n");
        report.append("Generated on: ").append(formatDate(new Date())).append("\n\n");

        switch (reportType) {
            case "Net Worth Summary":
                generateNetWorthReport(report);
                break;
            case "Asset Allocation":
                generateAssetAllocationReport(report);
                break;
            case "Debt Overview":
                generateDebtReport(report);
                break;
            case "Cash Flow Analysis":
                generateCashFlowReport(report);
                break;
        }

        reportText.setText(report.toString());

        JScrollPane scrollPane = new JScrollPane(reportText);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton printButton = new JButton("Print");
        JButton saveButton = new JButton("Save");
        JButton closeButton = new JButton("Close");

        printButton.addActionListener(e -> JOptionPane.showMessageDialog(dialog, "Printing functionality would be here"));
        saveButton.addActionListener(e -> JOptionPane.showMessageDialog(dialog, "Saving functionality would be here"));
        closeButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(printButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(closeButton);

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void generateNetWorthReport(StringBuilder report) {
        double totalAssets = calculateTotalAssets();
        double totalLiabilities = calculateTotalLiabilities();
        double netWorth = calculateNetWorth();

        report.append("TOTAL ASSETS: $").append(formatCurrency(totalAssets)).append("\n");
        report.append("TOTAL LIABILITIES: $").append(formatCurrency(totalLiabilities)).append("\n");
        report.append("NET WORTH: $").append(formatCurrency(netWorth)).append("\n\n");

        report.append("ASSET BREAKDOWN:\n");
        for (FinancialAsset asset : assets) {
            report.append("- ").append(asset.getName()).append(": $").append(formatCurrency(asset.getValue())).append("\n");
        }

        report.append("\nLIABILITY BREAKDOWN:\n");
        for (Loan loan : liabilities) {
            report.append("- ").append(loan.getName()).append(": $").append(formatCurrency(loan.getValue())).append("\n");
        }

        report.append("\nFINANCIAL HEALTH INDICATORS:\n");
        double debtToAssetRatio = totalLiabilities / totalAssets;
        report.append("- Debt-to-Asset Ratio: ").append(String.format("%.2f", debtToAssetRatio));
        if (debtToAssetRatio < 0.3) {
            report.append(" (Excellent)\n");
        } else if (debtToAssetRatio < 0.5) {
            report.append(" (Good)\n");
        } else if (debtToAssetRatio < 0.7) {
            report.append(" (Fair)\n");
        } else {
            report.append(" (Poor)\n");
        }
    }

    private void generateAssetAllocationReport(StringBuilder report) {
        double totalStocks = assets.stream()
                .filter(a -> a instanceof Stock)
                .mapToDouble(FinancialAsset::getValue)
                .sum();

        double totalBonds = assets.stream()
                .filter(a -> a instanceof Bond)
                .mapToDouble(FinancialAsset::getValue)
                .sum();

        double totalAssets = totalStocks + totalBonds;

        double stockPercentage = (totalStocks / totalAssets) * 100;
        double bondPercentage = (totalBonds / totalAssets) * 100;

        report.append("ASSET ALLOCATION SUMMARY:\n");
        report.append("Total Asset Value: $").append(formatCurrency(totalAssets)).append("\n\n");

        report.append("ALLOCATION BY ASSET CLASS:\n");
        report.append("- Stocks: $").append(formatCurrency(totalStocks)).append(" (").append(String.format("%.2f", stockPercentage)).append("%)\n");
        report.append("- Bonds: $").append(formatCurrency(totalBonds)).append(" (").append(String.format("%.2f", bondPercentage)).append("%)\n\n");

        report.append("STOCKS BREAKDOWN:\n");
        for (FinancialAsset asset : assets) {
            if (asset instanceof Stock) {
                Stock stock = (Stock) asset;
                report.append("- ").append(stock.getName())
                        .append(" (").append(stock.getTicker()).append("): $")
                        .append(formatCurrency(stock.getValue())).append(" (")
                        .append(String.format("%.2f", (stock.getValue() / totalStocks) * 100)).append("% of stocks)\n");
            }
        }

        report.append("\nBONDS BREAKDOWN:\n");
        for (FinancialAsset asset : assets) {
            if (asset instanceof Bond) {
                Bond bond = (Bond) asset;
                report.append("- ").append(bond.getName())
                        .append(" (").append(bond.getInterestRate()).append("% interest): $")
                        .append(formatCurrency(bond.getValue())).append(" (")
                        .append(String.format("%.2f", (bond.getValue() / totalBonds) * 100)).append("% of bonds)\n");
            }
        }

        report.append("\nRECOMMENDATIONS:\n");
        if (stockPercentage > 70) {
            report.append("- Your portfolio is heavily weighted toward stocks. Consider increasing bond allocation for better risk management.\n");
        } else if (bondPercentage > 70) {
            report.append("- Your portfolio is heavily weighted toward bonds. Consider increasing stock allocation for potentially higher returns.\n");
        } else {
            report.append("- Your portfolio has a balanced allocation between stocks and bonds.\n");
        }
    }

    private void generateDebtReport(StringBuilder report) {
        double totalDebt = calculateTotalLiabilities();
        double monthlyPayments = liabilities.stream().mapToDouble(Loan::calculateMonthlyPayment).sum();

        report.append("DEBT OVERVIEW:\n");
        report.append("Total Debt: $").append(formatCurrency(totalDebt)).append("\n");
        report.append("Monthly Debt Payments: $").append(formatCurrency(monthlyPayments)).append("\n\n");

        report.append("LOANS BREAKDOWN:\n");
        for (Loan loan : liabilities) {
            report.append("- ").append(loan.getName()).append(":\n");
            report.append("  Current Balance: $").append(formatCurrency(loan.getValue())).append("\n");
            report.append("  Interest Rate: ").append(loan.getInterestRate()).append("%\n");
            report.append("  Monthly Payment: $").append(formatCurrency(loan.calculateMonthlyPayment())).append("\n");
            report.append("  Payments Made: ").append(loan.getPaymentsMade()).append(" of ").append(loan.getTerm()).append("\n");
            report.append("  Remaining Payments: ").append(loan.getTerm() - loan.getPaymentsMade()).append("\n");

            // Calculate remaining interest
            double remainingInterest = 0;
            double balance = loan.getValue();
            double monthlyRate = loan.getInterestRate() / 100 / 12;
            double payment = loan.calculateMonthlyPayment();
            int remainingPayments = loan.getTerm() - loan.getPaymentsMade();

            for (int i = 0; i < remainingPayments; i++) {
                double interestPayment = balance * monthlyRate;
                double principalPayment = payment - interestPayment;

                remainingInterest += interestPayment;
                balance -= principalPayment;

                if (balance <= 0) break;
            }

            report.append("  Total Remaining Interest: $").append(formatCurrency(remainingInterest)).append("\n\n");
        }

        report.append("DEBT REPAYMENT STRATEGIES:\n");
        report.append("1. Debt Snowball: Pay minimum on all debts, then extra on smallest balance first.\n");
        report.append("2. Debt Avalanche: Pay minimum on all debts, then extra on highest interest rate first.\n");

        // Suggest which approach might be better
        boolean hasHighInterestDebt = liabilities.stream().anyMatch(loan -> loan.getInterestRate() > 8.0);
        if (hasHighInterestDebt) {
            report.append("\nRecommendation: Consider the Debt Avalanche method to minimize interest payments.\n");
        } else {
            report.append("\nRecommendation: Consider the Debt Snowball method for psychological wins and momentum.\n");
        }
    }

    private void generateCashFlowReport(StringBuilder report) {
        // This would be more detailed in a real app with income tracking
        double monthlyLiabilities = liabilities.stream().mapToDouble(Loan::calculateMonthlyPayment).sum();
        double monthlyAssetIncome = assets.stream()
                .filter(a -> a instanceof Bond)
                .mapToDouble(a -> ((Bond) a).calculateInterest() / 12)
                .sum();

        report.append("CASH FLOW ANALYSIS:\n\n");
        report.append("MONTHLY INCOME:\n");
        report.append("- Asset Generated Income: $").append(formatCurrency(monthlyAssetIncome)).append("\n");
        report.append("- Other Income: $0.00 (Add your income sources in a real application)\n");
        report.append("Total Monthly Income: $").append(formatCurrency(monthlyAssetIncome)).append("\n\n");

        report.append("MONTHLY EXPENSES:\n");
        report.append("- Debt Payments: $").append(formatCurrency(monthlyLiabilities)).append("\n");
        report.append("- Other Expenses: $0.00 (Add your expenses in a real application)\n");
        report.append("Total Monthly Expenses: $").append(formatCurrency(monthlyLiabilities)).append("\n\n");

        double netCashFlow = monthlyAssetIncome - monthlyLiabilities;
        report.append("NET MONTHLY CASH FLOW: $").append(formatCurrency(netCashFlow)).append("\n");

        if (netCashFlow < 0) {
            report.append("\nWARNING: Your current expenses exceed your income. Consider reducing expenses or increasing income sources.\n");
        } else {
            report.append("\nYour monthly cash flow is positive. Consider allocating the surplus to savings or investments.\n");
        }
    }

    private double calculateNetWorth() {
        return calculateTotalAssets() - calculateTotalLiabilities();
    }

    private double calculateTotalAssets() {
        return assets.stream().mapToDouble(FinancialAsset::getValue).sum();
    }

    private double calculateTotalLiabilities() {
        return liabilities.stream().mapToDouble(Loan::getValue).sum();
    }

    private String formatCurrency(double amount) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(amount);
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(date);
    }

    private void refreshUI() {
        // Remove current panel and replace with updated one
        frame.remove(currentPanel);

        if (currentPanel == dashboardPanel) {
            dashboardPanel = createDashboardPanel();
            currentPanel = dashboardPanel;
        } else if (currentPanel == assetsPanel) {
            assetsPanel = createAssetsPanel();
            currentPanel = assetsPanel;
        } else if (currentPanel == liabilitiesPanel) {
            liabilitiesPanel = createLiabilitiesPanel();
            currentPanel = liabilitiesPanel;
        } else if (currentPanel == reportsPanel) {
            reportsPanel = createReportsPanel();
            currentPanel = reportsPanel;
        } else if (currentPanel == settingsPanel) {
            settingsPanel = createSettingsPanel();
            currentPanel = settingsPanel;
        }

        frame.add(currentPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private class NavigationListener implements ActionListener {
        private String destination;

        public NavigationListener(String destination) {
            this.destination = destination;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.remove(currentPanel);

            switch (destination) {
                case "Dashboard":
                    currentPanel = dashboardPanel;
                    break;
                case "Assets":
                    currentPanel = assetsPanel;
                    break;
                case "Liabilities":
                    currentPanel = liabilitiesPanel;
                    break;
                case "Reports":
                    currentPanel = reportsPanel;
                    break;
                case "Settings":
                    currentPanel = settingsPanel;
                    break;
            }

            frame.add(currentPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        }
    }

    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start the application
        SwingUtilities.invokeLater(() -> {
            FinancialManager.getInstance();
        });
    }
}