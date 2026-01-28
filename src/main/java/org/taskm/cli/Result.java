package org.taskm.cli;

public class Result<T> {

    private boolean success ;
    private String message ;
    private T val ;

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
}
