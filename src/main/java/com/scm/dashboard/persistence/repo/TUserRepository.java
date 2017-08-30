package com.scm.dashboard.persistence.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.scm.dashboard.persistence.domain.TUser;

public interface TUserRepository extends CrudRepository<TUser, String> {

	@Modifying
	@Query("update TUser u set u.mail = :mail, u.displayName = :displayName where u.nsnAccount = :nsnAccount")
	int setMailAndDisplayNameFor(@Param("nsnAccount") String nsnAccount,
			@Param("mail") String mail, @Param("displayName") String displayName);
}
