package com.example.calender.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.calender.dto.MeetingInfo;
import com.example.calender.entity.Meeting;

public interface IMeeting {

	public void saveMeeting(MeetingInfo meetingInfo);

	public List<Meeting> getAllMeetingsByEmployeeId(Long employeeId);

	public Map<LocalDate, List<String>> getAllFreeSlotsForTwoEmployees(Long firstEmployeeId, Long secondEmployeeId);

	public Set<Long> getAllConflictEmployeeIds(MeetingInfo meetingInfo);

}
