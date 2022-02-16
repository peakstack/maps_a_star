package model;

public class Edge {
    private final Node target;
    private final double cost;

    public Edge(Node target, double cost){
        this.cost = cost;
        this.target = target;
    }

    public double getCost() {
        return cost;
    }

    public Node getTarget() {
        return target;
    }
}