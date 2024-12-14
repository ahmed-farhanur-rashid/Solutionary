package CurveFitting;

import CurveFitting.Linear.*;
import CurveFitting.NonLinear.*;
import UI.CompUtil;
import UI.ErrorUtil;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.util.List;

public class CurveFittingPanel extends JPanel {

    private static final int FIELD_WIDTH = 256; // Adjusted field width
    private static final int FIELD_HEIGHT = 64; // Adjusted field height
    private static final int FONT_SIZE = 18; // Font size for fields
    private static final int LATEX_LABEL_SIZE = 22;

    private final JTable table;
    private DefaultTableModel tableModel;

    public CurveFittingPanel(Color panelColor) {

        UIManager.put("Button.font", new Font("Arial", Font.BOLD, FONT_SIZE));
        CompUtil.setUpPanel(this, panelColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Insets are border between elements
        gbc.fill = GridBagConstraints.HORIZONTAL; // Stretches Component Horizontally


        // Row 1: DataPoint and nField
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Enter Number of Data Points (n):}\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 0, 1, 1, 0, 0, gbc);

        JTextField nField = CompUtil.createCustomField(FIELD_WIDTH, FIELD_HEIGHT, FONT_SIZE);
        CompUtil.addComponent(this, nField, 1, 0, 1, 1, 0.5, 0, gbc);

        JButton enterButton = new JButton("Enter");
        CompUtil.addComponent(this, enterButton, 2, 0, 1, 1, 0.5, 0, gbc);

        // Row 2: Table Input
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Input Table:}\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 1, 1, 1, 0, 0, gbc);

        // Row 2: JTable for input
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"x", "y"});
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.BOLD, 16));
        table.setRowHeight(table.getRowHeight() + 20);

        table.setShowGrid(true); // Enable gridlines
        table.setGridColor(Color.DARK_GRAY); // Set GridLine color

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
        tableHeader.setPreferredSize(new Dimension(tableHeader.getPreferredSize().width, 40));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        gbc.fill = GridBagConstraints.BOTH; // Stretch in both directions
        CompUtil.addComponent(this, scrollPane, 1, 1, 2, 1, 1, 1, gbc);
        //gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 3: comboBox
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Type of Eqn. To Fit To Data Points (n):}\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 2, 1, 1, 0, 0, gbc);

        JComboBox<String> eqnTypeComboBox = new JComboBox<>(new String[]{
                "<html><b>y = ax + b</b></html>",                   // Bold
                "<html><b>y = ae<sup>bx</sup></b></html>",          // Superscript
                "<html><b>y = ab<sup>x</sup></b></html>",           // Superscript
                "<html><b>y = ax<sup>2</sup> + bx + c</b></html>"   // Quadratic with superscript
        });
        eqnTypeComboBox.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));

        CompUtil.addComponent(this, eqnTypeComboBox, 1, 2, 2, 1, 1, 0, gbc);

        // Row 4: Calculate & Clear
        JButton clearButton = new JButton("Clear");
        CompUtil.addComponent(this, clearButton, 0, 3, 1, 1, 1, 0, gbc);

        JButton calculateButton = new JButton("Calculate");
        CompUtil.addComponent(this, calculateButton, 1, 3, 2, 1, 1, 0, gbc);

        // Row 6: Result Label
        JLabel resultLabel = new JLabel("Results will be displayed here.");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));

        CompUtil.addComponent(this, resultLabel, 0, 4, 2, 2, 0, 0, gbc);


        // Taking Input From Table
        enterButton.addActionListener(_ -> {
            try {
                // Parse the input from the nField
                int numRows = Integer.parseInt(nField.getText().trim());

                if (numRows <= 0) {
                    throw new NumberFormatException("Number of rows must be positive.");
                }

                // Clear existing table data
                tableModel.setRowCount(0);

                // Add the specified number of rows to the table
                for (int i = 0; i < numRows; i++) {
                    tableModel.addRow(new Object[]{"", ""}); // Add empty rows with 2 columns (X, Y)
                }
                table.setModel(tableModel);

            } catch (NumberFormatException ex) {
                ErrorUtil.invalidNumberOfRows();
            }
        });

        // Program Logic
        calculateButton.addActionListener(_ -> {

            List<Double> xList = CompUtil.extractTableColumnData(table, 0);
            List<Double> yList = CompUtil.extractTableColumnData(table, 1);

            int n = Integer.parseInt(nField.getText());
            String method = (String) eqnTypeComboBox.getSelectedItem();

            assert method != null;
            switch (method) {
                case "<html><b>y = ax + b</b></html>" -> {

                    String[] columnNames = {"X", "Y", "XY", "X²"};
                    tableModel = new DefaultTableModel(columnNames, 0);
                    table.setModel(tableModel);

                    double[] solution = LinearEquation.fit(xList, yList, n, tableModel);

                    resultLabel.setText("a = " + solution[0] + ",   b = " + solution[1]);
                }
                case "<html><b>y = ae<sup>bx</sup></b></html>" -> {

                    String[] columnNames = {"X", "y", "Y = lny", "XY", "X²"};
                    tableModel = new DefaultTableModel(columnNames, 0);
                    table.setModel(tableModel);

                    double[] solution = ExponentialEquation.fit(xList, yList, n, tableModel);

                    resultLabel.setText("a = " + Math.exp(solution[1]) + ",   b = " + solution[0]); // AntiLog of ln is Math.exp()
                }
                case "<html><b>y = ab<sup>x</sup></b></html>" -> {
                    String[] columnNames = {"X", "y", "Y = lny", "XY", "X²"};
                    tableModel = new DefaultTableModel(columnNames, 0);
                    table.setModel(tableModel);

                    double[] solution = ExponentialEquation.fit(xList, yList, n, tableModel);

                    resultLabel.setText("a = " + Math.exp(solution[1]) + ",   b = " + Math.exp(solution[0]));
                }
                case "<html><b>y = ax<sup>2</sup> + bx + c</b></html>" -> {

                    String[] columnNames = {"X", "Y", "XY", "X²Y", "X²", "X³", "X⁴"};
                    tableModel = new DefaultTableModel(columnNames, 0);
                    table.setModel(tableModel);

                    double[] solution = QuadraticEquation.fit(xList, yList, n, tableModel);

                    resultLabel.setText("a = " + solution[0] + ",   b = " + solution[1] + ",   c = " + solution[2]);
                }
            }
        });

        // Clear Button
        clearButton.addActionListener(_ -> {
            nField.setText("");
            tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"x", "y"});
            table.setModel(tableModel);
            resultLabel.setText("Results will be displayed here.");
        });
    }
}
