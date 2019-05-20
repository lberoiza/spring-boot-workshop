package de.osp.springbootworkshop.application.rest;

public class PetAlreadyExistsException extends PetShopApiException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public PetAlreadyExistsException(String message) {
        super(message);
    }
	
	
}
