package com.relaxingleg.pathfinding;

import com.relaxingleg.pathfinding.render.Cell;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * This class controls the cells and the pathfinding
 * @author Matt
 */
public class GridController {

    public static final Vector3f EMPTY_COLOUR = new Vector3f(1);
    public static final Vector3f BLOCKED_COLOUR = new Vector3f(0);
    public static final Vector3f CHECKPOINT_COLOUR = new Vector3f(0.75f, 0, 0.75f);
    private final int size;
    private List<Cell> empty = new ArrayList<>();
    private List<Cell> blocked = new ArrayList<>();

    /**
     * Will create a clear board and add some input listeners
     * @param size The size of the board
     */
    public GridController(int size) {
        this.size = size;
        createEmptyGrid(size);
    }

    private void createEmptyGrid(int size) {
        empty.clear();
        blocked.clear();
        for(int x = 0; x < size; x++) {
            for(int y = 0; y < size; y++) {
                if((x == 0 && y == 0) || (x == size-1 && y == size-1)) {
                    empty.add(new Cell(x, y, CHECKPOINT_COLOUR));
                    continue;
                }
                empty.add(new Cell(x, y, EMPTY_COLOUR));
            }
        }
    }

    /**
     * Gets the cells to render
     * @return The cells to render
     */
    public List<Cell> getCells() {
        List<Cell> cells = new ArrayList<>();
        cells.addAll(empty);
        cells.addAll(blocked);
        return cells;
    }

    /**
     * Getter for grid size
     * @return Grid size
     */
    public int getSize() {
        return size;
    }
}
