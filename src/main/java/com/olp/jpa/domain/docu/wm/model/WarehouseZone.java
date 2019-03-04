package com.olp.jpa.domain.docu.wm.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.inv.model.InvEnums.SubInventory;
import com.olp.jpa.domain.docu.wm.model.WmEnums.ZoneType;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/*
 * My Inc Confidential
 * Class: WarehouseZone.java
 * (C) Copyright My Inc. 2017
 */

@XmlRootElement(name="warehouse-zone", namespace="http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={ "id", "partitionCode", "zoneCode", "zoneName", "zoneType", "zoneSubType", "subInventory", "islocatorEnabled", "allowDynamicLocator", "warehouseRef", "warehouseCode", "revisionControl", "locators" })
public class WarehouseZone implements Serializable {

  @XmlElement(name="zone-id")
  private Long id;
  
  @XmlTransient
  private String tenantId;
  
  @XmlAttribute(name="partition-code")
  private String partitionCode;
  
  @XmlAttribute(name="zone-code")
  private String zoneCode;
  
  @XmlElement(name="zone-name")
  private String zoneName;
  
  @XmlElement(name="zone-type")
  private ZoneType zoneType;
  
  @XmlElement(name="zone-sub-type")
  private String zoneSubType;
  
  @XmlElement(name="sub-inventory")
  private SubInventory subInventory;
  
  @XmlElement(name="locator-enabled")
  private Boolean islocatorEnabled;
  
  @XmlElement(name="allow-dynamic-locator")
  private Boolean allowDynamicLocator;
  
  @XmlElement(name="warehouse")
  private String warehouseCode;
  
  @XmlElement(name="maint-storage-history")
  private boolean maintStorageHistory;
  
  private RevisionControlBean revisionControl;
  
  @XmlElement(name="locator")
  private List<WarehouseLocator> locators = new ArrayList<>();

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
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
  
  /**
   * @return the zoneCode
   */
  public String getZoneCode() {
    return zoneCode;
  }

  /**
   * @param zoneCode the zoneCode to set
   */
  public void setZoneCode(String zoneCode) {
    this.zoneCode = zoneCode;
  }

  /**
   * @return the zoneName
   */
  public String getZoneName() {
    return zoneName;
  }

  /**
   * @param zoneName the zoneName to set
   */
  public void setZoneName(String zoneName) {
    this.zoneName = zoneName;
  }

    public ZoneType getZoneType() {
        return zoneType;
    }

    public void setZoneType(ZoneType zoneType) {
        this.zoneType = zoneType;
    }

  /**
   * @return the zoneSubType
   */
  public String getZoneSubType() {
    return zoneSubType;
  }

  /**
   * @param zoneSubType the zoneSubType to set
   */
  public void setZoneSubType(String zoneSubType) {
    this.zoneSubType = zoneSubType;
  }

    public SubInventory getSubInventory() {
        return subInventory;
    }

    public void setSubInventory(SubInventory subInventory) {
        this.subInventory = subInventory;
    }

  /**
   * @return the islocatorEnabled
   */
  public Boolean getIslocatorEnabled() {
    return islocatorEnabled;
  }

  /**
   * @param islocatorEnabled the islocatorEnabled to set
   */
  public void setIslocatorEnabled(Boolean islocatorEnabled) {
    this.islocatorEnabled = islocatorEnabled;
  }

  /**
   * @return the allowDynamicLocator
   */
  public Boolean getAllowDynamicLocator() {
    return allowDynamicLocator;
  }

  /**
   * @param allowDynamicLocator the allowDynamicLocator to set
   */
  public void setAllowDynamicLocator(Boolean allowDynamicLocator) {
    this.allowDynamicLocator = allowDynamicLocator;
  }

    public boolean isMaintStorageHistory() {
        return maintStorageHistory;
    }

    public void setMaintStorageHistory(boolean maintStorageHistory) {
        this.maintStorageHistory = maintStorageHistory;
    }

  /**
   * @return the warehouseCode
   */
  public String getWarehouseCode() {
    return warehouseCode;
  }

  /**
   * @param warehouseCode the warehouseCode to set
   */
  public void setWarehouseCode(String warehouseCode) {
    this.warehouseCode = warehouseCode;
  }

  /**
   * @return the revisionControl
   */
  public RevisionControlBean getRevisionControl() {
    return revisionControl;
  }

  /**
   * @param revisionControl the revisionControl to set
   */
  public void setRevisionControl(RevisionControlBean revisionControl) {
    this.revisionControl = revisionControl;
  }

    public List<WarehouseLocator> getLocators() {
        return locators;
    }

    public void setLocators(List<WarehouseLocator> locators) {
        if (locators != null)
            this.locators = locators;
        else
            this.locators.clear();
    }

    public WarehouseZoneEntity convertTo(int mode) {
    
        WarehouseZoneEntity bean = new WarehouseZoneEntity();

        bean.setId(this.id);
        bean.setTenantId(this.tenantId);
        
        bean.setAllowDynamicLocator(this.allowDynamicLocator);
        bean.setIslocatorEnabled(this.islocatorEnabled);
        
        if (this.locators != null && !this.locators.isEmpty()) {
            List<WarehouseLocatorEntity> locatorList = new ArrayList<>();
            for(WarehouseLocator locator : this.locators) {
                WarehouseLocatorEntity le = locator.convertTo(mode);
                locatorList.add(le);
            }
            bean.setLocators(locatorList);
        }

        bean.setMaintStorageHistory(this.maintStorageHistory);
        bean.setSubInventory(this.subInventory);
        bean.setWarehouseCode(this.warehouseCode);
        bean.setZoneCode(this.zoneCode);
        bean.setZoneName(this.zoneName);
        bean.setZoneSubType(this.zoneSubType);
        bean.setZoneType(this.zoneType);
       
        bean.setRevisionControl(this.revisionControl);
        
        return(bean);
        
    }

}
