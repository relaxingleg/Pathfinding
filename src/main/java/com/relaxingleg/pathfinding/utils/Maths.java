package com.relaxingleg.pathfinding.utils;

import org.joml.Matrix4f;
import org.joml.Vector2f;

/**
 * Utility calls that handles maths functions
 * @author Matt
 */
public class Maths {

    /**
     * Will create a transformation matrix out of a 2D translation and scale
     * @param translation How much to translate it by
     * @param scale How much to scale it by
     * @return The transformation matrix
     */
    public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.identity();
        matrix4f.translate(translation.x, translation.y, 0);
        matrix4f.scale(scale.x, scale.y, 1);
        return matrix4f;
    }
}
