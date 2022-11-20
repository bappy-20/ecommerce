package com.inovex.main.controller.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inovex.main.entity.AreaModel;
import com.inovex.main.entity.Distributor;
import com.inovex.main.entity.Market;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.RegionModel;
import com.inovex.main.entity.TerritoryModel;
import com.inovex.main.entity.User;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.json.response.UserResponse;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.AreaService;
import com.inovex.main.service.DistributorService;
import com.inovex.main.service.MarketService;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.RegionModelService;
import com.inovex.main.service.TerritoryService;
import com.inovex.main.service.UserService;

@RestController
@RequestMapping("/api")
public class UserRestController {
	@Autowired
	MenuService menuService;
	@Autowired
	OrganizationRepository orgRepo;
	@Autowired
	UserService userService;
	@Autowired
	MarketService mktService;
	@Autowired
	AreaService areaService;
	@Autowired
	RegionModelService regionService;
	@Autowired
	TerritoryService territoryService;
	@Autowired
	DistributorService distService;

	@GetMapping("/user-list")
	public List<UserResponse> getAllUser2(HttpServletRequest request) {
		Set<Menu> menu = new HashSet<Menu>();
		List<UserResponse> response = new ArrayList<UserResponse>();
		if (request.getSession().getAttribute("orgId") != null) {
			long orgId = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgId);
			try {
				if (org.isPresent()) {
					menu = org.get().getMenu();
					String rolemanagement = "webuser";
					List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
					for (User mn : org.get().getUsers()) {
						UserResponse res = new UserResponse();
						res.setId(mn.getId());
						res.setFullName(mn.getFullName());
						res.setUsername(mn.getUsername());
						res.setEmail(mn.getEmail());
						res.setUserType(mn.getUserType());
						res.setMobile(mn.getMobile());
						res.setProfileImage(mn.getProfileImage());
						res.setPresentAddress(mn.getPresentAddress());
						res.setPermanentAddress(mn.getPermanentAddress());
						res.setNidNumber(mn.getNidNumber());
						res.setBloodGroup(mn.getBloodGroup());
						List<Long> marketList = userService.findMappedMarketByUserId(mn.getId());
						if (marketList != null) {
							ArrayList<String> marketNames = new ArrayList<String>();

							for (Long market1 : marketList) {
								Optional<Market> market2 = mktService.findById(market1);

								if (market2.isPresent()) {
									String marketName = market2.get().getMarketName();
									marketNames.add(marketName);
									// long marketId = market2.get().getId();
									long territoryId = market2.get().getTerritoryId();
									Optional<TerritoryModel> territory2 = territoryService.findById(territoryId);
									if (territory2.isPresent()) {
										res.setTerritoryName(territory2.get().getTerritoryName());
										long areaId = territory2.get().getAreaId1();
										Optional<AreaModel> area2 = areaService.findById(areaId);
										if (area2.isPresent()) {
											res.setAreaName(area2.get().getAreaName());

											long regionId = area2.get().getRegionId1();

											Optional<RegionModel> region = regionService.findById(regionId);
											if (region.isPresent()) {
												res.setRegionName(region.get().getRegionName());
											} else {
												// res.setRegionName("Region Name not found");

											}
										} else {
											// res.setTerritoryName("Territory Name Not found");

										}
									} else {
										// res.setTerritoryName("Territory Name Not found");
									}
								}
							}
							res.setMarkets(marketNames);

						}
						Long territoryId = userService.findMappedTerritoryByUserId(mn.getId());
						System.out.println(territoryId);
						if (territoryId != null) {
							Optional<TerritoryModel> territory2 = territoryService.findById(territoryId);
							if (territory2.isPresent()) {
								res.setTerritoryName(territory2.get().getTerritoryName());
								long areaId = territory2.get().getAreaId1();
								Optional<AreaModel> area2 = areaService.findById(areaId);
								if (area2.isPresent()) {
									res.setAreaName(area2.get().getAreaName());

									long regionId = area2.get().getRegionId1();

									Optional<RegionModel> region = regionService.findById(regionId);
									if (region.isPresent()) {
										res.setRegionName(region.get().getRegionName());
									} else {
										// res.setRegionName("Region Name not found");

									}
								} else {
									// res.setTerritoryName("Territory Name Not found");

								}
							}

						}
						Long areaId = userService.findMappedAreaByUserId(mn.getId());
						if (areaId != null) {
							Optional<AreaModel> area2 = areaService.findById(areaId);
							if (area2.isPresent()) {
								res.setAreaName(area2.get().getAreaName());

								long regionId = area2.get().getRegionId1();

								Optional<RegionModel> region = regionService.findById(regionId);
								if (region.isPresent()) {
									res.setRegionName(region.get().getRegionName());
								} else {
									// res.setRegionName("Region Name not found");

								}
							} else {
								// res.setTerritoryName("Territory Name Not found");

							}

						}
						Long regionId = userService.findMappedRegionByUserId(mn.getId());
						if (regionId != null) {
							Optional<RegionModel> region = regionService.findById(regionId);
							if (region.isPresent()) {
								res.setRegionName(region.get().getRegionName());
							} else {
								// res.setRegionName("Region Not found");
							}

						} else {

						}

						if (mn.getUserType().equals("TSO")) {
							ArrayList<String> marketName1 = new ArrayList<String>();
							marketName1.add("N/A");
							res.setMarkets(marketName1);
						}
						if (mn.getUserType().equals("ASM")) {
							ArrayList<String> marketName1 = new ArrayList<String>();
							marketName1.add("N/A");
							res.setMarkets(marketName1);
							res.setTerritoryName("N/A");
						}
						if (mn.getUserType().equals("Distributor")) {
							Long distributorId = userService.findDistributorNameByUserId(mn.getId());
							if (distributorId != null) {
								Optional<Distributor> dist = distService.findById(distributorId);
								if (dist.isPresent()) {
									res.setDistributorName(dist.get().getDistributorName());
								} else {
									res.setDistributorName("Distributor Not found");
								}

							} else {
								res.setDistributorName("Distributor Not found");

							}

							ArrayList<String> marketName1 = new ArrayList<String>();
							marketName1.add("N/A");

							res.setMarkets(marketName1);
							res.setTerritoryName("N/A");
							res.setRegionName("N/A");
							res.setAreaName("N/A");
						}
						if (mn.getUserType().equals("RSM")) {
							ArrayList<String> marketName1 = new ArrayList<String>();
							marketName1.add("N/A");
							res.setMarkets(marketName1);
							res.setTerritoryName("N/A");
							res.setAreaName("N/A");

						}
						res.setCanEdit(rights.get(0));
						res.setCanDelete(rights.get(1));
						response.add(res);

					}

				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return response;

	}

	@GetMapping("/dsr-user-list")
	public Set<User> getAllDSM(HttpServletRequest request) {
		Set<User> users = new HashSet<>();
		if (request.getSession().getAttribute("orgId") != null) {
			long orgId = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgId);
			try {
				if (org.isPresent()) {
					users = org
							.get().getUsers().stream().filter(p -> p.getUserType().equals("DSM")
									|| p.getUserType().equals("SR") || p.getUserType().equals("DSR"))
							.collect(Collectors.toSet());
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return users;
	}

	@PostMapping("/distributor-mapping/{id}")
	public ResponseData submitUserMapping(@RequestBody List<Long> userId, @PathVariable Long id,
			HttpServletRequest request) {

		ResponseData responseData = new ResponseData();
		try {

			userService.saveUserMapping(userId, id);
			responseData.setStatusCode(200);
			responseData.setMessage("update successfully");
			responseData.setData(null);
			return responseData;
		} catch (Exception e) {
			e.printStackTrace();
			responseData.setData(null);
			responseData.setStatusCode(500);
			responseData.setMessage(e.getMessage());
			return responseData;
		}

	}

	@RequestMapping(value = "/user-server-side", method = RequestMethod.POST)
	public String getDailySa(@RequestParam("start") int start, @RequestParam("length") int length,
			@RequestParam("search[value]") String searchParam) {

		ArrayList<Object> emp = userService.getPagination(start, length, searchParam);
		JSONArray main = new JSONArray();
		JSONArray obj1 = new JSONArray();

		Iterator itr = emp.iterator();
		while (itr.hasNext()) {
			obj1 = new JSONArray();
			Object[] obj = (Object[]) itr.next();
			obj1.put(String.valueOf(obj[0]));
			obj1.put(String.valueOf(obj[1]));
			obj1.put(String.valueOf(obj[2]));
			obj1.put(0);
			obj1.put(0);
			main.put(obj1);
		}
		JSONObject responseObj = new JSONObject();
		Long totalLength = userService.getCountWithSearchParm(searchParam);
		responseObj.put("data", main);
		responseObj.put("recordsTotal", totalLength);
		responseObj.put("recordsFiltered", totalLength);
		System.out.println(responseObj);
		return responseObj.toString();

	}

	@GetMapping("/user-profile/{id}")
	public ResponseData getUserProfile(@PathVariable long id) {
		ResponseData responseData = new ResponseData();
		try {
			User user = userService.getUser(id);
			responseData.setData(user);
			responseData.setStatusCode(200);
			responseData.setMessage("ok");
			return responseData;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			responseData.setMessage(e.getMessage());
			responseData.setStatusCode(500);
			return responseData;
		}

	}

	@DeleteMapping("/delete-user/{id}")
	public ResponseData deleteUser(@PathVariable Long id, HttpServletRequest request) {

		ResponseData responseData = new ResponseData();
		try {
			userService.deleteUser(id, request);
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

}
