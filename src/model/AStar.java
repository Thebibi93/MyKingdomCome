package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

// This class is used to find the path for the enemies
public class AStar {
    public static List<Cells> findPath(Cells start, Cells target) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Map<Cells, Node> nodeMap = new HashMap<>();

        Node startNode = new Node(start, null, 0, heuristic(start, target));
        openSet.add(startNode);
        nodeMap.put(start, startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.cell.equals(target)) {
                // Reconstruct the path
                List<Cells> path = new ArrayList<>();
                Node trace = current;
                while (trace != null) {
                    path.add(trace.cell);
                    trace = trace.parent;
                }
                Collections.reverse(path);
                return path;
            }

            for (Cells neighbor : current.cell.getNeighbors()) {
                if (neighbor != null && neighbor.isWalkable()) {
                    double tentativeGScore = current.gScore + 1; // Assuming each step has a cost of 1

                    Node neighborNode = nodeMap.get(neighbor);
                    if (neighborNode == null || tentativeGScore < neighborNode.gScore) {
                        if (neighborNode == null) {
                            neighborNode = new Node(neighbor, null, Double.MAX_VALUE, Double.MAX_VALUE);
                            nodeMap.put(neighbor, neighborNode);
                        }

                        neighborNode.parent = current;
                        neighborNode.gScore = tentativeGScore;
                        neighborNode.fScore = tentativeGScore + heuristic(neighbor, target);

                        if (!openSet.contains(neighborNode)) {
                            openSet.add(neighborNode);
                        }
                    }
                }
            }
        }

        return null; // No path found
    }

    private static double heuristic(Cells a, Cells b) {
        // A simple heuristic (Euclidean distance)
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    private static class Node implements Comparable<Node> {
        private final Cells cell;
        private Node parent;
        private double gScore; // Cost from start to this node
        private double fScore; // gScore + heuristic cost to goal

        public Node(Cells cell, Node parent, double gScore, double fScore) {
            this.cell = cell;
            this.parent = parent;
            this.gScore = gScore;
            this.fScore = fScore;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.fScore, other.fScore);
        }
    }
}
