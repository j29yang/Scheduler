package com.scm.dashboard.schedulers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.scm.dashboard.persistence.domain.TBranch;
import com.scm.dashboard.persistence.domain.TEclSvnAddress;
import com.scm.dashboard.persistence.domain.TEnvControlList;
import com.scm.dashboard.persistence.domain.TProject;
import com.scm.dashboard.service.BranchService;
import com.scm.dashboard.service.ECLSvnAddressService;
import com.scm.dashboard.service.EnvControlListService;
import com.scm.dashboard.service.ProjectService;

@Component
public class LockedComponentFetch {
	
	
	@Autowired
	private BranchService myBranchService;
	
	@Autowired
	private ProjectService myProjectService;
	
	@Autowired
	private ECLSvnAddressService myECLSvnAddressService;
	
	@Autowired
	private EnvControlListService myEnvControlListService;
	
	private static final Logger logger =  LoggerFactory.getLogger(FetchBuildsScheduler.class);
	
	@Scheduled(fixedDelay = 5*60*1000)
	public void syncEnviroControlListToDB(){
		List<TProject> projectList = new ArrayList<TProject>();
		//获取project 信息
		try{
			projectList = myProjectService.findAll();
		}catch(Exception e){
			e.printStackTrace();
		}
		//判断是否为 lockedcomponent project
		if(!projectList.isEmpty()){
			for(TProject tp:projectList){
				List<TBranch> branchList = new ArrayList<TBranch>();
				if("1".equals(tp.getIsComponentLocked())){
					//查找相对应的branch
					branchList = myBranchService.findByProjectId(tp.getId());
				}
				for(TBranch tb:branchList){
					TEclSvnAddress tESA = myECLSvnAddressService.findbyBranchId(tb.getId());
					//解析ECL
					if(tESA != null){						
						try{
							//找到相对应的svn地址
							URL url = new URL(tESA.getSvnAddress());
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							@SuppressWarnings("restriction")
							String encode = new sun.misc.BASE64Encoder().encode(("ca_hzcbtsscm" + ":" + "hzcbtsscm123").getBytes());
							String author = encode;
							conn.setRequestProperty("Authorization", "Basic " + author);
							
							DataInputStream input = new DataInputStream(conn.getInputStream());
							DataOutputStream output = new DataOutputStream(new FileOutputStream("ECL"));
							byte[] buffer = new byte[1024 * 8];
							int count = 0;
							while ((count = input.read(buffer)) > 0) {
								output.write(buffer, 0, count);
							}
							output.close();
							input.close();
							Properties ecl = new Properties();
							ecl.load(new FileInputStream("ECL"));
							Iterator it=ecl.entrySet().iterator();
							while(it.hasNext()){
								TEnvControlList tECL = new TEnvControlList();
								Map.Entry entry=(Map.Entry)it.next();
								String key = (String)entry.getKey();
								String value = (String)entry.getValue();
								tECL.setBranchId(tb.getId());
								tECL.setProjectId(tp.getId());
								tECL.setComponentName(key.replace("ECL_", ""));
								tECL.setVersion(value);
								TEnvControlList tECLTmp = myEnvControlListService.findByBranchIdAndName(tECL.getBranchId(), tECL.getComponentName());
								//更新数据库
								if(tECLTmp != null){
									tECL.setId(tECLTmp.getId());
									myEnvControlListService.update(tECL);
								}else{
									myEnvControlListService.update(tECL);
								}
							}
						}catch(Exception e){
							e.printStackTrace();
						}
						
					}else{
						logger.warn("branch:" + tp.getName() + "not config ECL svn address");
					}
				}
			}
			
		}
		
	}
}
