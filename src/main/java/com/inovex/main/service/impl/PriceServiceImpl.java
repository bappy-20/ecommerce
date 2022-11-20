package com.inovex.main.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.Organization;
import com.inovex.main.entity.Price;
import com.inovex.main.entity.PriceUpdateHistory;
import com.inovex.main.entity.ProductMapping;
import com.inovex.main.json.response.PriceResponse;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.PriceRepo;
import com.inovex.main.repository.PriceUpdateRepo;
import com.inovex.main.repository.ProductRepo;
import com.inovex.main.service.PriceService;
import com.inovex.main.service.ProductMappingService;

@Service
@Transactional
public class PriceServiceImpl implements PriceService {

	@Autowired
	PriceRepo pricRepo;
	@Autowired
	PriceUpdateRepo priceUpdateRepo;
	@Autowired
	ProductRepo proRepo;
	@Autowired
	OrganizationRepository orgRepo;
	@Autowired
	ProductMappingService prdMapppingService;

	@Override
	public Optional<Price> findById(Long id) {
		// TODO Auto-generated method stub
		return pricRepo.findById(id);
	}

	@Override
	public List<Price> findAll() {
		// TODO Auto-generated method stub
		return pricRepo.findAll();
	}

	@Override
	public Price getPrice(Long id) {
		Optional<Price> price = pricRepo.findById(id);
		if (price.isPresent()) {
			return price.get();
		} else {
			return null;
		}
	}

	@Override
	public Price save(Price entity, HttpServletRequest request) {
		Price price = new Price();
		// Price price = null;
		if (request.getSession().getAttribute("orgId") != null) {
			long id = (long) request.getSession().getAttribute("orgId");

			Optional<Organization> org = orgRepo.findById(id);
			Set<Price> list = new HashSet<Price>();
			if (org.isPresent()) {
				Optional<Price> productPrice = pricRepo.findByProductId(entity.getProductId());
				if (productPrice.isPresent()) {
					productPrice.get().setProductPrice(entity.getProductPrice());
					productPrice.get().setMrp(entity.getMrp());
					productPrice.get().setRetailPrice(entity.getRetailPrice());
					productPrice.get().setDealerPrice(entity.getDealerPrice());
					productPrice.get().setUpdatedAt(new Date());
					productPrice.get().setUpdatedBy((long) request.getSession().getAttribute("userId"));
					price = pricRepo.save(productPrice.get());
					if (price != null) {
						PriceUpdateHistory priceUpdate = new PriceUpdateHistory();
						priceUpdate.setProductId(entity.getProductId());
						priceUpdate.setProductPrice(entity.getProductPrice());
						priceUpdate.setMrp(entity.getMrp());
						priceUpdate.setRetailPrice(entity.getRetailPrice());
						priceUpdate.setDealerPrice(entity.getDealerPrice());
						priceUpdate.setCreatedAt(new Date());
						priceUpdate.setCreatedBy((long) request.getSession().getAttribute("userId"));
						priceUpdate.setUpdatedAt(new Date());
						priceUpdate.setUpdateDate(new Date());
						priceUpdateRepo.save(priceUpdate);
					}

				} else {
					entity.setActive(true);
					entity.setCreatedAt(new Date());
					entity.setUpdatedAt(new Date());
					entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
					price = pricRepo.save(entity);
					if (price != null) {
						PriceUpdateHistory priceUpdate = new PriceUpdateHistory();
						priceUpdate.setProductId(entity.getProductId());
						priceUpdate.setProductPrice(entity.getProductPrice());
						priceUpdate.setMrp(entity.getMrp());
						priceUpdate.setRetailPrice(entity.getRetailPrice());
						priceUpdate.setDealerPrice(entity.getDealerPrice());
						priceUpdate.setCreatedAt(new Date());
						priceUpdate.setCreatedBy(1);
						priceUpdate.setUpdatedAt(new Date());
						priceUpdate.setUpdateDate(new Date());
						priceUpdateRepo.save(priceUpdate);
					}
					list = org.get().getPrice();
					list.add(entity);
					org.get().setPrice(list);
					orgRepo.save(org.get());
				}

			}
		}
		return price;

	}

	@Override
	public List<Price> saveAll(List<Price> entities) {
		// TODO Auto-generated method stub
		return pricRepo.saveAll(entities);
	}

	@Override
	public void deleteById(Long id, HttpServletRequest request) {
		if (request.getSession().getAttribute("orgId") != null) {
			long orgId = (long) request.getSession().getAttribute("orgId");
			pricRepo.deleteFromOrg(orgId, id);
			pricRepo.deleteById(id);
		}

	}

	@Override
	public Price update(Price price, Long id, HttpServletRequest request) {
		Optional<Price> priceUpdate = pricRepo.findByProductId(price.getProductId());	
		if (priceUpdate.isPresent()) {
			priceUpdate.get().setProductId(price.getProductId());
			priceUpdate.get().setProductPrice(price.getProductPrice());
			priceUpdate.get().setDealerPrice(price.getDealerPrice());
			priceUpdate.get().setRetailPrice(price.getRetailPrice());
			priceUpdate.get().setMrp(price.getMrp());
			priceUpdate.get().setUpdatedBy((long) request.getSession().getAttribute("userId"));
			priceUpdate.get().setUpdatedAt(new Date());
			pricRepo.save(priceUpdate.get());

			PriceUpdateHistory priceUpdate1 = new PriceUpdateHistory();
			priceUpdate1.setProductId(price.getProductId());
			priceUpdate1.setProductPrice(price.getProductPrice());
			priceUpdate1.setMrp(price.getMrp());
			priceUpdate1.setRetailPrice(price.getRetailPrice());
			priceUpdate1.setDealerPrice(price.getDealerPrice());
			priceUpdate1.setCreatedAt(new Date());
			priceUpdate1.setCreatedBy((long) request.getSession().getAttribute("userId"));
			priceUpdate1.setUpdatedAt(new Date());
			priceUpdate1.setUpdateDate(new Date());
			priceUpdateRepo.save(priceUpdate1);

		}
		return priceUpdate.get();

	}

	@Override
	public Optional<Price> findByProductId(Long productId) {
		// TODO Auto-generated method stub
		return pricRepo.findByProductId(productId);
	}

	@Override
	public PriceResponse getPrice1(Long id) {
		// TODO Auto-generated method stub

		PriceResponse res = new PriceResponse();
		try {
			Optional<Price> price = pricRepo.findById(id);
			if (price.isPresent()) {
				// return price.get();
				res.setId(price.get().getId());
				res.setProductId(price.get().getProductId());
				long productid = price.get().getProductId();
				// System.out.println(productid);
				Optional<ProductMapping> prdMapping = prdMapppingService.findById(productid);
				if (prdMapping.isPresent()) {
					res.setProductName(prdMapping.get().getProductName());
				}
				res.setProductPrice(price.get().getProductPrice());
				res.setDealerPrice(price.get().getDealerPrice());
				res.setRetailPrice(price.get().getRetailPrice());
				res.setMrp(price.get().getMrp());
				//res.setC
				return res;
			} else {
				return null;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return res;
	}
}
