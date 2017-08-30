/**
 * @author xiaopan
 * @created Aug 24, 2016 11:40:04 AM
 */
package com.scm.dashboard.persistence.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 *  @author j29yang
 */
@Entity
@Table(name = "t_issues")
public class TIssues implements Serializable {
	
	private static final long serialVersionUID = 2438083934273498288L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(referencedColumnName = "id", name = "sc_id", insertable=false, updatable=false)
	private TScName scName;
	
	@Column(name = "level")
	private String level;
	
	@Column(name = "sc_id")
	private Integer scId;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "when_happen")
	private Date whenHappen;
	
	@Column(name = "status")
	private Integer status;

    @Column(name = "issue_type")
    private Integer issueType;

    @Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "close_time")
	private Date closeTime;
	
	@Column(name = "update_time")
	private Date updateTime;
	
    @Column(name = "duplicate")
    private Long duplicate;
    
    @Column(name = "reference")
    private Integer reference;
    
	@Column(name = "follow_up")
	private String followUp;
	
	@Column(name = "is_attach")
	private Integer isAttach;

	@Column(name = "root_cause")
	private String rootCause;
	
	@Column(name = "attach_time")
	private Date attachTime;

	@Column(name = "data_source")
	private String dataSource;

	public void setScName(TScName scName) {
		this.scName = scName;
	}

	public TScName getScName() {
		return scName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


    /**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the whenHappen
	 */
	public Date getWhenHappen() {
		return whenHappen;
	}

	/**
	 * @param whenHappen the whenHappen to set
	 */
	public void setWhenHappen(Date whenHappen) {
		this.whenHappen = whenHappen;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}


    public Integer getIssueType() {
        return issueType;
    }

    public void setIssueType(Integer issueType) {
        this.issueType = issueType;
    }

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the closeTime
	 */
	public Date getCloseTime() {
		return closeTime;
	}

	/**
	 * @param closeTime the closeTime to set
	 */
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Long getDuplicate() {
		return duplicate;
	}

	public void setDuplicate(Long duplicate) {
		this.duplicate = duplicate;
	}

	public Integer getReference() {
        return reference;
    }

    public void setReference(Integer ref) {
        this.reference = ref;
    }
    
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getFollowUp() {
		return followUp;
	}
	
	public void setFollowUp(String followUp) {
		this.followUp = followUp;
	}

	public Integer getIsAttach() {
		return isAttach;
	}

	public void setIsAttach(Integer isAttach) {
		this.isAttach = isAttach;
	}

	public String getRootCause() {
		return rootCause;
	}

	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}

	public Date getAttachTime() {
		return attachTime;
	}

	public void setAttachTime(Date attachTime) {
		this.attachTime = attachTime;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public Integer getScId() {
		return scId;
	}

	public void setScId(Integer scId) {
		this.scId = scId;
	}
}
