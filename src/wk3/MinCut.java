package wk3;

import utils.ContentReader;

import java.io.IOException;
import java.util.*;

public class MinCut {

    private static final int NUMBER_OF_TRIALS = 200;

    public static void main(String[] args) {
        try {
            Map<Integer, ArrayList<Integer>> adjacencyList = ContentReader.getAdjacencyList("/resources/kargerMinCut.txt");
            int minCut = getMinCutFromTrails(adjacencyList);
            System.out.println(minCut);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getMinCutFromTrails(Map<Integer, ArrayList<Integer>> adjacencyList) {
        int bestMinCuts = Integer.MAX_VALUE;
        for (int i = 0; i < NUMBER_OF_TRIALS; i++) {
            Map<Integer, ArrayList<Integer>> clonedAdjacencyList = cloneAdjacencyList(adjacencyList);
            int minCut = getMinCut(clonedAdjacencyList);
            if (minCut < bestMinCuts) {
                bestMinCuts = minCut;
            }
        }
        return bestMinCuts;
    }

    public static int getMinCut(Map<Integer, ArrayList<Integer>> adjacencyList) {
        while (adjacencyList.size() > 2) {
            VertexTuple randomVertices = getRandomVerticesToContract(adjacencyList);
            contractVertex(adjacencyList, randomVertices);
        }
        Integer vertex = (new ArrayList<>(adjacencyList.keySet())).get(0);
        return adjacencyList.get(vertex).size();
    }

    private static void contractVertex(Map<Integer, ArrayList<Integer>> adjacencyList, VertexTuple vertices) {
        Integer vertex1 = vertices.getVertex1();
        Integer vertex2 = vertices.getVertex2();

        ArrayList<Integer> contractedVertexAdjacentVertices = adjacencyList.get(vertex2);
        //Update adjacent vertices list to reflect merger of two vertices
        for (Integer adjacentVertex : contractedVertexAdjacentVertices) {
            ArrayList<Integer> adjacentVertices = adjacencyList.get(adjacentVertex);
            for (int i = 0; i < adjacentVertices.size(); i++) {
                if (adjacentVertices.get(i).equals(vertex2)) {
                    adjacentVertices.set(i, vertex1);
                }
            }
        }
        ArrayList<Integer> vertex1AdjacencyList = adjacencyList.get(vertex1);
        vertex1AdjacencyList.addAll(contractedVertexAdjacentVertices);
        //Remove self loops
        ArrayList<Integer> adjacencyListWithoutSelfLoops = new ArrayList<>();
        for (Integer adjacentVertex : vertex1AdjacencyList) {
            if (!adjacentVertex.equals(vertex1) && !adjacentVertex.equals(vertex2)) {
                adjacencyListWithoutSelfLoops.add(adjacentVertex);
            }
        }
        adjacencyList.put(vertex1, adjacencyListWithoutSelfLoops);
        adjacencyList.remove(vertex2);
    }

    private static VertexTuple getRandomVerticesToContract(Map<Integer, ArrayList<Integer>> adjacencyList) {
        Random r = new Random();
        Integer randomVertexPosition = r.nextInt(adjacencyList.keySet().size());
        ArrayList<Integer> keys = new ArrayList<>(adjacencyList.keySet());
        Integer vertex = keys.get(randomVertexPosition);

        ArrayList<Integer> adjacentVertices = adjacencyList.get(vertex);
        Integer randomAdjacentVertexPosition = r.nextInt(adjacentVertices.size());
        Integer adjacentVertex = adjacentVertices.get(randomAdjacentVertexPosition);

        return new VertexTuple(vertex, adjacentVertex);
    }

    private static Map<Integer, ArrayList<Integer>> cloneAdjacencyList(Map<Integer, ArrayList<Integer>> adjacencyList) {
        Map<Integer, ArrayList<Integer>> clone = new HashMap<>();
        for (Integer vertex : adjacencyList.keySet()) {
            clone.put(vertex, (ArrayList) adjacencyList.get(vertex).clone());
        }
        return clone;
    }

    private static class VertexTuple {
        private Integer vertex1;
        private Integer vertex2;

        public Integer getVertex1() {
            return vertex1;
        }

        public Integer getVertex2() {
            return vertex2;
        }

        public VertexTuple(Integer vertex1, Integer vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        }
    }
}
