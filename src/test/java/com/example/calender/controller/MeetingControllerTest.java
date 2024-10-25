package com.example.calender.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.calender.dto.MeetingInfo;
import com.example.calender.service.IMeeting;

@WebMvcTest(MeetingController.class)
public class MeetingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IMeeting meetingService;

	@Test
	public void testSaveMeeting() throws Exception {
		
		MeetingInfo meetingInfo = new MeetingInfo();
		
		meetingInfo.setDescription("Team Meeting");
		meetingInfo.setStartingDatetime(LocalDateTime.of(2024, 10, 25, 10, 0));
		meetingInfo.setEndDatetime(LocalDateTime.of(2024, 10, 25, 11, 0));
		meetingInfo.setEmployeeId(1L);

		mockMvc.perform(post("/api/v1/save/meeting").contentType(MediaType.APPLICATION_JSON).content(
				"{\"description\":\"Team Meeting\",\"startingDatetime\":\"2024-10-25T10:00:00\",\"endDatetime\":\"2024-10-25T11:00:00\",\"employeeId\":1}"))
				.andExpect(status().isCreated()).andExpect(content().string("Meeting Saved Successfully"));

		verify(meetingService, times(1)).saveMeeting(any(MeetingInfo.class));
	}

	@Test
	public void testGetAllFreeSlotsForTwoEmployees() throws Exception {
		
		Map<LocalDate, List<String>> freeSlots = new HashMap<>();
		
		freeSlots.put(LocalDate.of(2024, 10, 25),
				Arrays.asList("Free slot: 09:00 to 10:00", "Free slot: 11:00 to 12:00"));

		when(meetingService.getAllFreeSlotsForTwoEmployees(1L, 2L)).thenReturn(freeSlots);

		mockMvc.perform(get("/api/v1/get-available-slots/1/2")).andExpect(status().isOk())
				.andExpect(jsonPath("$.['2024-10-25'][0]").value("Free slot: 09:00 to 10:00"))
				.andExpect(jsonPath("$.['2024-10-25'][1]").value("Free slot: 11:00 to 12:00")).andDo(print());

		verify(meetingService, times(1)).getAllFreeSlotsForTwoEmployees(1L, 2L);
	}

	@Test
	public void testGetAllConflictEmployeeIds() throws Exception {
		
		MeetingInfo meetingInfo = new MeetingInfo();
		
		meetingInfo.setDescription("Project Discussion");
		meetingInfo.setStartingDatetime(LocalDateTime.of(2024, 10, 25, 14, 0));
		meetingInfo.setEndDatetime(LocalDateTime.of(2024, 10, 25, 15, 0));
		meetingInfo.setEmployeeId(3L);

		Set<Long> conflictEmployeeIds = new HashSet<>(Arrays.asList(1L, 2L));
		when(meetingService.getAllConflictEmployeeIds(any(MeetingInfo.class))).thenReturn(conflictEmployeeIds);

		mockMvc.perform(post("/api/v1/get-conflict-employees").contentType(MediaType.APPLICATION_JSON).content(
				"{\"description\":\"Project Discussion\",\"startingDatetime\":\"2024-10-25T14:00:00\",\"endDatetime\":\"2024-10-25T15:00:00\",\"employeeId\":3}"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$[0]").value(1))
				.andExpect(jsonPath("$[1]").value(2)).andDo(print());

		verify(meetingService, times(1)).getAllConflictEmployeeIds(any(MeetingInfo.class));
	}
}
