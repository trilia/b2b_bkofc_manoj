package com.olp.jpa.domain.docu.comm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.olp.annotations.KeyAttribute;
import com.olp.annotations.MultiTenant;
import com.olp.fwk.common.Constants;
import com.olp.jpa.common.RevisionControlBean;

@Entity
@Table(name = "trl_lov_definition", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id", "lov_code" }))
@NamedQueries({
		@NamedQuery(name = "LovDefinitionEntity.findbyLovCode", query = "SELECT t FROM LovDefinitionEntity t WHERE t.lovCode = :lovCode and t.tenantId = :tenant ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class LovDefinitionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "lov_definition_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;

	@Column(name = "lov_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String lovCode;

	@Column(name = "lov_name", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String lovName;

	@Enumerated(EnumType.STRING)
	@Column(name = "lov_type", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private LovType lovType;

	@Column(name = "enabled_flag", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private boolean isEnabled;

	@OneToMany(mappedBy = "lovDefRef", cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private List<LovValuesEntity> lovValues = new ArrayList<>();

	@Embedded
	@IndexedEmbedded
	private RevisionControlBean revisionControl;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the lovCode
	 */
	public String getLovCode() {
		return lovCode;
	}

	/**
	 * @param lovCode
	 *            the lovCode to set
	 */
	public void setLovCode(String lovCode) {
		this.lovCode = lovCode;
	}

	/**
	 * @return the lovName
	 */
	public String getLovName() {
		return lovName;
	}

	/**
	 * @param lovName
	 *            the lovName to set
	 */
	public void setLovName(String lovName) {
		this.lovName = lovName;
	}

	/**
	 * @return the lovType
	 */
	public LovType getLovType() {
		return lovType;
	}

	/**
	 * @param lovType
	 *            the lovType to set
	 */
	public void setLovType(LovType lovType) {
		this.lovType = lovType;
	}

	/**
	 * @return the isEnabled
	 */
	public boolean isEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled
	 *            the isEnabled to set
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * @return the lovValues
	 */
	public List<LovValuesEntity> getLovValues() {
		return lovValues;
	}

	/**
	 * @param lovValues
	 *            the lovValues to set
	 */
	public void setLovValues(List<LovValuesEntity> lovValues) {
		this.lovValues = lovValues;
	}

	/**
	 * @return the revisionControl
	 */
	public RevisionControlBean getRevisionControl() {
		return revisionControl;
	}

	/**
	 * @param revisionControl
	 *            the revisionControl to set
	 */
	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
	}

	public LovDefinition convertTo(int mode) {
		LovDefinition bean = new LovDefinition();
		if (mode <= Constants.CONV_COMPLETE_DEFINITION)
			bean.setId(this.id);
		bean.setTenantId(this.tenantId);
		bean.setEnabled(isEnabled);
		bean.setLovCode(lovCode);
		bean.setLovType(lovType);
		bean.setLovName(lovName);
		
		List<LovValues> lovValuesList = new ArrayList<LovValues>();
		for(LovValuesEntity lovValEntity : lovValues){
			LovValues lovValue = lovValEntity.convertTo(0);
			lovValuesList.add(lovValue);
		}
		bean.setLovValues(lovValuesList);
		bean.setRevisionControl(revisionControl);

		return bean;
	}
}
