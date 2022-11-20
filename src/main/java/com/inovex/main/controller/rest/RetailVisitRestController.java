package com.inovex.main.controller.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovex.main.entity.Retail;
import com.inovex.main.entity.RetailVisit;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.json.response.RetailVisitResponse;
import com.inovex.main.service.RetailService;
import com.inovex.main.service.RetailVisitService;

@RestController
@RequestMapping("/api")
public class RetailVisitRestController {

    @Autowired
    RetailVisitService retailVisitService;
    @Autowired
    RetailService retailService;

    @GetMapping("/retailVisit-ofAnEmployeeByName/{employeeId}/{selectedDate}")
    public List<RetailVisitResponse> getRetailVisitOfAnEmployeeByName(@PathVariable String employeeId,
            @PathVariable String selectedDate) {
        ResponseData responseData = new ResponseData();
        List<RetailVisit> rtl = new ArrayList<>();
        List<RetailVisitResponse> reslist = new ArrayList<>();
        try {
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            Date stDate = df2.parse(selectedDate);
            rtl = retailVisitService.getReatilVisitOfAnEmployeeByName(employeeId, stDate);
            if (rtl.size() > 0) {
                for (RetailVisit retailvisit : rtl) {
                    RetailVisitResponse res = new RetailVisitResponse();
                    res.setRetailId(retailvisit.getRetailId());
                    Optional<Retail> rt = retailService.findById(retailvisit.getRetailId());
                    if (rt.isPresent()) {
                        res.setRetailName(rt.get().getRetailName());
                        res.setRetailAddress(rt.get().getRetailAddress());
                    } else {
                        res.setRetailName("Not found!");
                        res.setRetailAddress("Not found!");
                    }
                    res.setSystemAddress(retailvisit.getRetailAddress());
                    res.setSrId(retailvisit.getSrId());
                    res.setLat(retailvisit.getLat());
                    res.setRetailLong(retailvisit.getRetailLong());
                    res.setVisitDate(retailvisit.getVisitDate());
                    reslist.add(res);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setData(null);
            responseData.setMessage(e.getMessage());
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
        }
        return reslist;
    }
}
