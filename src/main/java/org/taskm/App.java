package org.taskm;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.taskm.cli.Parser;
import org.taskm.cli.ParserResult;
import org.taskm.cli.Result;
import org.taskm.commands.*;
import org.taskm.commandsInfo.CommandSpecs;
import org.taskm.commandsInfo.CommandsList;
import org.taskm.commandsInfo.OptionSpecs;
import org.taskm.services.Session;


public class App {
         
    public static void main(String[] blabla) {
       
        CommandsList.addCommand(Set.of(
            new CommandSpecs(
                "use",
                Set.of(),
                "local",
                "Use a local storage",null,new UseCommand() 
                ),
            new CommandSpecs(
                "use",
                Set.of(),
                "global",
                "Use a global storage",
                null, new UseCommand()),
            new CommandSpecs(
                "init", 
                Set.of(
                    new OptionSpecs("--name", true, true , new HashSet<>())
                    ), 
                null,
                "initialize a local storage",null, new InitCommand()
                ), 
            new CommandSpecs(
                "exit",
                Set.of(),
                null,
                "exit the taskm",null, new ExitCommand()
                )
            ));  

        try (Terminal terminal = TerminalBuilder.builder().build()){
            
            

            Completer completer = new StringsCompleter(CommandsList.getCommands().
                stream().
                map( (command) -> {
                    return command.getName();
                }).
                toList()
            );
            LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).completer(completer).build();

            Session.getSession();
            while(true) {
                String prompt = new AttributedString(
                        "taskm >> ",
                        AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE).bold()
                        ).toAnsi();
                String expression = lineReader.readLine(prompt);

                if (expression.isEmpty())
                    continue;
                ArrayList<String> args = new ArrayList<>(Arrays.asList(
                            expression.split("[\\s]")
                            )) ;
                        
                Parser parser = new Parser(args);
                Result<ParserResult> result = parser.parse();
                if (!result.getSuccess()){ 
                    result.showFailure();
                    continue;
                }
                Command command = result.getValue().getCommandSpecs().getCommand();
                Result<Void> res =  command.execute(result.getValue().getTokens());
                if (res.getMessage() != null || !res.getMessage().isEmpty())System.out.println(res.getMessage());
                
            }
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }
        catch (UserInterruptException interruptedException){
            ExitCommand exitCommand = new ExitCommand();
            exitCommand.execute(List.of());
        }
    }
}
