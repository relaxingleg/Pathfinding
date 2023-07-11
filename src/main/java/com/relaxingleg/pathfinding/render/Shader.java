package com.relaxingleg.pathfinding.render;

import com.relaxingleg.pathfinding.utils.FileUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

/**
 * Controls the shader program for rendering
 * @author Matt
 */
public class Shader {

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
    private int programID;
    private int vertexID;
    private int fragmentID;
    private int transformationMatrixLocation;
    private int colourLocation;
    private int borderLocation;

    /**
     * Will create the shader program, bind attribute location, and get uniform locations
     */
    protected Shader() {
        programID = glCreateProgram();
        vertexID = createShader(GL_VERTEX_SHADER, "/vertexShader.glsl");
        fragmentID = createShader(GL_FRAGMENT_SHADER, "/fragmentShader.glsl");
        glAttachShader(programID, vertexID);
        glAttachShader(programID, fragmentID);
        glBindAttribLocation(programID, 0, "position");
        glValidateProgram(programID);
        glLinkProgram(programID);
        transformationMatrixLocation = glGetUniformLocation(programID, "transformationMatrix");
        colourLocation = glGetUniformLocation(programID, "passColour");
        borderLocation = glGetUniformLocation(programID, "border");
    }

    /**
     * Will create a shader
     * @param type The type of shader (vertex, fragment or geometry)
     * @param path Where the shader source is stored in the resources
     * @return The finished shader
     */
    private int createShader(int type, String path) {
        String source = FileUtils.loadResourceToString(path);
        int shaderID = glCreateShader(type);
        glShaderSource(shaderID, source);
        glCompileShader(shaderID);
        if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException("Couldn't compile shader. Log: " + glGetShaderInfoLog(shaderID));
        }
        return shaderID;
    }

    /**
     * Will start the shader program
     */
    public void start() {
        glUseProgram(programID);
    }

    /**
     * Will stop the shader program
     */
    public void stop() {
        glUseProgram(0);
    }

    /**
     * Will delete the shaders and program
     */
    public void cleanUp() {
        stop();
        glDetachShader(programID, vertexID);
        glDetachShader(programID, fragmentID);
        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);
        glDeleteProgram(programID);
    }

    /**
     * Will load the given transformation matrix to the shaders uniform
     * @param transformationMatrix The transformation matrix to load
     */
    public void loadTransformationMatrix(Matrix4f transformationMatrix) {
        transformationMatrix.get(matrixBuffer);
        glUniformMatrix4fv(transformationMatrixLocation, false, matrixBuffer);
    }

    /**
     * Will load a given colour to the shaders uniform
     * @param colour The colour to load
     */
    public void loadColour(Vector3f colour) {
        glUniform3f(colourLocation, colour.x, colour.y, colour.z);
    }

    /**
     * Will load if the cell borders are active the shaders uniform
     * @param hasBorder If the cell borders are active
     */
    public void loadBoarder(boolean hasBorder) {
        glUniform1i(borderLocation, hasBorder ? 1 : 0);
    }
}
