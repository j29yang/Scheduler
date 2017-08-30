package com.scm.dashboard.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.scm.dashboard.persistence.domain.TMember;

@Transactional(readOnly = true)
public interface TMemberRepository extends JpaRepository<TMember, String> {

	@Query(value = "select m from TMember m where m.projectWithUser.user.nsnAccount = ?1")
	List<TMember> findByUser(String nsnAccount);

	@Query(value = "select CONCAT(m.role.name, '_', m.projectWithUser.project.id) "
			+ "from TMember m where m.projectWithUser.user.nsnAccount = ?1")
	List<String> getRoles(String nsnAccount);
	
	@Query(value = "select CONCAT(?1, '|',m.roleId, '|', m.name, '|',m.description,'|', 0) "
			+ "from TRole m where m.roleId = 2")
	String getDefaultRole(String nsnAccount);
}
