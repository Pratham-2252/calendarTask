package com.example.calender.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.calender.dto.MeetingInfo;
import com.example.calender.service.IMeeting;

@RestController
@RequestMapping("/api/v1")
public class MeetingController {

	@Autowired
	private IMeeting meetingService;

	@PostMapping(value = "/save/meeting", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveMeeting(@RequestBody MeetingInfo meetingInfo) {

		meetingService.saveMeeting(meetingInfo);

		return ResponseEntity.status(HttpStatus.CREATED).body("Meeting Saved Successfully");
	}

	@GetMapping(value = "/get-available-slots/{firstEmployeeId}/{secondEmployeeId}")
	public ResponseEntity<?> getAllFreeSlotsForTwoEmployees(@PathVariable Long firstEmployeeId,
			@PathVariable Long secondEmployeeId) {

		Map<LocalDate, List<String>> allFreeSlotsForTwoEmployees = meetingService
				.getAllFreeSlotsForTwoEmployees(firstEmployeeId, secondEmployeeId);

		return ResponseEntity.ok(allFreeSlotsForTwoEmployees);
	}

	@PostMapping(value = "/get-conflict-employees", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllConflictEmployeeIds(@RequestBody MeetingInfo meetingInfo) {

		Set<Long> conflictEmployeeIds = meetingService.getAllConflictEmployeeIds(meetingInfo);

		return ResponseEntity.status(HttpStatus.CREATED).body(conflictEmployeeIds);
	}
}
