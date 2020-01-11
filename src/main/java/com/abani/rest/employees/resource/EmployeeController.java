package com.abani.rest.employees.resource;

import com.abani.rest.employees.model.DummyEmployee;
import com.abani.rest.employees.model.Employee;
import com.abani.rest.employees.model.MailUtil;
import com.abani.rest.employees.service.EmployeeService;
import com.abani.rest.employees.service.MailUtilService;
import com.abani.rest.employees.utility.MailSendUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;

@RestController
@RequestMapping(value = "/api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MailUtilService mailUtilService;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    public Executor executor;

    @GetMapping
    public String getApiInfo(){
        StringBuilder sb = new StringBuilder("<h2>Employees REST API</h2>");
        sb.append("<h4><u>Setup mail</u>(POST request)</h4>");
        sb.append("<p><a href='#'>/employees/api/set-mail</a></p>");
        sb.append("NOTE: Gmail only");
        sb.append("<p>Header: Content-Type=application/json</p>");
        sb.append("<p>Body Example: {sender : test@gmail.com, password : 1234, receiver : test@gmail.com}</p>");

        sb.append("<h4><u>Get all employees</u>(GET request)</h4>");
        sb.append("<a href='#'>/employees/api/all</a>");

        sb.append("<h4><u>Add Employee</u>(POST request)</h4>");
        sb.append("<a href='#'>/employees/api/add</a>");
        sb.append("<p>Header: Content-Type=application/json</p>");
        sb.append("NOTE: Phone number length must be 10");
        sb.append("<p>Body Example: {email : test@email.com, name : Test, designation : Test Engineer II, phone : 1234567890}</p>");

        sb.append("<h4><u>Find Employee by Id</u>(GET request)</h4>");
        sb.append("<a href='#'>/employees/api/find/{id}</a>");

        sb.append("<h4><u>Update Employee details</u>(PUT request)</h4>");
        sb.append("<a href='#'>/employees/api/edit/{id}</a>");
        sb.append("<p>Header: Content-Type=application/json</p>");
        sb.append("NOTE: Phone number length must be 10");
        sb.append("<p>Body Example: {email : test@email.com, name : Test, designation : Test Engineer II, phone : 1234567890}</p>");

        sb.append("<h4><u>Delete Employee by Id</u>(DELETE request)</h4>");
        sb.append("<a href='#'>/employees/api/delete/{id}</a>");
        return sb.toString();
    }

    @GetMapping(value = "/all")
    public List<Employee> getAll(){
        sendMail("Get Employee", "Retrieved All Employee Details");
        return employeeService.findAll();
    }

    @GetMapping(value = "/find/{id}")
    public Employee getEmployeeById(@PathVariable(value = "id") Long employeeId){
        Employee emp = null;
        try {
            emp = employeeService.findById(employeeId).get();
        }
        catch (NoSuchElementException e) {
            return new Employee();
        }

        sendMail("Get Employee", "Emloyee Id " + emp.getId());
        return emp;
    }

    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody DummyEmployee employee){
        Employee resultEmp = employee.parseEmployee();
        resultEmp = employeeService.save(resultEmp);
        sendMail("Added Employee", "Emloyee Id " + resultEmp.getId());
        return ResponseEntity.ok("Employee Details Added");
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity updateEmployeeById(@PathVariable("id")Long id, @RequestBody DummyEmployee employee){

        Employee resultEmp = employeeService.findById(id).orElse(null);
        if(resultEmp == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee details not found");
        }
        resultEmp = employee.parseEmployee();
        resultEmp.setId(id);
        employeeService.edit(resultEmp);

        sendMail("Update Employee", "Emloyee Id " + resultEmp.getId());
        return ResponseEntity.ok("Employee Details Updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id")Long id){

        Employee emp = employeeService.findById(id).orElse(null);
        if(emp == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee details not found");
        }
        employeeService.delete(emp);

        sendMail("Delete Employee", "Emloyee Id " + emp.getId());
        return ResponseEntity.ok("Employee Deleted");
    }

    @PostMapping(value = "/set-mail")
    public MailUtil saveMailUtil(@RequestBody MailUtil mailUtil){
        mailUtilService.save(mailUtil);
        return mailUtil;
    }

    @PutMapping(value = "/update-mail")
    public MailUtil updateMailUtil(@RequestBody MailUtil mailUtil){
        mailUtilService.save(mailUtil);
        return mailUtil;
    }

    private void sendMail(String subject, String message){
        MailUtil mailUtil = mailUtilService.getMailUtil();
        if(mailUtil != null){
            MailSendUtility.sendMail(
                    mailUtil,
                    subject,
                    message,
                    executor,
                    mailSender);
        }
    }
}
