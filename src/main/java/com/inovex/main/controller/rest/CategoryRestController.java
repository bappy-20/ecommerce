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

import com.inovex.main.entity.Category;
import com.inovex.main.entity.Menu;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.ProductSubCategory;
import com.inovex.main.json.response.CategoryResponse;
import com.inovex.main.json.response.ResponseData;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.CategoryService;
import com.inovex.main.service.MenuService;

@RestController
@RequestMapping("/api")
public class CategoryRestController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MenuService menuService;

    @GetMapping("/category")
    public Set<Category> getAllCategorys(HttpServletRequest request) {
        Set<Category> categoryList = new HashSet<Category>();
        Long orgId = Long.parseLong("295");
        Optional<Organization> org = orgRepo.findById(orgId);
        if (org.isPresent()) {
            try {
                categoryList = org.get().getCategories();
            } catch (Exception e) {

            }
        }
        return categoryList;
    }

    @PostMapping("/category")
    public ResponseData createCategory(@RequestBody Category category, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();

        try {
            Category ctg = categoryService.save(category, request);
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

    @GetMapping("/category/{id}")
    public ResponseData getCategory(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Category ctg = categoryService.getCategory(id);
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

    @DeleteMapping("/delete-category/{id}")
    public ResponseData deleteCategory(@PathVariable Long id, HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            categoryService.deleteById(id, request);
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

    @PutMapping("/category/{id}")
    public ResponseData updateCategory(@RequestBody Category category, @PathVariable Long id,
            HttpServletRequest request) {

        ResponseData responseData = new ResponseData();
        try {
            Category ctg = categoryService.update(category, id, request);
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

    @GetMapping("/get-sub-category/{id}")
    public Set<ProductSubCategory> getSubCategory(@PathVariable Long id) {
        Set<ProductSubCategory> list = new HashSet<>();
        try {
            Optional<Category> ctg = categoryService.findById(id);
            if (ctg.isPresent()) {
                list = ctg.get().getProductSubCategory();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @GetMapping("/category-list1")
    public List<CategoryResponse> getAllCategoryListRole(HttpServletRequest request) {
        Set<Menu> menu = new HashSet<Menu>();
        List<CategoryResponse> response = new ArrayList<CategoryResponse>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            try {
                if (org.isPresent()) {
                    menu = org.get().getMenu();
                    String rolemanagement = "productCategory";
                    List<Boolean> rights = menuService.getUserRight(menu, request, rolemanagement);
                    for (Category mn : org.get().getCategories()) {
                        CategoryResponse res = new CategoryResponse();
                        res.setId(mn.getId());
                        res.setName(mn.getName());
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

}
