package de.osp.springbootworkshop.domain.service;

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
