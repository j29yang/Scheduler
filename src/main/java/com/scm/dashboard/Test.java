package com.scm.dashboard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.scm.dashboard.service.dto.BuildDTO;

public class Test {
	public static void main(String[] args) {
//		String jenkinsBuildUrl = "http://10.140.19.16:8080/job/DSP_Build_Promo_FBLRC1707/8/api/json";
//		
//		Netty4ClientHttpRequestFactory httpRequestFactory = new Netty4ClientHttpRequestFactory();
//		RestTemplate restTemplate = new RestTemplate();
//		restTemplate.setRequestFactory(httpRequestFactory);
//		BuildDTO buildDto = restTemplate.getForObject(jenkinsBuildUrl, BuildDTO.class);
//		System.out.println(buildDto.getUnifiedCommitId());
		
//		String upStream = "[4]";
//		upStream = upStream.substring(1, upStream.length()-1);
//		System.out.println(upStream);
		
//		Pattern pattern = Pattern.compile(".*ScirtemCommitId=([A-Za-z0-9]+).*");
		Pattern pattern = Pattern.compile(".*([\\w\\d]+_[\\w\\d_]?[\\d]+_[\\d]+_[\\d]+).*");
		String desc = "This is ScirtemCommitId=493ef1e1457ca023f84a707bec656f782524a77a Test";
		if (desc != null) {
		    Matcher matcher = pattern.matcher(desc);
		    if (matcher.find()) {
		        System.out.println(matcher.group(1)); 
		    }else {
		    	System.out.println("doesn't match");
		    }
		}
	}
}
