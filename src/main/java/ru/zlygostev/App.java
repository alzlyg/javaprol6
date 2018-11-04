package ru.zlygostev;


import java.util.List;

/**
 *
 */
public class App 
{
    public static void main( String[] args ) {
        ProcessingArray processingArray = new ProcessingArray();
        Integer[] list = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8};
        List<Integer> newList = processingArray.takeElementsAfter(list);
        System.out.println(newList);
    }
}
