package Numerical_Integration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;


public class NumericalIntegrationPanel extends JPanel {

    private static final int FIELD_HEIGHT = 64; // Double the height of fields
    private static final int FONT_SIZE = 18; // Larger text size for fields

    public NumericalIntegrationPanel (Color panelColor) {

        UIManager.put("Button.font", new Font("Arial", Font.BOLD, FONT_SIZE));

        setLayout(new GridBagLayout());
        setBackground(panelColor);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets (5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: [0, 1, 2]: inputLabel and Input
        addLatexLabel("\\(\\textit{Enter Function and It's Limits:}\\)", 20, 0, 1, gbc);
        addLatexLabel("\\hspace{128pt}", 20, 1, 1, gbc);

        JTextField bField = new JTextField(2);
        bField.setFont(new Font("Arial", Font.PLAIN, 14)); // Font size
        gbc.gridx = 2; // Column 1
        gbc.gridy = 0; // Row 0
        gbc.weightx = 0; // No horizontal expansion
        gbc.weighty = 0; // No vertical expansion
        add(bField, gbc);

        addLatexLabel("\\int\\hspace{30pt}", 30, 2, 1, gbc);
        JTextField functionField = createSizedField();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Span across columns
        gbc.weightx = 0; // Allow horizontal expansion
        add(functionField, gbc);

        JTextField aField = new JTextField(2);
        aField.setFont(new Font("Arial", Font.PLAIN, 14)); // Font size
        gbc.gridx = 2; // Column 1
        gbc.gridy = 2; // Row 2
        gbc.weightx = 0; // No horizontal expansion
        gbc.weighty = 0; // No vertical expansion
        add(aField, gbc);

        // To dynamically set function field size, fucking finally.
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(functionField); // Get the parent JFrame
                int size;
                // Check if the frame is fullscreen
                if (frame != null && frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                    size = 512; // Set size for fullscreen
                } else {
                    size = 256; // Set size for normal state
                }
                functionField.setPreferredSize(new Dimension(size, FIELD_HEIGHT));
                revalidate();
                repaint();
            }
        });

        addLatexLabel("dx", 30, 4, 1, gbc);

        // Row 2: [3] Label, Select method, Calculate, clear
        addLatexLabel("\\(\\textit{Select Calculation Method:}\\)", 20, 0, 3, gbc);

        JComboBox<String> methodComboBox = new JComboBox<>(new String[]{
                "Trapezoidal Rule", "Simpson's ⅓ Rule", "Simpson's ⅜ Rule", "Weddle's Rule"
        });
        methodComboBox.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        addField(methodComboBox, 2, 3, 4, gbc);

        JButton calculateButton = new JButton("Calculate");
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Single column
        add(calculateButton, gbc);

        JButton clearButton = new JButton("Clear");
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 1; // Single column
        add(clearButton, gbc);
    }

    //----------------------------------------------------------------
    // Helper Methods
    //----------------------------------------------------------------

    private void addLatexLabel(String latexText, int size, int x, int y, GridBagConstraints gbc) {
        TeXFormula formula = new TeXFormula(latexText);
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, size);
        JLabel latexLabel = new JLabel(icon);
        latexLabel.setForeground(Color.WHITE); // White text color
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1; // Single column
        gbc.weightx = 0; // No horizontal expansion
        gbc.weighty = 0; // No vertical expansion
        add(latexLabel, gbc);
    }

    private JTextField createSizedField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(256, FIELD_HEIGHT)); // Height adjustment
        field.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE)); // Font size
        return field;
    }

    private void addField(JComponent component, int x, int y, int width, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width; // Span across columns
        gbc.weightx = 1; // Allow horizontal expansion
        add(component, gbc);
    }
}
