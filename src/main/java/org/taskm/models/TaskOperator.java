package org.taskm.models;

public interface TaskOperator {
    void createTask();
    void readTask();
    void updateTask( String description);
    void markTask(TaskStatus status);
    void deleteTask();
}
