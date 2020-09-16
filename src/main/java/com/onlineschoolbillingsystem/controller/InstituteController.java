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

/**
 * Institute related endpoints.
 */
@RestController
@RequestMapping(path = "/institutes")
public class InstituteController {

    private InstituteService instituteService;

    @Autowired
    public InstituteController(InstituteService instituteService, InstituteClassService instituteClassService) {
        this.instituteService = instituteService;
    }

    /**
     * get endpoint to get a specific institute by email.
     * @param email institute email.
     * @return - institute data.
     */
    @GetMapping
    public List<Institute> getAllInstitutes(@RequestParam(required = false) String email) {
        if(email != null){
            return Collections.singletonList(instituteService.getInstituteByEmail(email));
        }
        return instituteService.getAllInstitutes();
    }

    /**
     * Post endpoint to register an institute.
     * @param registerInstituteRequest - request.
     * @return registered institute details.
     */
    @PostMapping
    public Institute registerInstitute(@Valid @RequestBody RegisterInstituteRequest registerInstituteRequest) {
        return instituteService.registerInstitute(registerInstituteRequest.getEmail(),
                registerInstituteRequest.getPassword(),
                registerInstituteRequest.getAddress(), registerInstituteRequest.getContact());
    }

    /**
     * Post endpoint to register a class to a specific
     * institute.
     * @param instituteId - instituteId
     * @param registerClassRequest - class register request.
     * @return - registered class data.
     * @throws Exception
     */
    @PostMapping(path = "/{instituteId}/classes")
    public InstituteClass createClass(@PathVariable @NotEmpty long instituteId,
                                      @Valid @RequestBody CreateClassRequest registerClassRequest) throws Exception{
        Institute institute =  instituteService.getInstituteById(instituteId)
                .orElseThrow(() -> new Exception(String.format("Institute for ID: %s not found.", instituteId)));
        return instituteService.createClass(registerClassRequest.getSubject(),
                registerClassRequest.getDay(),
                registerClassRequest.getTime(),
                registerClassRequest.getLectureHall(),
                registerClassRequest.getInstructor(),
                registerClassRequest.getMonthlyFee(),
                institute);
    }

    /**
     * Get endpoint to fetch specific students monthly
     * bill.
     * @param instituteId id of the institute
     * @param studentId student id
     * @return monthly bill related data.
     * @throws Exception
     */
    @GetMapping(path = "/{instituteId}/students/{studentId}/monthly-bill")
    public MonthlyBill getMonthlyBillForStudent(@PathVariable long instituteId,
                                                @PathVariable long studentId) throws Exception {
        return instituteService.calculateMonthlyBillForStudent(
                instituteId, studentId);
    }

    /**
     * Get endpoint to get all the registered
     * classes for a specific institute.
     * @param instituteId - instituteId
     * @return list of registered classes.
     */
    @GetMapping(path = "/{instituteId}/classes")
    public Set<InstituteClass> getAllClassesByInstitute(@PathVariable long instituteId) {
        return instituteService.getAllClassesByInstitute(instituteId);
    }

}
