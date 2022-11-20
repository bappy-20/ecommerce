package com.inovex.main.controller.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

import com.inovex.main.entity.Distributor;
import com.inovex.main.entity.DistributorRequisition;
import com.inovex.main.entity.DistributorRequisitionResponse;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.DistributorRequisitionService;
import com.inovex.main.service.DistributorService;
import com.inovex.main.service.MenuService;
import com.inovex.main.service.UserService;

@RestController
@RequestMapping("/api")
public class DistributorRequisitionRestController {

	@Autowired
	DistributorService distService;
	@Autowired
	DistributorRequisitionService distributorService;
	@Autowired
	OrganizationRepository orgRepo;
	@Autowired
	MenuService menuService;
	@Autowired
	UserService userService;

	@GetMapping("/distributor-requisition")
	public Set<Distributor> getAllDistributors(HttpServletRequest request) {
		Set<Distributor> distributorList = new HashSet<Distributor>();
		Long orgId = Long.parseLong("295");
		Optional<Organization> org = orgRepo.findById(orgId);
		if (org.isPresent()) {
			try {
				distributorList = org.get().getDistributors();
			} catch (Exception e) {

			}
		}
		return distributorList;
	}

	@GetMapping("/distributor-requisition-res")
	public List<DistributorRequisitionResponse> getAllDistributorsRequisition(HttpServletRequest request) {
		List<DistributorRequisitionResponse> distributorList = new ArrayList<DistributorRequisitionResponse>();

		try {
			distributorList = distributorService.findAllResponse(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return distributorList;
	}

	@PostMapping("/distributor-requisition")
	public ResponseData createDistributor(@RequestBody DistributorRequisition distributor, HttpServletRequest request) {
		ResponseData responseData = new ResponseData();

		try {
			DistributorRequisition dst = distributorService.save(distributor, request);
			responseData.setData(dst);
			responseData.setStatusCode(201);
			responseData.setMessage("Distributor created successfully");

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

	@GetMapping("/distributor-requisition/{id}")
	public ResponseData getDistributor(@PathVariable Long id) {
		ResponseData responseData = new ResponseData();
		try {
			Optional<DistributorRequisition> dst = distributorService.findById(id);
			responseData.setData(dst.get());
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

	@DeleteMapping("/delete-distributor-requisition/{id}")
	public ResponseData deleteDistributor(@PathVariable Long id, HttpServletRequest request) {

		ResponseData responseData = new ResponseData();
		try {
			distributorService.deleteById(id, request);
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

	@PutMapping("/update-distributor-requisition/{id}")
	public ResponseData updateDistributor(@RequestBody DistributorRequisition distributor, @PathVariable Long id,
			HttpServletRequest request) {

		ResponseData responseData = new ResponseData();
		try {

			DistributorRequisition dst = distributorService.update(distributor, id, request);
			responseData.setStatusCode(200);
			responseData.setMessage("update successfully");
			responseData.setData(dst);
			return responseData;
		} catch (Exception e) {
			e.printStackTrace();
			responseData.setData(null);
			responseData.setStatusCode(500);
			responseData.setMessage(e.getMessage());
			return responseData;
		}
	}

	@PutMapping("/update-distributor-requisition-by-other-users/{id}")
	public ResponseData updateDistributorRequisition(@RequestBody DistributorRequisition distributor,
			@PathVariable Long id, HttpServletRequest request) {
		ResponseData responseData = new ResponseData();
		try {
			DistributorRequisition dst = distributorService.updateByOtherOperation(distributor, id, request);
			responseData.setStatusCode(200);
			responseData.setMessage("update successfully");
			responseData.setData(dst);
			return responseData;
		} catch (Exception e) {
			e.printStackTrace();
			responseData.setData(null);
			responseData.setStatusCode(500);
			responseData.setMessage(e.getMessage());
			return responseData;
		}
	}

	@PutMapping("/update-requisition-status/{reqId}/{dept}")
	public ResponseData updateRequisitionStatud(@PathVariable Long reqId, @PathVariable String dept,
			HttpServletRequest request) {

		ResponseData responseData = new ResponseData();
		try {

			DistributorRequisition dst = distributorService.update(reqId, dept);
			responseData.setStatusCode(200);
			responseData.setMessage("update successfully");
			responseData.setData(dst);
			return responseData;
		} catch (Exception e) {
			e.printStackTrace();
			responseData.setData(null);
			responseData.setStatusCode(500);
			responseData.setMessage(e.getMessage());
			return responseData;
		}
	}

	@GetMapping("/distributor-requisition-confirmed-list")
	public List<DistributorRequisitionResponse> getAllConfirmedDistributorsRequisition(HttpServletRequest request) {
		List<DistributorRequisitionResponse> distributorList = new ArrayList<DistributorRequisitionResponse>();

		try {
			distributorList = distributorService.findAllConfrimRequiResponse(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return distributorList;
	}

	@GetMapping("/distributor-requisition-processed-list")
	public List<DistributorRequisitionResponse> getAllProcessedDistributorsRequisition(HttpServletRequest request) {
		List<DistributorRequisitionResponse> distributorList = new ArrayList<DistributorRequisitionResponse>();

		try {
			distributorList = distributorService.findAllProcessedRequisitionResponse(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return distributorList;
	}

	@GetMapping("/distributor-requisition-approved-list")
	public List<DistributorRequisitionResponse> getAllApprovedDistributorsRequisition(HttpServletRequest request) {
		List<DistributorRequisitionResponse> distributorList = new ArrayList<DistributorRequisitionResponse>();

		try {
			distributorList = distributorService.findAllApprovedRequisitionResponse(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return distributorList;
	}

	@GetMapping("/distributor-requisition-verified-list")
	public List<DistributorRequisitionResponse> getAllVerifiedDistributorsRequisition(HttpServletRequest request) {
		List<DistributorRequisitionResponse> distributorList = new ArrayList<DistributorRequisitionResponse>();

		try {
			distributorList = distributorService.findAllVerifiedRequisitionResponse(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return distributorList;
	}

	// distributor-requisition-delivered-list
	@GetMapping("/distributor-requisition-delivered-list")
	public List<DistributorRequisitionResponse> getAlldeliveredDistributorsRequisition(HttpServletRequest request) {
		List<DistributorRequisitionResponse> distributorList = new ArrayList<DistributorRequisitionResponse>();

		try {
			distributorList = distributorService.findAllDeliveredRequisitionResponse(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return distributorList;
	}

	// distributor-requisition-received-list
	@GetMapping("/distributor-requisition-received-list")
	public List<DistributorRequisitionResponse> getAllReceivedDistributorsRequisition(HttpServletRequest request) {
		List<DistributorRequisitionResponse> distributorList = new ArrayList<DistributorRequisitionResponse>();

		try {
			distributorList = distributorService.findAllReceivedRequisitionResponse(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return distributorList;
	}

	@PutMapping("/update-distributor-requisition-receive/{id}")
	public ResponseData updateDistributorRequisitionReceived(@RequestBody DistributorRequisition distributor,
			@PathVariable Long id, HttpServletRequest request) {

		ResponseData responseData = new ResponseData();
		try {
			DistributorRequisition dst = distributorService.update1(distributor, id, request);
			responseData.setStatusCode(200);
			responseData.setMessage("update successfully");
			responseData.setData(dst);
			return responseData;
		} catch (Exception e) {
			e.printStackTrace();
			responseData.setData(null);
			responseData.setStatusCode(500);
			responseData.setMessage(e.getMessage());
			return responseData;
		}
	}

	/**
	 *
	 *
	 * @author bappi
	 *
	 */

	@GetMapping("/distributor-requisition-role-list")
	public List<DistributorRequisitionResponse> getAllDistReqRole1(HttpServletRequest request) {
		Set<Menu> menu = new HashSet<Menu>();
		List<DistributorRequisitionResponse> response = new ArrayList<DistributorRequisitionResponse>();
		if (request.getSession().getAttribute("orgId") != null) {
			long orgId = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgId);
			try {
				if (org.isPresent()) {
					menu = org.get().getMenu();
					String rolemanagement = "requisition";
					List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);

					for (DistributorRequisition distributorRequisition : org.get().getDistributorRequisition()) {
						DistributorRequisitionResponse res = new DistributorRequisitionResponse();
						res.setId(distributorRequisition.getId());
						Optional<Distributor> dis = distService.findById(distributorRequisition.getDealerId());
						if (dis.isPresent()) {
							res.setDistName(dis.get().getDistributorName());
						} else {
							res.setDistName("Not Found");
						}
						res.setCanEdit(rights.get(0));
						res.setCanDelete(rights.get(1));
						res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
						if (distributorRequisition.getStatus().equals("0")) {
							res.setTotalPrice(distributorRequisition.getTotalPrice());
							res.setNetDiscount(distributorRequisition.getNetDiscount());
							res.setGrandTotal(distributorRequisition.getGrandTotal());
							res.setStatus("Requisition_Placed");
						}
						if (distributorRequisition.getStatus().equals("1")) {
							res.setStatus("Confirmed_By_Distributor");
							res.setTotalPrice(distributorRequisition.getTotalPrice());
							res.setNetDiscount(distributorRequisition.getNetDiscount());
							res.setGrandTotal(distributorRequisition.getGrandTotal());
						}
						if (distributorRequisition.getStatus().equals("2")) {
							res.setStatus("Approved_By_SupplyChain");
							res.setTotalPrice(distributorRequisition.getTotalPriceOfSupplyChain());
							res.setNetDiscount(distributorRequisition.getNetDiscountOfSupplyChain());
							res.setGrandTotal(distributorRequisition.getGrandTotalOfSupplyChain());
						}
						if (distributorRequisition.getStatus().equals("3")) {
							res.setStatus("Approved_By_Accounce");
							res.setTotalPrice(distributorRequisition.getTotalPriceOfAccounce());
							res.setNetDiscount(distributorRequisition.getNetDiscountOfAccounce());
							res.setGrandTotal(distributorRequisition.getGrandTotalOfAccounce());
						}
						if (distributorRequisition.getStatus().equals("4")) {
							res.setStatus("Approved_By_Operation");
							res.setTotalPrice(distributorRequisition.getTotalPriceOfOperation());
							res.setNetDiscount(distributorRequisition.getNetDiscountOfOperation());
							res.setGrandTotal(distributorRequisition.getGrandTotalOfOperation());
						}
						if (distributorRequisition.getStatus().equals("5")) {
							res.setStatus("Delivered");
							res.setTotalPrice(distributorRequisition.getTotalPriceOfDelivery());
							res.setNetDiscount(distributorRequisition.getNetDiscountOfDelivery());
							res.setGrandTotal(distributorRequisition.getGrandTotalOfDelivery());
						}
						if (distributorRequisition.getStatus().equals("6")) {
							res.setStatus("Received");
							res.setTotalPrice(distributorRequisition.getTotalPriceOfDelivery());
							res.setNetDiscount(distributorRequisition.getNetDiscountOfDelivery());
							res.setGrandTotal(distributorRequisition.getGrandTotalOfDelivery());
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

//    @GetMapping("/requisition-role-list-for-one-dealer")
//    public List<DistributorRequisitionResponse> getAllDistReqRole(HttpServletRequest request) {
//        Set<Menu> menu = new HashSet<Menu>();
//        Set<DistributorRequisition> distReqList = new HashSet<DistributorRequisition>();
//        List<DistributorRequisitionResponse> response = new ArrayList<DistributorRequisitionResponse>();
//        if (request.getSession().getAttribute("orgId") != null) {
//            long orgId = (long) request.getSession().getAttribute("orgId");
//            Optional<Organization> org = orgRepo.findById(orgId);
//            try {
//                if (org.isPresent()) {
//                    if (request.getSession().getAttribute("distId") != null) {
//                        menu = org.get().getMenu();
//                        String rolemanagement = "requisition";
//                        List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
//                        long distId = (long) request.getSession().getAttribute("distId");
//                        distReqList = org.get().getDistributorRequisition().stream()
//                                .filter(p -> p.getDealerId() == distId).collect(Collectors.toSet());
//
//                        for (DistributorRequisition distributorRequisition : distReqList) {
//                            DistributorRequisitionResponse res = new DistributorRequisitionResponse();
//                            res.setId(distributorRequisition.getId());
//                            Optional<Distributor> dis = distService.findById(distributorRequisition.getDealerId());
//                            if (dis.isPresent()) {
//                                res.setDistName(dis.get().getDistributorName());
//                            } else {
//                                res.setDistName("Not Found");
//                            }
//                            res.setCanEdit(rights.get(0));
//                            res.setCanDelete(rights.get(1));
//                            res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
//                            if (distributorRequisition.getStatus().equals("0")) {
//                                res.setTotalPrice(distributorRequisition.getTotalPrice());
//                                res.setNetDiscount(distributorRequisition.getNetDiscount());
//                                res.setGrandTotal(distributorRequisition.getGrandTotal());
//                                res.setStatus("Requisition_Placed");
//                            }
//                            if (distributorRequisition.getStatus().equals("1")) {
//                                res.setStatus("Confirmed_By_Distributor");
//                                res.setTotalPrice(distributorRequisition.getTotalPrice());
//                                res.setNetDiscount(distributorRequisition.getNetDiscount());
//                                res.setGrandTotal(distributorRequisition.getGrandTotal());
//                            }
//                            if (distributorRequisition.getStatus().equals("2")) {
//                                res.setStatus("Approved_By_SupplyChain");
//                                res.setTotalPrice(distributorRequisition.getTotalPriceOfSupplyChain());
//                                res.setNetDiscount(distributorRequisition.getNetDiscountOfSupplyChain());
//                                res.setGrandTotal(distributorRequisition.getGrandTotalOfSupplyChain());
//                            }
//                            if (distributorRequisition.getStatus().equals("3")) {
//                                res.setStatus("Approved_By_Accounce");
//                                res.setTotalPrice(distributorRequisition.getTotalPriceOfAccounce());
//                                res.setNetDiscount(distributorRequisition.getNetDiscountOfAccounce());
//                                res.setGrandTotal(distributorRequisition.getGrandTotalOfAccounce());
//                            }
//                            if (distributorRequisition.getStatus().equals("4")) {
//                                res.setStatus("Approved_By_Operation");
//                                res.setTotalPrice(distributorRequisition.getTotalPriceOfOperation());
//                                res.setNetDiscount(distributorRequisition.getNetDiscountOfOperation());
//                                res.setGrandTotal(distributorRequisition.getGrandTotalOfOperation());
//                            }
//                            if (distributorRequisition.getStatus().equals("5")) {
//                                res.setStatus("Delivered");
//                                res.setTotalPrice(distributorRequisition.getTotalPriceOfDelivery());
//                                res.setNetDiscount(distributorRequisition.getNetDiscountOfDelivery());
//                                res.setGrandTotal(distributorRequisition.getGrandTotalOfDelivery());
//                            }
//                            if (distributorRequisition.getStatus().equals("6")) {
//                                res.setStatus("Received");
//                                res.setTotalPrice(distributorRequisition.getTotalPriceOfDelivery());
//                                res.setNetDiscount(distributorRequisition.getNetDiscountOfDelivery());
//                                res.setGrandTotal(distributorRequisition.getGrandTotalOfDelivery());
//                            }
//                            response.add(res);
//                        }
//                    }
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return response;
//    }

	@GetMapping("/requisition-role-list-for-one-dealer")
	public List<DistributorRequisitionResponse> getAllDistReqRole(HttpServletRequest request) {
		Set<Menu> menu = new HashSet<Menu>();
		List<DistributorRequisition> distReqList = new ArrayList<DistributorRequisition>();
		List<DistributorRequisitionResponse> response = new ArrayList<DistributorRequisitionResponse>();
		if (request.getSession().getAttribute("orgId") != null) {
			long orgId = (long) request.getSession().getAttribute("orgId");
			// String dealerName = (String) request.getSession().getAttribute("username");
			Optional<Organization> org = orgRepo.findById(orgId);
			try {
				if (org.isPresent()) {
					if (request.getSession().getAttribute("dealerid") != null) {
						menu = org.get().getMenu();
						String rolemanagement = "requisition";
						List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
						long dealerid = (long) request.getSession().getAttribute("dealerid");
						distReqList = distributorService.findDistReqByDealerId(request, dealerid);
						String dealerName = (String) request.getSession().getAttribute("username");

						for (DistributorRequisition distributorRequisition : distReqList) {
							DistributorRequisitionResponse res = new DistributorRequisitionResponse();
							res.setId(distributorRequisition.getId());
							res.setDistName(dealerName);
							res.setCanEdit(rights.get(0));
							res.setCanDelete(rights.get(1));
							res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
							if (distributorRequisition.getStatus().equals("0")) {
								res.setTotalPrice(distributorRequisition.getTotalPrice());
								res.setNetDiscount(distributorRequisition.getNetDiscount());
								res.setGrandTotal(distributorRequisition.getGrandTotal());
								res.setStatus("Requisition_Placed");
							}
							if (distributorRequisition.getStatus().equals("1")) {
								res.setStatus("Confirmed_By_Distributor");
								res.setTotalPrice(distributorRequisition.getTotalPrice());
								res.setNetDiscount(distributorRequisition.getNetDiscount());
								res.setGrandTotal(distributorRequisition.getGrandTotal());
							}
							if (distributorRequisition.getStatus().equals("2")) {
								res.setStatus("Approved_By_SupplyChain");
								res.setTotalPrice(distributorRequisition.getTotalPriceOfSupplyChain());
								res.setNetDiscount(distributorRequisition.getNetDiscountOfSupplyChain());
								res.setGrandTotal(distributorRequisition.getGrandTotalOfSupplyChain());
							}
							if (distributorRequisition.getStatus().equals("3")) {
								res.setStatus("Approved_By_Accounce");
								res.setTotalPrice(distributorRequisition.getTotalPriceOfAccounce());
								res.setNetDiscount(distributorRequisition.getNetDiscountOfAccounce());
								res.setGrandTotal(distributorRequisition.getGrandTotalOfAccounce());
							}
							if (distributorRequisition.getStatus().equals("4")) {
								res.setStatus("Approved_By_Operation");
								res.setTotalPrice(distributorRequisition.getTotalPriceOfOperation());
								res.setNetDiscount(distributorRequisition.getNetDiscountOfOperation());
								res.setGrandTotal(distributorRequisition.getGrandTotalOfOperation());
							}
							if (distributorRequisition.getStatus().equals("5")) {
								res.setStatus("Delivered");
								res.setTotalPrice(distributorRequisition.getTotalPriceOfDelivery());
								res.setNetDiscount(distributorRequisition.getNetDiscountOfDelivery());
								res.setGrandTotal(distributorRequisition.getGrandTotalOfDelivery());
							}
							if (distributorRequisition.getStatus().equals("6")) {
								res.setStatus("Received");
								res.setTotalPrice(distributorRequisition.getTotalPriceOfDelivery());
								res.setNetDiscount(distributorRequisition.getNetDiscountOfDelivery());
								res.setGrandTotal(distributorRequisition.getGrandTotalOfDelivery());
							}
							response.add(res);
						}
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	@GetMapping("/distributor-requisition-confirmed-role-list")
	public List<DistributorRequisitionResponse> getAllDistReqConfirmedRole(HttpServletRequest request) {
		Set<Menu> menu = new HashSet<Menu>();
		List<DistributorRequisitionResponse> response = new ArrayList<DistributorRequisitionResponse>();
		if (request.getSession().getAttribute("orgId") != null) {
			long orgId = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgId);
			try {
				if (org.isPresent()) {
					Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
					menu = org.get().getMenu();
					String rolemanagement = "supplyChainRequisition";
					List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);

//                    list = org.get().getDistributorRequisition().stream().filter(p -> p.getStatus().equals("1"))
//                           .collect(Collectors.toSet());
					list = org.get().getDistributorRequisition();

					for (DistributorRequisition distributorRequisition : list) {
						DistributorRequisitionResponse res = new DistributorRequisitionResponse();
						res.setId(distributorRequisition.getId());
						Optional<Distributor> dis = distService.findById(distributorRequisition.getDealerId());
						if (dis.isPresent()) {
							res.setDistName(dis.get().getDistributorName());
						} else {
							res.setDistName("Not Found");
						}
						res.setCanEdit(rights.get(0));
						res.setCanDelete(rights.get(1));
						res.setTotalPrice(distributorRequisition.getTotalPrice());
						res.setNetDiscount(distributorRequisition.getNetDiscount());
						res.setGrandTotal(distributorRequisition.getGrandTotal());
						res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
						res.setStatus(distributorRequisition.getStatus());
						if (distributorRequisition.getStatus().equals("1")) {
							res.setStatus("Confirmed_By_Distributor");
						}
						if (distributorRequisition.getStatus().equals("2")) {
							res.setStatus("Approved_By_SupplyChain");
						}
						if (distributorRequisition.getStatus().equals("3")) {
							res.setStatus("Approved_By_Accounce");
						}

						if (distributorRequisition.getStatus().equals("4")) {
							res.setStatus("Approved_By_Operation");
						}

						if (distributorRequisition.getStatus().equals("5")) {
							res.setStatus("Delivered");
						}
						if (distributorRequisition.getStatus().equals("6")) {
							res.setStatus("Received");
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

	@GetMapping("/distributor-requisition-procesed-role-list")
	public List<DistributorRequisitionResponse> getAllDistReqProcessedRole(HttpServletRequest request) {
		Set<Menu> menu = new HashSet<Menu>();
		List<DistributorRequisitionResponse> response = new ArrayList<DistributorRequisitionResponse>();
		if (request.getSession().getAttribute("orgId") != null) {
			long orgId = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgId);
			try {
				if (org.isPresent()) {
					Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
					menu = org.get().getMenu();
					String rolemanagement = "operationRequisition";
					List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
//                    list = org.get().getDistributorRequisition().stream().filter(p -> p.getStatus().equals("2"))
//                            .collect(Collectors.toSet());
					list = org.get().getDistributorRequisition();
					for (DistributorRequisition distributorRequisition : list) {
						DistributorRequisitionResponse res = new DistributorRequisitionResponse();
						res.setId(distributorRequisition.getId());
						Optional<Distributor> dis = distService.findById(distributorRequisition.getDealerId());
						if (dis.isPresent()) {
							res.setDistName(dis.get().getDistributorName());
						} else {
							res.setDistName("Not Found");
						}
						res.setCanEdit(rights.get(0));
						res.setCanDelete(rights.get(1));
						res.setTotalPrice(distributorRequisition.getTotalPriceOfSupplyChain());
						res.setNetDiscount(distributorRequisition.getNetDiscountOfSupplyChain());
						res.setGrandTotal(distributorRequisition.getGrandTotalOfSupplyChain());
						res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
						if (distributorRequisition.getStatus().equals("1")) {
							res.setStatus("Confirmed_By_Distributor");
						}
						if (distributorRequisition.getStatus().equals("2")) {
							res.setStatus("Approved_By_SupplyChain");
						}
						if (distributorRequisition.getStatus().equals("3")) {
							res.setStatus("Approved_By_Accounce");
						}

						if (distributorRequisition.getStatus().equals("4")) {
							res.setStatus("Approved_By_Operation");
						}

						if (distributorRequisition.getStatus().equals("5")) {
							res.setStatus("Delivered");
						}
						if (distributorRequisition.getStatus().equals("6")) {
							res.setStatus("Received");
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

	@GetMapping("/distributor-requisition-approved-role-list")
	public List<DistributorRequisitionResponse> getAllDistReqApprovedRole(HttpServletRequest request) {
		Set<Menu> menu = new HashSet<Menu>();
		List<DistributorRequisitionResponse> response = new ArrayList<DistributorRequisitionResponse>();
		if (request.getSession().getAttribute("orgId") != null) {
			long orgId = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgId);
			try {
				if (org.isPresent()) {
					Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
					menu = org.get().getMenu();
					String rolemanagement = "approvedRequisition";
					List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
//                    list = org.get().getDistributorRequisition().stream().filter(p -> p.getStatus().equals("3"))
//                            .collect(Collectors.toSet());
					list = org.get().getDistributorRequisition();
					for (DistributorRequisition distributorRequisition : list) {
						DistributorRequisitionResponse res = new DistributorRequisitionResponse();
						res.setId(distributorRequisition.getId());
						Optional<Distributor> dis = distService.findById(distributorRequisition.getDealerId());
						if (dis.isPresent()) {
							res.setDistName(dis.get().getDistributorName());
						} else {
							res.setDistName("Not Found");
						}
						res.setCanEdit(rights.get(0));
						res.setCanDelete(rights.get(1));
						res.setTotalPrice(distributorRequisition.getTotalPriceOfAccounce());
						res.setNetDiscount(distributorRequisition.getNetDiscountOfAccounce());
						res.setGrandTotal(distributorRequisition.getGrandTotalOfAccounce());
						res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
//                        if (distributorRequisition.getStatus().equals("3")) {
//                            res.setStatus("Approved_By_Accounce");
//                        } 
						if (distributorRequisition.getStatus().equals("1")) {
							res.setStatus("Confirmed_By_Distributor");
						}
						if (distributorRequisition.getStatus().equals("2")) {
							res.setStatus("Approved_By_SupplyChain");
						}
						if (distributorRequisition.getStatus().equals("3")) {
							res.setStatus("Approved_By_Accounce");
						}

						if (distributorRequisition.getStatus().equals("4")) {
							res.setStatus("Approved_By_Operation");
						}

						if (distributorRequisition.getStatus().equals("5")) {
							res.setStatus("Delivered");
						}
						if (distributorRequisition.getStatus().equals("6")) {
							res.setStatus("Received");
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

	@GetMapping("/distributor-requisition-verified-role-list")
	public List<DistributorRequisitionResponse> getAllDistVerifiedRole(HttpServletRequest request) {
		Set<Menu> menu = new HashSet<Menu>();
		List<DistributorRequisitionResponse> response = new ArrayList<DistributorRequisitionResponse>();
		if (request.getSession().getAttribute("orgId") != null) {
			long orgId = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgId);
			try {
				if (org.isPresent()) {
					Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
					menu = org.get().getMenu();
					String rolemanagement = "verifiedRequisition";
					List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
					/*
					 * for (Menu mn : menu) { DistributorRequisitionResponse res = new
					 * DistributorRequisitionResponse(); res.setId(mn.getId());
					 * res.setCanEdit(rights.get(0)); res.setCanDelete(rights.get(1));
					 * response.add(res); }
					 */
					list = org.get().getDistributorRequisition().stream().filter(p -> p.getStatus().equals("4"))
							.collect(Collectors.toSet());
					for (DistributorRequisition distributorRequisition : list) {
						DistributorRequisitionResponse res = new DistributorRequisitionResponse();
						res.setId(distributorRequisition.getId());
						Optional<Distributor> dis = distService.findById(distributorRequisition.getDealerId());
						if (dis.isPresent()) {
							res.setDistName(dis.get().getDistributorName());
						} else {
							res.setDistName("Not Found");
						}
						res.setCanEdit(rights.get(0));
						res.setCanDelete(rights.get(1));
						res.setTotalPrice(distributorRequisition.getTotalPriceOfOperation());
						res.setNetDiscount(distributorRequisition.getNetDiscountOfOperation());
						res.setGrandTotal(distributorRequisition.getGrandTotalOfOperation());
						res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
						if (distributorRequisition.getStatus().equals("4")) {
							res.setStatus("Approved_By_Operation");
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

	@GetMapping("/distributor-requisition-delivered-role-list")
	public List<DistributorRequisitionResponse> getAllDistDeliverdRole(HttpServletRequest request) {
		Set<Menu> menu = new HashSet<Menu>();
		List<DistributorRequisitionResponse> response = new ArrayList<DistributorRequisitionResponse>();
		if (request.getSession().getAttribute("orgId") != null) {
			long orgId = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgId);
			try {
				if (org.isPresent()) {
					Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
					menu = org.get().getMenu();
					String rolemanagement = "deliveredRequisition";
					List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);

					list = org.get().getDistributorRequisition().stream().filter(p -> p.getStatus().equals("5"))
							.collect(Collectors.toSet());
					for (DistributorRequisition distributorRequisition : list) {
						DistributorRequisitionResponse res = new DistributorRequisitionResponse();

						res.setId(distributorRequisition.getId());
						Optional<Distributor> dis = distService.findById(distributorRequisition.getDealerId());
						if (dis.isPresent()) {
							res.setDistName(dis.get().getDistributorName());
						} else {
							res.setDistName("Not Found");
						}
						res.setCanEdit(rights.get(0));
						res.setCanDelete(rights.get(1));
						res.setTotalPrice(distributorRequisition.getTotalPriceOfDelivery());
						res.setNetDiscount(distributorRequisition.getNetDiscountOfDelivery());
						res.setGrandTotal(distributorRequisition.getGrandTotalOfDelivery());
						res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
						if (distributorRequisition.getStatus().equals("5")) {
							res.setStatus("Delivered");
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

	@GetMapping("/partial-delivered-requisition-role-list")
	public List<DistributorRequisitionResponse> getPartialDeliveryRole(HttpServletRequest request) {
		Set<Menu> menu = new HashSet<Menu>();
		List<DistributorRequisitionResponse> response = new ArrayList<DistributorRequisitionResponse>();
		if (request.getSession().getAttribute("orgId") != null) {
			long orgId = (long) request.getSession().getAttribute("orgId");
			Optional<Organization> org = orgRepo.findById(orgId);
			try {
				if (org.isPresent()) {
					Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
					menu = org.get().getMenu();
					String rolemanagement = "verifiedRequisition";
					List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
					
					list = org.get().getDistributorRequisition().stream().filter(p->p.getDeliveryStatus().equals("Partial_Delivery")).collect(Collectors.toSet());

                    for (DistributorRequisition distributorRequisition : list) {
                        DistributorRequisitionResponse res = new DistributorRequisitionResponse();
                        res.setId(distributorRequisition.getId());
                        Optional<Distributor> dis = distService.findById(distributorRequisition.getDealerId());
                        if (dis.isPresent()) {
                            res.setDistName(dis.get().getDistributorName());
                        } else {
                            res.setDistName("Not Found");
                        }
                        res.setCanEdit(rights.get(0));
                        res.setCanDelete(rights.get(1));
                        res.setTotalPrice(distributorRequisition.getTotalPriceOfOperation());
                        res.setNetDiscount(distributorRequisition.getNetDiscountOfOperation());
                        res.setGrandTotal(distributorRequisition.getGrandTotalOfOperation());
                        res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
                        if (distributorRequisition.getStatus().equals("5")) {
                            res.setStatus("Partial_Delivery");
                        } else if (distributorRequisition.getStatus().equals("6")) {
                            res.setStatus("Recieved");
                        }
                        res.setDeliveryStatus(distributorRequisition.getDeliveryStatus());
                        response.add(res);
                    }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}
}
