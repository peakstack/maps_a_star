package app;

import com.formdev.flatlaf.FlatLightLaf;

/**
 * Main application entry point for the A* map route planner.
 * Sets up the UI look and feel and launches the route planner.
 */
public class Application {

    /**
     * Main method to start the application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        FlatLightLaf.setup();
        RoutePlanner planner = new RoutePlanner();
        planner.openMapViewer();
    }
}