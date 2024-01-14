package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixIterator2 implements Iterator<Integer> {
    private int[][] array;
    private int row;
    private int col;

    public MatrixIterator2(int[][] array) {
        this.array = array;
        row = 0;
        col = 0;
    }

    @Override
    public boolean hasNext() {
        while (row < array.length) {
            if (col < array[row].length) {
                return true;
            } else {
                col = 0;
                row++;
            }
        }
        return false;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements in the array");
        }
        Integer element = array[row][col];
        col++;
        return element;
    }
}