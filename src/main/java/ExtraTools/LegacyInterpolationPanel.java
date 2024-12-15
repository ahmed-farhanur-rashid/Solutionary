package ExtraTools;

import UI.CompUtil;
import UI.ErrorUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LegacyInterpolationPanel extends JPanel {

    private static final int FIELD_WIDTH = 256; // Adjusted field width
    private static final int FIELD_HEIGHT = 64; // Adjusted field height
    private static final int FONT_SIZE = 18; // Font size for fields
    private static final int LATEX_LABEL_SIZE = 22;
    private final JTable table;
    private DefaultTableModel tableModel;

    public LegacyInterpolationPanel(Color panelColor) {

        // Setting Up Panel
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, FONT_SIZE));
        CompUtil.setUpPanel(this, panelColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Insets are border between elements
        gbc.fill = GridBagConstraints.HORIZONTAL; // Stretches Component Horizontally


        //*****************//
        //      Row 0      //
        //*****************//


        // Column 0
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{(Optional) Enter y = f(x):}\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 0, 1, 1, 0, 0, gbc);

        // Column 1, 2
        JTextField functionField = CompUtil.createCustomField(FIELD_WIDTH, FIELD_HEIGHT, FONT_SIZE);
        CompUtil.addComponent(this, functionField, 1, 0, 2, 1, 0, 0, gbc);


        //*****************//
        //      Row 1      //
        //*****************//


        // Column 0
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Enter Number of Elements, (n):}\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 1, 1, 1, 0, 0, gbc);

        // Column 1
        JTextField nField = CompUtil.createCustomField(FIELD_WIDTH, FIELD_HEIGHT, FONT_SIZE);
        CompUtil.addComponent(this, nField, 1, 1, 1, 1, 1, 0, gbc);

        // Column 2
        JButton enterButton = new JButton("Enter");
        CompUtil.addComponent(this, enterButton, 2, 1, 1, 1, 0, 0, gbc);


        //*****************//
        //      Row 2      //
        //*****************//


        // Column 0
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Input Table:}\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 2, 1, 1, 0, 0, gbc);

        // Column 1, 2
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"x", "y"});
        table = CompUtil.createCustomTable(tableModel, FONT_SIZE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        gbc.fill = GridBagConstraints.BOTH; // Stretch in both directions
        CompUtil.addComponent(this, scrollPane, 1, 2, 2, 1, 1, 1, gbc);


        //*****************//
        //      Row 3      //
        //*****************//


        // Column 0
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\textit{Determine } y = f(x) \\text{ at } x:", LATEX_LABEL_SIZE, Color.WHITE),
                0, 3, 1, 1, 0, 0, gbc);

        // Column 1
        JTextField xField = CompUtil.createCustomField(FIELD_WIDTH, FIELD_HEIGHT, FONT_SIZE);
        CompUtil.addComponent(this, xField, 1, 3, 1, 1, 1, 0, gbc);

        // Column 2, Row - 3 & 4
        JLabel resultLabel = new JLabel("   y = f(x) at x: ");
        resultLabel.setHorizontalAlignment(SwingConstants.LEFT);
        resultLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        CompUtil.addComponent(this, resultLabel, 2, 3, 1, 2, 1, 0, gbc);


        //*****************//
        //      Row 4      //
        //*****************//


        // Column 0
        JButton clearButton = new JButton("Clear");
        CompUtil.addComponent(this, clearButton, 0, 4, 1, 1, 0, 0, gbc);

        // Column 1
        JButton calculateButton = new JButton("Calculate");
        CompUtil.addComponent(this, calculateButton, 1, 4, 1, 1, 0, 0, gbc);

        // Column 2 contains lower half of result Label.

//        This is for future use.
//        JButton showStepsButton = new JButton("Show Steps");
//        add(showStepsButton, gbc);


        //*****************//
        //      Row 5      //
        //*****************//


        JLabel emptyLabel = new JLabel("");
        CompUtil.addComponent(this, emptyLabel, 0, 5, 3, 1, 1, 0, gbc);


        //**************************************************//
        //      Action Listener & Logic Implementation      //
        //**************************************************//

        // Enter Button
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

        // Calculate Button
        calculateButton.addActionListener(_ -> {

        });

        // Clear Button
        clearButton.addActionListener(_ -> {
            functionField.setText("");
            nField.setText("");
            tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"x", "y"});
            table.setModel(tableModel);
            xField.setText("");
            resultLabel.setText("   y = f(x) at x: ");
        });
    }

}
