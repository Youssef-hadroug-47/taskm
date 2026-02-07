package org.taskm.models;


public interface TaskOperator {
    void updateTask( String description);
    void markTask(Task.TaskStatus status);
}
