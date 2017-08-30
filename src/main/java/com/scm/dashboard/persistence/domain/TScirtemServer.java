package com.scm.dashboard.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_scirtem_server")
public class TScirtemServer implements Serializable {

	private static final long serialVersionUID = 3008216556949777432L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "ip", nullable = false)
	private String ip;

	@Column(name = "hostname", nullable = false)
	private String hostname;

	@Column(name = "create_time", nullable = false)
	private Date createTime;

	@Column(name = "live_time", nullable = false)
	private Date liveTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLiveTime() {
		return liveTime;
	}

	public void setLiveTime(Date liveTime) {
		this.liveTime = liveTime;
	}
}
