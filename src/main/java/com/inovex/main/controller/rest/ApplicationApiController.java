package com.inovex.main.controller.rest;

import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.inovex.main.entity.Attendance;
import com.inovex.main.entity.AttendanceTime;
import com.inovex.main.entity.AttendanceType;
import com.inovex.main.entity.BrandModel;
import com.inovex.main.entity.Category;
import com.inovex.main.entity.CollectionModel;
import com.inovex.main.entity.DayModel;
import com.inovex.main.entity.Employee;
import com.inovex.main.entity.Expense;
import com.inovex.main.entity.ExpenseType;
import com.inovex.main.entity.FirebaseToken;
import com.inovex.main.entity.LeaveModel;
import com.inovex.main.entity.Location;
import com.inovex.main.entity.Market;
import com.inovex.main.entity.MonthlyOrderTarget;
import com.inovex.main.entity.MonthlyTarget;
import com.inovex.main.entity.OrderDashboardReponse;
import com.inovex.main.entity.OrderDetails;
import com.inovex.main.entity.OrderHistory;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.Price;
import com.inovex.main.entity.PrimaryInventory;
import com.inovex.main.entity.ProductMapping;
import com.inovex.main.entity.ProductModel;
import com.inovex.main.entity.Retail;
import com.inovex.main.entity.RetailComplain;
import com.inovex.main.entity.RetailType;
import com.inovex.main.entity.RetailVisit;
import com.inovex.main.entity.RouteModel;
import com.inovex.main.entity.Supplier;
import com.inovex.main.entity.User;
import com.inovex.main.entity.VisitTarget;
import com.inovex.main.file.File;
import com.inovex.main.file.FileService;
import com.inovex.main.file.FileStorageProperties;
import com.inovex.main.file.FileStorageService;
import com.inovex.main.file.InvalidFileException;
import com.inovex.main.json.response.AttendanceSummery;
import com.inovex.main.json.response.CollectionRespone;
import com.inovex.main.json.response.ContactResponse;
import com.inovex.main.json.response.EmployeeGradeResponse;
import com.inovex.main.json.response.EmployeeResponse;
import com.inovex.main.json.response.NonPainResponse;
import com.inovex.main.json.response.OrderTargetResponse;
import com.inovex.main.json.response.ProductResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.json.response.RetailResponse;
import com.inovex.main.json.response.RouteResponse;
import com.inovex.main.json.response.TargetResponse;
import com.inovex.main.json.response.VisitTargetResponse;
import com.inovex.main.service.AreaService;
import com.inovex.main.service.AttendanceService;
import com.inovex.main.service.AttendanceTimeService;
import com.inovex.main.service.AttendanceTypeService;
import com.inovex.main.service.BrandModelService;
import com.inovex.main.service.CollectionService;
import com.inovex.main.service.DayService;
import com.inovex.main.service.DistributorService;
import com.inovex.main.service.EmployeeService;
import com.inovex.main.service.ExpenseService;
import com.inovex.main.service.ExpenseTypeService;
import com.inovex.main.service.FirebaseTokenService;
import com.inovex.main.service.LeaveService;
import com.inovex.main.service.LocationService;
import com.inovex.main.service.MarketService;
import com.inovex.main.service.MeasurementUnitService;
import com.inovex.main.service.MonthlyOrderTargetService;
import com.inovex.main.service.MonthlyTargetService;
import com.inovex.main.service.OrderHisoryService;
import com.inovex.main.service.OrganizationService;
import com.inovex.main.service.PriceService;
import com.inovex.main.service.PrimaryInventoryService;
import com.inovex.main.service.ProductCategoryService;
import com.inovex.main.service.ProductMappingService;
import com.inovex.main.service.ProductModelService;
import com.inovex.main.service.RegionModelService;
import com.inovex.main.service.RetailComplainService;
import com.inovex.main.service.RetailService;
import com.inovex.main.service.RetailTypeService;
import com.inovex.main.service.RetailVisitService;
import com.inovex.main.service.RouteModelService;
import com.inovex.main.service.SupplierService;
import com.inovex.main.service.TerritoryService;
import com.inovex.main.service.UserService;
import com.inovex.main.service.UserTypeService;
import com.inovex.main.service.VisitTargetService;

@RestController

@RequestMapping("/api")
public class ApplicationApiController {

    @Autowired
    BrandModelService brandModelService;

    @Autowired
    ExpenseService expService;

    @Autowired
    ExpenseTypeService expenseTypeService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    UserTypeService userTypeService;

    @Autowired
    MarketService marketService;

    @Autowired
    TerritoryService territoryService;

    @Autowired
    DistributorService disService;

    @Autowired
    RetailService retailService;

    @Autowired
    RetailVisitService retailVisitService;

    @Autowired
    FileStorageProperties fileStorageProperties;

    @Autowired
    private FileService fileService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    OrganizationService orgService;

    @Autowired
    ProductModelService productService;

    @Autowired
    ProductCategoryService categoryService;

    @Autowired
    DayService dayService;

    @Autowired
    OrderHisoryService orderService;

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    RetailTypeService retailTypeService;

    @Autowired
    AttendanceTypeService attendanceTypeService;

    @Autowired
    RegionModelService regionService;

    @Autowired
    AreaService areaService;

    @Autowired
    SupplierService supplierService;

    @Autowired
    MeasurementUnitService measurementUnitService;

    @Autowired
    RouteModelService routeService;

    @Autowired
    MonthlyTargetService targetService;

    @Autowired
    LeaveService leaveService;

    @Autowired
    RetailComplainService retailComplainService;

    @Autowired
    VisitTargetService visitTarget;

    @Autowired
    CollectionService collectionService;

    @Autowired
    MonthlyOrderTargetService monthlyOrderTargetService;

    @Autowired
    FirebaseTokenService tokenService;

    @Autowired
    LocationService locationService;

    @Autowired
    AttendanceTimeService attendanceTimeService;

    @Autowired
    ProductMappingService proMappingService;

    @Autowired
    PrimaryInventoryService primaryInventoryService;

    @Autowired
    UserService userService;
    @Autowired
    PriceService priceService;

    @RequestMapping(value = "/all-employee", method = RequestMethod.GET)
    public List<Employee> getAllemployeeService() {

        return employeeService.getAll();
    }

    @RequestMapping(value = "/market-list/{empId}", method = RequestMethod.GET)
    public Set<Market> getMarketByEmpId(@PathVariable long empId) {
        Set<Market> mktList = new HashSet<>();
        Optional<User> emp = userService.findByUsername(Long.toString(empId));
        if (emp.isPresent()) {
            mktList = emp.get().getMkts();
        }
        return mktList;
    }

    @RequestMapping(value = "/retail-list/{mktId}", method = RequestMethod.GET)
    public Set<Retail> getRetailByMktId(@PathVariable long mktId) {
        Set<Retail> retailList = new HashSet<>();
        Optional<Market> mkt = marketService.findById(mktId);
        if (mkt.isPresent()) {
            retailList = mkt.get().getRetails();
        }
        return retailList;
    }

    @PutMapping("/reset-employee-password/{empId}/{password}")
    public ResponseData resetEmployeePass(@PathVariable String empId, @PathVariable String password) {

        ResponseData responseData = new ResponseData();
        try {

            userService.resetEmployeePasswordById(empId, password);
            responseData.setStatusCode(201);
            responseData.setMessage("Reset successfully");
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());

            return responseData;
        }
    }   

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public List<EmployeeResponse> getemployeeById(@RequestParam("employeeId") String employeeId,
            @RequestParam("password") String password, HttpServletRequest request) {
        List<EmployeeResponse> emp1 = new ArrayList<>();
        try {
            emp1 = userService.checkUserExistOrNot(employeeId, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return emp1;
    }

    @RequestMapping(value = "/save-firebase-token/{orgId}", method = RequestMethod.POST)
    public ResponseData saveFireBaseToken(@RequestBody FirebaseToken token, @PathVariable long orgId) {
        ResponseData responseData = new ResponseData();
        try {
            FirebaseToken cl = tokenService.save(token, orgId);
            responseData.setStatus("success");
            responseData.setStatusCode(201);
            responseData.setMessage("created successfully");
            return responseData;
        } catch (NullPointerException e) {
            e.printStackTrace();
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage("Please Fill up every field");
            return responseData;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage("Duplicate Entry ");
            return responseData;
        }

    }

    @RequestMapping(value = "/save-collection/{orgId}", method = RequestMethod.POST)
    public ResponseData saveCollection(@RequestBody CollectionModel colection, @PathVariable long orgId) {
        ResponseData responseData = new ResponseData();
        try {
            CollectionModel cl = collectionService.save(colection, orgId);
            if (cl != null) {
                responseData.setStatus("success");
                responseData.setStatusCode(201);
                responseData.setMessage("created successfully");
            } else {
                responseData.setStatus("Order not found");
                responseData.setStatusCode(201);
                responseData.setMessage(colection.getOrderId() + " is not in Database ");
            }
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @RequestMapping(value = "/save-retail-visit/{orgId}", method = RequestMethod.POST)
    public ResponseData saveRetailVisit(@RequestBody RetailVisit retail, @PathVariable long orgId) {
        ResponseData responseData = new ResponseData();
        try {
            RetailVisit rt = retailVisitService.save(retail, orgId);
            responseData.setStatus("success");
            responseData.setStatusCode(201);
            responseData.setMessage("created successfully");
            return responseData;
        } catch (NullPointerException e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage("Please Fill up every field");
            return responseData;
        } catch (DataIntegrityViolationException e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage("Duplicate Entry ");
            return responseData;
        }

    }

    @RequestMapping(value = "/save-location/{orgId}", method = RequestMethod.POST)
    public ResponseData saveLocation(@RequestBody Location location, @PathVariable long orgId) {
        ResponseData responseData = new ResponseData();
        try {
            Location rt = locationService.save(location, orgId);
            responseData.setStatus("success");
            responseData.setStatusCode(201);
            responseData.setMessage("created successfully");
            return responseData;
        } catch (NullPointerException e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage("Please Fill up every field");
            return responseData;
        } catch (DataIntegrityViolationException e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage("Duplicate Entry ");
            return responseData;
        }

    }

    @RequestMapping(value = "/save-location-list/{orgId}", method = RequestMethod.POST)
    public ResponseData saveListOfLocation(@RequestBody List<Location> location, @PathVariable long orgId) {
        ResponseData responseData = new ResponseData();
        try {
            List<Location> rt = locationService.saveAll(location, orgId);
            responseData.setStatus("success");
            responseData.setStatusCode(201);
            responseData.setMessage("created successfully");
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatus("Exception Occured!");
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }

    }

    @RequestMapping(value = "/save-retails/{orgId}", method = RequestMethod.POST)
    public ResponseData saveRetail(@RequestBody Retail retail, @PathVariable long orgId) {
        ResponseData responseData = new ResponseData();
        try {
            Retail rt = retailService.save(retail, orgId);
            responseData.setStatusCode(201);
            responseData.setMessage("created successfully");
            return responseData;
        } catch (NullPointerException e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage("Please Fill up every field");
            return responseData;
        } catch (DataIntegrityViolationException e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage("Duplicate Entry ");
            return responseData;
        }

    }

    @RequestMapping(value = "/get-retails-by-sr/{id}", method = RequestMethod.GET)
    public ResponseData saveRetail(@PathVariable String id) {
        ResponseData responseData = new ResponseData();
        try {
            List<Retail> rt = retailService.findAllByEmpId(id);
            if (rt.size() > 0) {
                responseData.setData(rt);
                responseData.setStatusCode(201);
                responseData.setMessage("created successfully");
            } else {
                responseData.setData(null);
                responseData.setStatusCode(204);
                responseData.setMessage("There is no data ");
            }

            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }

    }

    @GetMapping("/brand-list/{orgId}")
    public ResponseData getAllBrand(@PathVariable long orgId) {
        ResponseData responseData = new ResponseData();
        Set<BrandModel> brandList = new HashSet<BrandModel>();

        try {
            brandList = brandModelService.findAllByOrgId(orgId);
            if (brandList.size() > 0) {
                responseData.setStatus("success");
                responseData.setData(brandList);
                responseData.setStatusCode(201);
                responseData.setMessage("data found!");
            } else {
                responseData.setData(null);
                responseData.setStatusCode(204);
                responseData.setMessage("There is no data ");
            }

            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

    @RequestMapping(value = "/upload-multi-images", method = RequestMethod.POST)
    @ResponseBody
    public List<ResponseData> upload(HttpServletRequest request, Model model,

            @RequestParam("images") List<MultipartFile> file) throws ParseException, InvalidFileException, IOException {
        List<ResponseData> responselist = new ArrayList<>();
        String filename = null;
        DecimalFormat df2 = new DecimalFormat("#.##");
        for (MultipartFile multipartFile : file) {
            ResponseData response = new ResponseData();
            try {
                Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
                String dir = fileStorageLocation.toString();
                if (Files.exists(Paths.get(dir + "/" + multipartFile.getOriginalFilename()))) {
                    response.setMessage("Image already exists with the name");
                    response.setStatusCode(500);
                    responselist.add(response);
                } else {
                    filename = multipartFile.getOriginalFilename();
                    String ext = fileService.getFileExtension(filename);
                    double bytes = multipartFile.getSize();
                    double kilobytes = (bytes / 1024);
                    double mb = (kilobytes / 1024);

                    df2.setRoundingMode(RoundingMode.UP);
                    String size = df2.format(bytes);
                    File fl = new File();
                    fl.setFileName(filename);
                    fl.setFileExtension(ext);
                    fl.setFileSize(size);
                    fl.setFileDirectory(fileStorageLocation.toString());
                    fl.setType(ext);
                    fileService.uploadFile(multipartFile, fileStorageLocation.toString());
                    fileService.save(fl);
                    response.setMessage(filename);
                    response.setStatus("File upload succesful");
                    response.setStatusCode(200);
                    responselist.add(response);
                }
            } catch (NoSuchFileException e) {
                System.out.println("No such file/directory exists");
            } catch (DirectoryNotEmptyException e) {
                System.out.println("Directory is not empty.");
            } catch (IOException e) {
                System.out.println("Invalid permissions.");
            }
        }
        return responselist;
    }

    @RequestMapping(value = "/product-details", method = RequestMethod.GET)
    public ResponseData getProductDEtails(@RequestParam("distId") String orgId) {
        ResponseData response = new ResponseData();
        try {
            Set<ProductMapping> productList = new HashSet<>();
            List<ProductResponse> prdList = new ArrayList<>();
            Optional<Organization> org = orgService.findById(Long.parseLong(orgId));
            if (org.isPresent()) {
                productList = org.get().getProductMapping();
                for (ProductMapping proMapping : productList) {
                    ProductResponse res = new ProductResponse();
                    Optional<Price> price = priceService.findByProductId(proMapping.getId());
                    if (price.isPresent()) {
                        res.setProductPrice(price.get().getRetailPrice());
                        res.setProductMrpPrice(price.get().getMrp());
                        res.setRpPrice(price.get().getRetailPrice());
                        res.setDealerPrice(price.get().getDealerPrice());
                    } else {
                        res.setProductPrice(0);
                    }
                    Optional<Category> cat = categoryService.findById(proMapping.getProductCategory());
                    if (cat.isPresent()) {
                        res.setProductCategoryName(cat.get().getName());
                    } else {
                        res.setProductCategoryName("not found");
                    }
                    res.setProductName(proMapping.getProductName());
                    res.setMesuringType(proMapping.getMesuringType());
                    res.setMesuringQuantity(proMapping.getMesuringQuantity());
                    res.setSku(proMapping.getSku());
                    res.setSafetyStock(proMapping.getSafetyStock());
                    res.setStartingStock(proMapping.getStartingStock());
                    Optional<BrandModel> brand = brandModelService.findById(proMapping.getBrandId());
                    if (brand.isPresent()) {
                        res.setBrandName(brand.get().getBrandName());
                    } else {
                        res.setBrandName("not found!");
                    }
                    Optional<Supplier> sup = supplierService.findById(proMapping.getSupplierId());
                    if (sup.isPresent()) {
                        res.setSupplier(sup.get().getName());
                    } else {
                        res.setSupplier("not found!");
                    }
                    Optional<PrimaryInventory> pi = primaryInventoryService.findByProductId(proMapping.getId());
                    if (pi.isPresent()) {
                        res.setOnHand(pi.get().getOnHand());
                    } else {
                        res.setOnHand(0);
                    }

                    Optional<ProductModel> pr = productService.findById(proMapping.getProductId());
                    if (pr.isPresent()) {
                        res.setProductId(pr.get().getId());

                        res.setProductLabel(pr.get().getProductLabel());
                        res.setProductImage(pr.get().getProductImage());
                        res.setShortDiscription(pr.get().getShortDiscription());
                    }
                    prdList.add(res);
                }
            }

            response.setStatus("success");
            response.setMessage("Data found");
            response.setStatusCode(200);
            response.setData(prdList);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus("failed");
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
            response.setData(null);
            return response;
        }

    }

    @RequestMapping(value = "/route-list/{empId}", method = RequestMethod.GET)
    public ResponseData getRouteByEmpId(@PathVariable String empId) {
        List<RouteResponse> routeResponse = new ArrayList<>();
        ResponseData response = new ResponseData();
        try {
            List<DayModel> emp = dayService.getDayListByEmpId(empId);
            for (DayModel dayModel : emp) {
                RouteResponse res = new RouteResponse();
                res.setEmpId(empId);
                res.setDayName(dayModel.getDayName());
                System.out.println("empId " + empId + " getRouteId " + dayModel.getRouteId());
                Optional<RouteModel> route = routeService.findById(dayModel.getRouteId());
                if (route.isPresent()) {
                    res.setRoute(route.get());
                }
                routeResponse.add(res);
            }
            System.out.println("routeResponse " + routeResponse);
            if (emp.size() > 0) {
                response.setStatus("Data Found");
                response.setStatusCode(200);
                response.setMessage("Route List Found");
                response.setData(routeResponse);
            } else {
                response.setStatus("Data Not Found");
                response.setStatusCode(204);
                response.setMessage("No route assigned yet");
                response.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "/save-order/{orgId}", method = RequestMethod.POST)
    public ResponseData saveOrder(@RequestBody OrderHistory order, @PathVariable long orgId) {
        ResponseData responseData = new ResponseData();
        try {
            OrderHistory oh = orderService.save(order, orgId);
            responseData.setStatus("Success");
            responseData.setStatusCode(200);
            responseData.setMessage("created successfully");
            return responseData;
        } catch (NullPointerException e) {
            responseData.setStatus("Exception");
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage("NullPointerException");
            return responseData;
        } catch (DataIntegrityViolationException e) {
            responseData.setStatus("Failed");
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage("Duplicate Entry ");
            return responseData;
        }

    }

    @RequestMapping(value = "/save-attendance/{orgId}", method = RequestMethod.POST)
    public ResponseData saveAttendance(@RequestBody Attendance attendance, @PathVariable long orgId) {
        ResponseData responseData = new ResponseData();
        try {

            Attendance oh = attendanceService.save(attendance, orgId);
            if (oh != null) {
                responseData.setStatus(oh.getStatus());
            } else {
                responseData.setStatus("Error");
            }

            responseData.setStatusCode(200);
            responseData.setMessage("created successfully");
            return responseData;
        } catch (NullPointerException e) {
            e.printStackTrace();
            responseData.setStatus("Exception");
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage("NullPointerException");
            return responseData;
        } catch (DataIntegrityViolationException e) {
            responseData.setStatus("Failed");
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage("Duplicate Entry ");
            return responseData;
        }

    }

    @RequestMapping(value = "/retail-type/{distId}", method = RequestMethod.GET)
    public ResponseData getRetailType(@PathVariable long distId) {
        Set<RetailType> retailList = new HashSet<>();
        ResponseData responseData = new ResponseData();
        try {
            Optional<Organization> dist = orgService.findById(distId);
            if (dist.isPresent()) {
                retailList = dist.get().getRetailType();
                if (retailList.size() > 0) {
                    responseData.setData(retailList);
                    responseData.setStatus("Data Found");
                    responseData.setStatusCode(200);
                    responseData.setMessage("The list of Retail type");
                } else {
                    responseData.setData(null);
                    responseData.setStatus("Data not Found");
                    responseData.setStatusCode(204);
                    responseData.setMessage("No Retail type found in this distributor");
                }
            } else {
                responseData.setStatus("Disttributor not found");
                responseData.setStatusCode(204);
                responseData.setMessage("There is no distributor with that Id");

            }
        } catch (Exception e) {
            responseData.setStatus("Exception Occured");
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());

        }

        return responseData;
    }

    @RequestMapping(value = "/attendance-type/{distId}", method = RequestMethod.GET)
    public ResponseData getattendanceType(@PathVariable long distId) {
        Set<AttendanceType> attendaceTypeList = new HashSet<>();
        ResponseData responseData = new ResponseData();
        try {
            Optional<Organization> dist = orgService.findById(distId);
            if (dist.isPresent()) {
                attendaceTypeList = dist.get().getAttendanceTypes();
                if (attendaceTypeList.size() > 0) {
                    responseData.setData(attendaceTypeList);
                    responseData.setStatus("Data Found");
                    responseData.setStatusCode(200);
                    responseData.setMessage("The list of Attendance type");
                } else {
                    responseData.setData(null);
                    responseData.setStatus("Data not Found");
                    responseData.setStatusCode(204);
                    responseData.setMessage("No Attendance type found in this distributor");
                }
            } else {
                responseData.setStatus("Disttributor not found");
                responseData.setStatusCode(204);
                responseData.setMessage("There is no distributor with that Id");

            }
        } catch (Exception e) {
            responseData.setStatus("Exception Occured");
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());

        }

        return responseData;
    }

    @RequestMapping(value = "/employee-attendance", method = RequestMethod.GET)
    public ResponseData getattendance(@RequestParam String empId, @RequestParam String fromDate,

            @RequestParam String toDate) {
        ResponseData responseData = new ResponseData();
        try {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
            List<Attendance> attenlist = attendanceService.getAllBetweenDates(startDate, endDate, empId);
            if (attenlist.size() > 0) {
                responseData.setData(attenlist);
                responseData.setStatus("Date found");
                responseData.setStatusCode(200);
                responseData.setMessage("List of attendance found");
            } else {
                responseData.setData(null);
                responseData.setStatus("Date not found");
                responseData.setStatusCode(204);
                responseData.setMessage("List of attendance not  found");
            }

        } catch (Exception e) {
            responseData.setStatus("Exception Occured");
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());

        }

        return responseData;
    }

    @PostMapping("/expense/{orgId}")
    public ResponseData createNewCarExpense(@RequestBody Expense exp, @PathVariable long orgId) {
        ResponseData responseData = new ResponseData();
        try {
            Expense exp1 = expService.save(exp, orgId);
            responseData.setStatusCode(201);
            responseData.setData(exp1);
            responseData.setMessage("expense added successfully");
            return responseData;
        } catch (NullPointerException e) {
            e.printStackTrace();
            responseData.setData(null);

            responseData.setStatusCode(500);
            responseData.setMessage("Please Fill up every field");
            return responseData;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            responseData.setData(null);

            responseData.setStatusCode(500);
            responseData.setMessage("Duplicate expense id ");
            return responseData;
        }

    }

    @GetMapping("/get-expense-type/{distId}")
    public Set<ExpenseType> getExpenseType(@PathVariable Long distId) {
        Set<ExpenseType> expList = new HashSet<>();
        try {
            expList = expenseTypeService.findByOrg(distId);

            return expList;
        } catch (Exception e) {
            return expList;
        }

    }

    @GetMapping("/get-expense-by-emp/{empId}")
    public List<Expense> getExpenseByEmpId(@PathVariable String empId) {
        List<Expense> expList = new ArrayList<>();
        try {
            expList = expService.getExpenseByEmpId(empId);
            return expList;
        } catch (Exception e) {
            return expList;
        }

    }

    @GetMapping("/get-today-order-by-emp/{empId}")
    public ResponseData getOrderByCurrdateAndEpId(@PathVariable String empId) {

        List<OrderHistory> expList = new ArrayList<>();
        ResponseData responseData = new ResponseData();
        try {
            expList = orderService.getOrderByCurdate(empId);
            if (expList.size() > 0) {
                OrderDashboardReponse response = new OrderDashboardReponse();

                long total = 0;

                for (OrderHistory orderHistory : expList) {
                    total += orderHistory.getGrandTotal();
                }
                response.setGrandTotal(total);
                response.setNumberOfOrder(expList.size());

                responseData.setStatus(" order found!");
                responseData.setStatusCode(200);
                responseData.setData(response);
                responseData.setMessage(" successful");
            } else {
                responseData.setStatus("no order found!");
                responseData.setStatusCode(204);
                responseData.setData(null);
                responseData.setMessage(" no order right now");

            }

        } catch (Exception e) {
            responseData.setStatus("Exception occured!");
            responseData.setStatusCode(201);
            responseData.setData(null);
            responseData.setMessage(e.getMessage());

        }
        return responseData;
    }

    @GetMapping("/get-monthly-order-by-emp/{empId}")
    public ResponseData getOrderByCurMonthAndEpId(@PathVariable String empId) {

        Calendar cal = Calendar.getInstance();
        int res = cal.getActualMaximum(Calendar.DATE);
        LocalDate todaydate = LocalDate.now();

        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

        List<OrderHistory> expList = new ArrayList<>();
        ResponseData responseData = new ResponseData();
        try {
            Date stDate = df2.parse(todaydate.withDayOfMonth(1).toString());
            Date enDate = df2.parse(todaydate.withDayOfMonth(res).toString());
            System.out.println("stDate " + stDate + " enDate " + enDate);
            expList = orderService.getAllBetweenDates(stDate, enDate, empId);

            if (expList.size() > 0) {
                OrderDashboardReponse response = new OrderDashboardReponse();

                long total = 0;

                for (OrderHistory orderHistory : expList) {
                    total += orderHistory.getGrandTotal();
                }
                response.setGrandTotal(total);
                response.setNumberOfOrder(expList.size());

                responseData.setStatus(" order found!");
                responseData.setStatusCode(200);
                responseData.setData(response);
                responseData.setMessage(" successful");
            } else {
                responseData.setStatus("no order found!");
                responseData.setStatusCode(204);
                responseData.setData(null);
                responseData.setMessage("no order right now");

            }

        } catch (Exception e) {
            responseData.setStatus("Exception occured!");
            responseData.setStatusCode(201);
            responseData.setData(null);
            responseData.setMessage(e.getMessage());

        }
        return responseData;
    }

    @GetMapping("/get-shipped-order-by-emp/{empId}/{marketId}")
    public ResponseData getShippedOrderByEmpId(@PathVariable String empId, @PathVariable long marketId) {
        List<OrderHistory> expList = new ArrayList<>();
        ResponseData responseData = new ResponseData();
        try {

            expList = orderService.getShippedByEmp(empId, marketId);
            if (expList.size() > 0) {

                responseData.setStatus(" order found!");
                responseData.setStatusCode(200);
                responseData.setData(expList);
                responseData.setMessage(" successful");
            } else {
                responseData.setStatus("no order found!");
                responseData.setStatusCode(204);
                responseData.setData(null);
                responseData.setMessage("no order right now");

            }

        } catch (Exception e) {
            responseData.setStatus("Exception occured!");
            responseData.setStatusCode(201);
            responseData.setData(null);
            responseData.setMessage(e.getMessage());

        }
        return responseData;
    }

    @GetMapping("/get-contact-list/{orgId}")
    public ResponseData getContactList(@PathVariable long orgId) {
        List<ContactResponse> responseList = new ArrayList<ContactResponse>();
        Set<Retail> retails = new HashSet<>();
        Set<Supplier> suppliers = new HashSet<>();
        Set<Employee> empList = new HashSet<>();
        ResponseData responseData = new ResponseData();
        try {
            Optional<Organization> org = orgService.findById(orgId);
            if (org.isPresent()) {
                retails = org.get().getRetails();
                suppliers = org.get().getSuppliers();
                empList = org.get().getEmployess();
                if (empList.size() > 0) {
                    ContactResponse response = new ContactResponse();
                    for (Employee emp : empList) {
                        response.setName(emp.getEmpName());
                        response.setAddress(emp.getEmpAddress());
                        response.setPhone(emp.getEmpPhone());
                        response.setType("Employee");
                        responseList.add(response);
                    }
                }
                if (retails.size() > 0) {
                    ContactResponse response = new ContactResponse();
                    for (Retail retail : retails) {
                        response.setName(retail.getRetailName());
                        response.setAddress(retail.getRetailAddress());
                        response.setPhone(retail.getRetailPhone());
                        response.setType("Retail");
                        responseList.add(response);
                    }
                }

                if (suppliers.size() > 0) {
                    ContactResponse response = new ContactResponse();
                    for (Supplier sup : suppliers) {
                        response.setName(sup.getName());
                        response.setAddress(sup.getAddress());
                        response.setPhone(sup.getPhone());
                        response.setType("Supplier");
                        responseList.add(response);
                    }
                }
            }

            if (responseList.size() > 0) {

                responseData.setStatus(" Contact found!");
                responseData.setStatusCode(200);
                responseData.setData(responseList);
                responseData.setMessage(" successful");
            } else {
                responseData.setStatus("no Contact found!");
                responseData.setStatusCode(204);
                responseData.setData(null);
                responseData.setMessage(" no Contact right now");

            }

        } catch (Exception e) {
            responseData.setStatus("Exception occured!");
            responseData.setStatusCode(201);
            responseData.setData(null);
            responseData.setMessage(e.getMessage());

        }
        return responseData;
    }

    @GetMapping("/get-monthly-target/{empId}/{orgId}")
    public ResponseData getMonthTargetEpId(@PathVariable String empId, @PathVariable long orgId) {
        List<OrderHistory> expList = new ArrayList<>();
        List<TargetResponse> targetResponse = new ArrayList<>();
        ResponseData responseData = new ResponseData();
        try {
            List<MonthlyTarget> targetList = targetService.findAllOfCurrentMonth(empId, orgId);
            expList = orderService.findAllOfCurrentMonth(empId);
            for (MonthlyTarget monthlyTarget : targetList) {
                TargetResponse tr = new TargetResponse();
                tr.setProductName(monthlyTarget.getProductName());
                tr.setTargetquantity(monthlyTarget.getQuantity());
                tr.setTargetTotalValue(monthlyTarget.getTotalValue());
                tr.setTargetMonth(calculateBornDay(monthlyTarget.getTargetMonth().toString()));

                for (OrderHistory order : expList) {
                    System.out.println(" monthlyTarget.getProductId() " + monthlyTarget.getProductName());
                    OrderDetails or = order.getOrderDetails().stream()
                            .filter(p -> p.getProductName().equals(monthlyTarget.getProductName())).findAny()
                            .orElse(null);
                    if (or != null) {
                        tr.setSalequantity(or.getProductQuantity());
                        tr.setSaleTotalValue(or.getTotalPrice());
                        tr.setProductName(or.getProductName());
                    } else {
                        tr.setSalequantity(0);
                        tr.setSaleTotalValue(0);
                        tr.setProductName("no products found");
                    }

                }
                targetResponse.add(tr);
            }
            responseData.setStatus("success");
            responseData.setStatusCode(201);
            responseData.setData(targetResponse);
            responseData.setMessage("List of target");
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setStatus("Exception occured!");
            responseData.setStatusCode(201);
            responseData.setData(null);
            responseData.setMessage(e.getMessage());

        }
        return responseData;
    }

    public String calculateBornDay(String s1) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MMMM");
        Date d = sdf.parse(s1);
        String s = sdf1.format(d);
        return s.toUpperCase();
    }

    @GetMapping("/get-employee-grade/{orgId}")
    public ResponseData getEmployeeGrading(@PathVariable long orgId) {
        List<Object> expList = new ArrayList<>();
        List<EmployeeGradeResponse> gradeList = new ArrayList<>();
        ResponseData responseData = new ResponseData();
        try {
            expList = orderService.findAllOfCurrentMonthforGrading(orgId);
            Iterator itr = expList.iterator();
            int flag = 1;
            while (itr.hasNext()) {
                EmployeeGradeResponse grade = new EmployeeGradeResponse();
                Object[] obj = (Object[]) itr.next();
                System.out.println("emp name " + String.valueOf(obj[0]));
                Optional<Employee> emp = employeeService.findByEmployeeId(String.valueOf(obj[0]));
                if (emp.isPresent()) {
                    grade.setEmpName(emp.get().getEmpName());
                } else {
                    grade.setEmpName("employee name not found");
                }
                grade.setEmpId(Long.parseLong(String.valueOf(obj[0])));
                grade.setSaleValue(String.valueOf(obj[1]));
                grade.setGrade(Integer.toString(flag));
                gradeList.add(grade);
                flag++;
            }
            responseData.setStatus("success");
            responseData.setStatusCode(201);
            responseData.setData(gradeList);
            responseData.setMessage("");
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setStatus("Exception occured!");
            responseData.setStatusCode(201);
            responseData.setData(null);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }

    @PostMapping("/save-leave/{orgId}")
    public ResponseData createLeave(@RequestBody LeaveModel leave, @PathVariable long orgId) {
        ResponseData responseData = new ResponseData();
        try {
            LeaveModel lv = leaveService.save(leave, orgId);
            responseData.setStatusCode(201);
            responseData.setData(lv);
            responseData.setMessage("leave added successful");
            return responseData;
        } catch (NullPointerException e) {
            e.printStackTrace();
            responseData.setData(null);

            responseData.setStatusCode(500);
            responseData.setMessage("Please Fill up every field");
            return responseData;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            responseData.setData(null);

            responseData.setStatusCode(500);
            responseData.setMessage("Duplicate leave id ");
            return responseData;
        }

    }

    @RequestMapping(value = "/leave-list/{empId}", method = RequestMethod.GET)
    public ResponseData getLeaveByEmpId(@PathVariable long empId) {

        ResponseData response = new ResponseData();
        try {
            List<LeaveModel> leaveList = leaveService.findAllByEmpId(empId);

            if (leaveList.size() > 0) {
                response.setStatus("Data Found");
                response.setStatusCode(200);
                response.setMessage("leave List Found");
                response.setData(leaveList);
            } else {
                response.setStatus("Data Not Found");
                response.setStatusCode(204);
                response.setMessage("No leave yet");
                response.setData(null);
            }
        } catch (Exception e) {
            response.setStatus("Data Not Found");
            response.setStatusCode(204);
            response.setMessage(e.getMessage());
            response.setData(null);
        }
        return response;
    }

    @GetMapping("/get-attendance-summery/{empId}")
    public ResponseData getEmployeeAttendaceSummery(@PathVariable String empId) {
        List<Object> expList = new ArrayList<>();
        List<AttendanceSummery> summeryList = new ArrayList<>();
        ResponseData responseData = new ResponseData();
        try {

            Optional<AttendanceTime> attenTime = attendanceTimeService.findById(1l);
            if (attenTime.isPresent()) {
                System.out.println("in time " + attenTime.get().getInTime());
            }
            expList = attendanceService.findAllOfCurrentMonth(empId);
            if (expList.size() > 0) {

                Iterator itr = expList.iterator();
                while (itr.hasNext()) {
                    AttendanceSummery summery = new AttendanceSummery();
                    Object[] obj = (Object[]) itr.next();
                    if (String.valueOf(obj[0]) != null) {
                        System.out.println("atte type in null ");
                        summery.setAttendanceType(String.valueOf(obj[0]));
                    } else {
                        summery.setAttendanceType("0");
                    }
                    if (String.valueOf(obj[1]) != null) {
                        summery.setCount(String.valueOf(obj[1]));
                    } else {
                        summery.setCount("0");
                    }
                    if (String.valueOf(obj[2]) != null) {
                        summery.setEmpId(String.valueOf(obj[2]));
                    } else {
                        summery.setEmpId("0");
                    }

                    summeryList.add(summery);
                }
                responseData.setStatus("success");
                responseData.setStatusCode(201);
                responseData.setData(summeryList);
                responseData.setMessage("data found ");
            } else {
                responseData.setStatus("No data found!");
                responseData.setStatusCode(201);
                responseData.setData(null);
                responseData.setMessage("There is no data");
            }

        } catch (Exception e) {
            e.printStackTrace();
            responseData.setStatus("Exception occured!");
            responseData.setStatusCode(201);
            responseData.setData(null);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }

    @PostMapping("/save-retail-complain/{orgId}")
    public ResponseData createretailComplain(@RequestBody RetailComplain rc, @PathVariable long orgId) {
        ResponseData responseData = new ResponseData();
        try {
            RetailComplain lv = retailComplainService.save(rc, orgId);
            responseData.setStatusCode(201);
            responseData.setData(lv);
            responseData.setMessage("Retail Complain added successful");
            return responseData;
        } catch (NullPointerException e) {
            e.printStackTrace();
            responseData.setData(null);

            responseData.setStatusCode(500);
            responseData.setMessage("Please Fill up every field");
            return responseData;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            responseData.setData(null);

            responseData.setStatusCode(500);
            responseData.setMessage("Duplicate Retail Complain id ");
            return responseData;
        }

    }

    @RequestMapping(value = "/get-orders-by-sr/{employeeId}", method = RequestMethod.GET)
    public ResponseData getAllOrdersByEmpId(@PathVariable String employeeId) {
        ResponseData responseData = new ResponseData();
        try {
            List<OrderHistory> rt = orderService.findAllByEmployeeId(employeeId);
            if (rt.size() > 0) {
                responseData.setData(rt);
                responseData.setStatusCode(201);
                responseData.setMessage("Records found!");
            } else {
                responseData.setData(null);
                responseData.setStatusCode(204);
                responseData.setMessage("There is no data ");
            }

            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }

    }

    @RequestMapping(value = "/get-visited-retail/{employeeId}", method = RequestMethod.GET)
    public ResponseData getRetailVisit(@PathVariable String employeeId) {
        ResponseData responseData = new ResponseData();
        try {
            List<RetailResponse> responseList = new ArrayList<>();
            List<RetailVisit> rt = retailVisitService.findAllByEmpIdAndCurDate(employeeId);
            for (RetailVisit retailVisit : rt) {
                RetailResponse res = new RetailResponse();
                Optional<Retail> retail = retailService.findById(retailVisit.getRetailId());
                if (retail.isPresent()) {
                    res.setId(retail.get().getId());
                    res.setRetailName(retail.get().getRetailName());
                    res.setRetailAddress(retail.get().getRetailAddress());
                    res.setRetailOwner(retail.get().getRetailOwner());
                    res.setRetailPhone(retail.get().getRetailPhone());
                    res.setStatus("Visited");
                    responseList.add(res);
                } else {
                    res.setRetailName("Not Found!");
                    res.setRetailAddress("Not Found!");
                    res.setRetailOwner("Not Found!");
                    res.setRetailPhone("Not Found!");
                    res.setStatus("Not Found!");
                    responseList.add(res);
                }
            }
            responseData.setData(responseList);
            responseData.setStatus("success");
            responseData.setStatusCode(201);
            responseData.setMessage("Data Found!");
            return responseData;
        } catch (Exception e) {
            responseData.setStatus(e.getMessage());
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage("Exception occured!");
            return responseData;
        }

    }

    @RequestMapping(value = "/get-retail-complain/{employeeId}", method = RequestMethod.GET)
    public ResponseData getRetailComplain(@PathVariable String employeeId) {
        ResponseData responseData = new ResponseData();
        try {
            List<RetailComplain> rc = retailComplainService.findAllByEmployeeId(employeeId);
            responseData.setData(rc);
            responseData.setStatus("success");
            responseData.setStatusCode(201);
            responseData.setMessage("Data Found!");
            return responseData;
        } catch (Exception e) {
            responseData.setStatus(e.getMessage());
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage("Exception occured!");
            return responseData;
        }

    }

    @RequestMapping(value = "/get-visit-target-summery/{employeeId}", method = RequestMethod.GET)
    public ResponseData getVisitTarget(@PathVariable String employeeId) {
        ResponseData responseData = new ResponseData();
        try {
            VisitTargetResponse res = new VisitTargetResponse();
            Optional<VisitTarget> rc = visitTarget.findAllOfCurrentMonth(employeeId);
            List<RetailVisit> rt = retailVisitService.findAllByEmpIdAndCurDate(employeeId);
            if (rc.isPresent()) {
                res.setTargetedVisit(rc.get().getVisitNumber());
            } else {
                res.setTargetedVisit("0");
            }
            if (rt.size() > 0) {
                res.setVistedpoint(Integer.toString(rt.size()));
            } else {
                res.setVistedpoint("0");
            }
            responseData.setData(res);
            responseData.setStatus("success");
            responseData.setStatusCode(201);
            responseData.setMessage("Data Found!");
            return responseData;
        } catch (Exception e) {
            responseData.setStatus(e.getMessage());
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage("Exception occured!");
            return responseData;
        }

    }

    @GetMapping("/get-deliveryman-order-target/{empId}")
    public ResponseData getAllMonthlyOrderTargetByDate(@PathVariable String empId) {
        ResponseData responseData = new ResponseData();
        Optional<MonthlyOrderTarget> responseList = null;
        try {
            OrderTargetResponse otr = new OrderTargetResponse();
            responseList = monthlyOrderTargetService.findAllByCurMonthAndEmpId(empId);
            if (responseList.isPresent()) {
                otr.setEmpId(responseList.get().getEmpId());
                otr.setTagetedOrderQuantity(responseList.get().getOrderQuantity());
                otr.setTagetedOrderValue(responseList.get().getOrderValue());

            } else {
                otr.setEmpId(empId);
                otr.setTagetedOrderQuantity("0");
                otr.setTagetedOrderValue("0");

            }

            List<OrderHistory> empDelivered = orderService.getDeliveredOrderByEmployee(empId);
            if (empDelivered.size() > 0) {
                long sum = 0;
                for (OrderHistory orderHistory : empDelivered) {
                    sum += orderHistory.getTotal();
                }
                otr.setDeliveredOrderQuantity(Integer.toString(empDelivered.size()));
                otr.setDeliveredOrderValue(Long.toString(sum));

            } else {
                otr.setDeliveredOrderQuantity("0");
                otr.setDeliveredOrderValue("0");
            }

            responseData.setData(otr);
            responseData.setStatusCode(201);
            responseData.setMessage("Records found!");

        } catch (Exception e) {
            e.printStackTrace();
            responseData.setStatus(e.getMessage());
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage("Exception occured!");
        }
        return responseData;
    }

    @RequestMapping(value = "/get-non-paid-retail/{orgId}", method = RequestMethod.GET)
    public ResponseData getNonPaidRetail(@PathVariable long orgId) {
        ResponseData responseData = new ResponseData();
        List<CollectionRespone> resList = new ArrayList<>();
        List<NonPainResponse> orderList = new ArrayList<>();
        try {
            List<Object> rc = orderService.findNonPaidRetail(orgId);
            if (!rc.isEmpty() && rc.size() > 0) {
                Iterator itr = rc.iterator();
                while (itr.hasNext()) {
                    CollectionRespone res = new CollectionRespone();
                    Object[] obj = (Object[]) itr.next();
                    res.setRetailId(String.valueOf(obj[0]));
                    res.setRetailName(String.valueOf(obj[1]));
                    res.setMarketId(String.valueOf(obj[2]));
                    res.setMarketName(String.valueOf(obj[3]));
                    List<Object> orders = orderService.findNonPaidOrderByRetail(String.valueOf(obj[0]));
                    if (!orders.isEmpty() && orders.size() > 0) {
                        Iterator itr1 = orders.iterator();
                        while (itr1.hasNext()) {
                            NonPainResponse orderRes = new NonPainResponse();
                            Object[] obj1 = (Object[]) itr1.next();
                            orderRes.setOrderId(String.valueOf(obj1[0]));
                            if (!String.valueOf(obj1[1]).equals(null) || !String.valueOf(obj1[1]).equals("")) {
                                orderRes.setTotal(Long.parseLong(String.valueOf(obj1[1])));
                            } else {
                                orderRes.setTotal(0);
                            }

                            if (!String.valueOf(obj1[3]).equals(null) || !String.valueOf(obj1[3]).equals("")) {
                                orderRes.setGrandTotal(Long.parseLong(String.valueOf(obj1[3])));
                            } else {
                                orderRes.setGrandTotal(0);
                            }

                            if (!String.valueOf(obj1[2]).equals(null) || !String.valueOf(obj1[2]).equals("")) {
                                orderRes.setDiscount(Long.parseLong(String.valueOf(obj1[2])));

                            } else {
                                orderRes.setDiscount(0);
                            }
                            if (!String.valueOf(obj1[4]).equals(null) || !String.valueOf(obj1[4]).equals("")) {
                                orderRes.setOerderDate(String.valueOf(obj1[4]).substring(0, 10));
                            }
                            if (!String.valueOf(obj1[5]).equals(null) || !String.valueOf(obj1[5]).equals("")) {
                                orderRes.setDue(Long.parseLong(String.valueOf(obj1[5])));

                            } else {
                                orderRes.setDue(0);
                            }
                            orderList.add(orderRes);

                        }
                    }
                    res.setOrders(orderList);
                    resList.add(res);
                }
                responseData.setData(resList);
            } else {
                responseData.setData(null);
            }
            responseData.setStatus("success");
            responseData.setStatusCode(201);
            responseData.setMessage("Data Found!");
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setStatus(e.getMessage());
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage("Exception occured!");
            return responseData;
        }

    }
}
