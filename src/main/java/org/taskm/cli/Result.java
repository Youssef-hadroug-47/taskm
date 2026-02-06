package org.taskm.cli;

import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

public class Result<T> {

    private boolean success ;
    private String message ;
    private T val ;
    private final AttributedStyle failureStyle = AttributedStyle.DEFAULT.foreground(AttributedStyle.RED).bold();
    private final AttributedStyle successStyle = AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN).bold();

    public Result(boolean success , String message , T val ){
        this.message = message ;
        this.success = success ;
        this.val = val;
    }

    public boolean getSuccess(){return this.success;}
    public String getMessage(){return this.message;}
    public T getValue(){return this.val;}

    public void setSuccess(boolean success ){this.success = success;}
    public void setMessage(String message ){this.message = message;}
    public void setValue(T val){this.val = val;}
    public void printMessage(){
        if (message == null || message.isEmpty())
            return ;
        AttributedStringBuilder builder = new AttributedStringBuilder(); 
        System.out.println(
                builder.append("   " + message)
                .style( success ? successStyle : failureStyle)
                .toAnsi()
                );
    }

}
