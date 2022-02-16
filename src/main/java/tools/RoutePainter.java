package tools;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class RoutePainter implements Painter<JXMapViewer> {
    private final Color color = Color.RED;

    private JXMapViewer mapViewer;

    @Override
    public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
        g = (Graphics2D) g.create();

        Rectangle rect = map.getViewportBounds();
        g.translate(-rect.x, -rect.y);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(4));

        drawRoute(g, map);

        g.setColor(color);
        g.setStroke(new BasicStroke(2));

        drawRoute(g, map);

        g.dispose();
        mapViewer = map;
    }

    private void drawLine(GeoPosition start, GeoPosition end, JXMapViewer map, Graphics2D g) {
        Point2D pt = map.getTileFactory().geoToPixel(start, map.getZoom());
        Point2D pt2 = map.getTileFactory().geoToPixel(end, map.getZoom());

        g.drawLine((int) pt2.getX(), (int) pt2.getY(), (int) pt.getX(), (int) pt.getY());
    }

    private final List<GeoPosition> nodes = new ArrayList<>();

    public void update() {
        mapViewer.repaint();
    }

    private void drawRoute(Graphics2D g, JXMapViewer map) {
        for (int i = 0; i < nodes.size(); i++) {
            if (i == nodes.size() - 1) {
                break;
            }

            GeoPosition pos = nodes.get(i);
            GeoPosition nextPos = nodes.get(i + 1);

            drawLine(pos, nextPos, map, g);
        }
    }

    public void clearNodes() {
        nodes.clear();
    }

    public void addNodes(ArrayList<GeoPosition> positions) {
        nodes.addAll(positions);
    }
}