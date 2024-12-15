package UI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SolutionarySplashScreen extends JFrame {
    public SolutionarySplashScreen() {

        // Create the Splash Screen
        setUndecorated(true); // No title bar
        setSize(768, 432); // Fixed size
        setLocationRelativeTo(null); // Center the frame
        setLayout(new BorderLayout());

        // Load and validate the splash image
        JLabel imageLabel = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/splashScreen.gif"))));
        add(imageLabel, BorderLayout.CENTER);

        // Create a progress bar
        JProgressBar progressBar = new JProgressBar(0, 100); // Range from 0 to 100
        progressBar.setStringPainted(true); // Show text on the progress bar
        add(progressBar, BorderLayout.SOUTH);

        // Show the splash screen
        setVisible(true);

        // Start a new thread to update the progress bar
        // This was created by AI as the behaviour was unpredictable.
        new Thread(() -> {

            for (int i = 0; i <= 100; i++) {

                try {
                    Thread.sleep(20); // Adjusted speed of progress bar
                } catch (InterruptedException e) {
                    ErrorUtil.interruptionError(); //Alternatively, e.printStackTrace();
                }

                final int progressValue = i;
                // Ensure progress bar updates are on the Event Dispatch Thread
                SwingUtilities.invokeLater(() -> {
                    progressBar.setValue(progressValue);
                    progressBar.setString("Loading...   " + progressValue + "%" + "         Thread Count: " + Thread.activeCount());
                });
            }

            // Once progress reaches 100%, closes the splash screen and triggers main app opening
            SwingUtilities.invokeLater(() -> {
                dispose(); // Close splash screen
                openMainUI(); // Open the main UI
            });
        }).start();
    }

    // Method to open the main UI
    private void openMainUI() {
        SwingUtilities.invokeLater(() -> {
            UI mainUI = new UI(); // Assuming UI is your main class
            mainUI.setVisible(true); // Show the main UI after splash screen completes
        });
    }
}
