
package com.example.employee_management.controller;

import com.example.employee_management.dto.EmployeeRequest;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.repository.DepartmentRepository;
import com.example.employee_management.service.EmployeeService;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // DISPLAY EMPLOYEE LIST
    @GetMapping("/list")
    public String listEmployees(Model model, Authentication authentication) {

        model.addAttribute("employees", employeeService.getAllEmployees());

        addUserInfoToModel(model, authentication);

        return "employees/list";
    }

    // DISPLAY ADD FORM
    @GetMapping("/add")
    public String showAddForm(Model model, Authentication authentication) {

        model.addAttribute("employeeRequest", new EmployeeRequest());
        model.addAttribute("departments", departmentRepository.findAll());

        addUserInfoToModel(model, authentication);

        return "employees/add";
    }

    // ADD EMPLOYEE
    @PostMapping("/add")
    public String addEmployee(
            @ModelAttribute("employeeRequest") EmployeeRequest request,
            RedirectAttributes redirectAttributes) {

        employeeService.createEmployee(request);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Employee added successfully!");

        return "redirect:/employees/list";
    }

    // DISPLAY EDIT FORM
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable String id,
            Model model,
            Authentication authentication) {

        Employee employee = employeeService.getEmployeeById(id);

        EmployeeRequest request = new EmployeeRequest(
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment().getId()
        );

        model.addAttribute("employeeRequest", request);
        model.addAttribute("employeeId", id);
        model.addAttribute("departments", departmentRepository.findAll());

        addUserInfoToModel(model, authentication);

        return "employees/edit";
    }

    // UPDATE EMPLOYEE
    @PostMapping("/edit/{id}")
    public String updateEmployee(
            @PathVariable String id,
            @ModelAttribute("employeeRequest") EmployeeRequest request,
            RedirectAttributes redirectAttributes) {

        employeeService.updateEmployee(id, request);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Employee updated successfully!");

        return "redirect:/employees/list";
    }

    // DELETE EMPLOYEE
    @PostMapping("/delete/{id}")
    public String deleteEmployee(
            @PathVariable String id,
            RedirectAttributes redirectAttributes) {

        employeeService.deleteEmployee(id);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Employee deleted successfully!");

        return "redirect:/employees/list";
    }

    // SEARCH EMPLOYEES
    @GetMapping("/search")
    public String searchEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long departmentId,
            Model model,
            Authentication authentication) {

        model.addAttribute(
                "employees",
                employeeService.searchEmployees(name, departmentId));

        model.addAttribute("name", name);
        model.addAttribute("departmentId", departmentId);
        model.addAttribute("departments", departmentRepository.findAll());

        addUserInfoToModel(model, authentication);

        return "employees/search";
    }

    // USER INFORMATION
    private void addUserInfoToModel(
            Model model,
            Authentication authentication) {

        String username = authentication.getName();

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("username", username);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("role", isAdmin ? "ADMIN" : "USER");
    }
}
