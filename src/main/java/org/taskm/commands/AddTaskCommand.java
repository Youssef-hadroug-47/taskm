package org.taskm.commands;

import java.time.LocalDate;
import java.util.List;

import org.taskm.cli.Result;
import org.taskm.cli.Token;
import org.taskm.models.Task;
import org.taskm.models.Task.TaskStatus;
import org.taskm.models.Topic;
import org.taskm.services.Session;

public class AddTaskCommand implements Command {
    
    @Override
    public Result<Void> execute(List<Token> tokens){
        if (Session.getSession().getStorage() == null)
            return new Result<Void>(false, "Use a storage ", null);

        StringBuilder description = new StringBuilder("");
        for (int i = 3 ; i<tokens.size() ; i++)
            description.append(tokens.get(i).getVal());
        Topic currentTopic = Session.getSession().getTopic();

        LocalDate dateOfCreation = LocalDate.now();
        TaskStatus status = TaskStatus.todo;
        Task newTask = new Task(dateOfCreation, dateOfCreation, currentTopic, status , description.toString());
        currentTopic.addTask(List.of(newTask));

        return new Result<Void>(true, "Task created successfully with id "+newTask.getId(), null);
    }
    
    
}
