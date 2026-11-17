package com.example.employee_management.controller;

import com.example.employee_management.dto.EmployeeRequest;
import com.example.employee_management.entity.Department;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.repository.DepartmentRepository;
import com.example.employee_management.service.EmployeeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeWebController {

    private final EmployeeService employeeService;
    private final DepartmentRepository departmentRepository;

    public EmployeeWebController(
            EmployeeService employeeService,
            DepartmentRepository departmentRepository) {
        this.employeeService = employeeService;
        this.departmentRepository = departmentRepository;
    }

    // Display employee list
    @GetMapping("/list")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees/list";
    }

    // Display add employee form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employeeRequest", new EmployeeRequest());
        model.addAttribute("departments", departmentRepository.findAll());
        return "employees/add";
    }

    // Handle add employee form submission
    @PostMapping("/add")
    public String addEmployee(
            @ModelAttribute("employeeRequest") EmployeeRequest request,
            RedirectAttributes redirectAttributes) {

        employeeService.createEmployee(request);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Employee added successfully!"
        );

        return "redirect:/employees/list";
    }

    // Search employees by name or department
    @GetMapping("/search")
    public String searchEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long departmentId,
            Model model) {

        System.out.println("Search name = [" + name + "]");
        System.out.println("Department ID = [" + departmentId + "]");

        List<Employee> employees =
                employeeService.searchEmployees(name, departmentId);

        System.out.println("Result count = " + employees.size());

        model.addAttribute("employees", employees);
        model.addAttribute("name", name);
        model.addAttribute("departmentId", departmentId);
        model.addAttribute("departments", departmentRepository.findAll());

        return "employees/search";
    }
}