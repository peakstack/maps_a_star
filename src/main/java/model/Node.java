package model;

public class Node {
    private final double hScores;
    private double gScores;
    public double fScores = 0;
    private Node parentNode;
    private final String value;
    private Edge[] adjacencyVector;

    public Node(String value, double hValue){
        this.value = value;
        this.hScores = hValue;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public double getFScores() {
        return fScores;
    }

    public void setFScores(double fScores) {
        this.fScores = fScores;
    }

    public double getGScores() {
        return gScores;
    }

    public void setGScores(double gScores) {
        this.gScores = gScores;
    }

    public double getHScores() {
        return hScores;
    }

    public void setParent(Node parent) {
        this.parentNode = parent;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public Edge[] getAdjacencies() {
        return adjacencyVector;
    }

    public void setAdjacencies(Edge[] adjacencyVector) {
        this.adjacencyVector = adjacencyVector;
    }
}