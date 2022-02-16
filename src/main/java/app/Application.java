package app;

import com.formdev.flatlaf.FlatLightLaf;

public class Application {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        RoutePlanner planner = new RoutePlanner();
        planner.openMapViewer();
    }
}