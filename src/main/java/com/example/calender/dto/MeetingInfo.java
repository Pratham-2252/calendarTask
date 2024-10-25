package com.example.calender.dto;

import java.time.LocalDateTime;

public class MeetingInfo {

	private String description;

	private LocalDateTime startingDatetime;

	private LocalDateTime endDatetime;

	private Long employeeId;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getStartingDatetime() {
		return startingDatetime;
	}

	public void setStartingDatetime(LocalDateTime startingDatetime) {
		this.startingDatetime = startingDatetime;
	}

	public LocalDateTime getEndDatetime() {
		return endDatetime;
	}

	public void setEndDatetime(LocalDateTime endDatetime) {
		this.endDatetime = endDatetime;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

}
