package com.scm.dashboard;

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
		
		String upStream = "[4]";
		upStream = upStream.substring(1, upStream.length()-1);
		System.out.println(upStream);
	}
}
