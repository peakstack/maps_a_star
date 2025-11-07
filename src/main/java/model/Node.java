package model;

/**
 * Represents a node in the graph used for A* pathfinding.
 * Each node contains:
 * - gScores: actual cost from start to this node
 * - hScores: heuristic (estimated) cost from this node to goal
 * - fScores: total cost (g + h)
 */
public class Node {
    private final double hScores;
    private double gScores;
    private double fScores = 0;
    private Node parentNode;
    private final String value;
    private Edge[] adjacencyVector;

    /**
     * Constructs a new Node with a name and heuristic value.
     *
     * @param value  the name of the node
     * @param hValue the heuristic score (h-score)
     */
    public Node(String value, double hValue) {
        this.value = value;
        this.hScores = hValue;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Gets the name/value of this node.
     *
     * @return the node's name
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the f-score (total estimated cost).
     *
     * @return the f-score
     */
    public double getFScores() {
        return fScores;
    }

    /**
     * Sets the f-score (total estimated cost).
     *
     * @param fScores the new f-score
     */
    public void setFScores(double fScores) {
        this.fScores = fScores;
    }

    /**
     * Gets the g-score (actual cost from start).
     *
     * @return the g-score
     */
    public double getGScores() {
        return gScores;
    }

    /**
     * Sets the g-score (actual cost from start).
     *
     * @param gScores the new g-score
     */
    public void setGScores(double gScores) {
        this.gScores = gScores;
    }

    /**
     * Gets the h-score (heuristic cost to goal).
     *
     * @return the h-score
     */
    public double getHScores() {
        return hScores;
    }

    /**
     * Sets the parent node in the path.
     *
     * @param parent the parent node
     */
    public void setParent(Node parent) {
        this.parentNode = parent;
    }

    /**
     * Gets the parent node in the path.
     *
     * @return the parent node
     */
    public Node getParentNode() {
        return parentNode;
    }

    /**
     * Gets the adjacent edges from this node.
     *
     * @return array of adjacent edges
     */
    public Edge[] getAdjacencies() {
        return adjacencyVector;
    }

    /**
     * Sets the adjacent edges from this node.
     *
     * @param adjacencyVector array of edges
     */
    public void setAdjacencies(Edge[] adjacencyVector) {
        this.adjacencyVector = adjacencyVector;
    }
}