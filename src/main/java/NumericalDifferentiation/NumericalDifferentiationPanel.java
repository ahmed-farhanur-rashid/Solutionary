package NumericalDifferentiation;

import javax.swing.*;
import java.awt.*;

import UI.CompUtil;

public class NumericalDifferentiationPanel extends JPanel {

    private static final int FIELD_WIDTH = 512; // Adjusted field width
    private static final int FIELD_HEIGHT = 64; // Adjusted field height
    private static final int FONT_SIZE = 18; // Font size for fields
    private static final int LATEX_LABEL_SIZE = 22;

    public NumericalDifferentiationPanel(Color panelColor) {
        // Set panel properties
        CompUtil.setUpPanel(this, panelColor);

        // Setting up GridBag Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: Label & functionField
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Enter } \\frac{dy}{dx} = f(x, y):\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 0, 1, 1, 1, 0, gbc
        );

        JTextField functionField = CompUtil.createCustomField(FIELD_WIDTH, FIELD_HEIGHT, FONT_SIZE);
        CompUtil.addComponent(this, functionField, 1, 0, 1, 1, 0, 0, gbc);

        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\hspace{32px}", LATEX_LABEL_SIZE, Color.WHITE),
                2, 0, 1, 1, 1, 0, gbc
        );

        // Row 2: Initial Conditions Panel
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Initial Conditions:}\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 1, 1, 1, 1, 0, gbc
        );

        // Create FlowLayout panel for initial conditions
        JPanel initialConditionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        initialConditionsPanel.setBackground(getBackground()); // Match parent panel background

        // Add components to initialConditionsPanel
        initialConditionsPanel.add(CompUtil.createLatexLabel("y(", LATEX_LABEL_SIZE, Color.WHITE));

        JTextField x0Field = CompUtil.createCustomField(96, FIELD_HEIGHT, FONT_SIZE);
        initialConditionsPanel.add(x0Field);

        initialConditionsPanel.add(CompUtil.createLatexLabel(") = ", LATEX_LABEL_SIZE, Color.WHITE));

        JTextField y0Field = CompUtil.createCustomField(96, FIELD_HEIGHT, FONT_SIZE);
        initialConditionsPanel.add(y0Field);

        initialConditionsPanel.add(CompUtil.createLatexLabel("\\hspace{64px};[y(x_{0}) = y_{0}]", LATEX_LABEL_SIZE, Color.WHITE));

        // Add initialConditionsPanel to the main layout
        CompUtil.addComponent(this, initialConditionsPanel, 1, 1, 3, 1, 0, 0, gbc);

        // Row 3: Precision ComboBox

        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Select Precision:}\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 2, 1, 1, 1, 0, gbc
        );

        JComboBox<String> eqnTypeComboBox = new JComboBox<>(new String[]{
                "Runge-Kutta 1st Order",
                "Runge-Kutta 2nd Order",
                "Runge-Kutta 3rd Order",
                "Runge-Kutta 4th Order"
        });
        eqnTypeComboBox.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        CompUtil.addComponent(this, eqnTypeComboBox, 1, 2, 1, 1, 1, 0, gbc);

        // Row 4: Calculate & Clear Buttons
        JButton clearButton = new JButton("Clear");
        CompUtil.addComponent(this, clearButton, 0, 3, 1, 1, 1, 0, gbc);

        JButton calculateButton = new JButton("Calculate");
        CompUtil.addComponent(this, calculateButton, 1, 3, 1, 1, 1, 0, gbc);

        // Row 5: Result Label
        JLabel resultLabel = new JLabel("Results will be displayed here.");
        resultLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));

        CompUtil.addComponent(this, resultLabel, 0, 4, 2, 1, 1, 0, gbc);
    }
}
