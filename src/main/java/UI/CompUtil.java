package UI;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;

public class CompUtil {

    public static JLabel createCustomLabel() {
        JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return label;
    }

    /**
     * This method sets up a JPanel with GridBagLayout and a specific background color
     *
     * @param PANEL         The panel to be created with the specified background.
     * @param COLOR         The color of the background of the panel.
     */
    public static void setUpPanel(JPanel PANEL, Color COLOR) {
        PANEL.setBackground(COLOR);
        PANEL.setLayout(new GridBagLayout());
        PANEL.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     * Creates a JTextField with a specified size and font properties.
     *
     * @param FIELD_WIDTH  The width of the text field in pixels.
     * @param FIELD_HEIGHT The height of the text field in pixels.
     * @param FONT_SIZE    The size of the font to be applied to the text in the text field.
     * @return A configured JTextField with the specified width, height, and font size.
     */
    public static JTextField createCustomField(int FIELD_WIDTH, int FIELD_HEIGHT, int FONT_SIZE) {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT)); // Height adjustment
        field.setFont(new Font("Arial", Font.BOLD, FONT_SIZE)); // Font size
        return field;
    }

    /**
     * Creates a JLabel containing a rendered LaTeX formula.
     *
     * @param LATEX_TEXT The LaTeX string representing the formula to be displayed.
     * @param TEXT_SIZE  The size of the text to be rendered (e.g., 20 for a larger font).
     * @param TEXT_COLOR The color of the text (e.g., Color.BLACK or Color.WHITE, or Object of Color).
     * @return A JLabel containing the rendered LaTeX formula with specified text size and color.
     */
    public static JLabel createLatexLabel(String LATEX_TEXT, int TEXT_SIZE, Color TEXT_COLOR) {
        TeXFormula formula = new TeXFormula(LATEX_TEXT);
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, TEXT_SIZE);
        JLabel latexLabel = new JLabel(icon);
        latexLabel.setHorizontalAlignment(SwingConstants.LEFT);
        latexLabel.setForeground(TEXT_COLOR); // White text color

        // gbc.anchor = GridBagConstraints.EAST; // Align to the right

        return latexLabel;
    }

    /**
     * Creates an Icon containing a rendered LaTeX formula.
     *
     * @param LATEX_TEXT The LaTeX string representing the formula to be displayed.
     * @param TEXT_SIZE  The size of the text to be rendered (e.g., 20 for a larger font).
     * @return A JLabel containing the rendered LaTeX formula with specified text size and color.
     */
    public static Icon createLatexIcon(String LATEX_TEXT, int TEXT_SIZE) {
        TeXFormula formula = new TeXFormula(LATEX_TEXT);
        return formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, TEXT_SIZE);
    }

    /**
     * This method adds a component to a container with a specific grid position and constraints
     *
     * @param container  The container to which the component will be added.
     * @param component  The component to be added to the container.
     * @param x          The X coordinate of the component to be added to the container.
     * @param y          The Y coordinate of the component to be added
     * @param width      The width of the component to be added to the container
     * @param height     The height of the component to be added to the container
     * @param xExpansion The amount of horizontal expansion
     * @param yExpansion The amount of vertical expansion
     */
    public static void addComponent(Container container, Component component, int x, int y, int width, int height, double xExpansion, double yExpansion, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;

        // How many cells a component will span horizontally or vertically
        gbc.gridwidth = width; // Span across columns
        gbc.gridheight = height; // Span across rows

        // Determines how extra available space is distributed
        gbc.weightx = xExpansion; // Allow horizontal expansion
        gbc.weighty = yExpansion; // Allow vertical expansion

        // Add the component to the container with the specified grid position and constraints
        container.add(component, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        container.revalidate(); // Refresh container's layout manager
        container.repaint();    // Trigger UI redraw
    }
}
