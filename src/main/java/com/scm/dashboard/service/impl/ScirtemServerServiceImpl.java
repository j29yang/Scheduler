package com.scm.dashboard.service.impl;

import com.scm.dashboard.constant.CommonConstant;
import com.scm.dashboard.persistence.domain.TScirtemServer;
import com.scm.dashboard.persistence.repo.TScirtemServerRepository;
import com.scm.dashboard.utils.MyDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

/**
 * Created by amqu on 2017/6/20.
 */
@Service
public class ScirtemServerServiceImpl implements ScirtemServerService {

    @Autowired
    private TScirtemServerRepository tScirtemServerRepository;

    @Override
    public TScirtemServer addScirtemServer() {
        String ip="";
        String hostname="";
        try{
            InetAddress addr=InetAddress.getLocalHost();
            ip = addr.getHostAddress().toString();//获得本机IP　　
            hostname = addr.getHostName().toString();//获得本机名称
            //String canonical=addr.getCanonicalHostName();
        }catch(Exception e){
            ip = CommonConstant.UNKNOWN;
            hostname = CommonConstant.UNKNOWN;
        }

        TScirtemServer tScirtemServer = new TScirtemServer();
        tScirtemServer.setHostname(hostname);
        tScirtemServer.setIp(ip);
        tScirtemServer.setCreateTime(new Date());
        tScirtemServer.setLiveTime(new Date());
        return tScirtemServerRepository.save(tScirtemServer);
    }

    @Override
    public void updateLiveTime(long id) {
        if(id <= 0){
            return;
        }
        TScirtemServer tScirtemServer = tScirtemServerRepository.findOne(id);
        tScirtemServer.setLiveTime(new Date());
        tScirtemServerRepository.save(tScirtemServer);

    }

    @Override
    public List<TScirtemServer> getLiveServer() {
        Long baseTime = System.currentTimeMillis()-5*60l*1000;
        return tScirtemServerRepository.findAllByLiveTime(new Date(baseTime));
    }



}
