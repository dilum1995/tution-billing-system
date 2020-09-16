package com.onlineschoolbillingsystem.controller;

import com.onlineschoolbillingsystem.entity.InstituteClass;
import com.onlineschoolbillingsystem.entity.Student;
import com.onlineschoolbillingsystem.request.EnrollStudentInClassRequest;
import com.onlineschoolbillingsystem.service.InstituteClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(path = "/classes")
public class InstituteClassController {

    private InstituteClassService instituteClassService;

    @Autowired
    public InstituteClassController(InstituteClassService instituteClassService) {
        this.instituteClassService = instituteClassService;
    }

    /**
     * Get endpoint to get enrolled students
     * to a specific class.
     * @param classId - class Id
     * @return - student data.
     */
    @GetMapping(path = "/{classId}/students")
    public Set<Student> getAllStudentsInClass(@PathVariable long classId){
        return instituteClassService.getStudentsByClass(classId);
    }

    @PostMapping(path = "/{classId}/students")
    public Set<InstituteClass> enrollStudentInClass(@PathVariable long classId, @Valid @RequestBody EnrollStudentInClassRequest enrollStudentInClassRequest) throws Exception {
        return instituteClassService.enrollStudentInClass(enrollStudentInClassRequest.getStudentId(), classId);
    }

}
