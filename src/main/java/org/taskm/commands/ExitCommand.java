package org.taskm.commands;

import java.util.List;

import org.taskm.cli.Result;
import org.taskm.cli.Token;
import org.taskm.services.JSONStorage;
import org.taskm.services.Session;

public class ExitCommand implements Command {

   @Override
   public Result<Void> execute(List<Token> tokens){
        if (tokens.size() > 1)
            return new Result<Void>(false, "Invalid argument for :exit", null);

        if (Session.getSession().getFile() != null)JSONStorage.save(Session.getSession().getFile());
        System.exit(0);
        return new Result<Void>(true, "", null);
   }


}
