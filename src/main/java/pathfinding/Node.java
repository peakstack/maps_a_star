package pathfinding;

public class Node {
    private final String value;
    private double gScores;
    private final double hScores;
    public double fScores = 0;
    private Edge[] adjacencies;
    private Node parent;

    public Node(String val, double hVal){
        this.value = val;
        this.hScores = hVal;
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
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public Edge[] getAdjacencies() {
        return adjacencies;
    }

    public void setAdjacencies(Edge[] adjacencies) {
        this.adjacencies = adjacencies;
    }
}