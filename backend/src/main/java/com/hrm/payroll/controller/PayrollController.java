//package com.hrm.payroll.controller;
//
//import com.hrm.payroll.dto.PayrollDto;
//import com.hrm.payroll.service.PayrollService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/payroll")
//@RequiredArgsConstructor
//public class PayrollController {
//
//    private final PayrollService payrollService;
//
//    // Generate Payroll for Employee
//    @PostMapping("/generate/{employeeId}")
//    public PayrollDto generatePayroll(
//            @PathVariable Long employeeId,
//            @RequestParam String month) {
//
//        return payrollService.generatePayroll(employeeId, month);
//    }
//
//    // Get Payroll by Employee
//    @GetMapping("/{employeeId}")
//    public List<PayrollDto> getPayrollByEmployee(@PathVariable Long employeeId) {
//        return payrollService.getPayrollByEmployee(employeeId);
//    }
//}