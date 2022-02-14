import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;

public class AStar {
    private static double getDistanceFromLatLonInKm(double lat1, double lon1, double lat2, double lon2) {
        int R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2-lat1);
        double dLon = deg2rad(lon2-lon1);
        double a = Math.sin(dLat / 2.0D) * Math.sin(dLat / 2.0D) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                        Math.sin(dLon / 2.0D) * Math.sin(dLon / 2.0D);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c; //distance in km
    }

    private static double deg2rad(double deg) {
        return deg * (Math.PI / 180.0D);
    }

    /*
     * Die Kosten sind die realen Streckenwerte
     * Die H-Kosten sind die berechneten Luft-Distanzen der verschiedenen Geo-Punkte
     */
    public static List<Node> findPath(City start, City end){
        Node loerrach = new Node("Lörrach",
                getDistanceFromLatLonInKm(City.LOERRACH.latitude, City.LOERRACH.longitude, end.latitude, end.longitude));
        Node weil = new Node("Weil am Rhein",
                getDistanceFromLatLonInKm(City.WEIL.latitude, City.WEIL.longitude, end.latitude, end.longitude));
        Node binzen = new Node("Binzen",
                getDistanceFromLatLonInKm(City.BINZEN.latitude, City.BINZEN.longitude, end.latitude, end.longitude));
        Node efringen_kirchen = new Node("Efringen-Kirchen",
                getDistanceFromLatLonInKm(City.EFRINGEN_KIRCHEN.latitude, City.EFRINGEN_KIRCHEN.longitude, end.latitude, end.longitude));
        Node steinen = new Node("Steinen",
                getDistanceFromLatLonInKm(City.STEINEN.latitude, City.STEINEN.longitude, end.latitude, end.longitude));
        Node maulburg = new Node("Maulburg",
                getDistanceFromLatLonInKm(City.MAULBURG.latitude, City.MAULBURG.longitude, end.latitude, end.longitude));
        Node schopfheim = new Node("Schopfheim",
                getDistanceFromLatLonInKm(City.SCHOPFHEIM.latitude, City.SCHOPFHEIM.longitude, end.latitude, end.longitude));
        Node hausen = new Node("Hausen",
                getDistanceFromLatLonInKm(City.HAUSEN.latitude, City.HAUSEN.longitude, end.latitude, end.longitude));
        Node zell = new Node("Zell",
                getDistanceFromLatLonInKm(City.ZELL.latitude, City.ZELL.longitude, end.latitude, end.longitude));
        Node wittlingen = new Node("Wittlingen",
                getDistanceFromLatLonInKm(City.WITTLINGEN.latitude, City.WITTLINGEN.longitude, end.latitude, end.longitude));
        Node kandern = new Node("Kandern",
                getDistanceFromLatLonInKm(City.KANDERN.latitude, City.KANDERN.longitude, end.latitude, end.longitude));
        Node bad_bellingen = new Node("Bad Bellingen",
                getDistanceFromLatLonInKm(City.BAD_BELLINGEN.latitude, City.BAD_BELLINGEN.longitude, end.latitude, end.longitude));
        Node schliengen = new Node("Schliengen",
                getDistanceFromLatLonInKm(City.SCHLIENGEN.latitude, City.SCHLIENGEN.longitude, end.latitude, end.longitude));
        Node auggen = new Node("Auggen",
                getDistanceFromLatLonInKm(City.AUGGEN.latitude, City.AUGGEN.longitude, end.latitude, end.longitude));
        Node muellheim = new Node("Müllheim",
                getDistanceFromLatLonInKm(City.MUELLHEIM.latitude, City.MUELLHEIM.longitude, end.latitude, end.longitude));
        Node marzell = new Node("Malsburg-Marzell",
                getDistanceFromLatLonInKm(City.MARZELL.latitude, City.MARZELL.longitude, end.latitude, end.longitude));
        Node tegernau = new Node("Tegernau",
                getDistanceFromLatLonInKm(City.TEGERNAU.latitude, City.TEGERNAU.longitude, end.latitude, end.longitude));
        Node schoenau = new Node("Schönau",
                getDistanceFromLatLonInKm(City.SCHOENAU.latitude, City.SCHOENAU.longitude, end.latitude, end.longitude));
        Node muenstertal = new Node("Münstertal",
                getDistanceFromLatLonInKm(City.MUENSTERTAL.latitude, City.MUENSTERTAL.longitude, end.latitude, end.longitude));
        Node staufen = new Node("Staufen",
                getDistanceFromLatLonInKm(City.STAUFEN.latitude, City.STAUFEN.longitude, end.latitude, end.longitude));
        Node heitersheim = new Node("Heitersheim",
                getDistanceFromLatLonInKm(City.HEITERSHEIM.latitude, City.HEITERSHEIM.longitude, end.latitude, end.longitude));

        loerrach.setAdjacencies(new Edge[]{
                new Edge(steinen,8.0),
                new Edge(weil,6.9),
                new Edge(wittlingen,7.2),
                new Edge(binzen,5.6)
        });

        weil.setAdjacencies(new Edge[]{
                new Edge(loerrach,6.9),
                new Edge(binzen,5.4),
                new Edge(efringen_kirchen,10.3)
        });

        binzen.setAdjacencies(new Edge[]{
                new Edge(wittlingen,3.3),
                new Edge(weil,5.5),
                new Edge(efringen_kirchen,7.1),
                new Edge(loerrach,5.6)
        });

        efringen_kirchen.setAdjacencies(new Edge[]{
                new Edge(weil,10.3),
                new Edge(binzen,7.1),
                new Edge(bad_bellingen,11.5)
        });

        steinen.setAdjacencies(new Edge[]{
                new Edge(loerrach,8.1),
                new Edge(maulburg,4.0),
                new Edge(kandern,14.0)
        });

        maulburg.setAdjacencies(new Edge[]{
                new Edge(steinen,3.9),
                new Edge(schopfheim,4.5)
        });

        schopfheim.setAdjacencies(new Edge[]{
                new Edge(maulburg,4.4),
                new Edge(hausen,4.3)
        });

        hausen.setAdjacencies(new Edge[]{
                new Edge(schopfheim,4.2),
                new Edge(zell,4.4)
        });

        zell.setAdjacencies(new Edge[]{
                new Edge(tegernau,8.9),
                new Edge(hausen,4.4),
                new Edge(schoenau,10.8)
        });

        wittlingen.setAdjacencies(new Edge[]{
                new Edge(kandern,7.4),
                new Edge(binzen,4.2),
                new Edge(loerrach,7.2)
        });

        kandern.setAdjacencies(new Edge[]{
                new Edge(wittlingen,11.9),
                new Edge(marzell,9.3),
                new Edge(schliengen,8.9),
                new Edge(steinen,14.0),
                new Edge(muellheim,15.7)
        });

        bad_bellingen.setAdjacencies(new Edge[]{
                new Edge(schliengen,4.1),
                new Edge(efringen_kirchen,11.4)
        });

        schliengen.setAdjacencies(new Edge[]{
                new Edge(auggen,3.8),
                new Edge(bad_bellingen,4.1),
                new Edge(kandern,8.9)
        });

        auggen.setAdjacencies(new Edge[]{
                new Edge(muellheim,3.6),
                new Edge(schliengen,3.9),
                new Edge(heitersheim,11.5)
        });

        muellheim.setAdjacencies(new Edge[]{
                new Edge(marzell,15.7),
                new Edge(auggen,3.6),
                new Edge(staufen,14.4),
                new Edge(heitersheim, 9.0),
                new Edge(kandern,15.7)
        });

        marzell.setAdjacencies(new Edge[]{
                new Edge(muellheim,15.7),
                new Edge(kandern,9.3),
                new Edge(tegernau,10.6)
        });

        tegernau.setAdjacencies(new Edge[]{
                new Edge(zell,8.9),
                new Edge(marzell,10.6),
                new Edge(hausen,8.4)
        });

        schoenau.setAdjacencies(new Edge[]{
                new Edge(zell,10.7),
                new Edge(muenstertal,24.5)
        });

        muenstertal.setAdjacencies(new Edge[]{
                new Edge(schoenau,24.6),
                new Edge(staufen,5.6),
        });

        staufen.setAdjacencies(new Edge[]{
                new Edge(heitersheim,6.1),
                new Edge(muenstertal,5.6),
                new Edge(muellheim,14.4),
        });

        heitersheim.setAdjacencies(new Edge[]{
                new Edge(staufen,6.1),
                new Edge(muellheim,9.1),
                new Edge(auggen,11.5)
        });

        Node startNode = null, endNode = null;

        switch (start) {
            case LOERRACH:
                startNode = loerrach;
                break;
            case WEIL:
                startNode = weil;
                break;
            case BINZEN:
                startNode = binzen;
                break;
            case EFRINGEN_KIRCHEN:
                startNode = efringen_kirchen;
                break;
            case STEINEN:
                startNode = steinen;
                break;
            case MAULBURG:
                startNode = maulburg;
                break;
            case SCHOPFHEIM:
                startNode = schopfheim;
                break;
            case HAUSEN:
                startNode = hausen;
                break;
            case ZELL:
                startNode = zell;
                break;
            case WITTLINGEN:
                startNode = wittlingen;
                break;
            case KANDERN:
                startNode = kandern;
                break;
            case BAD_BELLINGEN:
                startNode = bad_bellingen;
                break;
            case SCHLIENGEN:
                startNode = schliengen;
                break;
            case AUGGEN:
                startNode = auggen;
                break;
            case MUELLHEIM:
                startNode = muellheim;
                break;
            case MARZELL:
                startNode = marzell;
                break;
            case TEGERNAU:
                startNode = tegernau;
                break;
            case SCHOENAU:
                startNode = schoenau;
                break;
            case MUENSTERTAL:
                startNode = muenstertal;
                break;
            case STAUFEN:
                startNode = staufen;
                break;
            case HEITERSHEIM:
                startNode = heitersheim;
                break;
        }

        switch (end) {
            case LOERRACH:
                endNode = loerrach;
                break;
            case WEIL:
                endNode = weil;
                break;
            case BINZEN:
                endNode = binzen;
                break;
            case EFRINGEN_KIRCHEN:
                endNode = efringen_kirchen;
                break;
            case STEINEN:
                endNode = steinen;
                break;
            case MAULBURG:
                endNode = maulburg;
                break;
            case SCHOPFHEIM:
                endNode = schopfheim;
                break;
            case HAUSEN:
                endNode = hausen;
                break;
            case ZELL:
                endNode = zell;
                break;
            case WITTLINGEN:
                endNode = wittlingen;
                break;
            case KANDERN:
                endNode = kandern;
                break;
            case BAD_BELLINGEN:
                endNode = bad_bellingen;
                break;
            case SCHLIENGEN:
                endNode = schliengen;
                break;
            case AUGGEN:
                endNode = auggen;
                break;
            case MUELLHEIM:
                endNode = muellheim;
                break;
            case MARZELL:
                endNode = marzell;
                break;
            case TEGERNAU:
                endNode = tegernau;
                break;
            case SCHOENAU:
                endNode = schoenau;
                break;
            case MUENSTERTAL:
                endNode = muenstertal;
                break;
            case STAUFEN:
                endNode = staufen;
                break;
            case HEITERSHEIM:
                endNode = heitersheim;
                break;
        }

        executeSearch(startNode, endNode);
        return printPath(endNode);
    }

    public static List<Node> printPath(Node targetNode){
        List<Node> path = new ArrayList<>();

        for (Node node = targetNode; node != null; node = node.getParent()){
            path.add(node);
        }
        Collections.reverse(path);
        return path;
    }

    private static void executeSearch(Node sourceNode, Node goal){
        Set<Node> exploredNodes = new HashSet<>();

        PriorityQueue<Node> nodeQueue = new PriorityQueue<>(30,
                Comparator.comparingDouble(i -> i.fScores)
        );
        sourceNode.setGScores(0);
        nodeQueue.add(sourceNode);
        boolean found = false;

        while (!nodeQueue.isEmpty() && !found){
            Node currentNode = nodeQueue.poll();
            exploredNodes.add(currentNode);

            if (currentNode.getValue().equals(goal.getValue())){
                found = true;
            }

            for (Edge edge : currentNode.getAdjacencies()){
                Node child = edge.getTarget();
                double cost = edge.getCost();
                double tempGScores = currentNode.getGScores() + cost;
                double tempFScores = tempGScores + child.getHScores();
                double childFScores = child.getFScores();

                if(exploredNodes.contains(child)
                        && tempFScores >= childFScores){
                    continue;
                } else if (!nodeQueue.contains(child)
                        || tempFScores < childFScores){
                    exploredNodes.remove(child);

                    child.setParent(currentNode);
                    child.setGScores(tempGScores);
                    child.setFScores(tempFScores);

                    nodeQueue.remove(child);
                    nodeQueue.add(child);
                }
            }
        }
    }
}



