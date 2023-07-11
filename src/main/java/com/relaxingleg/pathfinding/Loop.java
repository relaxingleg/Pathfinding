package com.relaxingleg.pathfinding;

import com.relaxingleg.pathfinding.io.Input;
import com.relaxingleg.pathfinding.io.Window;

public class Loop {

    private Window window;
    private Input input;
    private float deltaTime;

    public static void main(String[] args) {
        Loop loop = new Loop();
        loop.loop();
    }

    private Loop() {
        window = new Window(1920, 1080, "Pathfinding");
        input = window.getInput();
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
    }

    private void cleanUp() {
        window.cleanUp();
    }
}
