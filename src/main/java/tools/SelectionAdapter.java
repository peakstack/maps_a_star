package tools;

import org.jxmapviewer.JXMapViewer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * Mouse adapter for handling selection interactions on the map viewer.
 * Responds to right-click drag events.
 */
public class SelectionAdapter extends MouseAdapter {
    private boolean dragging;
    private final JXMapViewer viewer;

    private final Point2D startPos = new Point2D.Double();
    private final Point2D endPos = new Point2D.Double();

    public SelectionAdapter(JXMapViewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON3) {
            return;
        }

        startPos.setLocation(e.getX(), e.getY());
        endPos.setLocation(e.getX(), e.getY());
        dragging = true;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!dragging)
            return;

        endPos.setLocation(e.getX(), e.getY());
        viewer.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!dragging)
            return;

        if (e.getButton() != MouseEvent.BUTTON3)
            return;

        viewer.repaint();
        dragging = false;
    }
}