package org.taskm.commands;

import java.util.List;

import org.taskm.cli.Result;
import org.taskm.cli.Token;
import org.taskm.services.Session;

public class PWTCommand implements Command{
    
    @Override
    public Result<Void> execute(List<Token> tokens){
        if (Session.getSession().getStorage() == null)
            return new Result<Void>(false, "use a storage !", null);
        if (Session.getSession().getTopic() == null){
            System.out.println("Root");
        }
        else 
            System.out.println(Session.getSession().getTopic().getTitle());
        return new Result<Void>(true, "", null);

    }
    
}
