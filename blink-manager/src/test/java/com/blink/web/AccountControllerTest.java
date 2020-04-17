package com.blink.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.blink.domain.admin.AdminRepository;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.web.admin.web.dto.UserSignupRequestDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private HospitalRepository hospitalRepository;

	@Test
	public void signup() throws Exception {
		
		String filePath = "/Users/liangjinyong/Downloads/faq.png";
		FileSystemResource resource = new FileSystemResource(new File(filePath));
		
		UserSignupRequestDto requestDto = UserSignupRequestDto.builder()
				.username("mytest3")
				.password(passwordEncoder.encode("123456"))
				.hospitalName("our hospital")
				.hospitalTel("01011112222")
				.postcode("12345")
				.address("Seoul")
				.addressDetail("HwaGok")
				.employeeName("Tom")
				.employeePosition("Boss")
				.employeeTel("01044445555")
				.employeeEmail("test@naver.com")
				.agreenSendYn(1)
				.programInUse("Excel")
				.build();
		
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		param.add("userId", "userId");
		param.add("password", passwordEncoder.encode("123456"));
		param.add("hospitalName", "our hospital");
		param.add("hospitalTel", "01011112223");
		param.add("postcode", "11111");
		param.add("address", "Busan");
		param.add("addressDetail", "HwaGok");
		param.add("employeeName", "Tom");
		param.add("employeePosition", "Boss");
		param.add("employeeTel", "01044445555");
		param.add("employeeEmail", "test@naver.com");
		param.add("agreenSendYn", 1);
		param.add("programInUse", "PPT");
		param.add("licensePhoto", resource);
        
		// when
		String url = "http://localhost:" + port + "/blinkmanager/api/v1/user/signup";
		Long result = restTemplate.postForObject(url, requestDto, Long.class);
		
		// then
//		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result).isGreaterThan(0L);
	}
	
	public static Resource getUserFileResource() throws IOException {
	      //todo replace tempFile with a real file
	      Path tempFile = Files.createTempFile("upload-test-file", ".txt");
	      Files.write(tempFile, "some test content...\nline1\nline2".getBytes());
	      System.out.println("uploading: " + tempFile);
	      File file = tempFile.toFile();
	      //to upload in-memory bytes use ByteArrayResource instead
	      return new FileSystemResource(file);
	  }
}
