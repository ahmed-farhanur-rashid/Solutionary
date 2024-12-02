package UI;

import javax.swing.*;
import java.awt.*;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

public class UI extends JFrame {

    private final JButton dashButton;
    private final JButton eqnRootFinderButton;
    private final JButton interpolationButton;
    private final JButton curveFittingButton;
    private final JButton differentiationButton;
    private final JButton integrationButton;
    private final JButton extraToolsButton;

    private final JMenuBar menuBar;
    private final String[] menus = {"File", "Edit", "View", "Help"};

    public UI() {

        FlatDarkLaf.setup(); // Recommended by FlatLaf Developer
        FlatLaf.updateUI(); // Don't know why, but without it, nothing works

        setDefaultLookAndFeelDecorated(true);
        System.setProperty("flatlaf.windowDecorations", "true");
        System.setProperty("flatlaf.menuBarEmbedded", "true");

        UIManager.put("TitlePane.titleMargins", new Insets(10, 0, 10, 0));
        UIManager.put("TitlePane.font", new Font(UIManager.getFont("Label.font").getName(), Font.PLAIN, 16));

        // Spacing Settings
        int spacing = 10;

        // Coloring Settings
        Color frameColor = new Color(74, 81, 92);
        Color panelColor = new Color(37, 44, 56);
        Color labelColor = new Color(184, 186, 186);
        Color textColor = Color.WHITE;

        // Font Settings
        Font labelFont = new Font("Arial", Font.BOLD, 14);  // Correct font name
        UIManager.put("Label.font", labelFont);

        Font buttonFont = new Font("Bahnschrift", Font.BOLD, 20);  // Correct font name
        UIManager.put("Button.font", buttonFont);

        // Frame Settings
        setTitle("Solutionary version 0.0.03-Alpha.01");
        setSize(1200, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("src/resources/frameIcon.png").getImage());
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(frameColor);

        // Side Panel
        SidePanel sidePanel = new SidePanel(panelColor, frameColor, 280, getHeight());  // foreground, background

        dashButton = new PanelButton("Dashboard", "Go to dashboard", textColor, panelColor);
        eqnRootFinderButton = new PanelButton("Eqn. Root Finder", "Find roots of equations", textColor, panelColor);
        interpolationButton = new PanelButton("Interpolation", "Solve interpolation problems", textColor, panelColor);
        curveFittingButton = new PanelButton("Curve Fitting", "Fit curves to data", textColor, panelColor);
        differentiationButton = new PanelButton("Differentiation", "Perform differentiation", textColor, panelColor);
        integrationButton = new PanelButton("Integration", "Perform integration", textColor, panelColor);
        extraToolsButton = new PanelButton("Extra Tools", "Access extra tools", textColor, panelColor);

        // Addition into side panel
        sidePanel.add(Box.createRigidArea(new Dimension(0, spacing)));              // 10px Spacing
        sidePanel.add(new JLabel(new ImageIcon("src/resources/panel.gif")));
        sidePanel.add(Box.createRigidArea(new Dimension(0, 2 * spacing)));    // 20px Spacing
        sidePanel.add(new ColoredLabel("MAIN", labelColor));
        sidePanel.add(Box.createRigidArea(new Dimension(0, 2 * spacing)));    // 20px Spacing
        sidePanel.add(dashButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 2 * spacing)));    // 20px Spacing
        sidePanel.add(new ColoredLabel("MATH CHAPTERS", labelColor));
        sidePanel.add(Box.createRigidArea(new Dimension(0, 2 * spacing)));    // 20px Spacing

        sidePanel.add(eqnRootFinderButton);
        sidePanel.add(interpolationButton);
        sidePanel.add(curveFittingButton);
        sidePanel.add(differentiationButton);
        sidePanel.add(integrationButton);

        sidePanel.add(Box.createRigidArea(new Dimension(0, 2 * spacing)));
        sidePanel.add(new ColoredLabel("EXTRAS", labelColor));
        sidePanel.add(Box.createRigidArea(new Dimension(0, 2 * spacing)));

        sidePanel.add(extraToolsButton);

        // Menu Bar
        menuBar = new JMenuBar();
        for (String menuName : menus) {
            JMenu menu = new JMenu(menuName);
            menuBar.add(menu);
        }
        setJMenuBar(menuBar);   // Add the embedded menu bar

        // Adding components to the frame
        add(sidePanel, BorderLayout.WEST);  // Add the side panel to the left of the frame

        setVisible(true);
    }
}
