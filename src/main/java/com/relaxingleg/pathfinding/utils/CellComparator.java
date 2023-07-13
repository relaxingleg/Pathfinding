package com.relaxingleg.pathfinding.utils;

import com.relaxingleg.pathfinding.render.Cell;

import java.util.Comparator;

/**
 * This class is used by a heap to sort cells
 * @author Matt
 */
public class CellComparator implements Comparator<Cell> {

    /**
     * This is what does the comparing
     * @param cell1 the first object to be compared.
     * @param cell2 the second object to be compared.
     * @return 1 if cell1 has a lower fCost, 0 if they have equal fCost, -1 if cell2 has a lower fCost
     */
    @Override
    public int compare(Cell cell1, Cell cell2) {
        int cell1FCost = cell1.gethCost() + cell1.getgCost();
        int cell2FCost = cell2.gethCost() + cell2.getgCost();

        if(cell1FCost < cell2FCost) {
            return 1;
        } else if(cell1FCost > cell2FCost) {
            return -1;
        }
        return 0;
    }
}
