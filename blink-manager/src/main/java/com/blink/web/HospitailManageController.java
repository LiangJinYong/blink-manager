package com.blink.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.service.HospitalService;

@RestController
@RequestMapping("/hospital")
public class HospitailManageController {

	@Autowired
	private HospitalService hospitalService;

	@GetMapping("")
	public Map<String, Object> getHospitalListInfo(final Pageable pageable,
			@RequestParam(name = "hospitalName", defaultValue = "", required = false) String displayName) {

		Map<String, Object> result = hospitalService.getHospitalListInfo(displayName, pageable);
		
		return result;
	}

}
