package wk4;

import utils.ContentReader;
import utils.Edge;

import java.io.IOException;
import java.util.*;

// @IMPORTANT The large data set requires a large stack. I was able to get the solution setting the vm option -Xss8m

public class StronglyConnectedComponents {
    private static final int NUMBER_OF_COMPONENTS_TO_REPORT = 5;

    public static void main(String[] args) {
        try {
            ArrayList<Edge> edges = ContentReader.getEdges("/resources/SCC.txt");
            ArrayList<Set<Integer>> components = getStronglyConnectedComponents(edges);
            Integer[] componentSizes = new Integer[components.size()];
            for (int i = 0; i < components.size(); i++) {
                componentSizes[i] = components.get(i).size();
            }
            Arrays.sort(componentSizes, Collections.reverseOrder());

            for (int i = 0; i < NUMBER_OF_COMPONENTS_TO_REPORT; i++) {
                if (i < componentSizes.length) {
                    System.out.println(componentSizes[i]);
                } else {
                    System.out.println(0);
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Set<Integer>> getStronglyConnectedComponents(ArrayList<Edge> edges) {
        Integer[] sortOrder = getSortOrder(edges);
        Map<Integer, ArrayList<Integer>> adjacencyList = getAdjacencyList(edges);
        Set<Integer> discoveredVertices = new HashSet<>();
        ArrayList<Set<Integer>> components = new ArrayList<>();
        for (int position = sortOrder.length - 1; position >= 0; position--) {
            Integer vertex = sortOrder[position];
            if (!discoveredVertices.contains(vertex)) {
                Set<Integer> newComponent = new HashSet<>();
                components.add(newComponent);
                populateComponentSearchSets(vertex, newComponent, discoveredVertices, adjacencyList);
            }
        }
        return components;
    }

    private static void populateComponentSearchSets(
            Integer targetVertex,
            Set<Integer> verticesInComponent,
            Set<Integer> discoveredVertices,
            Map<Integer, ArrayList<Integer>> adjacencyList) {

        discoveredVertices.add(targetVertex);
        verticesInComponent.add(targetVertex);
        ArrayList<Integer> adjacentVertices = adjacencyList.getOrDefault(targetVertex, new ArrayList<>());
        for (Integer vertex : adjacentVertices) {
            if (!discoveredVertices.contains(vertex)) {
                populateComponentSearchSets(vertex, verticesInComponent, discoveredVertices, adjacencyList);
            }
        }
    }

    private static Integer[] getSortOrder(ArrayList<Edge> edges) {
        ArrayList<Edge> reversedEdges = getReverseEdges(edges);
        Map<Integer, ArrayList<Integer>> reversedAdjacencyList = getAdjacencyList(reversedEdges);
        Integer largestVertex = findLargestVertex(edges);
        Integer[] positionToVertex = new Integer[largestVertex];
        Set<Integer> discoveredVertices = new HashSet<>();
        OrderPosition orderPosition = new OrderPosition();

        for (Integer vertex : reversedAdjacencyList.keySet()) {
            if (!discoveredVertices.contains(vertex)) {
                populatePositionCollections(vertex, reversedAdjacencyList, orderPosition, discoveredVertices, positionToVertex);
            }
        }
        return positionToVertex;
    }

    private static void populatePositionCollections(
            Integer targetVertex,
            Map<Integer, ArrayList<Integer>> reversedAdjacencyList,
            OrderPosition orderPosition,
            Set<Integer> discoveredVertex,
            Integer[] positionToVertex) {
        discoveredVertex.add(targetVertex);
        ArrayList<Integer> adjacentVertices = reversedAdjacencyList.getOrDefault(targetVertex, new ArrayList<Integer>());

        for (Integer vertex : adjacentVertices) {
            if (!discoveredVertex.contains(vertex)) {
                populatePositionCollections(vertex, reversedAdjacencyList, orderPosition, discoveredVertex, positionToVertex);
            }
        }
        positionToVertex[orderPosition.getValue()] = targetVertex;
        orderPosition.increment();
    }

    private static Map<Integer, ArrayList<Integer>> getAdjacencyList(ArrayList<Edge> edges) {
        Map<Integer, ArrayList<Integer>> adjacencyList = new HashMap<>();
        for (Edge edge : edges) {
            ArrayList<Integer> adjacentVertices;
            if (adjacencyList.containsKey(edge.getHead())) {
                adjacentVertices = adjacencyList.get(edge.getHead());
            } else {
                adjacentVertices = new ArrayList<>();
                adjacencyList.put(edge.getHead(), adjacentVertices);
            }
            adjacentVertices.add(edge.getTail());
        }
        return adjacencyList;
    }

    private static ArrayList<Edge> getReverseEdges(ArrayList<Edge> edges) {
        ArrayList<Edge> reversedEdges = new ArrayList<>(edges.size());
        for (Edge edge : edges) {
            Edge reversedEdge = new Edge(edge.getTail(), edge.getHead());
            reversedEdges.add(reversedEdge);
        }
        return reversedEdges;
    }

    private static Integer findLargestVertex(ArrayList<Edge> edges) {
        Integer largestVertex = Integer.MIN_VALUE;
        for (Edge edge : edges) {
            if (edge.getHead() > largestVertex) {
                largestVertex = edge.getHead();
            } else if (edge.getTail() > largestVertex) {
                largestVertex = edge.getTail();
            }
        }
        return largestVertex;
    }

    private static class OrderPosition {
        Integer value;

        public Integer getValue() {
            return value;
        }

        public OrderPosition() {
            value = 0;
        }

        public void increment() {
            value++;
        }
    }
}
