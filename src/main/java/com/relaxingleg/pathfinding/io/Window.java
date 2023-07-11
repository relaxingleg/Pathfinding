package com.relaxingleg.pathfinding.io;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;

public class Window {

    private final long window;
    private final Input input;
    private int width, height;
    private String title;
    private GLFWWindowSizeCallback sizeCallback;
    private Vector3f backgroundColour = new Vector3f(0.5f);

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.input = new Input();

        if(!glfwInit()) {
            throw new RuntimeException("Couldn't initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        window = glfwCreateWindow(width, height, title, 0, 0);
        if(window == 0) {
            throw new RuntimeException("Couldn't create window");
        }

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidMode.width() - width)/2, (vidMode.height() - height)/2);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        createCallbacks();
        glfwShowWindow(window);
        createCapabilities();
    }

    private void createCallbacks() {
        sizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                glViewport(0, 0, width, height);
            }
        };

        glfwSetWindowSizeCallback(window, sizeCallback);
        glfwSetKeyCallback(window, input.getKeyCallback());
        glfwSetMouseButtonCallback(window, input.getMouseButtonCallback());
        glfwSetCursorPosCallback(window, input.getCursorPosCallback());
        glfwSetScrollCallback(window, input.getScrollCallback());
    }

    public void update() {
        glfwPollEvents();
    }

    public void render() {
        glfwSwapBuffers(window);
        glClearColor(backgroundColour.x, backgroundColour.y, backgroundColour.z, 1);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void cleanUp() {
        sizeCallback.free();
        input.cleanUp();
    }

    public boolean isWindowShouldClose() {
        return glfwWindowShouldClose(window);
    }

    public void setWindowShouldClose() {
        glfwSetWindowShouldClose(window, true);
    }

    public long getWindow() {
        return window;
    }

    public Input getInput() {
        return input;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        glfwSetWindowTitle(window, title);
        this.title = title;
    }

    public GLFWWindowSizeCallback getSizeCallback() {
        return sizeCallback;
    }

    public Vector3f getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(Vector3f backgroundColour) {
        this.backgroundColour = backgroundColour;
    }
}
