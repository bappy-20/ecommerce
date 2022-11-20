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

import com.inovex.main.entity.AreaModel;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.RegionModel;
import com.inovex.main.entity.TerritoryModel;
import com.inovex.main.json.response.AreaResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.RegionModelRepository;
import com.inovex.main.service.AreaService;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.TerritoryService;

@RestController
@RequestMapping("/api")
public class TerritoryRestController {

	@Autowired
	TerritoryService territoryService;
	@Autowired
	AreaService areaService;
	@Autowired
	RegionModelRepository regionService;
	@Autowired
	MenuService menuService;
	@Autowired
	OrganizationRepository orgRepo;

	@GetMapping("/territory")
	public List<AreaResponse> getAllTerritorys(HttpServletRequest request) {
		List<AreaResponse> response = new ArrayList<AreaResponse>();
		List<TerritoryModel> territory = new ArrayList<TerritoryModel>();

		try {

			territory = territoryService.findAll();
			for (TerritoryModel territoryModel : territory) {
				AreaResponse res = new AreaResponse();
				res.setId(territoryModel.getId());
				res.setTerritoryName(territoryModel.getTerritoryName());
				Optional<AreaModel> area = areaService.findById(territoryModel.getAreaId1());
				if (area.isPresent()) {
					res.setAreaName(area.get().getAreaName());
					Optional<RegionModel> region = regionService.findById(area.get().getRegionId1());
					if (region.isPresent()) {
						res.setRegionName(region.get().getRegionName());
					} else {
						res.setRegionName("Region not found!");
					}
				} else {
					res.setAreaName("area not found!");
				}
				response.add(res);
			}
		}

		catch (Exception e) {

		}
		return response;
	}

	@PostMapping("/territory")
	public ResponseData createTerritory(@RequestBody TerritoryModel territory, HttpServletRequest request) {
		ResponseData responseData = new ResponseData();

		try {
			TerritoryModel dst = territoryService.save(territory, request);
			responseData.setData(dst);
			responseData.setStatusCode(201);
			responseData.setMessage("Territory created successfully");

			return responseData;
		} catch (Exception e) {
			e.printStackTrace();
			responseData.setData(null);
			responseData.setMessage(e.getMessage());
			responseData.setStatusCode(500);

			return responseData;
		}
	}

	@GetMapping("/territory/{id}")
	public ResponseData getTerritory(@PathVariable Long id) {
		ResponseData responseData = new ResponseData();
		try {
			TerritoryModel trt = territoryService.getTerritory(id);
			responseData.setData(trt);
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

	@DeleteMapping("/delete-territory/{id}")
	public ResponseData deleteTerritory(@PathVariable Long id, HttpServletRequest request) {

		ResponseData responseData = new ResponseData();
		try {
			territoryService.deleteById(id, request);
			responseData.setStatusCode(204);
			responseData.setMessage("delete successfully");
			return responseData;
		} catch (Exception e) {
			responseData.setData(null);
			responseData.setStatusCode(500);
			responseData.setMessage(e.getMessage());

			return responseData;
		}
	}

	@PutMapping("/territory/{id}")
	public ResponseData updateTerritory(@RequestBody TerritoryModel territory, @PathVariable Long id,
			HttpServletRequest request) {

		ResponseData responseData = new ResponseData();
		try {
			TerritoryModel trt = territoryService.update(territory, id, request);
			responseData.setStatusCode(200);
			responseData.setMessage("update successfully");
			responseData.setData(trt);
			return responseData;
		} catch (Exception e) {
			responseData.setData(null);
			responseData.setStatusCode(500);
			responseData.setMessage(e.getMessage());
			return responseData;
		}
	}

	@GetMapping("/territory-list-role")
	public List<AreaResponse> getAllTerritoryRole(HttpServletRequest request) {
		Set<Menu> menu = new HashSet<Menu>();
		List<AreaResponse> response = new ArrayList<AreaResponse>();
		if (request.getSession().getAttribute("orgId") != null) {
			long orgId = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgId);
			try {
				if (org.isPresent()) {
					menu = org.get().getMenu();
					String rolemanagement = "territory";
					List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
					/*
					 * for (TerritoryModel mn : org.get().getTerritoryModels()) { TerritoryResponse
					 * res = new TerritoryResponse(); res.setId(mn.getId());
					 * res.setCanEdit(rights.get(0)); res.setCanDelete(rights.get(1));
					 * response.add(res); }
					 */
					for (TerritoryModel territoryModel : org.get().getTerritoryModels()) {
						AreaResponse res = new AreaResponse();
						res.setId(territoryModel.getId());
						res.setTerritoryName(territoryModel.getTerritoryName());
						Optional<AreaModel> area = areaService.findById(territoryModel.getAreaId1());
						if (area.isPresent()) {
							res.setAreaName(area.get().getAreaName());
							Optional<RegionModel> region = regionService.findById(area.get().getRegionId1());
							if (region.isPresent()) {
								res.setRegionName(region.get().getRegionName());
							} else {
								res.setRegionName("Region not found!");
							}
						} else {
							res.setAreaName("area not found!");
						}
						res.setCanEdit(rights.get(0));
						res.setCanDelete(rights.get(1));
						response.add(res);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	@GetMapping("/all-territory-list")
	public Set<TerritoryModel> getAllList(HttpServletRequest request) {

		Set<TerritoryModel> arealist = new HashSet<TerritoryModel>();
		if (request.getSession().getAttribute("orgId") != null) {
			long orgId = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgId);
			try {
				if (org.isPresent()) {

					arealist = org.get().getTerritoryModels();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return arealist;
	}

	@PostMapping("/territory-mapping/{id}")
	public ResponseData submitUserMapping(@RequestBody List<Long> territoryId, @PathVariable Long id,
			HttpServletRequest request) {

		ResponseData responseData = new ResponseData();
		try {

			territoryService.saveUserMapping(territoryId, id);
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
