package com.relaxingleg.pathfinding.io;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Input {

    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWCursorPosCallback cursorPosCallback;
    private GLFWScrollCallback scrollCallback;
    private boolean[] keys = new boolean[GLFW_KEY_LAST];
    private boolean[] buttons = new boolean[GLFW_MOUSE_BUTTON_LAST];
    private double mouseX, mouseY;
    private double scrollX, scrollY;

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

    protected void cleanUp() {
        keyCallback.free();
        mouseButtonCallback.free();
        cursorPosCallback.free();
        scrollCallback.free();
    }

    public boolean isKeyPressed(int key) {
        return keys[key];
    }

    public boolean isButtonPressed(int button) {
        return buttons[button];
    }

    public GLFWKeyCallback getKeyCallback() {
        return keyCallback;
    }

    public GLFWMouseButtonCallback getMouseButtonCallback() {
        return mouseButtonCallback;
    }

    public GLFWCursorPosCallback getCursorPosCallback() {
        return cursorPosCallback;
    }

    public GLFWScrollCallback getScrollCallback() {
        return scrollCallback;
    }

    public boolean[] getKeys() {
        return keys;
    }

    public boolean[] getButtons() {
        return buttons;
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public double getScrollX() {
        return scrollX;
    }

    public double getScrollY() {
        return scrollY;
    }
}
