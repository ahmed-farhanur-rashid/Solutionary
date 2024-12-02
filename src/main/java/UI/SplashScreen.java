package UI;

import javax.swing.*;
import java.awt.*;

public class SplashScreen {
    public SplashScreen() {
        // Set DPI-aware system properties (Java 9+ only)
        System.setProperty("sun.java2d.uiScale", "1.0");

        // Create the Splash Screen
        JFrame splashFrame = new JFrame();
        splashFrame.setUndecorated(true); // No title bar
        splashFrame.setSize(768, 432); // Fixed size
        splashFrame.setLocationRelativeTo(null); // Center the frame
        splashFrame.setLayout(new BorderLayout());

        // Load and validate the splash image
        try {
            JLabel imageLabel = new JLabel(new ImageIcon("src/resources/SplashScreen.png"));
            splashFrame.add(imageLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            ErrorUtils.noImageError(); // Show error if splash image fails to load
            return; // Exit if the image fails to load
        }

        // Create a progress bar
        JProgressBar progressBar = new JProgressBar(0, 100); // Range from 0 to 100
        progressBar.setStringPainted(true); // Show text on the progress bar
        splashFrame.add(progressBar, BorderLayout.SOUTH);

        // Show the splash screen
        splashFrame.setVisible(true);

        // Start a new thread to update the progress bar
        new Thread(() -> {

            for (int i = 0; i <= 100; i++) {

                try {
                    Thread.sleep(20); // Adjust speed of progress bar
                } catch (InterruptedException e) {
                    ErrorUtils.interruptionError(); //e.printStackTrace();
                }

                final int progressValue = i;
                // Ensure progress bar updates are on the Event Dispatch Thread
                SwingUtilities.invokeLater(() -> {
                    progressBar.setValue(progressValue);
                    progressBar.setString("Loading...   " + progressValue + "%" + "     Thread Count: " + Thread.activeCount());
                });
            }

            // Once progress reaches 100%, close the splash screen and trigger main app opening
            SwingUtilities.invokeLater(() -> {
                splashFrame.dispose(); // Close splash screen
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
