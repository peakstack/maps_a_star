package algo;

import model.City;
import model.Edge;
import model.GraphBuilder;
import model.Node;

import java.util.*;

/**
 * Implementation of the A* pathfinding algorithm.
 * Uses actual road distances as costs (g-scores) and air-line distances as heuristics (h-scores).
 */
public class AStar {
    private final GraphBuilder graphBuilder;

    public AStar() {
        this.graphBuilder = GraphBuilder.getInstance();
    }

    /**
     * Finds the shortest path between two cities using the A* algorithm.
     *
     * @param start the starting city
     * @param end   the destination city
     * @return a list of nodes representing the path from start to end
     */
    public List<Node> findPath(City start, City end) {
        Map<City, Node> graph = graphBuilder.buildGraph(end);

        Node startNode = graph.get(start);
        Node endNode = graph.get(end);

        if (startNode == null || endNode == null) {
            return Collections.emptyList();
        }

        executeSearch(startNode, endNode);
        return buildPath(endNode);
    }

    /**
     * Builds the path from start to target by following parent references.
     *
     * @param targetNode the destination node
     * @return a list of nodes representing the path
     */
    private List<Node> buildPath(Node targetNode) {
        List<Node> path = new ArrayList<>();

        for (Node node = targetNode; node != null; node = node.getParentNode()) {
            path.add(node);
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Executes the A* search algorithm.
     *
     * @param sourceNode the starting node
     * @param goalNode   the destination node
     */
    private void executeSearch(Node sourceNode, Node goalNode) {
        Set<Node> exploredNodes = new HashSet<>();
        PriorityQueue<Node> nodeQueue = new PriorityQueue<>(30,
                Comparator.comparingDouble(Node::getFScores)
        );

        sourceNode.setGScores(0);
        nodeQueue.add(sourceNode);

        while (!nodeQueue.isEmpty()) {
            Node currentNode = nodeQueue.poll();

            if (currentNode.getValue().equals(goalNode.getValue())) {
                return; // Goal found
            }

            exploredNodes.add(currentNode);

            for (Edge edge : currentNode.getAdjacencies()) {
                Node child = edge.getTarget();
                double currentCost = edge.getCost();
                double tempGScores = currentNode.getGScores() + currentCost;
                double tempFScores = tempGScores + child.getHScores();

                if (exploredNodes.contains(child) && tempFScores >= child.getFScores()) {
                    continue;
                }

                if (!nodeQueue.contains(child) || tempFScores < child.getFScores()) {
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



