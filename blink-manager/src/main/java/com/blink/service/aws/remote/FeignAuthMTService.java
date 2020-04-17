package com.blink.service.aws.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blink.web.admin.web.dto.AuthCodeMTDto;

@FeignClient(name="auth-code-mt-api", url = "${spring.auth-code-mt.host}", fallbackFactory = FeignAuthMTServiceFallbackFactory.class)
public interface FeignAuthMTService {
	
	@RequestMapping(method = RequestMethod.GET, path = "/page/RcvBlinkAuthMTMsg.jsp")
	String sendAuthCodeMT(@SpringQueryMap AuthCodeMTDto authCodeMTDto);

}
