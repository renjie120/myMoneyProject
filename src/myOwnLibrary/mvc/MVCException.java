package myOwnLibrary.mvc;

public class MVCException extends Exception {

    public MVCException(String err) {
        super(err);
    }

    public MVCException(Exception e) {
        super(e);
    }
    
    public MVCException(String errInfo,Exception e) {
        super(errInfo,e);
    }
}
