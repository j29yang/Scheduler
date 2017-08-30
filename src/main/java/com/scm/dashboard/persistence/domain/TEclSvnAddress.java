package com.scm.dashboard.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yizheng 2017-5-24
 *
 */
@Entity
@Table(name = "t_ecl_svn_address")
public class TEclSvnAddress {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column( name = "branch_id")
	private Long branchId;
	
	@Column( name = "svn_address")
	private String svnAddress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getSvnAddress() {
		return svnAddress;
	}

	public void setSvnAddress(String svnAddress) {
		this.svnAddress = svnAddress;
	}
	
}
