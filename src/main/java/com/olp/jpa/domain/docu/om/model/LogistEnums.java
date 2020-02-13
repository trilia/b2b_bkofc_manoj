package com.olp.jpa.domain.docu.om.model;

public class LogistEnums {

	public static enum ShipmentStatus {
		NEW, ASSIGNED, PICKED, IN_TRANSIT, DELIVERED, FAILED_DELIVER
	}

	public static enum TrackingStatus {
		NEW, ACTIVE, CANCELLED
	}
	
	public static enum ScheduleStatus {
		ON_TIME, DELAYED, UNPREDICATBLE
	}
	
	public static enum RoutingStatus {
		PENDING, COMPLETE, FAILED
	}
}
