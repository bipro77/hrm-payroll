//package com.hrm.payroll.controller;
//
//import com.hrm.payroll.dto.AttendanceDto;
//import com.hrm.payroll.service.AttendanceService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/attendance")
//@RequiredArgsConstructor
//public class AttendanceController {
//
//    private final AttendanceService attendanceService;
//
//    @PostMapping
//    public AttendanceDto markAttendance(@RequestBody AttendanceDto dto) {
//        return attendanceService.markAttendance(dto);
//    }
//
//    @GetMapping("/{employeeId}")
//    public List<AttendanceDto> getAttendance(@PathVariable Long employeeId) {
//        return attendanceService.getAttendanceByEmployee(employeeId);
//    }
//}