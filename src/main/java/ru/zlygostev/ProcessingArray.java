package ru.zlygostev;

import java.util.ArrayList;
import java.util.List;

public class ProcessingArray {
    private final static int stopElementOfList = 4;

    // Метод возвращает массив элементов идущих после последней 4
    public List<Integer> takeElementsAfter(Integer[] list) throws RuntimeException {
        List<Integer> newList = new ArrayList<Integer>();
        boolean stopElementIsPresent = false;
        for(int i = list.length-1; i >= 0; i--) {
            if (list[i] == stopElementOfList) {
                stopElementIsPresent = true;
                for(int j = i+1; j < list.length; j++) {
                    newList.add(list[j]);
                }
                break;
            }
        }
        if (!stopElementIsPresent) {throw new RuntimeException();}
        return newList;
    }

    // Метод возвращает false, если в массиве нет ни одной 4 или нет ни одной единицы
    public boolean checkElements(Integer[] arr) {
        boolean booleanOne = false;
        boolean booleanFour = false;
        for(int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) booleanOne = true;
            if (arr[i] == 4) booleanFour = true;
        }
        return (booleanOne&&booleanFour);
    }

}
