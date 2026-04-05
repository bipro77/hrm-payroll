//package com.hrm.payroll.controller;
//
//import com.hrm.payroll.dto.LeaveDto;
//import com.hrm.payroll.service.LeaveService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/leaves")
//@RequiredArgsConstructor
//public class LeaveController {
//
//    private final LeaveService leaveService;
//
//    @PostMapping
//    public LeaveDto applyLeave(@RequestBody LeaveDto dto) {
//        return leaveService.applyLeave(dto);
//    }
//
//    @PutMapping("/{id}/approve")
//    public LeaveDto approveLeave(@PathVariable Long id) {
//        return leaveService.approveLeave(id);
//    }
//
//    @GetMapping("/{employeeId}")
//    public List<LeaveDto> getLeaves(@PathVariable Long employeeId) {
//        return leaveService.getLeavesByEmployee(employeeId);
//    }
//}