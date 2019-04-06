package de.osp.springbootworkshop.application.rest;

/**
 * @author Denny
 */
public class PetNotExistsException extends PetShopApiException {
    public PetNotExistsException(String message) {
        super(message);
    }

    public PetNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PetNotExistsException(Throwable cause) {
        super(cause);
    }
}
