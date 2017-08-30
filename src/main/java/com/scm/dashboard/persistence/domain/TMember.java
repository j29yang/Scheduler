package com.scm.dashboard.persistence.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_member")
public class TMember implements Serializable {

	private static final long serialVersionUID = -5777306782165180655L;

	@EmbeddedId
	private ProjectWithUser projectWithUser;

	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private TRole role;

	public ProjectWithUser getProjectWithUser() {
		return projectWithUser;
	}

	public void setProjectWithUser(ProjectWithUser projectWithUser) {
		this.projectWithUser = projectWithUser;
	}

	public TRole getRole() {
		return role;
	}

	public void setRole(TRole role) {
		this.role = role;
	}

	public TMember() {}

	@Embeddable
	public static class ProjectWithUser implements Serializable {

		private static final long serialVersionUID = -6179815802679927062L;

		@ManyToOne
		@JoinColumn(name = "project_id", nullable = false)
		private TProject project;

		@ManyToOne
		@JoinColumn(name = "nsn_account", nullable = false)
		private TUser user;

		public ProjectWithUser() {
		}

		public ProjectWithUser(TProject project, TUser user) {
			this.project = project;
			this.user = user;
		}

		public TProject getProject() {
			return project;
		}

		public TUser getUser() {
			return user;
		}

		@Override
		public int hashCode() {
			String relation = String.format("%s_%s", project.getId(), getUser().getNsnAccount());
			return relation.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || obj.getClass() != getClass()) {
				return false;
			}

			final ProjectWithUser that = (ProjectWithUser) obj;

			if (this.project == null || this.user == null) {
				return false;
			} else if (that.getProject().getId() == that.getProject().getId() && 
					that.getUser().getNsnAccount().equals(that.getUser().getNsnAccount())) {
				return true;
			} else {
				return false;
			}
		}
	}
}
