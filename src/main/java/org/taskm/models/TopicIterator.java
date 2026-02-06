package org.taskm.models;

public interface TopicIterator {
    boolean hasNext();
    boolean hasPrev();
    Topic next(String name);
    Topic prev();
}
