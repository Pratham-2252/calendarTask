package com.example.calender.dto;

import java.util.List;

import com.example.calender.entity.Meeting;

public class CalendarInfo {

	private List<Meeting> firstEmployeeCalender;

	private List<Meeting> secondEmployeeCalender;

	public List<Meeting> getFirstEmployeeCalender() {
		return firstEmployeeCalender;
	}

	public void setFirstEmployeeCalender(List<Meeting> firstEmployeeCalender) {
		this.firstEmployeeCalender = firstEmployeeCalender;
	}

	public List<Meeting> getSecondEmployeeCalender() {
		return secondEmployeeCalender;
	}

	public void setSecondEmployeeCalender(List<Meeting> secondEmployeeCalender) {
		this.secondEmployeeCalender = secondEmployeeCalender;
	}

}
