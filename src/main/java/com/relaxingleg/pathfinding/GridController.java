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
    private final int size;
    private List<Cell> empty = new ArrayList<>();
    private List<Cell> blocked = new ArrayList<>();
    private boolean placement = true;

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

    /**
     * Getter for the placement value
     * @return Placement
     */
    public boolean isPlacement() {
        return placement;
    }
}
