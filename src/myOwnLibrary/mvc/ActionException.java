package myOwnLibrary.mvc;

public class ActionException extends Exception{
    public ActionException(String err) {
        super(err);
    }

    public ActionException(Exception e) {
        super(e);
    }
    
    public ActionException(String errInfo,Exception e) {
        super(errInfo,e);
    }
}
