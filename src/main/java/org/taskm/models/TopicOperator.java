package org.taskm.models;

import java.util.List;

public interface TopicOperator {
    void addTopic(List<Topic> children);
    Topic getTopic(String name);
    void updateTopic(String Title);
    void deleteTopic(List<String> topics);
}
