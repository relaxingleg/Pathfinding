package com.relaxingleg.pathfinding.utils;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class is a heap for optimised searching
 * @param <T> The object the heap stores
 * @author Matt
 */
public class Heap<T> {

    private final List<T> items = new LinkedList<>();
    private final Comparator<T> comparator;

    /**
     * Creates an empty heap
     * @param comparator How to compare
     */
    public Heap(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    /**
     * Gets the top item of the heap without removing it
     * @return The top item of the heap
     */
    public T peek() {
        return items.get(0);
    }

    /**
     * Gets the top item of the heap and removes it
     * @return The top item of the heap
     */
    public T pop() {
        T item = items.get(0);
        removeTop();
        return item;
    }

    /**
     * Puts an item into the heap
     * @param item The item to put in the heap
     */
    public void put(T item) {
        items.add(item);
        swapUp(items.size()-1);
    }

    /**
     * Clears the heap
     */
    public void clear() {
        items.clear();
    }

    /**
     * Gets the size of the heap
     * @return The size of the heap
     */
    public int size() {
        return items.size();
    }

    /**
     * Checks if the heap is empty
     * @return If the heap is empty
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Will run an action on all items in the heap
     * @param action The action to run
     */
    public void forEach(Consumer<T> action) {
        items.forEach(action);
    }

    /**
     * Will return all the items in the heap
     * @return All items in the heap
     */
    public List<T> getAllItems() {
        return new LinkedList<>(items);
    }

    private void swapUp(int position) {
        int parentPosition = getParentPosition(position);
        T item = items.get(position);
        T parentItem = items.get(parentPosition);
        int swap = comparator.compare(item, parentItem);
        if(swap > 0) {
            swapItems(parentPosition, position);
            if(parentPosition == 0) return;
            swapUp(parentPosition);
        }
    }

    private void swapDown(int position) {
        int firstChildPosition = getFirstChildPosition(position);
        if(firstChildPosition >= items.size()) return;
        T item = items.get(position);
        T firstChild = items.get(firstChildPosition);
        if(firstChildPosition +1 >= items.size()) {
            if(comparator.compare(firstChild, item) < 0) return;
            swapItems(firstChildPosition, position);
            return;
        }
        T secondChild = items.get(firstChildPosition+1);
        int swapFirst = comparator.compare(firstChild, item);
        int swapSecond = comparator.compare(secondChild, item);
        if(swapFirst > 0 && swapSecond < 0) {
            swapItems(position, firstChildPosition);
            swapDown(firstChildPosition);
        } else if(swapFirst < 0 && swapSecond > 0) {
            swapItems(position, firstChildPosition+1);
            swapDown(firstChildPosition+1);
        } else {
            if(comparator.compare(firstChild, secondChild) > 0) {
                swapItems(position, firstChildPosition);
                swapDown(firstChildPosition);
            } else {
                swapItems(position, firstChildPosition+1);
                swapDown(firstChildPosition+1);
            }
        }
    }

    private void swapItems(int positionA, int positionB) {
        T temp = items.get(positionA);
        items.set(positionA, items.get(positionB));
        items.set(positionB, temp);
    }

    private void removeTop() {
        if(items.size() == 1) {
            items.remove(0);
            return;
        }
        T lastItem = items.get(size()-1);
        items.remove(lastItem);
        items.set(0, lastItem);
        swapDown(0);
    }

    private int getParentPosition(int position) {
        return (position-1)/2;
    }

    private int getFirstChildPosition(int position) {
        return 2*position+1;
    }
}
