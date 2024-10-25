package com.example.calender.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.calender.dto.CalendarInfo;
import com.example.calender.dto.MeetingInfo;
import com.example.calender.entity.Meeting;
import com.example.calender.repository.MeetingRepository;

@Service("meetingService")
public class MeetingService implements IMeeting {

	@Autowired
	private MeetingRepository meetingRepository;

	@Override
	public void saveMeeting(MeetingInfo meetingInfo) {

		Meeting meeting = new Meeting();

		meeting.setMeetingId(UUID.randomUUID());
		meeting.setDescription(meetingInfo.getDescription());
		meeting.setStartingDatetime(meetingInfo.getStartingDatetime());
		meeting.setEndDatetime(meetingInfo.getEndDatetime());
		meeting.setEmployeeId(meetingInfo.getEmployeeId());

		meetingRepository.save(meeting);
	}

	@Override
	public List<Meeting> getAllMeetingsByEmployeeId(Long employeeId) {

		return meetingRepository.findAllByEmployeeId(employeeId);
	}

	@Override
	public Map<LocalDate, List<String>> getAllFreeSlotsForTwoEmployees(Long firstEmployeeId, Long secondEmployeeId) {

		CalendarInfo calendarInfo = convertToCalendarInfo(firstEmployeeId, secondEmployeeId);

		Set<LocalDate> meetingDates = getAllMeetingsDates(calendarInfo);

		return getAllAvailableSlots(calendarInfo, meetingDates);
	}

	private CalendarInfo convertToCalendarInfo(Long firstEmployeeId, Long secondEmployeeId) {

		CalendarInfo calendarInfo = new CalendarInfo();

		List<Meeting> firstEmployeeMeetings = getAllMeetingsByEmployeeId(firstEmployeeId);

		List<Meeting> seconfEmployeeMeetings = getAllMeetingsByEmployeeId(secondEmployeeId);

		calendarInfo.setFirstEmployeeCalender(firstEmployeeMeetings);

		calendarInfo.setSecondEmployeeCalender(seconfEmployeeMeetings);

		return calendarInfo;
	}

	private Map<LocalDate, List<String>> getAllAvailableSlots(CalendarInfo calendarInfo, Set<LocalDate> meetingDates) {

		Map<LocalDate, List<String>> availableSlots = new HashMap<>();

		LocalTime workDayStart = LocalTime.of(9, 0);

		LocalTime workDayEnd = LocalTime.of(18, 0);

		meetingDates.forEach(date -> {

			LocalDateTime startOfWorkday = LocalDateTime.of(date, workDayStart);

			LocalDateTime endOfWorkday = LocalDateTime.of(date, workDayEnd);

			List<Meeting> firstEmployeeMeetingList = calendarInfo.getFirstEmployeeCalender().stream()
					.filter(e -> e.getStartingDatetime().toLocalDate().equals(date)).toList();

			List<Meeting> secondEmployeeMeetingList = calendarInfo.getSecondEmployeeCalender().stream()
					.filter(e -> e.getStartingDatetime().toLocalDate().equals(date)).toList();

			List<Meeting> allMeetings = new ArrayList<>();

			allMeetings.addAll(firstEmployeeMeetingList);

			allMeetings.addAll(secondEmployeeMeetingList);

			allMeetings.sort(Comparator.comparing(Meeting::getStartingDatetime));

			List<String> commonAvailableSlots = new ArrayList<>();

			LocalDateTime availableTime = startOfWorkday;

			for (Meeting meeting : allMeetings) {

				if (availableTime.isBefore(meeting.getStartingDatetime())) {

					commonAvailableSlots.add(formatSlot(availableTime, meeting.getStartingDatetime()));
				}

				if (availableTime.isBefore(meeting.getEndDatetime())) {

					availableTime = meeting.getEndDatetime();
				}
			}

			if (availableTime.isBefore(endOfWorkday)) {

				commonAvailableSlots.add(formatSlot(availableTime, endOfWorkday));
			}

			availableSlots.put(date, commonAvailableSlots);

		});

		return availableSlots;
	}

	private Set<LocalDate> getAllMeetingsDates(CalendarInfo calendarInfo) {

		Set<LocalDate> meetingDates = new HashSet<>();

		calendarInfo.getFirstEmployeeCalender().stream()
				.forEach(e -> meetingDates.add(e.getStartingDatetime().toLocalDate()));

		calendarInfo.getSecondEmployeeCalender().stream()
				.forEach(e -> meetingDates.add(e.getStartingDatetime().toLocalDate()));

		return meetingDates;
	}

	private static String formatSlot(LocalDateTime start, LocalDateTime end) {

		return String.format("Free slot: %s to %s", start.toLocalTime(), end.toLocalTime());
	}

	@Override
	public Set<Long> getAllConflictEmployeeIds(MeetingInfo meetingInfo) {

		Set<Long> conflictingEmployeeIds = new HashSet<>();

		List<Meeting> meetings = meetingRepository.findAll();

		meetings.forEach(e -> {

			if (meetingInfo.getStartingDatetime().isBefore(e.getEndDatetime())
					&& meetingInfo.getEndDatetime().isAfter(e.getStartingDatetime())) {

				conflictingEmployeeIds.add(e.getEmployeeId());
			}
		});

		return conflictingEmployeeIds;
	}

}
