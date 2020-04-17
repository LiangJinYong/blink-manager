package com.blink.service.aws.remote;

import org.springframework.stereotype.Component;

import com.blink.web.admin.web.dto.AuthCodeMTDto;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FeignAuthMTServiceFallbackFactory  implements FallbackFactory<FeignAuthMTService>{
	
	@Override
	public FeignAuthMTService create(Throwable cause) {
		
		return new FeignAuthMTService() {
			
			@Override
			public String sendAuthCodeMT(AuthCodeMTDto authCodeMTDto) {
				log.error("Feign Fallback error msg. cause : {}, cause.getMessage() : {}", cause, cause.getMessage());
				return "FAIL";
			}
		};
	}

}
