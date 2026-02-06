package org.taskm.models;

import java.util.List;

public interface TopicOperator {
    void addTopic(List<Topic> children);
    void addTask (List<Task> children);
    void readTopic();
    void updateTopic(String Title);
    void deleteTopic();
}
