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
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class RoutePlanner {
    private final GeoPosition loerrach = new GeoPosition(City.LOERRACH.getLatitude(), City.LOERRACH.getLongitude());
    private final GeoPosition weil = new GeoPosition(City.WEIL.getLatitude(), City.WEIL.getLongitude());
    private final GeoPosition binzen = new GeoPosition(City.BINZEN.getLatitude(), City.BINZEN.getLongitude());
    private final GeoPosition efringen_kirchen = new GeoPosition(City.EFRINGEN_KIRCHEN.getLatitude(), City.EFRINGEN_KIRCHEN.getLongitude());
    private final GeoPosition steinen = new GeoPosition(City.STEINEN.getLatitude(), City.STEINEN.getLongitude());
    private final GeoPosition maulburg = new GeoPosition(City.MAULBURG.getLatitude(), City.MAULBURG.getLongitude());
    private final GeoPosition schopfheim = new GeoPosition(City.SCHOPFHEIM.getLatitude(), City.SCHOPFHEIM.getLongitude());
    private final GeoPosition hausen = new GeoPosition(City.HAUSEN.getLatitude(), City.HAUSEN.getLongitude());
    private final GeoPosition zell = new GeoPosition(City.ZELL.getLatitude(), City.ZELL.getLongitude());
    private final GeoPosition wittlingen = new GeoPosition(City.WITTLINGEN.getLatitude(), City.WITTLINGEN.getLongitude());
    private final GeoPosition kandern = new GeoPosition(City.KANDERN.getLatitude(), City.KANDERN.getLongitude());
    private final GeoPosition bad_bellingen = new GeoPosition(City.BAD_BELLINGEN.getLatitude(), City.BAD_BELLINGEN.getLongitude());
    private final GeoPosition schliengen = new GeoPosition(City.SCHLIENGEN.getLatitude(), City.SCHLIENGEN.getLongitude());
    private final GeoPosition auggen = new GeoPosition(City.AUGGEN.getLatitude(), City.AUGGEN.getLongitude());
    private final GeoPosition muellheim = new GeoPosition(City.MUELLHEIM.getLatitude(), City.MUELLHEIM.getLongitude());
    private final GeoPosition marzell = new GeoPosition(City.MARZELL.getLatitude(), City.MARZELL.getLongitude());
    private final GeoPosition tegernau = new GeoPosition(City.TEGERNAU.getLatitude(), City.TEGERNAU.getLongitude());
    private final GeoPosition schoenau = new GeoPosition(City.SCHOENAU.getLatitude(), City.SCHOENAU.getLongitude());
    private final GeoPosition muenstertal = new GeoPosition(City.MUENSTERTAL.getLatitude(), City.MUENSTERTAL.getLongitude());
    private final GeoPosition staufen = new GeoPosition(City.STAUFEN.getLatitude(), City.STAUFEN.getLongitude());
    private final GeoPosition heitersheim = new GeoPosition(City.HEITERSHEIM.getLatitude(), City.HEITERSHEIM.getLongitude());

    final List<GeoPosition> track = Arrays.asList(
            loerrach, weil, binzen, efringen_kirchen,
            steinen, maulburg, schopfheim, hausen,
            zell, wittlingen, kandern, bad_bellingen,
            schliengen, auggen, muellheim, marzell,
            tegernau, schoenau, muenstertal,
            staufen, heitersheim
    );

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

        startCombo.addActionListener(e -> searchRoute(startCombo, endCombo));
        endCombo.addActionListener(e -> searchRoute(startCombo, endCombo));

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
                    searchRoute(startCombo, endCombo);

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }).start();
    }

    private final AStar aStar = new AStar();

    private void searchRoute(JComboBox<City> startCombo, JComboBox<City> endCombo) {
        City start = (City) startCombo.getSelectedItem();
        City end = (City) endCombo.getSelectedItem();

        List<Node> foundPath = aStar.findPath(Objects.requireNonNull(start), Objects.requireNonNull(end));
        routePainter.clearNodes();

        ArrayList<GeoPosition> positions = new ArrayList<>();
        for(Node node : foundPath) {
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
