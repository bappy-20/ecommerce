package com.inovex.main.controller.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovex.main.entity.Market;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.TerritoryModel;
import com.inovex.main.json.response.AreaResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.MarketService;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.TerritoryService;

@RestController
@RequestMapping("/api")
public class MarketRestController {

    @Autowired
    MarketService mktService;
    @Autowired
    TerritoryService terriService;
    @Autowired
    MenuService menuService;
    @Autowired
    OrganizationRepository orgRepo;

    @GetMapping("/market")
    public List<AreaResponse> getAllMarkets(HttpServletRequest request) {
        List<AreaResponse> response = new ArrayList<AreaResponse>();
        List<Market> market = new ArrayList<Market>();

        try {

            market = mktService.findAll();
            for (Market market2 : market) {
                AreaResponse res = new AreaResponse();
                res.setId(market2.getId());
                res.setMarketName(market2.getMarketName());
                res.setAddress(market2.getAddress());
                Optional<TerritoryModel> terri = terriService.findById(market2.getTerritoryId());
                if (terri.isPresent()) {
                    res.setTerritoryName(terri.get().getTerritoryName());
                }
                response.add(res);
            }

        }

        catch (Exception e) {

        }
        return response;
    }

    @PostMapping("/market")
    public ResponseData createMarket(@RequestBody Market market, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            // System.out.println(" start ");
            Market mkt = mktService.save(market, request);
            responseData.setData(mkt);
            responseData.setStatusCode(201);
            responseData.setMessage("Market created successfully");

            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setData(null);
            responseData.setMessage(e.getMessage());
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());

            return responseData;
        }
    }

    @GetMapping("/market/{id}")
    public ResponseData getMarket(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Market mkt = mktService.getMarket(id);
            responseData.setData(mkt);
            responseData.setStatusCode(200);
            responseData.setMessage("ok");
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);

            responseData.setMessage(e.getMessage());
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());

            return responseData;
        }
    }

    @DeleteMapping("/delete-market/{id}")
    public ResponseData deleteMarket(@PathVariable Long id,HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            mktService.deleteById(id,request);
            responseData.setStatusCode(204);
            responseData.setMessage("delete successfully");
            return responseData;
        } catch (Exception e) {
        	e.printStackTrace();
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());

            return responseData;
        }
    }

    @PutMapping("/market/{id}")
    public ResponseData updateMarket(@RequestBody Market market, @PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            Market dst = mktService.update(market, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(dst);
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/market-list-role")
    public List<AreaResponse> getAllMarketRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<AreaResponse> response = new ArrayList<AreaResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "market";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);

                    for (Market market2 : org.get().getMkt()) {
                        AreaResponse res = new AreaResponse();
                        res.setId(market2.getId());
                        res.setMarketName(market2.getMarketName());
                        res.setAddress(market2.getAddress());
                        res.setCanEdit(rights.get(0));
                        res.setCanDelete(rights.get(1));
                        Optional<TerritoryModel> terri = terriService.findById(market2.getTerritoryId());
                        if (terri.isPresent()) {
                            res.setTerritoryName(terri.get().getTerritoryName());
                        }
                        response.add(res);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @GetMapping("/all-market-list")
    public Set<Market> getAllList(HttpServletRequest request) {
        Set<Market> arealist = new HashSet<Market>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {

                    arealist = org.get().getMkt();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arealist;
    }

    @PostMapping("/market-mapping/{id}")
    public ResponseData submitUserMapping(@RequestBody List<Long> mktId, @PathVariable Long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {

            mktService.saveUserMapping(mktId, id);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(null);
            return responseData;
        } catch (Exception e) {

            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }
}
