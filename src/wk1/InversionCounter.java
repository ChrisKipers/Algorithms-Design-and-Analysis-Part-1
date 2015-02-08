package wk1;

import utils.ContentReader;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;

public class InversionCounter {
    public static void main(String[] args) {
        try {
            int[] testNumbers = ContentReader.getIntArrayFromFile("/resources/IntegerArray.txt");
            BigInteger totalInversions = getNumberOfInversionsAndSortArray(testNumbers);
            System.out.println(totalInversions);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static BigInteger getNumberOfInversionsAndSortArray(int[] array) {
        if (array.length <= 1) {
            return new BigInteger("0");
        } else {
            int middlePoint = array.length / 2;
            int mergeIndex = 0, leftIndex = 0, rightIndex = 0;
            int[] leftArray = Arrays.copyOfRange(array, 0, middlePoint);
            int[] rightArray = Arrays.copyOfRange(array, middlePoint, array.length);

            BigInteger inversionsFromLeft = getNumberOfInversionsAndSortArray(leftArray);
            BigInteger inversionsFromRight = getNumberOfInversionsAndSortArray(rightArray);
            BigInteger inversionsFromMerge = new BigInteger("0");

            while (mergeIndex < array.length) {
                if (leftIndex < leftArray.length && rightIndex < rightArray.length) {
                    if (leftArray[leftIndex] > rightArray[rightIndex]) {
                        inversionsFromMerge = inversionsFromMerge.add(new BigInteger(String.valueOf(leftArray.length - leftIndex)));
                        array[mergeIndex] = rightArray[rightIndex];
                        rightIndex++;
                    } else {
                        array[mergeIndex] = leftArray[leftIndex];
                        leftIndex++;
                    }
                } else if (leftIndex < leftArray.length) {
                    array[mergeIndex] = leftArray[leftIndex];
                    leftIndex++;
                } else {
                    array[mergeIndex] = rightArray[rightIndex];
                    rightIndex++;
                }

                mergeIndex++;
            }

            BigInteger total = inversionsFromLeft.add(inversionsFromRight).add(inversionsFromMerge);
            return total;
        }
    }
}
