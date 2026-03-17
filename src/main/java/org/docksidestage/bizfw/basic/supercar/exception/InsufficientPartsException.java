package org.docksidestage.bizfw.basic.supercar.exception;

/**
 * @author akinari.tsuji
 */
public class InsufficientPartsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InsufficientPartsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
