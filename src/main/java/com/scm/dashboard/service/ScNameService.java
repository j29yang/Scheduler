package com.scm.dashboard.service;

import com.scm.dashboard.persistence.domain.TScName;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by amqu on 2017/4/19.
 */
public interface ScNameService {
    List<TScName> getAll();
    
    List<TScName> getListByProjectId(Long projectId);

    TScName findByName(String errorSc);

    TScName addScName(String errorSc, Long projectId);

    TScName updateScName(String oldName, String newName);
    
    void deleteScName(String errorSc);
    
    boolean isExistComponent(String errorSc);
}
