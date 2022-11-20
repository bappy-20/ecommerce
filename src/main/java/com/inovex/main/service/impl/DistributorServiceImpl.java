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

import com.inovex.main.entity.Distributor;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.DistributorRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.DistributorService;

@Service
@Transactional
public class DistributorServiceImpl implements DistributorService {

    @Autowired
    DistributorRepo distributorRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<Distributor> findById(Long id) {
        // TODO Auto-generated method stub
        return distributorRepo.findById(id);
    }

    @Override
    public List<Distributor> findAll() {
        // TODO Auto-generated method stub
        return distributorRepo.findAll();
    }

    @Override
    public Distributor getDistributor(Long id) {
        Optional<Distributor> distributor = distributorRepo.findById(id);
        if (distributor.isPresent())
            return distributor.get();
        throw new NotFoundException();
    }

    @Override
    public long findDistByUserId(long employeeId) {
        // TODO Auto-generated method stub
        return distributorRepo.findDistByUserId(employeeId);
    }

    @Override
    public void deleteById(Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        if (request.getSession().getAttribute("orgId") != null) {
            long orgid = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgid);
            if (org.isPresent()) {
                distributorRepo.deleteFromOrg(orgid, id);
                distributorRepo.deleteById(id);
            } else {
                System.out.println("org not found");
            }
        }
    }

    @Override
    public Distributor update(Distributor distributor, Long id, HttpServletRequest request) {
        if (!id.equals(distributor.getId())) {

            throw new NotFoundException("Distributor not found");
        }
        Optional<Distributor> distributorUpdate = distributorRepo.findById(id);
        distributorUpdate.get().setDistributorName(distributor.getDistributorName());
        distributorUpdate.get().setDistributorAddress(distributor.getDistributorAddress());
        distributorUpdate.get().setNid(distributor.getNid());
        distributorUpdate.get().setDistributorOwner(distributor.getDistributorOwner());
        distributorUpdate.get().setDistributorPhone(distributor.getDistributorPhone());
        distributorUpdate.get().setDistributorPhone(distributor.getDistributorPhone());
        distributorUpdate.get().setDistributorCredit(distributor.getDistributorCredit());
        distributorUpdate.get().setTradeImage(distributor.getTradeImage());
        distributorUpdate.get().setUpdatedAt(new Date());
        distributorUpdate.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
        distributorRepo.save(distributorUpdate.get());
        return distributorUpdate.get();

    }

    @Override
    public Distributor save(Distributor entity, HttpServletRequest request) {
        Distributor distributor = new Distributor();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");

            Optional<Organization> org = orgRepo.findById(id);
            Set<Distributor> list = new HashSet<Distributor>();
            if (org.isPresent()) {
                list = org.get().getDistributors();
                entity.setCreatedAt(new Date());
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                distributor = distributorRepo.save(entity);
                list.add(distributor);
                org.get().setDistributors(list);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("Org is Null");
        }
        return distributor;
    }

	@Override
	public Long findDistributorIdByUserId(long userId) {
		// TODO Auto-generated method stub
		return distributorRepo.getDistributorIdByUserId(userId);
	}
    
//    @Override
//    public List<Boolean> getUserRight(Set<Menu> menu, HttpServletRequest request, String rolemanagement) {
//        // TODO Auto-generated method stub
//        String role = (String) request.getSession().getAttribute("role");
//        List<Boolean> list = new ArrayList<>();
//        Optional<Menu> matchingObject = menu.stream().filter(p -> p.getRoleName().equals(role)).findFirst();
//        if (matchingObject.isPresent()) {
//            Optional<SubMenu> subMenu = matchingObject.get().getSubMenu().stream()
//                    .filter(p -> p.getSubMenuName().equals(rolemanagement)).findFirst();
//            if (subMenu.isPresent()) {
//                boolean canEdit = subMenu.get().isCanEdit();
//                boolean canDelete = subMenu.get().isCanDelete();
//                list.add(canEdit);
//                list.add(canDelete);
//            } else {
//                list.add(true);
//                list.add(true);
//            }
//
//        } else {
//            list.add(false);
//            list.add(false);
//        }
//
//        return list;
//    }

//	@Override
//	public List<Boolean> getUserRight(Set<Distributor> distributor, HttpServletRequest request, String rolemanagement) {
//		// TODO Auto-generated method stub
//		 String role = (String) request.getSession().getAttribute("role");
//         List<Boolean> list = new ArrayList<>();
//         Optional<Distributor> matchingObject = distributor.stream().filter(p ->p.getDistributorName().equals(role)).findFirst();
//         if (matchingObject.isPresent()) {
//           Optional<SubMenu> subMenu = matchingObject.get().getSubMenu().stream()
//                   .filter(p -> p.getSubMenuName().equals(rolemanagement)).findFirst();
//           if (subMenu.isPresent()) {
//               boolean canEdit = subMenu.get().isCanEdit();
//               boolean canDelete = subMenu.get().isCanDelete();
//               list.add(canEdit);
//               list.add(canDelete);
//           } else {
//               list.add(true);
//               list.add(true);
//           }
//
//       } else {
//           list.add(false);
//           list.add(false);
//       }
//
//       return list;
//	}
}
