package org.taskm.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.taskm.cli.Result;
import org.taskm.cli.Token;
import org.taskm.models.Topic;
import org.taskm.services.Session;

public class AddTopicCommand implements Command {
    
    @Override
    public Result<Void> execute(List<Token> tokens){
        if (Session.getSession().getStorage() == null)
            return new Result<Void>(false, "Use a storage ", null);

        StringBuilder title = new StringBuilder("");
        for (int i = 3 ; i< tokens.size() ;i++)
            title.append(tokens.get(i).getVal());

        Topic currentTopic = Session.getSession().getTopic();
        LocalDate dateOfCreation = LocalDate.now();
        Topic newTopic = new Topic(currentTopic, dateOfCreation , dateOfCreation ,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), title.toString() , 0); 
        if (currentTopic != null )
            currentTopic.addTopic(List.of(newTopic));
        else 
            Session.getSession().getStorage().addTopic(List.of(newTopic));
        return new Result<Void>(true, "" , null);

    }
    
}
