package com.scm.dashboard.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import com.scm.dashboard.service.ScNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.dashboard.persistence.domain.TScName;
import com.scm.dashboard.persistence.repo.TScNameRepository;

@Service
public class ScNameServiceImpl implements ScNameService {
    
    @Autowired
    private TScNameRepository myTScNameRepository;
    
    @Override
    public List<TScName> getAll(){
        return (List<TScName>) myTScNameRepository.findAll();
    }
    
    @Override
    public List<TScName> getListByProjectId(Long projectId){
        return myTScNameRepository.findListByProjectId(projectId);
    }

    @Override
    public TScName findByName(String errorSc) {
        return myTScNameRepository.findByName(errorSc);
    }

    @Override
    @Transactional
    public TScName addScName(String errorSc, Long projectId) {
        TScName tsc = new TScName();
        tsc.setName(errorSc);
        tsc.setProjectId(projectId);
        return myTScNameRepository.save(tsc);
    }

    @Override
    @Transactional
    public TScName updateScName(String oldName, String newName) {
        TScName tsc = new TScName();
        tsc = myTScNameRepository.findByName(oldName);
        tsc.setName(newName);
        return myTScNameRepository.save(tsc);
    }

    @Override
    @Transactional
    public void deleteScName(String errorSc) {
        TScName tsc = new TScName();
        tsc = myTScNameRepository.findByName(errorSc);
        myTScNameRepository.delete(tsc);
    }

    @Override
    public boolean isExistComponent(String errorSc) {
        TScName tsc = new TScName();
        tsc = myTScNameRepository.findByName(errorSc);
        if (null == tsc){
            return false;
        } else {
            return true;
        }
    }

}
