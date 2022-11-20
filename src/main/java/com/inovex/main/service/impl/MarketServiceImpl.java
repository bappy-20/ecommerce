package com.inovex.main.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.Market;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.TerritoryModel;
import com.inovex.main.entity.User;
import com.inovex.main.repository.MarketRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.TerritoryRepository;
import com.inovex.main.repository.UserRepository;
import com.inovex.main.service.MarketService;

@Service
@Transactional
public class MarketServiceImpl implements MarketService {

	@Autowired
	MarketRepo mktRepo;
	@Autowired
	TerritoryRepository teriRepo;
	@Autowired
	OrganizationRepository orgRepo;
	@Autowired
	UserRepository userRepo;

	@Override
	public Optional<Market> findById(Long id) {
		// TODO Auto-generated method stub
		return mktRepo.findById(id);
	}

	@Override
	public List<Market> findAll() {
		// TODO Auto-generated method stub
		return mktRepo.findAll();
	}

	@Override
	public Market getMarket(Long id) {
		Optional<Market> market = mktRepo.findById(id);
		if (market.isPresent())
			return market.get();
		throw new NotFoundException();
	}

	@Override
	public Market save(Market mkt, HttpServletRequest request) {
		Market market = new Market();
		if (request.getSession().getAttribute("orgId") != null) {
			long id = (long) request.getSession().getAttribute("orgId");

			Optional<Organization> org = orgRepo.findById(id);
			Set<Market> mktList = new HashSet<>();
			Set<Market> mktList1 = new HashSet<>();
			if (org.isPresent()) {
				mktList1 = org.get().getMkt();
				Optional<TerritoryModel> ter = teriRepo.findById(mkt.getTerritoryId());
				if (ter.isPresent()) {
					mktList = ter.get().getMarkets();
					mkt.setActive(true);
					mkt.setCreatedAt(new Date());
					mkt.setUpdatedAt(new Date());
					mkt.setCreatedBy((long) request.getSession().getAttribute("userId"));
					mktList.add(mkt);
					ter.get().setMarkets(mktList);
					market = mktRepo.save(mkt);
					teriRepo.save(ter.get());
					mktList1.add(market);
					org.get().setMkt(mktList1);
					orgRepo.save(org.get());

				}
			}
		}

		return market;
	}

	@Override
	public void deleteById(Long id, HttpServletRequest request) {
		// TODO Auto-generated method stub

		if (request.getSession().getAttribute("orgId") != null) {
			long orgid = (long) request.getSession().getAttribute("orgId");
			
			Optional<Organization> org = orgRepo.findById(orgid);
			if (org.isPresent()) {
				mktRepo.deleteFromUserMarket(id);
				mktRepo.deleteFromMarketReail(id);
				mktRepo.deleteFromOrg(orgid, id);
				mktRepo.deleteRef(id);
				mktRepo.deleteById(id);
				
			}

		}

		
	}

	@Override
	public Market update(Market market, Long id, HttpServletRequest request) {
		if (!id.equals(market.getId())) {

			throw new NotFoundException("Market not found");
		}
		Optional<Market> marketUpdate = mktRepo.findById(id);
		marketUpdate.get().setMarketName(market.getMarketName());
		if (marketUpdate.get().getTerritoryId() != 0
				&& marketUpdate.get().getTerritoryId() != market.getTerritoryId()) {
			throw new NotFoundException("Market already assigned in a Territory.Please delete and re-create");
		}

		marketUpdate.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
		marketUpdate.get().setAddress(market.getAddress());
		marketUpdate.get().setTerritoryId(market.getTerritoryId());
		mktRepo.save(marketUpdate.get());
		return marketUpdate.get();

	}

	@Override
	public void saveUserMapping(List<Long> mktId, Long id) {
		Optional<User> user = userRepo.findById(id);
		Set<Market> list = new HashSet<>();
		Set<User> userList = new HashSet<>();
		if (user.isPresent()) {
			for (Long long1 : mktId) {
				Optional<Market> area = mktRepo.findById(long1);
				if (area.isPresent()) {
					list.add(area.get());
					userList = area.get().getUsers();
					userList.add(user.get());
					area.get().setCustomUser(userList);
					mktRepo.save(area.get());

				}
			}
			user.get().setCustomMarket(list);
			userRepo.save(user.get());
		}

	}

}
