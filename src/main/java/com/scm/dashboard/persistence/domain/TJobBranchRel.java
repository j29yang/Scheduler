package com.scm.dashboard.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by amqu on 2017/7/25.
 */
@Entity
@Table(name = "t_job_branch_rel")
public class TJobBranchRel implements Serializable{

    private static final long serialVersionUID = 432626752803462318L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(referencedColumnName = "id",name="job_id")
    private TJob job;

    @Column(name="branch_id")
    private Long branchId;

    @Column(name="create_time")
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TJob getJob() {
        return job;
    }

    public void setJob(TJob job) {
        this.job = job;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
