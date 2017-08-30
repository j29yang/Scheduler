package com.scm.dashboard.persistence.repo;

import com.scm.dashboard.persistence.domain.TJenkinsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by amqu on 2017/5/23.
 */
public interface TJenkinsLogRepository extends JpaRepository<TJenkinsLog, Long> {


    @Query(value="SELECT * FROM t_jenkins_log  WHERE status=0 limit 1", nativeQuery = true)
    TJenkinsLog getOneNewLog();

    @Query(value="SELECT * FROM t_jenkins_log  WHERE id =:id", nativeQuery = true)
    TJenkinsLog getLog(@Param("id")Long id);


    @Transactional
    @Modifying
    @Query("UPDATE TJenkinsLog tl SET tl.status = :status WHERE tl.id=:id AND tl.status = :lastStatus")
    int updateStatus(@Param("id")Long id,@Param("status")int status,@Param("lastStatus")int lastStatus);

    @Transactional
    @Modifying
    @Query("UPDATE TJenkinsLog tl SET tl.status = :status, tl.logSize = :logSize WHERE tl.id =:id AND tl.status = :lastStatus")
    int updateStatus(@Param("id")Long id,@Param("status")int status,@Param("lastStatus")int lastStatus,@Param("logSize") Integer logSize );



    @Transactional
    @Modifying
    @Query("UPDATE TJenkinsLog tl SET tl.status = 2,tl.issueId =:issueId,tl.logSize = :logSize WHERE tl.id=:id")
    int updateSuccess(@Param("id")Long id,@Param("issueId")long issueId,@Param("logSize") Integer logSize);

}
