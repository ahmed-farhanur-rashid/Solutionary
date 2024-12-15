package ATES;
import UI.CompUtil;
import UI.ErrorUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ATESPanel extends JPanel {

    private static final int FIELD_WIDTH = 256; // Adjusted field width
    private static final int FIELD_HEIGHT = 64; // Adjusted field height
    private static final int FONT_SIZE = 17; // Font size for fields
    private static final int LATEX_LABEL_SIZE = 21;
    private final JTable table;
    private DefaultTableModel tableModel;

    public ATESPanel(Color panelColor) {

        // Setting up panel
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, FONT_SIZE));
        CompUtil.setUpPanel(this, panelColor);

        // Setting up layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Stretch components horizontally


        //*****************//
        //      Row 0      //
        //*****************//


        // Column 0
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Enter    f(x): }\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 0, 1, 1, 0, 0, gbc);

        // Column 1, 2
        JTextField functionField = CompUtil.createCustomField(FIELD_WIDTH, FIELD_HEIGHT, FONT_SIZE);
        CompUtil.addComponent(this, functionField, 1, 0, 2, 1, 1, 0, gbc);


        //*****************//
        //      Row 1      //
        //*****************//


        // Column 0
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Tolerance: }\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 1, 1, 1, 0, 0, gbc);

        // Column 1, 2
        JTextField toleranceField = CompUtil.createCustomField(FIELD_WIDTH, FIELD_HEIGHT, FONT_SIZE);
        CompUtil.addComponent(this, toleranceField, 1, 1, 2, 1, 1, 0, gbc);


        //*****************//
        //      Row 2      //
        //*****************//


        // Column 0
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Lower Bound  (a): }\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 2, 1, 1, 0, 0, gbc);

        // Column 1, 2
        JTextField aField = CompUtil.createCustomField(FIELD_WIDTH, FIELD_HEIGHT, FONT_SIZE);
        CompUtil.addComponent(this, aField, 1, 2, 2, 1, 1, 0, gbc);


        //*****************//
        //      Row 3      //
        //*****************//


        // Column 0
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Upper Bound  (b): }\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 3, 1, 1, 0, 0, gbc);

        // Column 1, 2
        JTextField bField = CompUtil.createCustomField(FIELD_WIDTH, FIELD_HEIGHT, FONT_SIZE);
        CompUtil.addComponent(this, bField, 1, 3, 2, 1, 1, 0, gbc);


        //*****************//
        //      Row 4      //
        //*****************//


        // Column 0
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("", LATEX_LABEL_SIZE, Color.WHITE),
                0, 4, 1, 1, 0, 0, gbc);

        // Column 1, 2
        JCheckBox autoFindABCheckbox = new JCheckBox("      Automatically find interval [a, b]");

        autoFindABCheckbox.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        autoFindABCheckbox.setForeground(Color.WHITE); // Text color
        CompUtil.addComponent(this, autoFindABCheckbox, 1, 4, 2, 1, 0, 0, gbc);


        //*****************//
        //      Row 5      //
        //*****************//


        // Column 0
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Select Calculation Method:           }\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 5, 1, 1, 0, 0, gbc);

        // Column 1, 2
        JComboBox<String> methodComboBox = new JComboBox<>(new String[]{
                "Bisection", "False Position", "Newton Raphson"
        });
        methodComboBox.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        CompUtil.addComponent(this, methodComboBox, 1, 5 ,2, 1, 0, 0, gbc);


        //*****************//
        //      Row 6      //
        //*****************//


        // Column 0
        JButton calculateButton = new JButton("Calculate");
        CompUtil.addComponent(this, calculateButton, 0, 6, 1, 1, 0, 0, gbc);

        // Column 1
        JLabel resultLabel = new JLabel("Root:   ");
        resultLabel.setHorizontalAlignment(SwingConstants.LEFT); // Align text to the left
        resultLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE)); // Optional: Adjust font size and style
        CompUtil.addComponent(this, resultLabel, 1, 6, 1, 1, 0, 0, gbc);

        // Column 2
        JButton clearButton = new JButton("Clear");
        CompUtil.addComponent(this, clearButton, 2, 6, 1, 1, 0, 0, gbc);


        //*****************//
        //      Row 7      //
        //*****************//


        // Column 0, 1, 2
        tableModel = new DefaultTableModel(); // Dummy table model, needed to create table properly.
        table = CompUtil.createCustomTable(tableModel, FONT_SIZE + 1);

        // Create a scrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Adding scroll pane to layout
        gbc.fill = GridBagConstraints.BOTH; // Stretch in both directions
        CompUtil.addComponent(this, scrollPane, 0, 7, 3, 1, 1, 1, gbc);


        //**************************************************//
        //      Action Listener & Logic Implementation      //
        //**************************************************//


        // Calculate button
        calculateButton.addActionListener(_ -> {
            try {
                String stringExpression = functionField.getText();
                Expression expression = new ExpressionBuilder(stringExpression).variables("x").build();

                double a;
                double b;

                if(autoFindABCheckbox.isSelected()) {
                    Interval interval = IntervalFinder.findInterval(expression);

                    a = interval.a;
                    b = interval.b;

                    boolean isNaN = Double.isNaN(a) || Double.isNaN(b);

                    aField.setText(isNaN ? "No interval found" : String.valueOf(a));
                    bField.setText(isNaN ? "No interval found" : String.valueOf(b));

                    if(isNaN) {
                        ErrorUtil.noIntervalError();
                    }
                }
                else {
                    a = Double.parseDouble(aField.getText());
                    b = Double.parseDouble(bField.getText());
                }

                double tolerance = Double.parseDouble(toleranceField.getText());
                double root = 0;
                String method = (String) methodComboBox.getSelectedItem();

                tableModel.setRowCount(0);

                assert method != null;
                switch (method) {
                    case "Bisection" -> {

                        String[] columnNames = {"Iteration", "Lower Bound (a)", "Upper Bound (b)", "Midpoint", "f(Midpoint)"};
                        tableModel = new DefaultTableModel(columnNames, 0);
                        table.setModel(tableModel);

                        root = Bisection.execute(expression, a, b, tolerance, tableModel);

                    }
                    case "False Position" -> {

                        String[] columnNames = {"Iteration", "Lower Bound (a)", "Upper Bound (b)", "Midpoint", "f(a)", "f(b)", "f(Midpoint)"};
                        tableModel = new DefaultTableModel(columnNames, 0);
                        table.setModel(tableModel);

                        root = FalsePosition.execute(expression, a, b, tolerance, tableModel);

                    }
                    case "Newton Raphson" -> {

                        String[] columnNames = {"Iteration", "<html>X<sub>n</sub></html>", "<html>X<sub>n+1</sub></html>"};
                        tableModel = new DefaultTableModel(columnNames, 0);
                        table.setModel(tableModel);

                        root = NewtonRaphson.execute(expression, a, b, tolerance, tableModel);
                    }
                }

                resultLabel.setText("Root: " + (Double.isNaN(root) ? "No root in interval" : root));
                resultLabel.setForeground(Color.RED);
            } catch(Exception exception) {
                exception.printStackTrace(System.out);
            }
        });

        // Clear button
        clearButton.addActionListener(_ -> {
            functionField.setText("");
            toleranceField.setText("");
            aField.setText("");
            bField.setText("");

            autoFindABCheckbox.setSelected(false);

            // Clearing table non need to tableModel.setRowCount(0);
            tableModel = null;
            tableModel = new DefaultTableModel(); // Dummy table model, needed to create table properly.
            table.setModel(tableModel);

            resultLabel.setText("Root: "); // Reset the result label
            resultLabel.setForeground(Color.WHITE);
        });
    }
}
