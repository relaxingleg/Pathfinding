package com.relaxingleg.pathfinding.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Utility class for file handling
 * @author Matt
 */
public class FileUtils {

    /**
     * Will load a file from the resources as a string
     * @param path Where the resource is stored
     * @return The resource as a string
     */
    public static String loadResourceToString(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtils.class.getResourceAsStream(path)));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }
}
