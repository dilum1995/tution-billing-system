package com.onlineschoolbillingsystem.controller;

import com.onlineschoolbillingsystem.entity.Institute;
import com.onlineschoolbillingsystem.entity.InstituteClass;
import com.onlineschoolbillingsystem.entity.MonthlyBill;
import com.onlineschoolbillingsystem.request.CreateClassRequest;
import com.onlineschoolbillingsystem.request.RegisterInstituteRequest;
import com.onlineschoolbillingsystem.service.InstituteClassService;
import com.onlineschoolbillingsystem.service.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/institutes")
public class InstituteController {

    private InstituteService instituteService;

    @Autowired
    public InstituteController(InstituteService instituteService, InstituteClassService instituteClassService) {
        this.instituteService = instituteService;
    }

    @GetMapping
    public List<Institute> getAllInstitutes(@RequestParam(required = false) String email) {
        if(email != null){
            return Collections.singletonList(instituteService.getInstituteByEmail(email));
        }
        return instituteService.getAllInstitutes();
    }

    @PostMapping
    public Institute registerInstitute(@Valid @RequestBody RegisterInstituteRequest registerInstituteRequest) {
        return instituteService.registerInstitute(registerInstituteRequest.getEmail(), registerInstituteRequest.getPassword(),
                registerInstituteRequest.getAddress(), registerInstituteRequest.getContact());
    }

    @PostMapping(path = "/{instituteId}/classes")
    public InstituteClass createClass(@PathVariable @NotEmpty long instituteId,
                                      @Valid @RequestBody CreateClassRequest registerClassRequest) throws Exception{
        Institute institute =  instituteService.getInstituteById(instituteId).orElseThrow(() -> new Exception(String.format("Institute for ID: %s not found.", instituteId)));
        return instituteService.createClass(registerClassRequest.getSubject(), registerClassRequest.getDay(), registerClassRequest.getTime(), registerClassRequest.getLectureHall(), registerClassRequest.getInstructor(), registerClassRequest.getMonthlyFee(), institute);
    }

    @GetMapping(path = "/{instituteId}/students/{studentId}/monthly-bill")
    public MonthlyBill getMonthlyBillForStudent(@PathVariable long instituteId, @PathVariable long studentId) throws Exception {
        return instituteService.calculateMonthlyBillForStudent(instituteId, studentId);
    }

    @GetMapping(path = "/{instituteId}/classes")
    public Set<InstituteClass> getAllClassesByInstitute(@PathVariable long instituteId) {
        return instituteService.getAllClassesByInstitute(instituteId);
    }

}
