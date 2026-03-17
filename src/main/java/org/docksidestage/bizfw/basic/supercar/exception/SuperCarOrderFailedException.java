package org.docksidestage.bizfw.basic.supercar.exception;

public class SuperCarOrderFailedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SuperCarOrderFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
