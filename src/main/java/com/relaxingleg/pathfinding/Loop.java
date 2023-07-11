package com.relaxingleg.pathfinding;

import com.relaxingleg.pathfinding.io.Input;
import com.relaxingleg.pathfinding.io.Window;
import com.relaxingleg.pathfinding.render.Cell;
import com.relaxingleg.pathfinding.render.Renderer;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * The main loop of the engine
 * Also contains the main method
 * @author Matt
 */
public class Loop {

    private Window window;
    private Input input;
    private Renderer renderer;
    private float deltaTime;

    /**
     * The main method of the program
     * @param args The args of the JVM
     */
    public static void main(String[] args) {
        Loop loop = new Loop();
        loop.loop();
    }

    private Loop() {
        window = new Window(1920, 1080, "Pathfinding");
        input = window.getInput();
        renderer = new Renderer();
    }

    private void loop() {
        float currentFrameTime;
        float lastFrameTime = System.currentTimeMillis();
        while (!window.isWindowShouldClose()) {
            update();
            render();
            currentFrameTime = System.currentTimeMillis();
            deltaTime = currentFrameTime - lastFrameTime;
            lastFrameTime = currentFrameTime;
        }
        cleanUp();
    }

    private void update() {
        window.update();
    }

    private void render() {
        window.render();
        renderer.render(window, List.of(new Cell(0, 0, new Vector3f(1))), 10, false);
    }

    private void cleanUp() {
        window.cleanUp();
        renderer.cleanUp();
    }
}