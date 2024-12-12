package UI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class DashBoardPanel extends JPanel {

    public DashBoardPanel(Color panelColor) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);
        setBackground(panelColor);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/dashboard.gif")))));

        add(new JLabel("Select from left panel to get started!"));
        add(Box.createRigidArea(new Dimension(0, 64)));
        add(new JLabel("Recent Activity: "));
    }
}
