package org.taskm.commandsInfo;

import java.util.HashSet;
import java.util.Set;

import org.taskm.commands.Command;

public class CommandSpecs {
    private String name ;
    private Set<String> aliases;
    private String subCommand;
    private boolean hasArgument;
    private Set<OptionSpecs> options;
    private Set<OptionSpecs> requiredOptions;
    private String description ;
    private Command command;
    private String format;

    public CommandSpecs(String name , Set<String> aliases , Set<OptionSpecs> options , String subCommand, boolean hasArgument ,
            String description , String format ,Command command ){
        this.aliases = aliases;
        this.command = command ;
        this.subCommand = subCommand;
        this.hasArgument = hasArgument;
        this.description = description;
        this.format = format;
        this.name = name ;
        this.options = options;
        this.requiredOptions = new HashSet<>();
        for (OptionSpecs o : options)
            if (o.isRequired())
                this.requiredOptions.add(o);
    }
    
    public String getName (){return name;}
    public String getSubCommand(){return subCommand;}
    public Set<OptionSpecs> getOptions(){return options;}
    public String getDescription(){return description;}
    public String getFormat(){return format;}
    public Command getCommand(){return command;}
    public Set<String> getAliases(){return aliases;}
    public Set<OptionSpecs> getRequiredOptions(){return requiredOptions;}
    public boolean hasSubCommand(){return subCommand != null && !subCommand.isEmpty() ;}
    public boolean hasOptions(){return options != null && options.size() != 0 ;}
    public boolean hasRequiredOptions(){return options != null && options.stream().anyMatch( option -> option.isRequired() );}
    public boolean hasAliases(){return aliases != null && !aliases.isEmpty();}
    public boolean hasArgument(){return hasArgument;}
}
