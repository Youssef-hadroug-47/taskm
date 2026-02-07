package org.taskm.models;

import java.util.List;

public interface TaskOperator {
    void addTask(List<Task> tasks);
    Task getTask(int id);
    void updateTask( String description);
    void markTask(Task.TaskStatus status);
    void deleteTask(List<Integer> tasks);
}
