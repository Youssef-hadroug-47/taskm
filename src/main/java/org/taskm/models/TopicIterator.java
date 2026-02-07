package org.taskm.models;

import java.util.function.Consumer;

public interface TopicIterator {
    boolean hasNext();
    boolean hasPrev();
    Topic next(String name);
    Topic prev();
    void BFS(Consumer<Topic> consumer);
    void DFS(Consumer<Topic> consumer);
}
