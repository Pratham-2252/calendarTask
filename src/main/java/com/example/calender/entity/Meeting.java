package com.example.calender.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MEETING")
public class Meeting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "MEETING_ID")
	private UUID meetingId;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "STARTING_DATETIME")
	private LocalDateTime startingDatetime;

	@Column(name = "END_DATETIME")
	private LocalDateTime endDatetime;

	@Column(name = "EMPLOYEE_ID")
	private Long employeeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(UUID meetingId) {
		this.meetingId = meetingId;
	}

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
