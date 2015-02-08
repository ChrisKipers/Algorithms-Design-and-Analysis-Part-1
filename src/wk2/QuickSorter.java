package wk2;

import utils.ContentReader;

import java.io.IOException;

public class QuickSorter {

    public static void main(String[] args) {
        try {
            int[] testNumbers = ContentReader.getIntArrayFromFile("/resources/QuickSort.txt");
            int[] testNumbersForLastElementSort = testNumbers.clone();
            int[] testNumbersForMedianSort = testNumbers.clone();

            int numberOfSwapsForFirstElementSort = getNumberOfComparisonsFromQuickSort(testNumbers, 0, testNumbers.length - 1, new ChooseFirstElement());
            int numberOfSwapsForLastElementSort = getNumberOfComparisonsFromQuickSort(testNumbersForLastElementSort, 0, testNumbersForLastElementSort.length - 1, new ChooseLastElement());
            int numberOfSwapsForMedianElementSort = getNumberOfComparisonsFromQuickSort(testNumbersForMedianSort, 0, testNumbersForMedianSort.length - 1, new ChooseMedianElement());

            System.out.println("Number of swaps for first element sort: " + numberOfSwapsForFirstElementSort);
            System.out.println("Number of swaps for last element sort: " + numberOfSwapsForLastElementSort);
            System.out.println("Number of swaps for merge element sort: " + numberOfSwapsForMedianElementSort);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    private static int getNumberOfComparisonsFromQuickSort(int[] values, int startPos, int endPos, PivotChooser pivotChooser) {
        if (startPos >= endPos) {
            return 0;
        }
        int pivotPosition = pivotChooser.choosePivot(values, startPos, endPos);

        //swap pivot with beginning position
        int pivotValue = values[pivotPosition];
        values[pivotPosition] = values[startPos];
        values[startPos] = pivotValue;

        int abovePivotValueBorder = startPos + 1;
        for (int i = abovePivotValueBorder; i <= endPos; i++) {
            if (values[i] < pivotValue) {
                int borderValue = values[abovePivotValueBorder];
                values[abovePivotValueBorder] = values[i];
                values[i] = borderValue;
                abovePivotValueBorder++;
            }
        }

        values[startPos] = values[abovePivotValueBorder - 1];
        values[abovePivotValueBorder - 1] = pivotValue;

        int swapsForSubRoutine = endPos - startPos;
        int swapsFromSmallerSideSort = getNumberOfComparisonsFromQuickSort(values, startPos, abovePivotValueBorder - 2, pivotChooser);
        int swapsFromBiggerSideSort = getNumberOfComparisonsFromQuickSort(values, abovePivotValueBorder, endPos, pivotChooser);

        return swapsForSubRoutine + swapsFromSmallerSideSort + swapsFromBiggerSideSort;
    }

    private interface PivotChooser {
        public int choosePivot(int[] values, int startPos, int endPos);
    }

    private static class ChooseFirstElement implements PivotChooser {
        @Override
        public int choosePivot(int[] values, int startPos, int endPos) {
            return startPos;
        }
    }

    private static class ChooseLastElement implements PivotChooser {
        @Override
        public int choosePivot(int[] values, int startPos, int endPos) {
            return endPos;
        }
    }

    private static class ChooseMedianElement implements PivotChooser {
        @Override
        public int choosePivot(int[] values, int startPos, int endPos) {
            int middlePosition = ((endPos - startPos) / 2) + startPos;
            if (isValueMedium(values[startPos], values[middlePosition], values[endPos]))  {
                return startPos;
            } else if (isValueMedium(values[middlePosition], values[startPos], values[endPos])) {
                return middlePosition;
            } else {
                return endPos;
            }
        }

        private boolean isValueMedium(int targetValue, int compareValue1, int compareValue2) {
            return ((targetValue < compareValue1 && targetValue > compareValue2) ||
                    (targetValue > compareValue1 && targetValue < compareValue2));
        }
    }
}
