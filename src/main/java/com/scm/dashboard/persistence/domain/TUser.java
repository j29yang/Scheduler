package com.scm.dashboard.persistence.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_user")
public class TUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "nsn_account")
	private String nsnAccount;

	@Column(name = "nsn_id", length = 8)
	private String nsnId;

	@Column(name = "mail", nullable = true)
	private String mail;

	@Column(name = "display_name", nullable = true)
	private String displayName;

	public TUser() {
	}

	public TUser(String nsnAccount, String nsnId, String mail, String displayName) {
		this.nsnAccount = nsnAccount;
		this.nsnId = nsnId;
		this.mail = mail;
		this.displayName = displayName;
	}

	public String getNsnAccount() {
		return nsnAccount;
	}

	public String getNsnId() {
		return nsnId;
	}

	public String getMail() {
		return mail;
	}

	public String getDisplayName() {
		return displayName;
	}
}
