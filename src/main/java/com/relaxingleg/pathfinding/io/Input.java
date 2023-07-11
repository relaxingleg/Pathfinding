package com.relaxingleg.pathfinding.io;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * This class controls the GLFW input
 * @author Matt
 */
public class Input {

    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWCursorPosCallback cursorPosCallback;
    private GLFWScrollCallback scrollCallback;
    private boolean[] keys = new boolean[GLFW_KEY_LAST];
    private boolean[] buttons = new boolean[GLFW_MOUSE_BUTTON_LAST];
    private double mouseX, mouseY;
    private double scrollX, scrollY;

    /**
     * This will create all the callbacks for the inputs
     */
    protected Input() {
        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = (action != GLFW_RELEASE);
            }
        };
        mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = (action != GLFW_RELEASE);
            }
        };
        cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = xpos;
                mouseY = ypos;
            }
        };
        scrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scrollX += xoffset;
                scrollY += yoffset;
            }
        };
    }

    /**
     * This will free all the callbacks
     */
    protected void cleanUp() {
        keyCallback.free();
        mouseButtonCallback.free();
        cursorPosCallback.free();
        scrollCallback.free();
    }

    /**
     * Checks if a given keyboard key is pressed
     * @param key The key to check
     * @return If the key is pressed
     */
    public boolean isKeyPressed(int key) {
        return keys[key];
    }

    /**
     * Checks if a given mouse button is pressed
     * @param button The button to check
     * @return If the key is pressed
     */
    public boolean isButtonPressed(int button) {
        return buttons[button];
    }

    /**
     * Getter for key callback
     * @return Key callback
     */
    public GLFWKeyCallback getKeyCallback() {
        return keyCallback;
    }

    /**
     * Getter for mouse button callback
     * @return Mouse button callback
     */
    public GLFWMouseButtonCallback getMouseButtonCallback() {
        return mouseButtonCallback;
    }

    /**
     * Getter for cursor pos callback
     * @return Cursor pos callback
     */
    public GLFWCursorPosCallback getCursorPosCallback() {
        return cursorPosCallback;
    }

    /**
     * Getter for scroll callback
     * @return Scroll callback
     */
    public GLFWScrollCallback getScrollCallback() {
        return scrollCallback;
    }

    /**
     * Getter for pressed keyboard keys array
     * @return Pressed keyboard keys
     */
    public boolean[] getKeys() {
        return keys;
    }

    /**
     * Getter for pressed mouse buttons array
     * @return Pressed mouse buttons
     */
    public boolean[] getButtons() {
        return buttons;
    }

    /**
     * Getter for cursor X position
     * @return Cursor X position
     */
    public double getMouseX() {
        return mouseX;
    }

    /**
     * Getter for cursor Y position
     * @return Cursor Y position
     */
    public double getMouseY() {
        return mouseY;
    }

    /**
     * Getter for scroll X
     * @return Scroll X
     */
    public double getScrollX() {
        return scrollX;
    }

    /**
     * Getter for scroll Y
     * @return Scroll Y
     */
    public double getScrollY() {
        return scrollY;
    }
}
