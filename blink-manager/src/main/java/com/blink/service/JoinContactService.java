package com.blink.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.joinContact.JoinContact;
import com.blink.domain.joinContact.JoinContactRepository;
import com.blink.enumeration.SearchPeriod;
import com.blink.enumeration.VisitAim;
import com.blink.util.CommonUtils;
import com.blink.web.admin.web.dto.joinConcact.JoinContactResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class JoinContactService {

	private final JoinContactRepository joinContactRepository;

	public JoinContactResponseDto getJoinContacts(String searchText, SearchPeriod period, Pageable pageable) {
		
		LocalDateTime time = CommonUtils.getSearchPeriod(period);
		
		Page<JoinContact> joinContactList = joinContactRepository.findBySearchTextAndPeriod(searchText, time, pageable);
		
		Long unanwseredCount = joinContactRepository.findCountByAnswerYn(false);
		Long answeredCount = joinContactRepository.findCountByAnswerYn(true);
		String mostUsedProgram = joinContactRepository.findMostUserProgram();
		
		JoinContactResponseDto responseDto = new JoinContactResponseDto(joinContactList.getTotalElements(), unanwseredCount, answeredCount, mostUsedProgram, joinContactList);
		return responseDto;
	}

	public void saveAnswer(Long joinContactId, String answerContent, boolean visitReserveYn, LocalDateTime visitDate,
			VisitAim visitAim) {
		
		JoinContact joinContact = joinContactRepository.findById(joinContactId).orElseThrow(() -> new IllegalArgumentException("No such join contact"));
		
		joinContact.answer(answerContent, visitReserveYn, visitDate, visitAim);
	}

	public void saveQuestion(String clinicName, String email, String name, String tel, String inquiry,
			String usedProgram) {
		JoinContact joinContact = JoinContact.builder() //
		.clinicName(clinicName) //
		.email(email) //
		.name(name) //
		.tel(tel) //
		.inquiry(inquiry) //
		.usedProgram(usedProgram) //
		.build();
		
		joinContactRepository.save(joinContact);
	}
}
