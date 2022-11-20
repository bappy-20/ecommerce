package com.inovex.main.service;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.ReturnProduct;

public interface ReturnProductService {
	
	ReturnProduct save(ReturnProduct entity, HttpServletRequest request);

}
