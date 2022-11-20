package com.inovex.main.service;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.CurrentLocation;

public interface CurrentLocationService {
	
	CurrentLocation save(CurrentLocation entity, HttpServletRequest request);

}
