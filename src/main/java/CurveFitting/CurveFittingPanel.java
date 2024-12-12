package CurveFitting;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class CurveFittingPanel extends JPanel {

    private static final int FIELD_HEIGHT = 64; // Double the height of fields
    private static final int FONT_SIZE = 18; // Larger text size for fields
    private JTable table;
    private DefaultTableModel tableModel;

    public CurveFittingPanel(Color panelColor) {

        UIManager.put("Button.font", new Font("Arial", Font.BOLD, FONT_SIZE));

        setLayout(new GridBagLayout());
        setBackground(panelColor);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Insets are border between elements
        gbc.fill = GridBagConstraints.HORIZONTAL; // Stretches Component Horizontally

        // Row 1: DataPoint and nField
        addLatexLabel("\\(\\textit{Enter Number of Data Points (n):}\\)", 0, 0, gbc);
        JTextField nField = createSizedField();
        addField(nField, 1, 0, 1, gbc);

        // Row 2: Table Input
        addLatexLabel("\\(\\textit{Input Table:}\\)", 0, 1, gbc);

        // Row 2: JTable for input
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"X", "Y"});
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.BOLD, 16));
        table.setRowHeight(table.getRowHeight() + 20);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
        tableHeader.setPreferredSize(new Dimension(tableHeader.getPreferredSize().width, 40));
        JScrollPane scrollPane = new JScrollPane(table);                                                // Create a scrollPane
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Span across all three columns
        gbc.weightx = 1;   // Allow horizontal expansion
        gbc.weighty = 1;   // Allow vertical expansion
        gbc.fill = GridBagConstraints.BOTH; // Stretch in both directions
        add(scrollPane, gbc);

        // Row 3: comboBox
        addLatexLabel("\\(\\textit{Type of Eqn. To Fit To Data Points (n):}\\)", 0, 2, gbc);

        JComboBox<String> eqnTypeComboBox = new JComboBox<>(new String[]{
                "<html><b>y = ax + b</b></html>",                   // Bold
                "<html><b>y = ae<sup>bx</sup></b></html>",          // Superscript
                "<html><b>y = ab<sup>x</sup></b></html>",           // Superscript
                "<html><b>y = ax<sup>2</sup> + bx + c</b></html>"   // Quadratic with superscript
        });
        eqnTypeComboBox.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        addField(eqnTypeComboBox, 1, 2, 1, gbc);

        // Row 4: Calculate & Clear
        JButton calculateButton = new JButton("Calculate");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1; // Single column
        add(calculateButton, gbc);

        JButton clearButton = new JButton("Clear");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(clearButton, gbc);

        // Row 6: Empty

//        JLabel empty = new JLabel("");
//        gbc.gridx = 0;
//        gbc.gridy = 4;
//        gbc.gridwidth = 2;
//        add(empty, gbc);

        // Row 6: Result Label
        JLabel resultLabel = new JLabel("Results will be displayed here.");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(resultLabel, gbc);

    }

    //----------------------------------------------------------------
    // Helper Methods
    //----------------------------------------------------------------

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

    private JTextField createSizedField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(0, FIELD_HEIGHT)); // Height adjustment
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
