package org.taskm.commandsInfo;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CommandsList {
    private static Set<CommandSpecs> commands ;

    public static void addCommand(CommandSpecs command ){
        if (commands == null){
            commands = new HashSet<>(List.of(command));
            return;
        }
        commands.add(command);    
    }
    public static void addCommand(List<CommandSpecs> com){
        if (commands == null){
            commands = new HashSet<>(com);
            return;
        }
        commands.addAll(commands);

    }
    public static Set<CommandSpecs> getCommands(){return commands;}
}
