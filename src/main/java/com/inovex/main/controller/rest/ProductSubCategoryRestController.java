package com.inovex.main.controller.rest;

import java.util.HashSet;
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

import com.inovex.main.entity.Category;
import com.inovex.main.entity.ProductSubCategory;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.service.CategoryService;
import com.inovex.main.service.ProductSubCategoryService;

@RestController
@RequestMapping("/api")
public class ProductSubCategoryRestController {

    @Autowired
    ProductSubCategoryService subCategoryService;
    @Autowired
    CategoryService catService;

    @GetMapping("/sub-category")
    public Set<ProductSubCategory> getAllCategorys(HttpServletRequest request) {
        Set<ProductSubCategory> categoryList = new HashSet<ProductSubCategory>();

        if (request.getSession().getAttribute("cateId") != null) {
            long cateId = (long) request.getSession().getAttribute("cateId");
            Optional<Category> org = catService.findById(cateId);
            if (org.isPresent()) {
                try {
                    categoryList = org.get().getProductSubCategory();
                } catch (Exception e) {

                }
            }
        }

        return categoryList;
    }

    @PostMapping("/sub-category")
    public ResponseData createCategory(@RequestBody ProductSubCategory category, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            ProductSubCategory ctg = subCategoryService.save(category, request);
            responseData.setData(ctg);
            responseData.setStatusCode(201);
            responseData.setMessage("Category created successfully");

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

    @GetMapping("/sub-category/{id}")
    public ResponseData getCategory(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            ProductSubCategory ctg = subCategoryService.getCategory(id);
            responseData.setData(ctg);
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

    @DeleteMapping("/sub-category/{id}")
    public ResponseData deleteCategory(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            subCategoryService.deleteById(id, request);
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

    @PutMapping("/sub-category/{id}")
    public ResponseData updateCategory(@RequestBody ProductSubCategory category, @PathVariable Long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            ProductSubCategory ctg = subCategoryService.update(category, id, request);
            responseData.setStatusCode(200);
            responseData.setMessage("update successfully");
            responseData.setData(ctg);
            return responseData;
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setStatusCode(500);
            responseData.setMessage(e.getMessage());
            return responseData;
        }
    }

}
