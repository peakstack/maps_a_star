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
import pathfinding.AStar;
import pathfinding.City;
import pathfinding.Node;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);

        File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
        tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));

        final JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(tileFactory);

        GeoPosition loerrach = new GeoPosition(47.616, 7.668);
        GeoPosition weil = new GeoPosition(47.593, 7.623);
        GeoPosition binzen = new GeoPosition(47.631, 7.624);
        GeoPosition efringen_kirchen = new GeoPosition(47.655, 7.563);
        GeoPosition steinen = new GeoPosition(47.644, 7.739);
        GeoPosition maulburg = new GeoPosition(47.643, 7.780);
        GeoPosition schopfheim = new GeoPosition(47.650, 7.825);
        GeoPosition hausen = new GeoPosition(47.680, 7.841);
        GeoPosition zell = new GeoPosition(47.707, 7.852);
        GeoPosition wittlingen = new GeoPosition(47.656, 7.648);
        GeoPosition kandern = new GeoPosition(47.713, 7.662);
        GeoPosition bad_bellingen = new GeoPosition(47.731, 7.557);
        GeoPosition schliengen = new GeoPosition(47.755, 7.579);
        GeoPosition auggen = new GeoPosition(47.787, 7.593);
        GeoPosition muellheim = new GeoPosition(47.804, 7.629);
        GeoPosition marzell = new GeoPosition(47.771, 7.726);
        GeoPosition tegernau = new GeoPosition(47.719, 7.795);
        GeoPosition schoenau = new GeoPosition(47.785, 7.893);
        GeoPosition muenstertal = new GeoPosition(47.854, 7.784);
        GeoPosition staufen = new GeoPosition(47.882, 7.729);
        GeoPosition heitersheim = new GeoPosition(47.874, 7.655);

        List<GeoPosition> track = Arrays.asList(
                loerrach, weil, binzen, efringen_kirchen,
                steinen, maulburg, schopfheim, hausen,
                zell, wittlingen, kandern, bad_bellingen,
                schliengen, auggen, muellheim, marzell,
                tegernau, schoenau, muenstertal,
                staufen, heitersheim
        );

        mapViewer.zoomToBestFit(new HashSet<>(track), 0.7);
        mapViewer.setZoom(8);

        Set<Waypoint> waypoints = track
                .stream()
                .map(DefaultWaypoint::new)
                .collect(Collectors.toSet());

        RoutePainter routePainter = new RoutePainter();
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

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Suche eine Start-Stadt aus");

        final List<City> cities = new ArrayList<>(Arrays.asList(City.values()));

        String[] tfLabels = cities
                .stream()
                .map(Enum::name)
                .toArray(String[]::new);

        final JComboBox<String> combo = new JComboBox<>(tfLabels);

        panel.setLayout(new GridLayout());
        panel.add(label);
        panel.add(combo);

        JPanel panel2 = new JPanel();
        JLabel label2 = new JLabel("Suche eine End-Stadt aus");

        final List<City> cities2 = new ArrayList<>(Arrays.asList(City.values()));

        String[] tfLabels2 = cities2
                .stream()
                .map(Enum::name)
                .toArray(String[]::new);

        final JComboBox<String> combo2 = new JComboBox<>(tfLabels2);

        panel2.setLayout(new GridLayout());
        panel2.add(label2);
        panel2.add(combo2);

        JButton searchButton = new JButton("Suchen");

        searchButton.addActionListener(e -> {
            City start = City.valueOf(Objects.requireNonNull(combo.getSelectedItem()).toString());
            City end = City.valueOf(Objects.requireNonNull(combo2.getSelectedItem()).toString());

            List<Node> path = AStar.findPath(start, end);

            RoutePainter.nodes.clear();
            ArrayList<GeoPosition> positions = new ArrayList<>();
            for(Node node : path) {
                switch (node.value) {
                    case "Lörrach":
                        positions.add(loerrach);
                        break;
                    case "Weil am Rhein":
                        positions.add(weil);
                        break;
                    case "Binzen":
                        positions.add(binzen);
                        break;
                    case "Efringen-Kirchen":
                        positions.add(efringen_kirchen);
                        break;
                    case "Steinen":
                        positions.add(steinen);
                        break;
                    case "Maulburg":
                        positions.add(maulburg);
                        break;
                    case "Schopfheim":
                        positions.add(schopfheim);
                        break;
                    case "Hausen":
                        positions.add(hausen);
                        break;
                    case "Zell":
                        positions.add(zell);
                        break;
                    case "Wittlingen":
                        positions.add(wittlingen);
                        break;
                    case "Kandern":
                        positions.add(kandern);
                        break;
                    case "Bad Bellingen":
                        positions.add(bad_bellingen);
                        break;
                    case "Schliengen":
                        positions.add(schliengen);
                        break;
                    case "Auggen":
                        positions.add(auggen);
                        break;
                    case "Müllheim":
                        positions.add(muellheim);
                        break;
                    case "Malsburg-Marzell":
                        positions.add(marzell);
                        break;
                    case "Tegernau":
                        positions.add(tegernau);
                        break;
                    case "Schönau":
                        positions.add(schoenau);
                        break;
                    case "Münstertal":
                        positions.add(muenstertal);
                        break;
                    case "Staufen":
                        positions.add(staufen);
                        break;
                    case "Heitersheim":
                        positions.add(heitersheim);
                        break;
                }
            }
            RoutePainter.nodes.addAll(positions);
            RoutePainter.update();
        });

        final JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.SOUTH);
        frame.add(searchButton, BorderLayout.EAST);
        frame.add(mapViewer);
        frame.setSize(1600, 1200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        mapViewer.addPropertyChangeListener("zoom", evt -> {
            updateWindowTitle(frame, mapViewer);
        });

        mapViewer.addPropertyChangeListener("center", evt -> {
            updateWindowTitle(frame, mapViewer);
        });

        updateWindowTitle(frame, mapViewer);
    }

    protected static void updateWindowTitle(JFrame frame, JXMapViewer mapViewer)
    {
        double lat = mapViewer.getCenterPosition().getLatitude();
        double lon = mapViewer.getCenterPosition().getLongitude();
        int zoom = mapViewer.getZoom();

        frame.setTitle(String.format("(%.2f / %.2f) - Zoom: %d", lat, lon, zoom));
    }

}