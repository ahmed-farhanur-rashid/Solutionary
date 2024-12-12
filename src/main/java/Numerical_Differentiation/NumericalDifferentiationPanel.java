package Numerical_Differentiation;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;

public class NumericalDifferentiationPanel extends JPanel {

    private static final int FIELD_HEIGHT = 64; // Adjusted field height
    private static final int FONT_SIZE = 18; // Font size for fields

    public NumericalDifferentiationPanel(Color panelColor) {
        // Set panel properties
        setLayout(new GridBagLayout());
        setBackground(panelColor);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: Label & functionField
        addLatexLabel("\\(\\textit{Enter } \\frac{dy}{dx} = f(x, y):\\)", 0, 0, gbc);
        JTextField functionField = createSizedField();
        addField(functionField, 1, 0, 1, gbc);
        addLatexLabel("\\hspace{64px}", 2, 0, gbc);

        // Row 2: Initial Conditions Panel
        addLatexLabel("\\(\\textit{Initial Conditions:}\\)", 0, 1, gbc);

        // Create FlowLayout panel for initial conditions
        JPanel initialConditionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        initialConditionsPanel.setBackground(getBackground()); // Match parent panel background

        // Add components to initialConditionsPanel
        initialConditionsPanel.add(createLatexLabel("y("));

        JTextField x0Field = createSizedField(96); // Field for x0
        initialConditionsPanel.add(x0Field);

        initialConditionsPanel.add(createLatexLabel(") = "));

        JTextField y0Field = createSizedField(96); // Field for y0
        initialConditionsPanel.add(y0Field);

        // Add initialConditionsPanel to the main layout
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        add(initialConditionsPanel, gbc);

        // Row 3: Precision ComboBox
        addLatexLabel("\\(\\textit{Select Precision:}\\)", 0, 2, gbc);
        JComboBox<String> eqnTypeComboBox = new JComboBox<>(new String[]{
                "Runge-Kutta 1st Order",
                "Runge-Kutta 2nd Order",
                "Runge-Kutta 3rd Order",
                "Runge-Kutta 4th Order"
        });
        eqnTypeComboBox.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        addField(eqnTypeComboBox, 1, 2, 1, gbc);

        // Row 4: Calculate & Clear Buttons
        JButton calculateButton = new JButton("Calculate");
        JButton clearButton = new JButton("Clear");

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(calculateButton, gbc);

        gbc.gridx = 1;
        add(clearButton, gbc);

        // Row 5: Result Label
        JLabel resultLabel = new JLabel("Results will be displayed here.");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(resultLabel, gbc);
    }

    // Helper method to create a LaTeX label
    private JLabel createLatexLabel(String latexText) {
        TeXFormula formula = new TeXFormula(latexText);
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
        JLabel latexLabel = new JLabel(icon);
        latexLabel.setForeground(Color.WHITE);
        return latexLabel;
    }

    // Adds a LaTeX label directly to the panel with constraints
    private void addLatexLabel(String latexText, int x, int y, GridBagConstraints gbc) {
        JLabel latexLabel = createLatexLabel(latexText);
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(latexLabel, gbc);
    }

    // Helper method to create a consistent text field
    private JTextField createSizedField() {
        return createSizedField(512); // Default width for large fields
    }

    private JTextField createSizedField(int width) {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(width, FIELD_HEIGHT));
        field.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        return field;
    }

    // Adds a component (e.g., text field or combo box) to the panel
    private void addField(JComponent component, int x, int y, int width, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.weightx = 1;
        add(component, gbc);
    }
}
