package org.taskm.cli;

public class CommandResult {

    private boolean success ;
    private String message ;

    public CommandResult(boolean success , String message ){
        this.message = message ;
        this.success = success ;
    }

    public boolean getSuccess(){return this.success;}
    public String getMessage(){return this.message;}

    public void setSuccess(boolean success ){this.success = success;}
    public void setMessage(String message ){this.message = message;}

}
