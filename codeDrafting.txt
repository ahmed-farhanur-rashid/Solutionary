package ATES;
import UI.ErrorUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class ATESPanel extends JPanel {

    private static final int FIELD_HEIGHT = 64; // Double the height of fields
    private static final int FONT_SIZE = 18; // Larger text size for fields
    private final JTable table;
    private DefaultTableModel tableModel;

    public ATESPanel(Color panelColor) {

        UIManager.put("Button.font", new Font("Arial", Font.BOLD, FONT_SIZE));

        setLayout(new GridBagLayout());
        setBackground(panelColor);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around the panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Stretch components horizontally

        // Row 1: f(x)
        addLatexLabel("f(x):", 0, 0, gbc);
        JTextField functionField = createSizedField();
        addField(functionField, 1, 0, 2, gbc);

        // Row 2: Tolerance
        addLatexLabel("Tolerance:", 0, 1, gbc);
        JTextField toleranceField = createSizedField();
        addField(toleranceField, 1, 1, 2, gbc);

        // Row 3: Lower Bound (a)
        addLatexLabel("Lower Bound \\(\\text{ (a)}\\):", 0, 2, gbc);
        JTextField aField = createSizedField();
        addField(aField, 1, 2, 2, gbc);

        // Row 4: Upper Bound (b)
        addLatexLabel("Upper Bound \\(\\text{ (b)}\\):", 0, 3, gbc);
        JTextField bField = createSizedField();
        addField(bField, 1, 3, 2, gbc);

        // Row 5: Empty space and auto interval checkbox
        addLatexLabel("", 0, 4, gbc); // Empty label
        JCheckBox autoFindABCheckbox = new JCheckBox("      Automatically find interval [a, b]");

        autoFindABCheckbox.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        autoFindABCheckbox.setForeground(Color.WHITE); // Text color
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Span across columns 2 and 3
        add(autoFindABCheckbox, gbc);

        // Row 6: Method selection dropdown
        addLatexLabel("\\(\\textit{Select Calculation Method:}\\)", 0, 5, gbc);
        JComboBox<String> methodComboBox = new JComboBox<>(new String[]{
                "Bisection", "False Position", "Fixed Point Iteration", "Newton Raphson", "Secant"
        });
        methodComboBox.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        addField(methodComboBox, 1, 5, 2, gbc);

        // Row 7: Calculate button, result label, and clear button
        JButton calculateButton = new JButton("Calculate");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1; // Single column
        add(calculateButton, gbc);

        // Result label and clear button
        JLabel resultLabel = new JLabel("Root:   ");
        resultLabel.setHorizontalAlignment(SwingConstants.LEFT); // Align text to the left
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Optional: Adjust font size and style
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1; // Single column
        add(resultLabel, gbc);

        JButton clearButton = new JButton("Clear");
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 1; // Single column
        add(clearButton, gbc);

        // Row 8: JTable for iterations
        tableModel = new DefaultTableModel(); // Dummy table model, needed to create table properly.
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.BOLD, 16));
        table.setRowHeight(table.getRowHeight() + 20);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
        tableHeader.setPreferredSize(new Dimension(tableHeader.getPreferredSize().width, 40));

        // Create a scrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add the scrollPane to the layout
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 3; // Span across all three columns
        gbc.weightx = 1;   // Allow horizontal expansion
        gbc.weighty = 1;   // Allow vertical expansion
        gbc.fill = GridBagConstraints.BOTH; // Stretch in both directions
        add(scrollPane, gbc);


        // Logic Implementation
        calculateButton.addActionListener(e -> {
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

        clearButton.addActionListener(e -> {
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

    // Helper method to add a LaTeX label
    private void addLatexLabel(String latexText, int x, int y, GridBagConstraints gbc) {
        TeXFormula formula = new TeXFormula(latexText);
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
        JLabel latexLabel = new JLabel(icon);
        latexLabel.setForeground(Color.WHITE); // White text color
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1; // Single column
        gbc.weightx = 0; // No horizontal expansion
        gbc.weighty = 0; // No vertical expansion
        gbc.anchor = GridBagConstraints.EAST; // Align to the right
        add(latexLabel, gbc);
    }

    // Helper method to create a resized text field
    private JTextField createSizedField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(0, FIELD_HEIGHT)); // Height adjustment
        field.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE)); // Font size
        return field;
    }

    // Helper method to add a text field or other input component
    private void addField(JComponent component, int x, int y, int width, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width; // Span across columns
        gbc.weightx = 1; // Allow horizontal expansion
        add(component, gbc);
    }
}
