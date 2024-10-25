package com.example.calender.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.calender.entity.Meeting;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

	public List<Meeting> findAllByEmployeeId(Long employeeId);

	public List<Meeting> findAll();
}
