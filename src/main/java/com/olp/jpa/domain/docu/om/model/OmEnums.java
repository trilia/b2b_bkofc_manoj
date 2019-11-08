package com.olp.jpa.domain.docu.om.model;

public class OmEnums {
	public static enum CompOrderStatus {
		RECEIVED,
		PROCESSING,
	};

	public static enum PaymentStatus {
		SUCCESS,
		FAIL
	}
	
	public static enum PaymentMethod {
		WALLET
	}
	
	public static enum CompOrderLineStatus {
		RECEIVED,
		PROCESSING,
		PARTIALLY_FULFILLED,
		FULFILLED
	}
}
