package com.olp.jpa.domain.docu.llty.model;

public class LoyaltyEnums {

	public static enum LifecycleStatus {
		INACTIVE,
		ACTIVE,
		SUSPENDED,
		TERMINATED
		};

	public static enum TierMoveType {
		 AUTOMATIC,
	     MANUAL
    };
    
    public static enum TierReviewFrequency {
        IMMEDIATE,
        DAILY,
        WEEKLY,
        FORTNIGHTLY,
        MONTHLY,
        QUARTERLY,
        HALF_YEARLY,
        YEARLY
    };
   
   public static enum TierValidity {
	   CAL_YEAR,
	   FIN_YEAR,
	   MONTH,
	   CAL_QUARTER,
	   FIN_QUARTER,
	   CAL_HALF_YEAR,
	   FIN_HALF_YEAR,
	   YEAR 
	 };
	 
	public static enum SpendConversionAlgo {
	    SIMPLE_FACTOR,
	    FORMULA,
	    EXTERNAL_PROCESS
	};
	
	public static enum Scope {
		GLOBAL,
		LOCAL
	};
	
	public static enum ParticipationStatus {
		ACTIVE,
	    INACTIVE
	};
	
	public static enum TxnType {
        PURCHASE_CREDIT,
        RETURN_DEBIT,
        MISC_CREDIT,
        MISC_DEBIT,
        TIER_UPGRADE,
        TIER_DOWNGRADE,
	};


}
