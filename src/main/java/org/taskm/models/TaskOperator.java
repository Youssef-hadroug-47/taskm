package org.taskm.models;

public interface TaskOperator {
    void readTask();
    void updateTask( String description);
    void markTask(Task.TaskStatus status);
    void deleteTask();
}
