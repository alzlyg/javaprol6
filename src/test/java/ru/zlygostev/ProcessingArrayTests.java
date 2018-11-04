package ru.zlygostev;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ProcessingArrayTests {

    @Test
    public void testRightBound() {
        ProcessingArray processingArray = new ProcessingArray();
        Integer[] list = new Integer[]{1, 2, 3, 4};
        List<Integer> newList = processingArray.takeElementsAfter(list);
        System.out.println(newList);
    }

    @Test
    public void testLeftBound() {
        ProcessingArray processingArray = new ProcessingArray();
        Integer[] list = new Integer[]{4, 1, 2, 3};
        List<Integer> newList = processingArray.takeElementsAfter(list);
        System.out.println(newList);
    }

    @Test
    public void testTwoStopNumber() {
        ProcessingArray processingArray = new ProcessingArray();
        Integer[] list = new Integer[]{1, 2, 3, 4, 5, 6, 4, 7, 8};
        List<Integer> newList = processingArray.takeElementsAfter(list);
        System.out.println(newList);
    }

    @Test
    public void testNoStopNumber() {
        // При отсутствии 4 должен выброситься RuntimeExceprion
        ProcessingArray processingArray = new ProcessingArray();
        Integer[] list = new Integer[]{1, 2, 3, 5, 6, 7, 8};
        List<Integer> newList = processingArray.takeElementsAfter(list);
        System.out.println(newList);
    }

    @Test
    public void testOneAndFourCheckElements() {
        ProcessingArray processingArray = new ProcessingArray();
        Integer[] list = new Integer[]{1, 2, 3, 4, 5, 6, 7};
        Assert.assertEquals(true, processingArray.checkElements(list));
    }

    @Test
    public void testNoFourCheckElements() {
        ProcessingArray processingArray = new ProcessingArray();
        Integer[] list = new Integer[]{1, 2, 3, 5, 6, 7, 8};
        Assert.assertEquals(false, processingArray.checkElements(list));
    }

    @Test
    public void testNoOneCheckElements() {
        ProcessingArray processingArray = new ProcessingArray();
        Integer[] list = new Integer[]{0, 2, 3, 4, 5, 6, 7, 8};
        Assert.assertEquals(false, processingArray.checkElements(list));
    }

    @Test
    public void testNoOneNoFourCheckElements() {
        ProcessingArray processingArray = new ProcessingArray();
        Integer[] list = new Integer[]{0, 2, 3, 5, 6, 7, 8};
        Assert.assertEquals(false, processingArray.checkElements(list));
    }
}
