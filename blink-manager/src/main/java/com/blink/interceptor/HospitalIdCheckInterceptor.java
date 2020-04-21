package com.blink.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.blink.domain.admin.AdminRepository;
import com.blink.util.JwtUtil;

public class HospitalIdCheckInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println("#######" + jwtUtil + "@@@" + adminRepository);
		final Map<String, Object> pathVariables = (Map<String, Object>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

		if (pathVariables != null && pathVariables.containsKey("hospitalId")) {
			Long hospitalId = Long.parseLong((String) pathVariables.get("hospitalId"));
			String authorizationHeader = request.getHeader("Authorization");
			String token = authorizationHeader.substring(7);
			String userName = jwtUtil.extractUsername(token);
		}
		return true;
	}
}
