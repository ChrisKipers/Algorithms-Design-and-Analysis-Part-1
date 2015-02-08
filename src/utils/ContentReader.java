package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
}
