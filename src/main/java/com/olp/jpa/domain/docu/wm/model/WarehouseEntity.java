package com.olp.jpa.domain.docu.wm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.search.annotations.Analyze;
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
import com.olp.jpa.common.CommonEnums;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.CommonEnums.WorkflowStatus;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.common.TenantBasedSearchFilterFactory;
import com.olp.jpa.domain.docu.org.model.Location;
import com.olp.jpa.domain.docu.org.model.LocationEntity;
import com.olp.jpa.domain.docu.org.model.OrganizationEntity;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.hibernate.search.annotations.ContainedIn;

/*
 * Trilla Inc Confidential
 * Class: WarehouseEntity.java
 * (C) Copyright Trilla Inc. 2017
 * 
 * @author raghosh
 */

@Entity
@Table(name="trl_warehouses"
      , uniqueConstraints=@UniqueConstraint(columnNames={"tenant_id", "warehouse_code"})
)
@NamedQueries({
   @NamedQuery(name="WarehouseEntity.findByWarehouseCode", query="SELECT t FROM WarehouseEntity t JOIN FETCH t.zones t1  WHERE t.warehouseCode = :code and t.tenantId = :tenant ")
})
@Cacheable(true)
@Indexed(index="SetupDataIndex")
@FullTextFilterDef(name="filter-warehouse-by-tenant", impl=TenantBasedSearchFilterFactory.class)
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class WarehouseEntity implements Serializable {

  private static final long serialVersionUID = -1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="warehouse_id", nullable=false)
  @DocumentId
  private Long id;

  @KeyAttribute
  @Column(name="tenant_id", nullable=false)
  @Fields({
    @Field(analyze=Analyze.NO, store = Store.YES)
  })
  private String tenantId;

  @Column(name="warehouse_code", nullable=false)
  @Fields({
      @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
  })
  private String warehouseCode;

  @Column(name="warehouse_name", nullable=false)
  @Fields({
      @Field(index=Index.YES, analyze=Analyze.NO, store=Store.YES)
  })
  private String warehouseName;

  @ManyToOne
  @JoinColumn(name="organization_ref")
  @ContainedIn
  private OrganizationEntity organizationRef;

  @Column(name="wm_control_flag")
  @Fields({
      @Field(index=Index.YES, analyze=Analyze.NO, store=Store.YES)
  })
  private Boolean wmControlEnabled;
  
  @OneToOne(cascade={CascadeType.ALL})
  @IndexedEmbedded(includeEmbeddedObjectId=true, depth=1)
  private LocationEntity whLocation;
  
  @Column(name="lifecycle_status", nullable=false)
  @Fields({
      @Field(index=Index.YES, analyze=Analyze.NO, store=Store.YES)
  })
  @Enumerated(EnumType.STRING)
  private LifeCycleStatus lifecycleStatus;
  
  @Embedded
  @IndexedEmbedded
  private RevisionControlBean revisionControl;

  @OneToMany(mappedBy="warehouseRef", cascade={CascadeType.ALL})
  @IndexedEmbedded(includeEmbeddedObjectId=true, depth=1)
  private List<WarehouseZoneEntity> zones = new ArrayList<>();
  
  @OneToMany(mappedBy="warehouseRef", cascade={CascadeType.ALL})
  @IndexedEmbedded(includeEmbeddedObjectId=true, depth=1)
  private Set<WarehouseWorkflowStatusEntity> workflowStatus;

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

  public OrganizationEntity getOrganizationRef() {
    return organizationRef;
  }

  public void setOrganizationRef(OrganizationEntity organizationRef) {
    this.organizationRef = organizationRef;
  }

  public Boolean getWmControlEnabled() {
    return wmControlEnabled;
  }

  public void setWmControlEnabled(Boolean wmControlEnabled) {
    this.wmControlEnabled = wmControlEnabled;
  }

  public RevisionControlBean getRevisionControl() {
    return revisionControl;
  }

  public void setRevisionControl(RevisionControlBean revisionControl) {
    this.revisionControl = revisionControl;
  }

  public List<WarehouseZoneEntity> getZones() {
    return zones;
  }

    public LocationEntity getWhLocation() {
        return whLocation;
    }

    public void setWhLocation(LocationEntity whLocation) {
        this.whLocation = whLocation;
    }

  public void setZones(List<WarehouseZoneEntity> zones) {
      if (zones != null)
          this.zones = zones;
      else
          this.zones.clear();
  }

    public LifeCycleStatus getLifecycleStatus() {
        return lifecycleStatus;
    }

    public void setLifecycleStatus(LifeCycleStatus lifecycleStatus) {
        this.lifecycleStatus = lifecycleStatus;
    }

    public Set<WarehouseWorkflowStatusEntity> getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(Set<WarehouseWorkflowStatusEntity> workflowStatus) {
        if (workflowStatus != null)
            this.workflowStatus = workflowStatus;
        else
            this.workflowStatus.clear();
    }

  public Warehouse convertTo(int mode) {
    
    Warehouse bean = new Warehouse();
    
    if (mode <= Constants.CONV_COMPLETE_DEFINITION)
      bean.setId(this.id);
  
    bean.setTenantId(this.tenantId);
    bean.setPartitionCode("defsffsfs");
    bean.setOrganizationCode(organizationRef.getOrgCode());
    bean.setWarehouseCode(warehouseCode);
    bean.setWarehouseName(warehouseName);
    bean.setWmControlEnabled(wmControlEnabled);
    if (this.whLocation != null) {
        Location loc = this.whLocation.convertTo(mode);
        bean.setWhLocation(loc);
    }
    if (this.workflowStatus != null && !this.workflowStatus.isEmpty()) {
        int count = 0;
        Iterator<WarehouseWorkflowStatusEntity> iter = this.workflowStatus.iterator();
        while (iter.hasNext()) {
            WarehouseWorkflowStatusEntity wfStat = iter.next();
            if (wfStat.getWorkflowStatus() == WorkflowStatus.NEW || wfStat.getWorkflowStatus() == WorkflowStatus.IN_PROGRESS) {
                count++;
            }
        }
        bean.setNumOpenWorkflow(count);
    }
    bean.setRevisionControl(revisionControl);
    
    if (zones != null && !zones.isEmpty()) {
        List<WarehouseZone> zoneList = new ArrayList<>();
        for(WarehouseZoneEntity zone : zones) {
            WarehouseZone whZone = zone.convertTo(mode);
            zoneList.add(whZone);
        }
        bean.setZones(zoneList);
    }
    
    if (mode <= Constants.CONV_WITH_REVISION_INFO)
      bean.setRevisionControl(this.revisionControl);
    
    return(bean);
  }

}
