package com.inovex.main.controller.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovex.main.json.response.CollectionResponse;
import com.inovex.main.service.CollectionService;

@RestController
@RequestMapping("/api")
public class CollectionRestController {
	@Autowired 
	CollectionService collectionService;
	
	@GetMapping("/collection-date-range/{date1}/{date2}")
	public List<CollectionResponse> getAllCollectionByDateRange(HttpServletRequest request ,@PathVariable String date1, @PathVariable String date2){
		
		List<Object> collectionList = new ArrayList<Object>();
		List<CollectionResponse> responseList = new ArrayList<CollectionResponse>();
		try {
			System.out.println(date1);
			System.out.println(date2);
			
			//list = collectionService.findAllByDateRange(request, date1, date2);
			collectionList = collectionService.findAllByDateRange(request, date1, date2);
			Iterator itr = collectionList.iterator();
			while (itr.hasNext()) {
				CollectionResponse cr = new CollectionResponse();
				Object[] obj = (Object[]) itr.next();
				cr.setOrderId(String.valueOf(obj[0]));
				cr.setRecieveAmount(Long.parseLong(String.valueOf(obj[1])));
				cr.setDueAmount(Long.parseLong(String.valueOf(obj[2])));
				cr.setTotal(Long.parseLong(String.valueOf(obj[3])));
				cr.setCollectionDate(String.valueOf(obj[4]));
				responseList.add(cr);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return responseList;
		
	}

}
