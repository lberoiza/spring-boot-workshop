package de.osp.springbootworkshop.application.rest;

public abstract class PetShopApiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public PetShopApiException(String message) {
        super(message);
    }
	
}
