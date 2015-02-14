package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public abstract class ContentReader {

    public static int[] getIntArrayFromFile(String fileName) throws java.io.IOException {
        InputStream is = ContentReader.class.getResourceAsStream(fileName);
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line;
        while ((line = br.readLine()) != null) {
            int lineValue = Integer.valueOf(line);
            numbers.add(lineValue);
        }
        br.close();
        int[] allNumbers = new int[numbers.size()];
        for (int i = 0; i < numbers.size(); i++) {
            allNumbers[i] = numbers.get(i);
        }
        return allNumbers;
    }

    public static Map<Integer, ArrayList<Integer>> getAdjacencyList(String fileName) throws java.io.IOException {
        InputStream is = ContentReader.class.getResourceAsStream(fileName);
        Map<Integer, ArrayList<Integer>> adjacencyList = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] vertices = line.split("\t");
            ArrayList<Integer> adjacentVertices = new ArrayList<>();
            for (int i = 1; i < vertices.length; i++) {
                adjacentVertices.add(Integer.parseInt(vertices[i]));
            }
            adjacencyList.put(Integer.parseInt(vertices[0]), adjacentVertices);
        }
        br.close();
        return adjacencyList;
    }

    public static ArrayList<Edge> getEdges(String fileName) throws java.io.IOException {
        InputStream is = ContentReader.class.getResourceAsStream(fileName);
        ArrayList<Edge> edges = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] vertices = line.split(" ");
            Edge edge = new Edge(Integer.parseInt(vertices[0]), Integer.parseInt(vertices[1]));
            edges.add(edge);
        }
        br.close();
        return edges;
    }
}
