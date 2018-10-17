package ru.pb.springstart.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.pb.springstart.entity.Employee;
import ru.pb.springstart.entity.Office;
import ru.pb.springstart.entity.Subdivision;
import ru.pb.springstart.service.EmployeeService;
import ru.pb.springstart.service.ServiceResponse;
import ru.pb.springstart.service.SubdivisionService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Pavel Barmyonkov on 15.10.18.
 * pbarmenkov@gmail.com
 */

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SubdivisionService subdivisionService;

    @GetMapping("/employee/{id}")
    public String viewAddEmployee(@PathVariable("id") int subdivisionId, Model model) {
        model.addAttribute("subdivisionId", subdivisionId);
        return "employeeAdd";
    }

    @PostMapping("/addEmployee")
    public @ResponseBody
    ServiceResponse addEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult result, @RequestParam("subdivisionId") int subdivisionId) {

        System.out.println("дата" + employee.getDateBirth());

        ServiceResponse serviceResponse = new ServiceResponse();

        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(
                            Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
                    );

            serviceResponse.setValidated(false);
            serviceResponse.setErrorMessages(errors);
        } else {

            try {

                employee.setSubdivision(new Subdivision(subdivisionId));
                employeeService.save(employee);

                serviceResponse.setValidated(true);
                serviceResponse.setData(employee);

            } catch (ConstraintViolationException e) {
                serviceResponse.setValidated(false);
                Map<String, String> errors = new HashMap<>();
                errors.put("email", "Сотрудник с таким email уже существует");
                serviceResponse.setErrorMessages(errors);
            }
        }

        return serviceResponse;
    }

    @PostMapping("/getEmployeesPage")
    public @ResponseBody
    ResponseEntity<ServiceResponse> getEmployeesPage(@RequestParam(name = "subdivisionId") int id, @RequestParam(name = "page", defaultValue = "0") int page,
                                                     @RequestParam(name = "orderBy", defaultValue = "fullName") String orderBy) {

        int recordOnPage = 10; //Кол-во отображаемых записей на одной странице

        int countRecordsAll = employeeService.getAllRecords(id);
        int countPage = (int) Math.ceil((double) countRecordsAll / recordOnPage);
        List<Employee> employees = employeeService.getListByPage(page, recordOnPage, id, orderBy);

        return new ResponseEntity<>(new ServiceResponse("ok", employees, countPage), HttpStatus.OK);

    }

    @InitBinder("employee")
    public void dataBinding(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, "dateBirth", new CustomDateEditor(dateFormat, true));
    }


    @GetMapping("/employeeEdit/{id}")
    public String employeeViewUpdate(@PathVariable("id") int id, Model model) {

        Employee employee = employeeService.getById(id);
        model.addAttribute("employee", employee);
        return "employeeEdit";
    }

    @PostMapping("/employeeEdit")
    public @ResponseBody
    ServiceResponse employeeEdit(@ModelAttribute("employee") @Valid Employee employee, BindingResult result) {

        ServiceResponse serviceResponse = new ServiceResponse();

        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(
                            Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
                    );
            serviceResponse.setValidated(false);
            serviceResponse.setErrorMessages(errors);
        } else {

            try {
                serviceResponse.setData(employee);
                serviceResponse.setValidated(true);
                employeeService.update(employee);

            } catch (DataIntegrityViolationException e) {
                serviceResponse.setValidated(false);
                Map<String, String> errors = new HashMap<>();
                errors.put("name", "Сотрудник с таким Email уже существует");
                serviceResponse.setErrorMessages(errors);
            }
        }

        return serviceResponse;
    }

    @PostMapping("/removeEmployee")
    public @ResponseBody
    ResponseEntity removeEmployee(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/html; charset=utf-8");

        Employee employee = employeeService.getById(Integer.parseInt(request.getParameter("id")));
        employeeService.remove(employee);

        return ResponseEntity.ok().headers(headers).body("Сотрудник успешно удалён!");
    }
}
