package com.cydeo.controller;

import com.cydeo.dto.AddressDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TeacherDTO;
import com.cydeo.service.AddressService;
import com.cydeo.service.ParentService;
import com.cydeo.service.StudentService;
import com.cydeo.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*1.task write a schoolcontroller and api endpoint for "teachers",
return all teachers as json you will get an error try to solve it.*/

@RestController
public class SchoolController {

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final ParentService parentService;
    private final AddressService addressService;

    public SchoolController(TeacherService teacherService, StudentService studentService, ParentService parentService, AddressService addressService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.parentService = parentService;
        this.addressService = addressService;
    }

    @GetMapping("/teachers")
    public List<TeacherDTO> readAllTeacher() {
        List<TeacherDTO> teachers = teacherService.findAll();
        return teachers;
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<ResponseWrapper> readOneTeacher(@PathVariable("id") Long id) throws Exception {
        TeacherDTO teacherDTO = teacherService.findById(id);

       /* if (teacherDTO != null) {
            ResponseWrapper response = new ResponseWrapper("Teacher found", teacherDTO);
            return ResponseEntity.ok(response);
        } else {
            ResponseWrapper response = new ResponseWrapper("Teacher not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }*/
        return ResponseEntity.ok(new ResponseWrapper("A teacher is successfully retrieved", teacherDTO));
    }

    @GetMapping("/students")
    public ResponseEntity<ResponseWrapper> readAllStudents() {
        return ResponseEntity.ok(new ResponseWrapper("Students are successfully retrieved", studentService.findAll()));
    }

    /*create a parent endpoint where status code is 202
    * additional header has "parent", "returned"
    * and following json body structure
    * "Parents are successfully retrieved." message
    * code: 202
    * success: true
    * and student data*/

    @GetMapping("/parents")
    public ResponseEntity<ResponseWrapper> readAllParents() {
        ResponseWrapper responseWrapper =
                new ResponseWrapper(true, "Parents are retrieved successfully",
                        HttpStatus.OK.value(), parentService.findAll());
        return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
    }

    /*create an endpoint for individual address information
    * /address/1
    * return status code 200
    * "address .. is successfully retrieved" message
    * and address information*/

    @GetMapping("/address/{id}")
    public ResponseEntity<ResponseWrapper> getAddress(@PathVariable("id") Long id) throws Exception {
        AddressDTO addressDTO = addressService.findById(id);
        return ResponseEntity.ok(new ResponseWrapper("Address "+id+" is successfully retrieved", addressDTO));
    }

    @PutMapping("/address/{id}")
    public AddressDTO updateAddress(@PathVariable("id") Long id, @RequestBody AddressDTO addressDTO) throws Exception {
        addressDTO.setId(id);
        return addressService.update(addressDTO);
    }

}
