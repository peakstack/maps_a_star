package model;

/**
 * Represents a directed edge in the graph connecting two nodes.
 * Each edge has a target node and a cost (distance).
 */
public class Edge {
    private final Node target;
    private final double cost;

    /**
     * Constructs a new Edge with a target node and cost.
     *
     * @param target the destination node
     * @param cost   the cost (distance) to reach the target
     */
    public Edge(Node target, double cost) {
        this.cost = cost;
        this.target = target;
    }

    /**
     * Gets the cost of this edge.
     *
     * @return the edge cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * Gets the target node of this edge.
     *
     * @return the target node
     */
    public Node getTarget() {
        return target;
    }
}