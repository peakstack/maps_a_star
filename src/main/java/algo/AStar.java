package algo;

import model.City;
import model.Edge;
import model.Node;

import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;

public class AStar {
    //Haversine-Formel
    //https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula
    private double getDistanceFromLatLonInKm(double lat1, double lon1, double lat2, double lon2) {
        int R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2-lat1);
        double dLon = deg2rad(lon2-lon1);
        double a = Math.sin(dLat / 2.0D) * Math.sin(dLat / 2.0D) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                        Math.sin(dLon / 2.0D) * Math.sin(dLon / 2.0D);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c; //distance in km
    }

    private double deg2rad(double deg) {
        return deg * (Math.PI / 180.0D);
    }

    /*
     * Die Kosten sind die realen Streckenwerte
     * Die H-Kosten sind die berechneten Luft-Distanzen der verschiedenen Geo-Punkte
     */
    public List<Node> findPath(City start, City end){
        Node loerrach = new Node("Lörrach",
                getDistanceFromLatLonInKm(City.LOERRACH.getLatitude(), City.LOERRACH.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node weil = new Node("Weil am Rhein",
                getDistanceFromLatLonInKm(City.WEIL.getLatitude(), City.WEIL.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node binzen = new Node("Binzen",
                getDistanceFromLatLonInKm(City.BINZEN.getLatitude(), City.BINZEN.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node efringen_kirchen = new Node("Efringen-Kirchen",
                getDistanceFromLatLonInKm(City.EFRINGEN_KIRCHEN.getLatitude(), City.EFRINGEN_KIRCHEN.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node steinen = new Node("Steinen",
                getDistanceFromLatLonInKm(City.STEINEN.getLatitude(), City.STEINEN.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node maulburg = new Node("Maulburg",
                getDistanceFromLatLonInKm(City.MAULBURG.getLatitude(), City.MAULBURG.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node schopfheim = new Node("Schopfheim",
                getDistanceFromLatLonInKm(City.SCHOPFHEIM.getLatitude(), City.SCHOPFHEIM.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node hausen = new Node("Hausen",
                getDistanceFromLatLonInKm(City.HAUSEN.getLatitude(), City.HAUSEN.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node zell = new Node("Zell",
                getDistanceFromLatLonInKm(City.ZELL.getLatitude(), City.ZELL.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node wittlingen = new Node("Wittlingen",
                getDistanceFromLatLonInKm(City.WITTLINGEN.getLatitude(), City.WITTLINGEN.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node kandern = new Node("Kandern",
                getDistanceFromLatLonInKm(City.KANDERN.getLatitude(), City.KANDERN.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node bad_bellingen = new Node("Bad Bellingen",
                getDistanceFromLatLonInKm(City.BAD_BELLINGEN.getLatitude(), City.BAD_BELLINGEN.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node schliengen = new Node("Schliengen",
                getDistanceFromLatLonInKm(City.SCHLIENGEN.getLatitude(), City.SCHLIENGEN.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node auggen = new Node("Auggen",
                getDistanceFromLatLonInKm(City.AUGGEN.getLatitude(), City.AUGGEN.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node muellheim = new Node("Müllheim",
                getDistanceFromLatLonInKm(City.MUELLHEIM.getLatitude(), City.MUELLHEIM.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node marzell = new Node("Malsburg-Marzell",
                getDistanceFromLatLonInKm(City.MARZELL.getLatitude(), City.MARZELL.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node tegernau = new Node("Tegernau",
                getDistanceFromLatLonInKm(City.TEGERNAU.getLatitude(), City.TEGERNAU.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node schoenau = new Node("Schönau",
                getDistanceFromLatLonInKm(City.SCHOENAU.getLatitude(), City.SCHOENAU.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node muenstertal = new Node("Münstertal",
                getDistanceFromLatLonInKm(City.MUENSTERTAL.getLatitude(), City.MUENSTERTAL.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node staufen = new Node("Staufen",
                getDistanceFromLatLonInKm(City.STAUFEN.getLatitude(), City.STAUFEN.getLongitude(), end.getLatitude(), end.getLongitude()));
        Node heitersheim = new Node("Heitersheim",
                getDistanceFromLatLonInKm(City.HEITERSHEIM.getLatitude(), City.HEITERSHEIM.getLongitude(), end.getLatitude(), end.getLongitude()));

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

    public List<Node> printPath(Node targetNode){
        List<Node> path = new ArrayList<>();

        for (Node node = targetNode; node != null; node = node.getParentNode()){
            path.add(node);
        }
        Collections.reverse(path);
        return path;
    }

    private void executeSearch(Node sourceNode, Node goal){
        Set<Node> exploredNodes = new HashSet<>();

        PriorityQueue<Node> nodeQueue = new PriorityQueue<>(30,
                Comparator.comparingDouble(i -> i.fScores)
        );
        sourceNode.setGScores(0);
        nodeQueue.add(sourceNode);
        boolean goalFound = false;

        while (!nodeQueue.isEmpty() && !goalFound){
            Node currentNode = nodeQueue.poll();
            exploredNodes.add(currentNode);

            if (currentNode.getValue().equals(goal.getValue())){
                goalFound = true;
            }

            for (Edge edge : currentNode.getAdjacencies()){
                Node child = edge.getTarget();
                double currentCost = edge.getCost();
                double tempGScores = currentNode.getGScores() + currentCost;
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



