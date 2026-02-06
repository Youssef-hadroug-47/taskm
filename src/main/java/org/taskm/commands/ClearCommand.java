package org.taskm.commands;

import java.util.List;

import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp.Capability;
import org.taskm.cli.Result;
import org.taskm.cli.Token;
import org.taskm.services.Session;

public class ClearCommand implements Command {

    @Override
    public Result<Void> execute(List<Token> tokens) {
        Terminal terminal = Session.getSession().getTerminal();
        terminal.puts(Capability.clear_screen);
        terminal.flush();
        return new Result<Void>(true, "" , null);
    }

}
