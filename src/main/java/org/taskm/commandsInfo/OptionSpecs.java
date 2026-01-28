package org.taskm.commandsInfo;

import java.util.Set;

public class OptionSpecs{
    private String name ;
    private boolean required ;
    private boolean hasValue ;
    private Set<String> requiredArguments ;
    
    public OptionSpecs(String name , boolean required , boolean hasValue , Set<String> requiredArguments){
        this.name = name;
        this.required = required;
        this.hasValue = hasValue;
        this.requiredArguments = requiredArguments;
    }

    public boolean isRequired(){return required;}
    public boolean hasValue(){return hasValue;}
    public String getName(){return name;}
    public Set<String> getArguments(){return requiredArguments;}
}
