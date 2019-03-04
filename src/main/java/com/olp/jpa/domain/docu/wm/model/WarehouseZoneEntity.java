package com.olp.jpa.domain.docu.wm.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.FullTextFilterDef;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.olp.annotations.KeyAttribute;
import com.olp.annotations.MultiTenant;
import com.olp.fwk.common.Constants;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.common.TenantBasedSearchFilterFactory;
import com.olp.jpa.domain.docu.inv.model.InvEnums.SubInventory;
import com.olp.jpa.domain.docu.wm.model.WmEnums.ZoneType;
import java.io.Serializable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/*
 * Trilla Inc Confidential
 * Class: WarehouseZoneEntity.java
 * (C) Copyright Trilla Inc. 2017
 * 
 * @author raghosh
 */

@Entity
@Table(name="trl_warehouse_zones"
      , uniqueConstraints=@UniqueConstraint(columnNames={"tenant_id", "warehouse_code", "zone_code"})
)
@NamedQueries({
   @NamedQuery(name="WarehouseZone.findByZoneCode", query="SELECT t from WarehouseZoneEntity t JOIN FETCH t.locators WHERE t.warehouseCode = :whCode AND t.zoneCode = :zoneCode and t.tenantId = :tenant "),
    @NamedQuery(name="WarehouseZone.findByWhCode", query="SELECT t from WarehouseZoneEntity t JOIN FETCH t.locators WHERE t.warehouseCode = :whCode and t.tenantId = :tenant ")
})
@Cacheable(true)
@Indexed(index="SetupDataIndex")
@FullTextFilterDef(name="filter-wh-zone-by-tenant", impl=TenantBasedSearchFilterFactory.class)
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class WarehouseZoneEntity implements Serializable {

  private static final long serialVersionUID = -1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="zone_id", nullable=false)
  @DocumentId
  private Long id;

  @KeyAttribute
  @Column(name="tenant_id", nullable=false)
  @Fields({
        @Field(analyze=Analyze.NO, store = Store.YES)
  })
  private String tenantId;

  @KeyAttribute
  @Column(name="zone_code", nullable=false)
  @Fields({
      @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
  })
  private String zoneCode;
  
  @Column(name="zone_name", nullable=false)
  @Fields({
      @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
  })
  private String zoneName;
  
  @Column(name="zone_type", nullable=false)
  @Fields({
      @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
  })
  @javax.persistence.Enumerated(javax.persistence.EnumType.STRING)
  private ZoneType  zoneType;
  
  @Column(name="zone_sub_type", nullable=false)
  @Fields({
      @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
  })
  private String zoneSubType;
  
  @Column(name="sub_inventory", nullable=false)
  @Fields({
      @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
  })
  @Enumerated(EnumType.STRING)
  private SubInventory subInventory;
  
  @Column(name="locator_enabled", nullable=false)
  @Fields({
      @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
  })
  private Boolean islocatorEnabled;
  
  @Column(name="allow_dynamic_locator", nullable=false)
  @Fields({
      @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
  })
  private Boolean allowDynamicLocator;
  
  @ManyToOne
  @JoinColumn(name="warehouse_ref")
  @ContainedIn
  private WarehouseEntity warehouseRef;
  
  @Column(name="warehouse_code", nullable=false)
  @Fields({
      @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
  })
  private String warehouseCode;
  
    @Column(name="maint_storage_history", nullable=true)
    @Fields({
        @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
    })
    private boolean maintStorageHistory = false; // whether to maintain pallet storage history or purge it. Default is false i.e. no history

  @Embedded
  @IndexedEmbedded
  private RevisionControlBean revisionControl;
  
  @OneToMany(mappedBy="zoneRef", cascade={CascadeType.ALL})
  @IndexedEmbedded(includeEmbeddedObjectId=true, depth=1)
  private List<WarehouseLocatorEntity> locators = new ArrayList<>();
  
  
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

  /**
   * @return the warehouseRef
   */
  public WarehouseEntity getWarehouseRef() {
    return warehouseRef;
  }

  /**
   * @param warehouseRef the warehouseRef to set
   */
  public void setWarehouseRef(WarehouseEntity warehouseRef) {
    this.warehouseRef = warehouseRef;
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

    public boolean isMaintStorageHistory() {
        return maintStorageHistory;
    }

    public void setMaintStorageHistory(boolean maintStorageHistory) {
        this.maintStorageHistory = maintStorageHistory;
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

  /**
   * @return the locators
   */
  public List<WarehouseLocatorEntity> getLocators() {
    return locators;
  }

  /**
   * @param locators the locators to set
   */
  public void setLocators(List<WarehouseLocatorEntity> locators) {
      if (locators != null)
        this.locators = locators;
      else
          this.locators.clear();
  }

  public WarehouseZone convertTo(int mode) {
    
    WarehouseZone bean = new WarehouseZone();
    
    if (mode <= Constants.CONV_COMPLETE_DEFINITION)
        bean.setId(this.id);
  
    bean.setTenantId(this.tenantId);
    bean.setPartitionCode("fefefefwe");
    
    bean.setAllowDynamicLocator(this.allowDynamicLocator);
    bean.setIslocatorEnabled(this.islocatorEnabled);
    bean.setMaintStorageHistory(this.maintStorageHistory);
    bean.setSubInventory(this.subInventory);
    if (this.warehouseRef != null)
        bean.setWarehouseCode(this.warehouseRef.getWarehouseCode());
    else
        bean.setWarehouseCode(this.warehouseCode);
    
    bean.setZoneCode(this.zoneCode);
    bean.setZoneName(this.zoneName);
    bean.setZoneSubType(this.zoneSubType);
    bean.setZoneType(this.zoneType);
    bean.setRevisionControl(this.revisionControl);
    
    if (this.locators != null && !this.locators.isEmpty()) {
        List<WarehouseLocator> locatorList = new ArrayList<>();
        for(WarehouseLocatorEntity wle : this.locators) {
            WarehouseLocator locator = wle.convertTo(mode);
            locatorList.add(locator);
        }
        bean.setLocators(locatorList);
    }
    
    if (mode <= Constants.CONV_WITH_REVISION_INFO)
        bean.setRevisionControl(this.revisionControl);
  
    return(bean);
  }

}
