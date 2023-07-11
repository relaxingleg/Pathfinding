package com.relaxingleg.pathfinding.render;

import com.relaxingleg.pathfinding.utils.Maths;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.w3c.dom.UserDataHandler;

import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * This class is responsible for rendering the cells to the screen
 * @author Matt
 */
public class Renderer {

    private final Shader shader = new Shader();
    private final int vaoID;
    private final int vboID;

    /**
     * Will create the VAO and VBO for the renderer
     */
    public Renderer() {
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        FloatBuffer positions = BufferUtils.createFloatBuffer(8);
        positions.put(new float[]{
                -1, 1,
                -1, -1,
                1, 1,
                1, -1
        });
        positions.flip();
        glBufferData(GL_ARRAY_BUFFER, positions, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    /**
     * Renders a set of cells
     * @param cells All the cells to be rendered
     * @param size The size of the current grid
     * @param bordersActive If the cell borders are active
     */
    public void render(List<Cell> cells, int size, boolean bordersActive) {
        shader.start();
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        for(Cell cell : cells) {
            shader.loadColour(cell.getColour());
            shader.loadBoarder(bordersActive);
            glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
        }
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        shader.stop();
    }

    /**
     * Will delete the VAO and VBO
     */
    public void cleanUp() {
        glDeleteVertexArrays(vaoID);
        glDeleteBuffers(vboID);
    }
}
