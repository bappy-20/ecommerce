
package com.inovex.main.controller.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

import com.inovex.main.entity.Category;
import com.inovex.main.entity.Product;
import com.inovex.main.json.response.ProductPriceCalResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.BrandModelService;
import com.inovex.main.service.CategoryService;
import com.inovex.main.service.MeasurementUnitService;
import com.inovex.main.service.ProductCategoryService;
import com.inovex.main.service.ProductService;
import com.inovex.main.service.SecondaryInventoryService;
import com.inovex.main.service.SupplierService;

@RestController

@RequestMapping("/api")
public class ProductRestController {

	@Autowired
	BrandModelService brandService;

	@Autowired
	ProductService productService;

	@Autowired
	ProductCategoryService productCategory;

	@Autowired
	SupplierService supplierService;

	@Autowired
	MeasurementUnitService measurementUnitService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	SecondaryInventoryService secondaryInventoryService;

	@Autowired
	OrganizationRepository orgRepo;

//	@GetMapping("/product")
//	public List<ProductResponse> getAllProducts(HttpServletRequest request) {
//		List<ProductResponse> productRes = new ArrayList<ProductResponse>();
//
//		Set<Product> productList = new HashSet<Product>();
//		Long orgId = Long.parseLong("295");
//		Optional<Organization> org = orgRepo.findById(orgId);
//		if (org.isPresent()) {
//			try {
//				productList = org.get().getProducts();
//				for (Product product2 : productList) {
//					ProductResponse res = new ProductResponse();
//					res.setId(product2.getId());
//					res.setProductName(product2.getProductName());
//					res.setSku(product2.getSku());
//					res.setProductLabel(product2.getProductLabel());
//
//					Optional<Category> cat = productCategory.findById(product2.getProductCategory());
//					if (cat.isPresent()) {
//						res.setProductCategoryName(cat.get().getName());
//					} else {
//						res.setProductCategoryName("not found!");
//					}
//					res.setShortDiscription(product2.getShortDiscription());
//
//					Optional<MeasurementUnit> mesure = measurementUnitService
//							.findById(Long.parseLong(product2.getMesuringType()));
//					if (mesure.isPresent()) {
//						res.setMesuringType(mesure.get().getName());
//					} else {
//						res.setMesuringType("not found!");
//					}
//
//					res.setProductPrice(product2.getProductPrice());
//					res.setProductMrpPrice(product2.getProductMrpPrice());
//					res.setStartingStock(product2.getStartingStock());
//					res.setSafetyStock(product2.getSafetyStock());
//					res.setDiscountType(product2.getDiscountType());
//					res.setAvailableDiscount(product2.getAvailableDiscount());
//					res.setAvailableOffer(product2.getAvailableOffer());
//					res.setProductImage(product2.getProductImage());
//					Optional<Supplier> supp = supplierService.findById(product2.getSupplierId());
//					if (supp.isPresent()) {
//						res.setSupplier(supp.get().getName());
//					} else {
//						res.setSupplier("not found!");
//					}
//					Optional<BrandModel> brand = brandService.findById(product2.getBrandId());
//					if (brand.isPresent()) {
//						res.setBrandName(brand.get().getBrandName());
//					} else {
//						res.setBrandName("not found!");
//					}
//
//					productRes.add(res);
//				}
//			}
//
//			catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return productRes;
//	}

	@PostMapping("/product")
	public ResponseData createProduct(@RequestBody Product product, HttpServletRequest request) {
		ResponseData responseData = new ResponseData();

		try {
			Product prc = productService.save(product, request);
			responseData.setData(prc);
			responseData.setStatusCode(201);
			responseData.setMessage("Product created successfully");

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

	@GetMapping("/product/{id}")
	public ResponseData getProduct(@PathVariable Long id) {
		ResponseData responseData = new ResponseData();
		try {
			Product prc = productService.getProduct(id);
			responseData.setData(prc);
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

	@DeleteMapping("/delete-product/{id}")
	public ResponseData deleteProduct(@PathVariable Long id, HttpServletRequest request) {

		ResponseData responseData = new ResponseData();
		try {
			productService.deleteById(id, request);
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

	@PutMapping("/product/{id}")
	public ResponseData updateProduct(@RequestBody Product product, @PathVariable Long id, HttpServletRequest request) {

		ResponseData responseData = new ResponseData();
		try {
			Product prc = productService.update(product, id, request);
			responseData.setStatusCode(200);
			responseData.setMessage("update successfully");
			responseData.setData(prc);
			return responseData;
		} catch (Exception e) {
			responseData.setData(null);
			responseData.setStatusCode(500);
			responseData.setMessage(e.getMessage());
			return responseData;
		}
	}

	@GetMapping("/product-by-category/{id}")
	public List<Product> getAllProductsByCat(@PathVariable long id, HttpServletRequest request) {
		List<Product> product = new ArrayList<Product>();
		try {
			product = productService.getproductById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}

	@GetMapping("/product-by-categoryName/{category}")
	public List<Product> getAllProductsByCat(@PathVariable String category, HttpServletRequest request) {
		List<Product> product = new ArrayList<Product>();
		try {
			Optional<Category> catId = categoryService.findByName(category);
			if (catId.isPresent()) {
				product = productService.getproductById(catId.get().getId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}

//	@RequestMapping(value = "/product-server-side", method = RequestMethod.POST)
//	public String getDailySalary(@RequestParam("start") int start, @RequestParam("length") int length,
//
//			@RequestParam("search[value]") String searchParam) {
//
//		ArrayList<Object> product = productService.getPagination(start, length, searchParam);
//		JSONArray main = new JSONArray();
//		JSONArray obj1 = new JSONArray();
//
//		Iterator itr = product.iterator();
//		while (itr.hasNext()) {
//			obj1 = new JSONArray();
//			Object[] obj = (Object[]) itr.next();
//			obj1.put(String.valueOf(obj[0]));
//			obj1.put(String.valueOf(obj[1]));
//			obj1.put(String.valueOf(obj[2]));
//			obj1.put(0);
//			obj1.put(0);
//			main.put(obj1);
//		}
//		JSONObject responseObj = new JSONObject();
//		Long totalLength = productService.getCountWithSearchParm(searchParam);
//		responseObj.put("data", main);
//		responseObj.put("recordsTotal", totalLength);
//		responseObj.put("recordsFiltered", totalLength);
//		return responseObj.toString();
//	}

	@GetMapping("/get-product-value/{id}/{quantity}")
	public ProductPriceCalResponse getProductValu(@PathVariable Long id, @PathVariable Long quantity) {
		ProductPriceCalResponse res = new ProductPriceCalResponse();
		try {
			long value = 0;
			Product prc = productService.getProduct(id);
			if (prc != null) {
				long stockquantity = secondaryInventoryService.getProductQuantity(id);
				if (stockquantity < quantity) {
					res.setStatusCode(203);
					res.setMessage("Delivery quantity exceeds stock.\n The current stock is :  " + stockquantity);
				} else {
					res.setStatusCode(200);
				}
				value = quantity * prc.getProductMrpPrice();
				res.setTotalPrice(value);
				if (prc.isDiscount()) {
					if (prc.getDiscountType().equals("BDT")) {
						if (!prc.getAvailableDiscount().equals(null) && !prc.getAvailableDiscount().equals("")) {
							long discountedPrice = value - Long.parseLong(prc.getAvailableDiscount());
							res.setDiscounted(discountedPrice);
						} else {
							res.setDiscounted(value);
						}
					} else {
						if (!prc.getAvailableDiscount().equals(null) && !prc.getAvailableDiscount().equals("")) {
							long calculateDiscount = (value * (Long.parseLong(prc.getAvailableDiscount()))) / 100;
							long discountedPrice = value - calculateDiscount;
							res.setDiscounted(discountedPrice);
						} else {
							res.setDiscounted(value);
						}
					}
				} else {
					res.setDiscounted(value);
				}

			} else {
				res.setStatusCode(204);
				res.setMessage("Product not found!");
			}

		} catch (Exception e) {
			res.setStatusCode(500);
			res.setMessage(e.getMessage());
		}
		return res;
	}
}
