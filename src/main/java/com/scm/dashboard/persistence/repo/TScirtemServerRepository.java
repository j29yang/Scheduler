package com.scm.dashboard.persistence.repo;

import com.scm.dashboard.persistence.domain.TScirtemServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by amqu on 2017/6/20.
 */
public interface TScirtemServerRepository extends JpaRepository<TScirtemServer, Long> {

    @Query("SELECT TSS FROM  TScirtemServer TSS WHERE TSS.liveTime>:baseTime")
    List<TScirtemServer> findAllByLiveTime(@Param("baseTime") Date baseTime);
}
