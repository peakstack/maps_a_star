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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        tileFactory.setThreadPoolSize(8);

        GeoPosition loerrach = new GeoPosition(City.LOERRACH.getLatitude(), City.LOERRACH.getLongitude());
        GeoPosition weil = new GeoPosition(City.WEIL.getLatitude(), City.WEIL.getLongitude());
        GeoPosition binzen = new GeoPosition(City.BINZEN.getLatitude(), City.BINZEN.getLongitude());
        GeoPosition efringen_kirchen = new GeoPosition(City.EFRINGEN_KIRCHEN.getLatitude(), City.EFRINGEN_KIRCHEN.getLongitude());
        GeoPosition steinen = new GeoPosition(City.STEINEN.getLatitude(), City.STEINEN.getLongitude());
        GeoPosition maulburg = new GeoPosition(City.MAULBURG.getLatitude(), City.MAULBURG.getLongitude());
        GeoPosition schopfheim = new GeoPosition(City.SCHOPFHEIM.getLatitude(), City.SCHOPFHEIM.getLongitude());
        GeoPosition hausen = new GeoPosition(City.HAUSEN.getLatitude(), City.HAUSEN.getLongitude());
        GeoPosition zell = new GeoPosition(City.ZELL.getLatitude(), City.ZELL.getLongitude());
        GeoPosition wittlingen = new GeoPosition(City.WITTLINGEN.getLatitude(), City.WITTLINGEN.getLongitude());
        GeoPosition kandern = new GeoPosition(City.KANDERN.getLatitude(), City.KANDERN.getLongitude());
        GeoPosition bad_bellingen = new GeoPosition(City.BAD_BELLINGEN.getLatitude(), City.BAD_BELLINGEN.getLongitude());
        GeoPosition schliengen = new GeoPosition(City.SCHLIENGEN.getLatitude(), City.SCHLIENGEN.getLongitude());
        GeoPosition auggen = new GeoPosition(City.AUGGEN.getLatitude(), City.AUGGEN.getLongitude());
        GeoPosition muellheim = new GeoPosition(City.MUELLHEIM.getLatitude(), City.MUELLHEIM.getLongitude());
        GeoPosition marzell = new GeoPosition(City.MARZELL.getLatitude(), City.MARZELL.getLongitude());
        GeoPosition tegernau = new GeoPosition(City.TEGERNAU.getLatitude(), City.TEGERNAU.getLongitude());
        GeoPosition schoenau = new GeoPosition(City.SCHOENAU.getLatitude(), City.SCHOENAU.getLongitude());
        GeoPosition muenstertal = new GeoPosition(City.MUENSTERTAL.getLatitude(), City.MUENSTERTAL.getLongitude());
        GeoPosition staufen = new GeoPosition(City.STAUFEN.getLatitude(), City.STAUFEN.getLongitude());
        GeoPosition heitersheim = new GeoPosition(City.HEITERSHEIM.getLatitude(), City.HEITERSHEIM.getLongitude());

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

        JPanel topPanel = new JPanel();

        JPanel startPanel = new JPanel();
        JPanel endPanel = new JPanel();

        final List<City> startCities = new ArrayList<>(Arrays.asList(City.values()));
        final List<City> endCities = new ArrayList<>(Arrays.asList(City.values()));

        String[] tfLabelsStart = startCities
                .stream()
                .map(Enum::name)
                .toArray(String[]::new);

        String[] tfLabelsEnd = endCities
                .stream()
                .map(Enum::name)
                .toArray(String[]::new);

        final JComboBox<String> startCombo = new JComboBox<>(tfLabelsStart);
        final JComboBox<String> endCombo = new JComboBox<>(tfLabelsEnd);

        startCombo.addActionListener(e -> searchRoute(startCombo, endCombo,
                loerrach, weil, binzen, efringen_kirchen,
                steinen, maulburg, schopfheim, hausen,
                zell, wittlingen, kandern, bad_bellingen,
                schliengen, auggen, muellheim, marzell,
                tegernau, schoenau, muenstertal,
                staufen, heitersheim));

        endCombo.addActionListener(e -> searchRoute(startCombo, endCombo,
                loerrach, weil, binzen, efringen_kirchen,
                steinen, maulburg, schopfheim, hausen,
                zell, wittlingen, kandern, bad_bellingen,
                schliengen, auggen, muellheim, marzell,
                tegernau, schoenau, muenstertal,
                staufen, heitersheim));

        startPanel.setLayout(new GridLayout());
        startPanel.add(startCombo);

        endPanel.setLayout(new GridLayout());
        endPanel.add(endCombo);

        topPanel.add(startPanel);
        topPanel.add(endPanel);

        final JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(mapViewer);
        frame.setSize(1600, 1200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        mapViewer.addPropertyChangeListener("zoom", evt -> updateWindowTitle(frame, mapViewer));
        mapViewer.addPropertyChangeListener("center", evt -> updateWindowTitle(frame, mapViewer));
        updateWindowTitle(frame, mapViewer);
    }

    private static void searchRoute(JComboBox<String> startCombo, JComboBox<String> endCombo,
                                    GeoPosition loerrach, GeoPosition weil, GeoPosition binzen,
                                    GeoPosition efringen_kirchen, GeoPosition steinen,
                                    GeoPosition maulburg, GeoPosition schopfheim, GeoPosition hausen,
                                    GeoPosition zell, GeoPosition wittlingen, GeoPosition kandern,
                                    GeoPosition bad_bellingen, GeoPosition schliengen, GeoPosition auggen,
                                    GeoPosition muellheim, GeoPosition marzell, GeoPosition tegernau,
                                    GeoPosition schoenau, GeoPosition muenstertal, GeoPosition staufen,
                                    GeoPosition heitersheim) {
        City start = City.valueOf(Objects.requireNonNull(startCombo.getSelectedItem()).toString());
        City end = City.valueOf(Objects.requireNonNull(endCombo.getSelectedItem()).toString());

        List<Node> path = AStar.findPath(start, end);

        RoutePainter.nodes.clear();
        ArrayList<GeoPosition> positions = new ArrayList<>();
        for(Node node : path) {
            switch (node.getValue()) {
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
    }

    protected static void updateWindowTitle(JFrame frame, JXMapViewer mapViewer)
    {
        double lat = mapViewer.getCenterPosition().getLatitude();
        double lon = mapViewer.getCenterPosition().getLongitude();
        int zoom = mapViewer.getZoom();

        frame.setTitle(String.format("A* Kartensuche (%.2f / %.2f) - Zoom: %d", lat, lon, zoom));
    }

}