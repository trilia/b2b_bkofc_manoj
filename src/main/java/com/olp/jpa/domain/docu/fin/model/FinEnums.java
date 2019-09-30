package com.olp.jpa.domain.docu.fin.model;

/**
 *
 * @author Jayesh Jain
 */
public class FinEnums {
    
    public static enum CustInvoiceType {
    	STANDARD,
    	CREDITINV
    };
    
    public static enum CalPeriodStatus{
    	READY//check
    };
    
    public static enum PaymentTerm {
    	IMMEDIATE,
        COD,
        REFUND,
        CREDIT
    };
    
    public static enum PaymentMethod {
        COD,
        GATEWAY,
        WALLET
    };
    
    public static enum TaxDirectionType {
        IN,
        OUT,
        BOTH
    }
    
    public static enum TaxApplicationType {
        SUMMARY,
        LINE
    }
    
    public static enum TaxComputationType {
        FIXED,
        PERCENTAGE,
        FORMULA
    };
    
    public static enum TaxOffsetType {
        OFFSET,
        NONOFFSET
    };
    
    public static enum PayableInvLineType {
        STANDARD,
        CREDIT,
        TAX
    }
    
    public static enum AccountClass {
        ASSET,
        LIABILITY,
        EQUITY,
        RETAINED_EARNINGS,
        INCOME,
        EXPENSE
    }
    
    public static enum LedgerStatus {
    	NEW,
    	POSTED,
    	CANCELLED
    }
    
    public static enum LedgerLineType {
    	CREDIT,
    	DEBIT
    }

}
