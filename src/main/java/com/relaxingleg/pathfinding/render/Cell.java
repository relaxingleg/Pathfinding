package com.relaxingleg.pathfinding.render;

import org.joml.Vector3f;

/**
 * This class represents a single cell
 * @author Matt
 */
public class Cell {

    private final int x, y;
    private Vector3f colour;

    /**
     * Initializes the cell
     * @param x The X coordinate of the cell
     * @param y The Y coordinate of the cell
     * @param colour The colour the cell should display
     */
    public Cell(int x, int y, Vector3f colour) {
        this.x = x;
        this.y = y;
        this.colour = colour;
    }

    /**
     * Getter for the cell X coordinate
     * @return Cell X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for the cell Y coordinate
     * @return Cell Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Getter for the cell colour
     * @return Cell colour
     */
    public Vector3f getColour() {
        return colour;
    }

    /**
     * Setter for the cell colour
     * @param colour New cell colour
     */
    public void setColour(Vector3f colour) {
        this.colour = colour;
    }
}
