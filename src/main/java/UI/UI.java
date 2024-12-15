package UI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

// Secondary Imports
import ATES.ATESPanel;
import CurveFitting.CurveFittingPanel;
import Interpolation.InterpolationPanel;
import NumericalDifferentiation.NumericalDifferentiationPanel;
import NumericalIntegration.NumericalIntegrationPanel;
import ExtraTools.ExtraToolsPanel;

public class UI extends JFrame {

    // UI components
    private final JButton dashButton;
    private final JButton eqnRootFinderButton;
    private final JButton interpolationButton;
    private final JButton curveFittingButton;
    private final JButton differentiationButton;
    private final JButton integrationButton;
    private final JButton extraToolsButton;

    private final JMenuBar menuBar;
    private final String[] menus = {"File", "Edit", "View", "Help"};

    // Component spacing
    private final int spacing = 10;

    // Coloring Settings
    private final Color frameColor = new Color(74, 81, 92);
    private final Color sidePanelColor = new Color(37, 44, 56);

    // Font Settings
    private final Font labelFont = new Font("Calibri", Font.BOLD, 14);
    private final Font buttonFont = new Font("Calibri", Font.BOLD, 20);

    // Constructor
    public UI() {

        setUpLookAndFeel();

        setUpFrame(this);

        // Side Panel Creation
        SidePanel sidePanel = new SidePanel(sidePanelColor, frameColor, 280, getHeight());  // foreground, background

        // Side Panel navigation buttons creation
        dashButton = new PanelButton("Dashboard", "Go to dashboard", sidePanelColor);
        eqnRootFinderButton = new PanelButton("Eqn. Root Finder", "Find roots of equations", sidePanelColor);
        interpolationButton = new PanelButton("Interpolation", "Solve interpolation problems", sidePanelColor);
        curveFittingButton = new PanelButton("Curve Fitting", "Fit curves to data", sidePanelColor);
        differentiationButton = new PanelButton("Differentiation", "Perform differentiation", sidePanelColor);
        integrationButton = new PanelButton("Integration", "Perform integration", sidePanelColor);
        extraToolsButton = new PanelButton("Extra Tools", "Access extra tools", sidePanelColor);

        // Addition into side panel
        sidePanel.add(Box.createRigidArea(new Dimension(0, spacing)));              // 10px Spacing
        sidePanel.add(new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/panel.gif")))));
        sidePanel.add(Box.createRigidArea(new Dimension(0, 2 * spacing)));    // 20px Spacing
        sidePanel.add(new JLabel("MAIN"));
        sidePanel.add(Box.createRigidArea(new Dimension(0, 2 * spacing)));    // 20px Spacing
        sidePanel.add(dashButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 2 * spacing)));    // 20px Spacing
        sidePanel.add(new JLabel("MATH CHAPTERS"));
        sidePanel.add(Box.createRigidArea(new Dimension(0, 2 * spacing)));    // 20px Spacing

        sidePanel.add(eqnRootFinderButton);
        sidePanel.add(interpolationButton);
        sidePanel.add(curveFittingButton);
        sidePanel.add(differentiationButton);
        sidePanel.add(integrationButton);

        sidePanel.add(Box.createRigidArea(new Dimension(0, 2 * spacing)));    // 20px Spacing
        sidePanel.add(new JLabel("EXTRAS"));
        sidePanel.add(Box.createRigidArea(new Dimension(0, 2 * spacing)));    // 20px Spacing

        sidePanel.add(extraToolsButton);


        // Menu Bar, this gets added to frame rather than panel
        menuBar = new JMenuBar();
        for (String menuName : menus) {
            JMenu menu = new JMenu(menuName);
            menuBar.add(menu);
        }
        setJMenuBar(menuBar);


        //***************************************//
        // Actual Layout and Panel Swapping Logic//
        //***************************************//


        // Pairing / Associating Card Layout With My mainPanel
        CardLayout cardLayout = new CardLayout();

        //noinspection ExtractMethodRecommender -- Removed warnings for extracting method.
        JPanel mainPanel = new JPanel(cardLayout);

        // Creating panels to work as cards
        JPanel dashboardPanel = new DashBoardPanel(frameColor);
        JPanel atesPanel = new ATESPanel(frameColor);
        JPanel curveFittingPanel = new CurveFittingPanel(frameColor);
        JPanel interpolationExtrapolationPanel = new InterpolationPanel(frameColor);
        JPanel numericalDifferentiationPanel = new NumericalDifferentiationPanel(frameColor);
        JPanel numericalIntegrationPanel = new NumericalIntegrationPanel(frameColor);
        JPanel extraToolsPanel = new ExtraToolsPanel(frameColor);

        // Adding panels as cards to my mainPanel.
        mainPanel.add(dashboardPanel, "Dashboard");
        mainPanel.add(atesPanel, "ATES");
        mainPanel.add(curveFittingPanel, "CurveFitting");
        mainPanel.add(interpolationExtrapolationPanel, "InterpolationExtrapolation");
        mainPanel.add(numericalDifferentiationPanel, "NumericalDifferentiation");
        mainPanel.add(numericalIntegrationPanel, "NumericalIntegration");
        mainPanel.add(extraToolsPanel, "ExtraTools");

        // Syntax: button.addActionListener(e -> Action); but here e isn't used hence replaced with Java21 placeholder.

        // Shows Dashboard
        dashButton.addActionListener(_ -> cardLayout.show(mainPanel, "Dashboard"));

        // Shows ATES Panel
        eqnRootFinderButton.addActionListener(_ -> cardLayout.show(mainPanel, "ATES"));

        // Shows Curve Fitting Panel
        curveFittingButton.addActionListener(_ -> cardLayout.show(mainPanel, "CurveFitting"));

        // Shows Interpolation Panel
        interpolationButton.addActionListener(_ -> cardLayout.show(mainPanel, "InterpolationExtrapolation"));

        // Shows Differentiation Panel
        differentiationButton.addActionListener(_ -> cardLayout.show(mainPanel, "NumericalDifferentiation"));

        // Shows Integration Panel
        integrationButton.addActionListener(_ -> cardLayout.show(mainPanel, "NumericalIntegration"));

        // Shows Extra Tools Panel
        extraToolsButton.addActionListener(_ -> cardLayout.show(mainPanel, "ExtraTools"));

        // Adding components to the frame
        add(sidePanel, BorderLayout.WEST);  // Add the side panel to the left of the frame
        add(mainPanel, BorderLayout.CENTER); // Add the main panel to the

        setVisible(true);
    }


    //**************************************************************************************************//
    // These methods are only used once, in this class hence they don't need to be put in another class //
    //**************************************************************************************************//


    private void setUpLookAndFeel() {
        // Recommended by FlatLaf Developer
        FlatMacDarkLaf.setup();
        FlatLaf.updateUI();

        setDefaultLookAndFeelDecorated(true);
        System.setProperty("flatlaf.windowDecorations", "true");
        System.setProperty("flatlaf.menuBarEmbedded", "true");

        UIManager.put("TitlePane.titleMargins", new Insets(10, 0, 10, 0));
        UIManager.put("TitlePane.font", new Font(UIManager.getFont("Label.font").getName(), Font.PLAIN, 16));

        UIManager.put("Label.font", labelFont);
        UIManager.put("Label.foreground", new Color(184, 186, 186));

        UIManager.put("Button.font", buttonFont);
    }

    private void setUpFrame (JFrame frame) {
        // Setting up the frame.
        frame.setTitle("Solutionary 1.0.0"); // See Apache Commons Versioning
        frame.setSize(1200, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(frameColor);
        frame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/frameIcon.png"))).getImage());
    }
}
