package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class responsible for building and managing the city graph.
 * The graph is initialized once and reused for all pathfinding operations.
 */
public class GraphBuilder {
    private static GraphBuilder instance;
    private final Map<City, Node> cityNodeMap;

    private GraphBuilder() {
        this.cityNodeMap = new HashMap<>();
    }

    /**
     * Gets the singleton instance of GraphBuilder.
     *
     * @return the GraphBuilder instance
     */
    public static synchronized GraphBuilder getInstance() {
        if (instance == null) {
            instance = new GraphBuilder();
        }
        return instance;
    }

    /**
     * Builds the graph for a given destination city.
     * Each node's heuristic score is calculated based on distance to the destination.
     *
     * @param destination the destination city
     * @return a map of cities to their corresponding nodes
     */
    public Map<City, Node> buildGraph(City destination) {
        cityNodeMap.clear();

        // Initialize all nodes with heuristic values
        for (City city : City.values()) {
            double hScore = getDistanceFromLatLonInKm(
                    city.getLatitude(),
                    city.getLongitude(),
                    destination.getLatitude(),
                    destination.getLongitude()
            );
            cityNodeMap.put(city, new Node(city.toString(), hScore));
        }

        // Define all edges (bidirectional connections)
        addEdge(City.LOERRACH, City.STEINEN, 8.0);
        addEdge(City.LOERRACH, City.WEIL, 6.9);
        addEdge(City.LOERRACH, City.WITTLINGEN, 7.2);
        addEdge(City.LOERRACH, City.BINZEN, 5.6);

        addEdge(City.WEIL, City.BINZEN, 5.4);
        addEdge(City.WEIL, City.EFRINGEN_KIRCHEN, 10.3);

        addEdge(City.BINZEN, City.WITTLINGEN, 3.3);
        addEdge(City.BINZEN, City.EFRINGEN_KIRCHEN, 7.1);

        addEdge(City.EFRINGEN_KIRCHEN, City.BAD_BELLINGEN, 11.5);

        addEdge(City.STEINEN, City.MAULBURG, 4.0);
        addEdge(City.STEINEN, City.KANDERN, 14.0);

        addEdge(City.MAULBURG, City.SCHOPFHEIM, 4.5);

        addEdge(City.SCHOPFHEIM, City.HAUSEN, 4.3);

        addEdge(City.HAUSEN, City.ZELL, 4.4);

        addEdge(City.ZELL, City.TEGERNAU, 8.9);
        addEdge(City.ZELL, City.SCHOENAU, 10.8);

        addEdge(City.WITTLINGEN, City.KANDERN, 7.4);

        addEdge(City.KANDERN, City.MARZELL, 9.3);
        addEdge(City.KANDERN, City.SCHLIENGEN, 8.9);
        addEdge(City.KANDERN, City.MUELLHEIM, 15.7);

        addEdge(City.BAD_BELLINGEN, City.SCHLIENGEN, 4.1);

        addEdge(City.SCHLIENGEN, City.AUGGEN, 3.8);

        addEdge(City.AUGGEN, City.MUELLHEIM, 3.6);
        addEdge(City.AUGGEN, City.HEITERSHEIM, 11.5);

        addEdge(City.MUELLHEIM, City.MARZELL, 15.7);
        addEdge(City.MUELLHEIM, City.STAUFEN, 14.4);
        addEdge(City.MUELLHEIM, City.HEITERSHEIM, 9.0);

        addEdge(City.MARZELL, City.TEGERNAU, 10.6);

        addEdge(City.TEGERNAU, City.HAUSEN, 8.4);

        addEdge(City.SCHOENAU, City.MUENSTERTAL, 24.5);

        addEdge(City.MUENSTERTAL, City.STAUFEN, 5.6);

        addEdge(City.STAUFEN, City.HEITERSHEIM, 6.1);

        return cityNodeMap;
    }

    /**
     * Adds a bidirectional edge between two cities.
     *
     * @param city1 the first city
     * @param city2 the second city
     * @param cost  the cost (distance) of the edge
     */
    private void addEdge(City city1, City city2, double cost) {
        addDirectedEdge(city1, city2, cost);
        addDirectedEdge(city2, city1, cost);
    }

    /**
     * Adds a directed edge from one city to another.
     *
     * @param from the source city
     * @param to   the destination city
     * @param cost the cost (distance) of the edge
     */
    private void addDirectedEdge(City from, City to, double cost) {
        Node fromNode = cityNodeMap.get(from);
        Node toNode = cityNodeMap.get(to);

        Edge[] adjacencies = fromNode.getAdjacencies();
        Edge[] newAdjacencies = adjacencies == null ?
                new Edge[1] :
                new Edge[adjacencies.length + 1];

        if (adjacencies != null) {
            System.arraycopy(adjacencies, 0, newAdjacencies, 0, adjacencies.length);
        }
        newAdjacencies[newAdjacencies.length - 1] = new Edge(toNode, cost);
        fromNode.setAdjacencies(newAdjacencies);
    }

    /**
     * Calculates the distance between two geographic coordinates using the Haversine formula.
     * Source: https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula
     *
     * @param lat1 latitude of the first point
     * @param lon1 longitude of the first point
     * @param lat2 latitude of the second point
     * @param lon2 longitude of the second point
     * @return the distance in kilometers
     */
    private double getDistanceFromLatLonInKm(double lat1, double lon1, double lat2, double lon2) {
        int R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2 - lat1);
        double dLon = deg2rad(lon2 - lon1);
        double a = Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                        Math.sin(dLon / 2.0) * Math.sin(dLon / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    /**
     * Converts degrees to radians.
     *
     * @param deg angle in degrees
     * @return angle in radians
     */
    private double deg2rad(double deg) {
        return deg * (Math.PI / 180.0);
    }
}
