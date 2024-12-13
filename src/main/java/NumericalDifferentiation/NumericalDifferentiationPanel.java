package NumericalDifferentiation;

import javax.swing.*;
import java.awt.*;

import UI.CompUtil;
import UI.ErrorUtil;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class NumericalDifferentiationPanel extends JPanel {

    private static final int FIELD_WIDTH = 512; // Adjusted field width
    private static final int FIELD_HEIGHT = 48; // Adjusted field height
    private static final int FONT_SIZE = 18; // Font size for fields
    private static final int LATEX_LABEL_SIZE = 22;

    @SuppressWarnings("RedundantLabeledSwitchRuleCodeBlock")
    public NumericalDifferentiationPanel(Color panelColor) {
        // Set panel properties
        CompUtil.setUpPanel(this, panelColor, true);
        UIManager.put("ScrollBar.thumbArc", 10); // Rounded thumb
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("ScrollBar.track", Color.DARK_GRAY);
        UIManager.put("ScrollBar.width", 20); // Explicit width


        // Setting up GridBag Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Label & functionField
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Enter } \\frac{dy}{dx} = f(x, y):\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 0, 1, 1, 1, 0, gbc
        );

        JTextField functionField = CompUtil.createCustomField(FIELD_WIDTH, FIELD_HEIGHT, FONT_SIZE);
        functionField.setMinimumSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        CompUtil.addComponent(this, functionField, 1, 0, 1, 1, 1, 0, gbc);

//        CompUtil.addComponent(this,
//                CompUtil.createLatexLabel("\\hspace{16px}", LATEX_LABEL_SIZE, Color.WHITE),
//                2, 0, 1, 1, 1, 0, gbc
//        );

        // Row 1: Initial Conditions Panel
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Initial Conditions:}\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 1, 1, 1, 1, 0, gbc
        );

        // Create FlowLayout panel for initial conditions
        JPanel initialConditionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        initialConditionsPanel.setBackground(getBackground()); // Match parent panel background
        initialConditionsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

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

        // Row 2

        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\textit{Enter Step Size (h):}", LATEX_LABEL_SIZE, Color.WHITE),
                0, 2, 1, 1, 1, 0, gbc
        );

        JTextField stepSizeField = CompUtil.createCustomField(256, 32, FONT_SIZE);
        CompUtil.addComponent(this, stepSizeField, 1, 2, 1, 1, 0, 0, gbc);

        // Row 3

        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\textit{Solve for } x_{n}:", LATEX_LABEL_SIZE, Color.WHITE),
                0, 3, 1, 1, 1, 0, gbc
        );

        JTextField solveForXField = CompUtil.createCustomField(256, 32, FONT_SIZE);
        CompUtil.addComponent(this, solveForXField, 1, 3, 1, 1, 0, 0, gbc);

        // Row 4: Precision ComboBox

        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Select Precision:}\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 4, 1, 1, 1, 0, gbc
        );

        JComboBox<String> precisionComboBox = new JComboBox<>(new String[]{
                "Runge-Kutta 1st Order",
                "Runge-Kutta 2nd Order",
                "Runge-Kutta 3rd Order",
                "Runge-Kutta 4th Order"
        });
        precisionComboBox.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        CompUtil.addComponent(this, precisionComboBox, 1, 4, 1, 1, 1, 0, gbc);

        // Row 5: Calculate & Clear Buttons
        JButton clearButton = new JButton("Clear");
        CompUtil.addComponent(this, clearButton, 0, 5, 1, 1, 1, 0, gbc);

        JButton calculateButton = new JButton("Calculate");
        CompUtil.addComponent(this, calculateButton, 1, 5, 1, 1, 1, 0, gbc);

        // Row 6: Result Panel with JScrollPane;
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //resultPanel.setBackground(getBackground());

        gbc.fill = GridBagConstraints.BOTH;

        JScrollPane scrollbar = new JScrollPane(resultPanel);
        scrollbar.getVerticalScrollBar().setUnitIncrement(16);
        scrollbar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollbar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        CompUtil.addComponent(this, scrollbar, 0, 6, 2, 1, 1, 1, gbc);

        //******************************************************//
        //      Action Listener and Logic Implementations       //
        //******************************************************//

        calculateButton.addActionListener(_ -> {
            try {
                String stringExpression = functionField.getText();
                Expression expression = new ExpressionBuilder(stringExpression).variables("x", "y").build();

                double x0 = Double.parseDouble(x0Field.getText());
                double y0 = Double.parseDouble(y0Field.getText());
                double stepSize = Double.parseDouble(stepSizeField.getText());
                double xn = Double.parseDouble(solveForXField.getText());

                String method = (String) precisionComboBox.getSelectedItem();

                assert method != null;
                switch (method) {
                    case "Runge-Kutta 1st Order" -> {
                        RungeKutta.Runge_Kutta_1st_Order(expression, stepSize, x0, y0, xn, resultPanel);
                    }
                    case "Runge-Kutta 2nd Order" -> {
                        RungeKutta.Runge_Kutta_2nd_Order(expression, stepSize, x0, y0, xn, resultPanel);
                    }
                    case "Runge-Kutta 3rd Order" -> {
                        RungeKutta.Runge_Kutta_3rd_Order(expression, stepSize, x0, y0, xn, resultPanel);
                    }
                    case "Runge-Kutta 4th Order" -> {
                        RungeKutta.Runge_Kutta_4th_Order(expression, stepSize, x0, y0, xn, resultPanel);
                    }
                }
            } catch (NumberFormatException ex) {
                ErrorUtil.invalidInput();
            }
        });

    }
}
