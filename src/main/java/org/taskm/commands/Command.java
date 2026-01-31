package org.taskm.commands;

import java.util.List;

import org.taskm.cli.Result;
import org.taskm.cli.Token;

public interface Command {
    Result<Void> execute(List<Token> tokens);
}
