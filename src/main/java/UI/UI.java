package UI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;

// Secondary Imports
import ATES.ATESPanel;
import Curve_Fitting.CurveFittingPanel;
import Interpolation_Extrapolation.InterpolationExtrapolationPanel;
import Numerical_Differentiation.NumericalDifferentiationPanel;
import Numerical_Integration.NumericalIntegrationPanel;
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

        // Recommended by FlatLaf Developer
        FlatMacDarkLaf.setup();
        FlatLaf.updateUI();

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
        Color fieldColor = new Color(34, 41, 43);
        Color textColor = Color.WHITE;

        // Font Settings
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font buttonFont = new Font("Calibri", Font.BOLD, 20);

        UIManager.put("Label.font", labelFont);
        UIManager.put("Button.font", buttonFont);

        // Frame Settings
        setTitle("Solutionary version 0.0.2-Beta.015");
        setSize(1200, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("src/resources/frameIcon.png").getImage());

/// This is a workaround for no pic in jar file, use when compiling final release
//        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/frameIcon.png"))).getImage());

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

/// This is a workaround for no pic in jar file, use when compiling final release
//        sidePanel.add(new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/panel.gif")))));

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

        sidePanel.add(Box.createRigidArea(new Dimension(0, 2 * spacing)));    // 20px Spacing
        sidePanel.add(new ColoredLabel("EXTRAS", labelColor));
        sidePanel.add(Box.createRigidArea(new Dimension(0, 2 * spacing)));    // 20px Spacing

        sidePanel.add(extraToolsButton);

        // Menu Bar
        menuBar = new JMenuBar();
        for (String menuName : menus) {
            JMenu menu = new JMenu(menuName);
            menuBar.add(menu);
        }
        setJMenuBar(menuBar);   // Add the embedded menu bar


        ///  So, it begins...

        // Pairing Card Layout With My Main Panel
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        JPanel dashboardPanel = new DashBoardPanel(frameColor);
        JPanel atesPanel = new ATESPanel(frameColor);
        JPanel curveFittingPanel = new CurveFittingPanel(frameColor);
        JPanel interpolationExtrapolationPanel = new InterpolationExtrapolationPanel(frameColor);
        JPanel numericalDifferentiationPanel = new NumericalDifferentiationPanel(frameColor);
        JPanel numericalIntegrationPanel = new NumericalIntegrationPanel(frameColor);

        mainPanel.add(dashboardPanel, "Dashboard");
        mainPanel.add(atesPanel, "ATES");
        mainPanel.add(curveFittingPanel, "CurveFitting");
        mainPanel.add(interpolationExtrapolationPanel, "InterpolationExtrapolation");
        mainPanel.add(numericalDifferentiationPanel, "NumericalDifferentiation");
        mainPanel.add(numericalIntegrationPanel, "NumericalIntegration");

        // Shows Dashboard
        dashButton.addActionListener(e -> cardLayout.show(mainPanel, "Dashboard"));

        // Shows ATES Panel
        eqnRootFinderButton.addActionListener(e -> cardLayout.show(mainPanel, "ATES"));

        // Shows Curve Fitting Panel
        curveFittingButton.addActionListener(e -> cardLayout.show(mainPanel, "CurveFitting"));

        // Shows Interpolation Panel
        interpolationButton.addActionListener(e -> cardLayout.show(mainPanel, "InterpolationExtrapolation"));

        // Shows Differentiation Panel
        differentiationButton.addActionListener(e -> cardLayout.show(mainPanel, "NumericalDifferentiation"));

        // Shows Integration Panel
        integrationButton.addActionListener(e -> cardLayout.show(mainPanel, "NumericalIntegration"));

//        // Shows Extra Tools Panel
//        extraToolsButton.addActionListener(e -> cardLayout.show(mainPanel, "ExtraTools"));

//        // Menu Bar Actions
//        JMenuItem exitItem = new JMenuItem("Exit");
//        exitItem.addActionListener(e -> System.exit(0));

        // Adding components to the frame
        add(sidePanel, BorderLayout.WEST);  // Add the side panel to the left of the frame
        add(mainPanel, BorderLayout.CENTER); // Add the main panel to the

        setVisible(true);
    }
}
