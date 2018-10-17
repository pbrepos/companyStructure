package ru.pb.springstart.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.pb.springstart.entity.Office;
import ru.pb.springstart.entity.Subdivision;
import ru.pb.springstart.service.OfficeService;
import ru.pb.springstart.service.ServiceResponse;
import ru.pb.springstart.service.SubdivisionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Pavel Barmyonkov on 12.10.18.
 * pbarmenkov@gmail.com
 */

@Controller
public class SubdivisionController {

    @Autowired
    private SubdivisionService subdivisionService;

    @Autowired
    private OfficeService officeService;


    @GetMapping("/addFormSubdivision/{type}/{id}")
    public String addFormSubdivision(@PathVariable("type") String type, @PathVariable("id") int id, Model model) {
        model.addAttribute("type", type);
        model.addAttribute("id", id);
        return "subdivisionAdd";
    }


//    @PostMapping("/addSubdivision")
//    public @ResponseBody  ResponseEntity<?> addSubdivision(HttpServletRequest request, HttpServletResponse response){
//        Subdivision subdivisionNew = new Subdivision();
//        subdivisionNew.setName(request.getParameter("name"));
//        subdivisionNew.setFullNameHead(request.getParameter("fiohead"));
//
//        if (request.getParameter("type").equals("office")){
//            int officeId = Integer.parseInt(request.getParameter("id"));
//            subdivisionNew.setOffice(officeService.getOfficeById(officeId));
//        } else {
//            int subdivisionId  = Integer.parseInt(request.getParameter("id"));
//            Subdivision subdivision = subdivisionService.getSubdivisionById(subdivisionId);
//            Office office = subdivision.getOffice();
//            subdivisionNew.setOffice(office);
//            subdivisionNew.setParentSubdivision(subdivision);
//        }
//
//        subdivisionService.save(subdivisionNew);
//
//        return ResponseEntity.ok().body("Success add");
//    }

    @PostMapping("/addSubdivision")
    @ResponseBody
    public ServiceResponse addSubdivision(@ModelAttribute("subdivision") @Valid Subdivision subdivision, BindingResult result,
                                          @RequestParam("type") String type,
                                          @RequestParam("id") int id) {

        ServiceResponse serviceResponse = new ServiceResponse();

        if (result.hasErrors()) {

            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(
                            Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
                    );

            serviceResponse.setValidated(false);
            serviceResponse.setErrorMessages(errors);

        } else {

            Subdivision subdivisionNew = new Subdivision();
            subdivisionNew.setName(subdivision.getName());
            subdivisionNew.setFullNameHead(subdivision.getFullNameHead());

            if (type.equals("office")) {

                subdivisionNew.setOffice(new Office(id));

            } else {

                Subdivision subdivisionBd = subdivisionService.getSubdivisionById(id);
                Office office = new Office(subdivisionBd.getOffice().getId());
                subdivisionNew.setOffice(office);
                subdivisionNew.setParentSubdivision(new Subdivision(subdivisionBd.getId()));

            }

            try {

                subdivisionService.save(subdivisionNew);
                serviceResponse.setValidated(true);
                serviceResponse.setData(subdivisionNew);

            } catch (ConstraintViolationException e) {

                serviceResponse.setValidated(false);
                Map<String, String> errors = new HashMap<>();
                errors.put("name", "Раздел с таким названием уже существует");
                serviceResponse.setErrorMessages(errors);
            }
        }

        return serviceResponse;
    }

    @PostMapping("/removeSubdivision")
    public @ResponseBody
    ResponseEntity removeOffice(HttpServletRequest request) {
        Subdivision subdivision = subdivisionService.getSubdivisionById(Integer.parseInt(request.getParameter("id")));
        subdivisionService.remove(subdivision);
        return ResponseEntity.ok().body("Success subdivision remove");
    }

    @PostMapping("/getSubdivisionInfo")
    public @ResponseBody
    ResponseEntity<ServiceResponse> getSubdivisionInfo(@RequestBody Subdivision subdivision) {

        Subdivision subdivisionObject = subdivisionService.getSubdivisionById(subdivision.getId());
        Subdivision subdivisionNew = new Subdivision();
        subdivisionNew.setId(subdivisionObject.getId());
        subdivisionNew.setName(subdivisionObject.getName());
        subdivisionNew.setFullNameHead(subdivisionObject.getFullNameHead());

        return new ResponseEntity<>(new ServiceResponse("ok", subdivisionNew), HttpStatus.OK);
    }


    @PostMapping("/updateSubdivision")
    @ResponseBody
    public ServiceResponse updateOffice(@ModelAttribute("subdivision") @Valid Subdivision subdivision, BindingResult result) {

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
                subdivisionService.update(subdivision);
                serviceResponse.setValidated(true);
                serviceResponse.setData(subdivision);
            } catch (DataIntegrityViolationException e) {
                serviceResponse.setValidated(false);
                Map<String, String> errors = new HashMap<>();
                errors.put("name", "Раздел с таким названием уже существует");
                serviceResponse.setErrorMessages(errors);
            }
        }

        return serviceResponse;
    }


}
