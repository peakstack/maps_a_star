class Edge {
    private final double cost;
    private final Node target;

    public Edge(Node target, double cost){
        this.target = target;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public Node getTarget() {
        return target;
    }
}