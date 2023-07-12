package com.relaxingleg.pathfinding;

import com.relaxingleg.pathfinding.io.Input;
import com.relaxingleg.pathfinding.io.InputListener;
import com.relaxingleg.pathfinding.io.Window;
import com.relaxingleg.pathfinding.render.Cell;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_C;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

/**
 * This class controls the cells and the pathfinding
 * @author Matt
 */
public class GridController {

    public static final Vector3f EMPTY_COLOUR = new Vector3f(1);
    public static final Vector3f BLOCKED_COLOUR = new Vector3f(0);
    public static final Vector3f CHECKPOINT_COLOUR = new Vector3f(0.75f, 0, 0.75f);
    public static final Vector3f PATH_COLOUR = new Vector3f(0, 0.75f, 0);
    public static final Vector3f OPEN_COLOUR = new Vector3f(0, 0, 0.75f);
    public static final Vector3f CLOSED_COLOUR = new Vector3f(0.75f, 0, 0);
    private final int size;
    private List<Cell> empty = new ArrayList<>();
    private List<Cell> blocked = new ArrayList<>();
    private List<Cell> open = new ArrayList<>();
    private List<Cell> closed = new ArrayList<>();
    private boolean placement = true;
    private boolean pathFound = false;

    /**
     * Will create a clear board and add some input listeners
     * @param size The size of the board
     * @param input The input object
     * @param window The window
     */
    public GridController(int size, Input input, Window window) {
        this.size = size;
        createEmptyGrid(size);
        input.addInputListener(new InputListener("switch-placement", GLFW_KEY_ENTER, () -> placement = !placement));
        input.addInputListener(new InputListener("clear-board", GLFW_KEY_C, () -> {
            if(!placement) return;
            createEmptyGrid(size);
        }));
        input.addInputListener(new InputListener("switch-cell", GLFW_MOUSE_BUTTON_LEFT, () -> {
            if(!placement) return;
            double mouseX = input.getMouseX();
            double mouseY = input.getMouseY();
            double mouseXNormalized = mouseX/window.getWidth();
            double mouseYNormalized = mouseY/window.getHeight();

            float aspectRatio = (float)window.getHeight()/window.getWidth();
            float height = 1f/size;
            float width = height*aspectRatio;
            float margin = (1-width*size)/2;

            if(mouseXNormalized <= margin || mouseXNormalized >= 1-margin) return;

            float mouseXNoMargin = (float)(mouseXNormalized-margin)/(width*size);
            int gridX = (int)(mouseXNoMargin/height);
            int gridY = (int)(mouseYNormalized/height);

            if((gridX == 0 && gridY == 0) || (gridX == size-1 && gridY == size-1)) return;

            for(int i = 0; i < empty.size(); i++) {
                Cell cell = empty.get(i);
                if(cell.getX() == gridX && cell.getY() == gridY) {
                    empty.remove(cell);
                    cell.setColour(BLOCKED_COLOUR);
                    blocked.add(cell);
                    return;
                }
            }

            for(int i = 0; i < blocked.size(); i++) {
                Cell cell = blocked.get(i);
                if(cell.getX() == gridX && cell.getY() == gridY) {
                    blocked.remove(cell);
                    cell.setColour(EMPTY_COLOUR);
                    empty.add(cell);
                    return;
                }
            }
        }));
    }

    /**
     * Controls the pathfinding logic
     */
    public void update() {
        if(placement || pathFound) return;

        Cell currentCell = open.get(0);
        for(int i = 1; i < open.size(); i++) {
            if((currentCell.getgCost() + currentCell.gethCost()) > (open.get(i).getgCost() + open.get(i).gethCost())) {
                currentCell = open.get(i);
            }
        }

        open.remove(currentCell);
        closed.add(currentCell);
        currentCell.setColour(CLOSED_COLOUR);

        if(currentCell.getX() == size-1 && currentCell.getY() == size-1) {
            pathFound = true;
            retracePath(currentCell);
            return;
        }

        List<Cell> neighbours = new ArrayList<>();
        for(Cell cell : open) {
            boolean flag1 = cell.getX() >= currentCell.getX()-1;
            boolean flag2 = cell.getX() <= currentCell.getX()+1;
            boolean flag3 = cell.getY() >= currentCell.getY()-1;
            boolean flag4 = cell.getY() <= currentCell.getY()+1;
            if(flag1 && flag2 && flag3 && flag4) {
                neighbours.add(cell);
            }
        }

        for(int i = 0; i < empty.size(); i++) {
            Cell cell = empty.get(i);
            boolean flag1 = cell.getX() >= currentCell.getX()-1;
            boolean flag2 = cell.getX() <= currentCell.getX()+1;
            boolean flag3 = cell.getY() >= currentCell.getY()-1;
            boolean flag4 = cell.getY() <= currentCell.getY()+1;
            if(flag1 && flag2 && flag3 && flag4) {
                neighbours.add(cell);
            }
        }

        for(Cell cell : neighbours) {
            int newGCost = currentCell.getgCost() + getDistance(cell, currentCell);
            int newHCost = getDistance(cell, new Cell(size-1, size-1, null));
            if(cell.getgCost() == -1) {
                empty.remove(cell);
                cell.setParent(currentCell);
                cell.setgCost(newGCost);
                cell.sethCost(newHCost);
                cell.setColour(OPEN_COLOUR);
                open.add(cell);
            } else {
                int oldFCost = cell.getgCost() + cell.gethCost();
                int newFCost = newGCost + newHCost;
                if(newFCost < oldFCost) {
                    cell.setParent(currentCell);
                    cell.setgCost(newGCost);
                    cell.sethCost(newHCost);
                }
            }
        }
    }

    private int getDistance(Cell cellA, Cell cellB) {
        int distanceX = Math.abs(cellA.getX() - cellB.getX());
        int distanceY = Math.abs(cellA.getY() - cellB.getY());

        if(distanceX > distanceY) {
            return 14*distanceY + 10*(distanceX-distanceY);
        } else {
            return 14*distanceX + 10*(distanceY-distanceX);
        }
    }

    private void retracePath(Cell cell) {
        cell.setColour(PATH_COLOUR);
        Cell parent = cell.getParent();
        if(parent == null) return;
        retracePath(parent);
    }

    private void createEmptyGrid(int size) {
        empty.clear();
        blocked.clear();
        open.clear();
        closed.clear();
        pathFound = false;
        for(int x = 0; x < size; x++) {
            for(int y = 0; y < size; y++) {
                if(x == 0 && y == 0) {
                    Cell startCell = new Cell(x, y, CHECKPOINT_COLOUR);
                    startCell.setgCost(0);
                    startCell.sethCost(getDistance(startCell, new Cell(size-1, size-1, null)));
                    open.add(startCell);
                    continue;
                } else if(x == size-1 && y == size-1) {
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
        cells.addAll(open);
        cells.addAll(closed);
        return cells;
    }

    /**
     * Getter for grid size
     * @return Grid size
     */
    public int getSize() {
        return size;
    }

    /**
     * Getter for the placement value
     * @return Placement
     */
    public boolean isPlacement() {
        return placement;
    }
}
