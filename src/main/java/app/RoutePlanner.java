package app;

import algo.AStar;
import com.formdev.flatlaf.FlatLightLaf;
import model.City;
import model.Node;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.*;
import tools.RoutePainter;
import tools.SelectionAdapter;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class RoutePlanner {
    private final Map<City, GeoPosition> cityPositions = new HashMap<>();
    private final List<GeoPosition> track;

    {
        // Initialize city positions map
        for (City city : City.values()) {
            cityPositions.put(city, new GeoPosition(city.getLatitude(), city.getLongitude()));
        }

        // Initialize track with all positions
        track = Arrays.stream(City.values())
                .map(cityPositions::get)
                .collect(Collectors.toList());
    }

    private final AtomicBoolean running = new AtomicBoolean(false);

    final RoutePainter routePainter = new RoutePainter();
    final List<City> startCities = new ArrayList<>(Arrays.asList(City.values()));
    final List<City> endCities = new ArrayList<>(Arrays.asList(City.values()));

    City[] tfLabelsStart = startCities.toArray(new City[0]);
    City[] tfLabelsEnd = endCities.toArray(new City[0]);

    JComboBox<City> startCombo = new JComboBox<>(tfLabelsStart);
    JComboBox<City> endCombo = new JComboBox<>(tfLabelsEnd);

    public void openMapViewer() {
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);

        File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
        tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));

        final JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(tileFactory);
        tileFactory.setThreadPoolSize(8);

        mapViewer.zoomToBestFit(new HashSet<>(track), 0.7);
        mapViewer.setZoom(8);

        Set<Waypoint> waypoints = track
                .stream()
                .map(DefaultWaypoint::new)
                .collect(Collectors.toSet());

        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);

        List<Painter<JXMapViewer>> painters = new ArrayList<>();
        painters.add(routePainter);
        painters.add(waypointPainter);

        CompoundPainter<JXMapViewer> cp = new CompoundPainter<>(painters);

        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);

        mapViewer.addMouseListener(new CenterMapListener(mapViewer));
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
        mapViewer.addKeyListener(new PanKeyListener(mapViewer));

        SelectionAdapter sa = new SelectionAdapter(mapViewer);
        mapViewer.addMouseListener(sa);
        mapViewer.addMouseMotionListener(sa);
        mapViewer.setOverlayPainter(cp);

        startCombo.addActionListener(e -> searchRoute());
        endCombo.addActionListener(e -> searchRoute());

        final JFrame frame = createJFrame(mapViewer);

        mapViewer.addPropertyChangeListener("zoom", evt -> updateWindowTitle(frame, mapViewer));
        mapViewer.addPropertyChangeListener("center", evt -> updateWindowTitle(frame, mapViewer));
        updateWindowTitle(frame, mapViewer);
    }

    private JFrame createJFrame(JXMapViewer mapViewer) {
        JPanel topPanel = new JPanel();
        JPanel startPanel = new JPanel();
        JPanel endPanel = new JPanel();

        startPanel.setLayout(new GridLayout());
        startPanel.add(startCombo);

        endPanel.setLayout(new GridLayout());
        endPanel.add(endCombo);

        JButton button = new JButton("Demo");

        button.addActionListener(e -> playDemo());

        topPanel.add(startPanel);
        topPanel.add(endPanel);
        topPanel.add(button);

        final JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(mapViewer);
        frame.setSize(1600, 1200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        return frame;
    }

    private void playDemo() {
        running.set(!running.get());

        if (!running.get()) {
            return;
        }

        new Thread(() -> {
            for(int i = 0; i < track.size(); i++) {
                for (int j = 0; j < track.size(); j++) {
                    if (!running.get()) {
                        return;
                    }
                    startCombo.setSelectedIndex(i);
                    endCombo.setSelectedIndex(j);
                    searchRoute();

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }).start();
    }

    private final AStar aStar = new AStar();

    private void searchRoute() {
        City start = (City) startCombo.getSelectedItem();
        City end = (City) endCombo.getSelectedItem();

        if (start == null || end == null) {
            return;
        }

        List<Node> foundPath = aStar.findPath(start, end);
        routePainter.clearNodes();

        ArrayList<GeoPosition> positions = new ArrayList<>();
        for (Node node : foundPath) {
            // Find city by name and get its position
            for (City city : City.values()) {
                if (city.toString().equals(node.getValue())) {
                    positions.add(cityPositions.get(city));
                    break;
                }
            }
        }
        routePainter.addNodes(positions);
        routePainter.update();
    }

    private void updateWindowTitle(JFrame frame, JXMapViewer mapViewer)
    {
        double lat = mapViewer.getCenterPosition().getLatitude();
        double lon = mapViewer.getCenterPosition().getLongitude();
        int zoom = mapViewer.getZoom();

        frame.setTitle(String.format("A* Kartensuche (%.2f / %.2f) - Zoom: %d", lat, lon, zoom));
    }
}
