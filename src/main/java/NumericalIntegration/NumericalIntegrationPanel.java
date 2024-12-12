package NumericalIntegration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import UI.CompUtil;
import UI.ErrorUtil;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class NumericalIntegrationPanel extends JPanel {

    private static final int FIELD_HEIGHT = 64; // Double the height of fields
    private static final int FIELD_WIDTH = 300;
    private static final int FONT_SIZE = 18; // Larger text size for fields
    private static final int LATEX_LABEL_SIZE = 22;


    public NumericalIntegrationPanel(Color panelColor) {

        UIManager.put("Button.font", new Font("Arial", Font.BOLD, FONT_SIZE));

        CompUtil.setUpPanel(panelColor, this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        //************************************************************************//
        // Adding components to GridBag layout, Row & Column starts at zero here. //
        //************************************************************************//

        //*****************//
        //      Row 0      //
        //*****************//

        // column 0, 1: is empty.
        // Adds upper limit field (on top of Integral sign), column: 2.
        JTextField bField = CompUtil.createCustomField(96, 32, 16);
        CompUtil.addComponent(this, bField, 2, 0, 1, 1, 0, 0, gbc);
        // column 3, 4, 5: is empty.

        //*****************//
        //      Row 1      //
        //*****************//

        // Adds label("Enter Function and It's Limits:"), column: 0.
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Enter Function and It's Limits:}\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 1, 1, 1, 0, 0, gbc
        );

        // Adds emptySpace(64px), column: 1.
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\hspace{24px}", LATEX_LABEL_SIZE, Color.WHITE),
                1, 1, 1, 1, 0, 0, gbc
        );

        // Adds Integral Symbol, column: 2.
        JLabel integralSign = CompUtil.createLatexLabel("\\int\\hspace{16pt}", LATEX_LABEL_SIZE, Color.WHITE);
        integralSign.setHorizontalAlignment(JLabel.CENTER);
        CompUtil.addComponent(this, integralSign, 2, 1, 1, 1, 0, 0, gbc
        );

        // Adds function field, column: [3, 4]: width 2.
        JTextField functionField = CompUtil.createCustomField(FIELD_WIDTH, FIELD_HEIGHT, FONT_SIZE);
        CompUtil.addComponent(this, functionField, 3, 1, 2, 1, 0, 0, gbc);

        // To dynamically set function field size, fucking finally.
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(functionField); // Get the parent JFrame
                int size;
                // Check if the frame is fullscreen
                if (frame != null && frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                    size = 2 * FIELD_WIDTH; // Set size for fullscreen
                } else {
                    size = FIELD_WIDTH; // Set size for normal state
                }
                functionField.setPreferredSize(new Dimension(size, FIELD_HEIGHT));
                revalidate();
                repaint();
            }
        });

        // Adds dx sign, column: 5

        JLabel dxLabel = CompUtil.createLatexLabel("dx", 30, Color.WHITE);
        dxLabel.setHorizontalAlignment(JLabel.CENTER);
        CompUtil.addComponent(this, dxLabel, 5, 1, 1, 1, 0, 0, gbc
        );

        //*****************//
        //      Row 2      //
        //*****************//

        // column 0, 1: is empty.
        // Adds field for lower limit (below Integral sign), column: 2.
        JTextField aField = CompUtil.createCustomField(96, 32, 16);
        CompUtil.addComponent(this, aField, 2, 2, 1, 1, 0, 0, gbc);
        // column 3, 4, 5: is empty.

        //*****************//
        //      Row 3      //
        //*****************//

        // Adds label for dropdown menu, column: 0.
        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Select Calculation Method:}\\)", LATEX_LABEL_SIZE, Color.WHITE),
                0, 3, 1, 1, 0, 0, gbc
        );

        // column 1: is empty.

        // Adds method drop down menu, column: 2.
        JComboBox<String> methodComboBox = new JComboBox<>(new String[]{
                "Trapezoidal Rule", "Simpson's ⅓ Rule", "Simpson's ⅜ Rule", "Weddle's Rule"
        });
        methodComboBox.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        CompUtil.addComponent(this, methodComboBox, 2, 3, 3, 1, 0, 0, gbc);

        //*****************//
        //      Row 4      //
        //*****************//

        CompUtil.addComponent(this,
                CompUtil.createLatexLabel("\\(\\textit{Enter The Number of Intervals (n):}\\)",
                        LATEX_LABEL_SIZE, Color.WHITE),
                0, 4, 1, 1, 0, 0, gbc
        );

        JTextField intervalField = CompUtil.createCustomField(128, 32, FONT_SIZE);
        //JTextField intervalField = new JTextField();
        CompUtil.addComponent(this, intervalField, 3, 4, 1, 1, 0, 0, gbc);


        // Adds clear button, column: 4.
        JButton clearButton = new JButton("Clear");
        CompUtil.addComponent(this, clearButton, 4, 4, 1, 1, 0, 0, gbc);

        //*****************//
        //      Row 5      //
        //*****************//

        // Adds calculation button, column: 2, 3 [width 2].
        JButton calculateButton = new JButton("Calculate");
        CompUtil.addComponent(this, calculateButton, 2, 5, 3, 1, 0, 0, gbc);

        //*****************//
        //      Row 6      //
        //*****************//

        // Just empty spaces, all columns
        JLabel spacer = new JLabel();
        spacer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        CompUtil.addComponent(this, spacer, 0, 6, 6, 1, 0, 0, gbc);

        //*****************//
        //      Row 7      //
        //*****************//

        // Formula Icon
        JLabel formulaLabel = CompUtil.createCustomLabel();
        formulaLabel.setPreferredSize(new Dimension(128, 192));
        formulaLabel.setIcon(null);
        CompUtil.addComponent(this, formulaLabel, 0, 7, 5, 1, 0, 0, gbc);

        // Display result
        Icon defaultResultIcon = CompUtil.createLatexIcon("\\(\\textit{Result will be displayed here.}\\)", LATEX_LABEL_SIZE);

        JLabel resultLabel = CompUtil.createCustomLabel();
        resultLabel.setIcon(defaultResultIcon);
        CompUtil.addComponent(this, resultLabel, 0, 8, 2, 1, 0, 0, gbc);


        //**************************************************//
        //      Action Listener & Logic Implementation      //
        //**************************************************//


        String trapezoidal_Rule_Formula = "\\int_{x_{0}}^{x_{0}+nh} y dx = \\frac{h}{2} \\left[ (y_{0} + y_{n}) + 2 \\sum_{k=1}^{n-1} y_{k} \\right]";
        String simsons_1_3_Rule_Formula = "\\int_{x_{0}}^{x_{0}+nh} y dx = \\frac{h}{3} \\left[ (y_{0} + y_{n}) + 4 \\sum_{k=1,3,5,\\ldots}^{n-1} y_{k} + 2 \\sum_{k=2,4,6,\\ldots}^{n-2} y_{k} \\right]";
        String simpsons_3_8_Rule_Formula = "\\int_{x_{0}}^{x_{0}+nh} y dx = \\frac{3h}{8} \\left[ (y_{0} + y_{n}) + 3 \\sum_{\\substack{k=1 \\\\ k \\neq 3,6,9,...}}^{n-1} y_{k} + 2 \\sum_{\\substack{k=3,6,9,...}}^{n-3} y_{k} \\right]";
        String weddles_Rule_Formula = "\\int_{x_{0}}^{x_{0}+nh} y dx = \\frac{3h}{10} \\left[ \\sum_{\\substack{k=0,2,4,6,...}}^{n} y_{k} + 5 \\sum_{\\substack{k=1,3,5,...}}^{n-1} y_{k} + \\sum_{\\substack{k=3,6,9,...}}^{n-3} y_{k} \\right]";

        Icon trapezoidal_Rule_Formula_Icon = CompUtil.createLatexIcon(trapezoidal_Rule_Formula, LATEX_LABEL_SIZE);
        Icon simpsons_1_3_Rule_Formula_Icon = CompUtil.createLatexIcon(simsons_1_3_Rule_Formula, LATEX_LABEL_SIZE);
        Icon simpsons_3_8_Rule_Formula_Icon = CompUtil.createLatexIcon(simpsons_3_8_Rule_Formula, LATEX_LABEL_SIZE);
        Icon weddles_Rule_Formula_Icon = CompUtil.createLatexIcon(weddles_Rule_Formula, LATEX_LABEL_SIZE);

        calculateButton.addActionListener(_ -> {
            try {
                // Parsing input and performing calculations
                String stringExpression = functionField.getText();
                Expression expression = new ExpressionBuilder(stringExpression).variables("x").build();

                double lowerLimit = Double.parseDouble(aField.getText());
                double upperLimit = Double.parseDouble(bField.getText());
                int interval = Integer.parseInt(intervalField.getText());
                String method = (String) methodComboBox.getSelectedItem();
                double result = 0;

                assert method != null;
                switch (method) {
                    case "Trapezoidal Rule" -> {
                        result = Trapezoidal.calculate(expression, lowerLimit, upperLimit, interval);
                        formulaLabel.setIcon(trapezoidal_Rule_Formula_Icon);
                    }
                    case "Simpson's ⅓ Rule" -> {
                        result = Simpsons_1_3.calculate(expression, lowerLimit, upperLimit, interval);
                        formulaLabel.setIcon(simpsons_1_3_Rule_Formula_Icon);
                    }
                    case "Simpson's ⅜ Rule" -> {
                        result = Simpsons_3_8.calculate(expression, lowerLimit, upperLimit, interval);
                        formulaLabel.setIcon(simpsons_3_8_Rule_Formula_Icon);
                    }
                    case "Weddle's Rule" -> {
                        result = Weddles.calculate(expression, lowerLimit, upperLimit, interval);
                        formulaLabel.setIcon(weddles_Rule_Formula_Icon);
                    }
                }

                // updating result
                String updatedResultString = "\\(\\textit{=     " + result + "}\\)";
                resultLabel.setIcon(CompUtil.createLatexIcon(updatedResultString, LATEX_LABEL_SIZE));

            } catch (Exception exception) {
                ErrorUtil.invalidInput();
            }
        });

        clearButton.addActionListener(_-> {
            functionField.setText("");
            aField.setText("");
            bField.setText("");
            intervalField.setText("");
            formulaLabel.setIcon(null);
            resultLabel.setIcon(defaultResultIcon);
        });
    }
}
