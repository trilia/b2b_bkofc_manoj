package com.olp.jpa.domain.docu.comm.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
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
@Table(name = "trl_lov_values", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id", "lov_code" }))
@NamedQueries({
		@NamedQuery(name = "LovValuesEntity.findByLovValue", query = "SELECT t FROM LovValuesEntity t WHERE t.lovCode = :lovCode and t:value = value and t.tenantId = :tenant ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class LovValuesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "lov_values_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;

	@Column(name = "lov_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String lovCode;

	@ManyToOne(optional = true)
	@JoinColumn(name = "lov_def_ref")
	@ContainedIn
	private LovDefinitionEntity lovDefRef;

	@Column(name = "lov_value", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String value;

	@Column(name = "enabled_flag", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private boolean isEnabled;

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
	 * @return the lovDefRef
	 */
	public LovDefinitionEntity getLovDefRef() {
		return lovDefRef;
	}

	/**
	 * @param lovDefRef
	 *            the lovDefRef to set
	 */
	public void setLovDefRef(LovDefinitionEntity lovDefRef) {
		this.lovDefRef = lovDefRef;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
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

	public LovValues convertTo(int mode) {
		LovValues bean = new LovValues();
		if (mode <= Constants.CONV_COMPLETE_DEFINITION)
			bean.setId(this.id);
		bean.setTenantId(this.tenantId);
		bean.setIsEnabled(isEnabled);
		bean.setLovCode(lovCode);
		bean.setValue(value);
		bean.setLovDefRef(lovDefRef.getLovCode());
		bean.setRevisionControl(revisionControl);
		return bean;
	}

}
