package ru.pb.springstart.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.pb.springstart.entity.Office;
import ru.pb.springstart.service.OfficeService;
import ru.pb.springstart.service.ServiceResponse;
import ru.pb.springstart.service.SubdivisionService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Pavel Barmyonkov on 11.10.18.
 * pbarmenkov@gmail.com
 */

@Controller
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    @Autowired
    private SubdivisionService subdivisionService;


    @GetMapping("/office")
    public String getOfficeAddPage() {
        return "officeAdd";
    }

    @PostMapping("/addOffice")
    @ResponseBody
    public ServiceResponse addOffice(@ModelAttribute("office") @Valid Office office,
                                     BindingResult result) {

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
                officeService.save(office);
                serviceResponse.setValidated(true);
                serviceResponse.setData(office);
            } catch (ConstraintViolationException e) {
                serviceResponse.setValidated(false);
                Map<String, String> errors = new HashMap<>();
                errors.put("name", "Офис с таким названием уже существует");
                serviceResponse.setErrorMessages(errors);
            }

        }

        return serviceResponse;
    }


    @PostMapping("/removeOffice")
    public @ResponseBody
    ResponseEntity removeOffice(HttpServletRequest request) {
        Office office = officeService.getOfficeById(Integer.parseInt(request.getParameter("id")));
        officeService.remove(office);
        return ResponseEntity.ok().body("Success office remove");
    }

    @GetMapping("/")
    public String getAllUsers(Model model) {
        model.addAttribute("offices", officeService.getAll());
        return "index";
    }

    @PostMapping("/getOfficeInfo")
    public @ResponseBody
    ResponseEntity<ServiceResponse> getOfficeInfo(@RequestBody Office office) {

        Office officeObject = officeService.getOfficeById(office.getId());
        Office officeNew = new Office();
        officeNew.setId(officeObject.getId());
        officeNew.setName(officeObject.getName());
        officeNew.setAddress(officeObject.getAddress());

        return new ResponseEntity<>(new ServiceResponse("ok", officeNew), HttpStatus.OK);
    }

    @PostMapping("/updateOffice")
    @ResponseBody
    public ServiceResponse updateOffice(@ModelAttribute("office") @Valid Office office,
                                        BindingResult result) {

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
                serviceResponse.setData(office);
                officeService.update(office);
                serviceResponse.setValidated(true);
            } catch (DataIntegrityViolationException e) {
                serviceResponse.setValidated(false);
                Map<String, String> errors = new HashMap<>();
                errors.put("name", "Офис с таким названием уже существует");
                serviceResponse.setErrorMessages(errors);
            }

        }

        return serviceResponse;
    }
}
