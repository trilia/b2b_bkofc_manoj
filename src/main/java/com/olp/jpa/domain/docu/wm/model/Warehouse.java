package com.olp.jpa.domain.docu.wm.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.org.model.Location;
import com.olp.jpa.domain.docu.org.model.LocationEntity;
import com.olp.jpa.domain.docu.org.model.OrganizationEntity;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/*
 * Trilla Inc Confidential
 * Class: Warehouse.java
 * (C) Copyright Trilla Inc. 2017
 * 
 * @author raghosh
 */

@XmlRootElement(name="warehouse", namespace="http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={ "id", "partitionCode", "warehouseCode", "warehouseName", "organizationRef", "wmControlEnabled", "whLocation", "numOpenWorkflow", "revisionControl", "zones" })
public class Warehouse {

  private static final long serialVersionUID = -1L;
  
  @XmlElement(name="warehouse-id")
  private Long id;

  @XmlTransient
  private String tenantId;
  
  @XmlAttribute(name="partition-code")
  private String partitionCode;

  @XmlAttribute(name="warehouse-code")
  private String warehouseCode;

  @XmlElement(name="warehouse-name")
  private String warehouseName;

  @XmlElement(name="organization-code")
  private String organizationCode;

  @XmlElement(name="wm-control-flag")
  private Boolean wmControlEnabled;
  
  @XmlElement(name="warehouse-location")
  private Location whLocation;
  
  @XmlElement(name="num-open-workflow")
  private int numOpenWorkflow = 0;

  private RevisionControlBean revisionControl;

  @XmlElement(name="zone")
  private List<WarehouseZone> zones;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getPartitionCode() {
        return partitionCode;
    }

    public void setPartitionCode(String partitionCode) {
        this.partitionCode = partitionCode;
    }

  public String getWarehouseCode() {
    return warehouseCode;
  }

  public void setWarehouseCode(String warehouseCode) {
    this.warehouseCode = warehouseCode;
  }

  public String getWarehouseName() {
    return warehouseName;
  }

  public void setWarehouseName(String warehouseName) {
    this.warehouseName = warehouseName;
  }

  public String getOrganizationCode() {
    return organizationCode;
  }

  public void setOrganizationCode(String organizationCode) {
    this.organizationCode = organizationCode;
  }

  public Boolean getWmControlEnabled() {
    return wmControlEnabled;
  }

  public void setWmControlEnabled(Boolean wmControlEnabled) {
    this.wmControlEnabled = wmControlEnabled;
  }

    public Location getWhLocation() {
        return whLocation;
    }

    public void setWhLocation(Location whLocation) {
        this.whLocation = whLocation;
    }

    public int getNumOpenWorkflow() {
        return numOpenWorkflow;
    }

    public void setNumOpenWorkflow(int numOpenWorkflow) {
        this.numOpenWorkflow = numOpenWorkflow;
    }

  public RevisionControlBean getRevisionControl() {
    return revisionControl;
  }

  public void setRevisionControl(RevisionControlBean revisionControl) {
    this.revisionControl = revisionControl;
  }

    public List<WarehouseZone> getZones() {
        return zones;
    }

    public void setZones(List<WarehouseZone> zones) {
        this.zones = zones;
    }
  
  

  public WarehouseEntity convertTo(int mode) {
    
    WarehouseEntity bean = new WarehouseEntity();
    
    bean.setId(this.id);
    bean.setTenantId(this.tenantId);
    
    OrganizationEntity organizationRef = new OrganizationEntity();
    organizationRef.setOrgCode(organizationCode);
    bean.setOrganizationRef(organizationRef);
    
    bean.setWarehouseCode(warehouseCode);
    bean.setWarehouseName(warehouseName);
    bean.setWmControlEnabled(wmControlEnabled);
    if (this.whLocation != null) {
        LocationEntity le = this.whLocation.convertTo(mode);
        bean.setWhLocation(le);
    }
        
    bean.setRevisionControl(revisionControl);
    
    List<WarehouseZoneEntity> zoneList = new ArrayList<>();
    for(WarehouseZone zone : zones) {
      WarehouseZoneEntity warehouseZoneEntity = zone.convertTo(mode);
      zoneList.add(warehouseZoneEntity);
    }
    bean.setZones(zoneList);
    
    return(bean);
  }

}
