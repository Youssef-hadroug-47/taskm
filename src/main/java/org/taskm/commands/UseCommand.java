package org.taskm.commands;

import java.util.List;

import org.taskm.cli.Result;
import org.taskm.cli.Token;
import org.taskm.services.Session;

public class UseCommand implements Command {


    @Override
    public Result<Void> execute(List<Token> tokens){
        
        if (Session.getSession().getAvailableStorages().size() == 1 && 
                tokens.get(1).getVal().equals("local"))
                return new Result<Void>(false, "Unfound local storage :" , null);       

        String subCommand = tokens.get(1).getVal();
        switch (subCommand){
            case "global":
                Session.getSession().setStorage(Session.getSession().getAvailableStorages().getFirst());
                break;
            case "local":
                Session.getSession().setStorage(Session.getSession().getAvailableStorages().getLast());
        }
        return new Result<Void>(true, "Successfully selected storage of name "+Session.getSession().getStorage().getName() , null);
    }


}
