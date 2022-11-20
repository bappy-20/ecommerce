package com.inovex.main.repository;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.ProductModel;

@Repository
public interface ProductModelRepo extends JpaRepository<ProductModel, Long> {

    @Query(value = "SELECT product_mapping.id,product_mapping.product_name,category.name ,organizations_product_mapping.product_mapping_id"
            + " FROM organizations_product_mapping,product_mapping inner join "
            + "category on product_mapping.product_category=category.id where "
            + "organizations_product_mapping.product_mapping_id=product_mapping.id"
            + " and organizations_product_mapping.organization_id=?3 LIMIT ?1,?2", nativeQuery = true)
    public ArrayList<Object> getPagination(int start, int length, long orgId);

    @Query(value = "SELECT product_mapping.id,product_mapping.product_name,category.name"
            + " FROM organizations_product_mapping, product_mapping "
            + " inner join category on product_mapping.product_category=category.id " + " WHERE "
            + " (product_mapping.id like ?1%" + " OR product_mapping.product_name like ?1%"
            + " OR category.name like ?1%  )"
            + "and organizations_product_mapping.product_mapping_id=product_mapping.id"
            + "and organizations_product_mapping.organization_id=?4 LIMIT ?2,?3", nativeQuery = true)
    public ArrayList<Object> getPaginationWithSerachParam(String query, int start, int length, long orgId);

    @Query(value = "select count(*) from organizations_product_mapping, product_mapping "
            + " inner join category on product_mapping.product_category=category.id " + " WHERE "
            + " (product_mapping.id like ?1%" + " OR product_mapping.product_name like ?1%"
            + " OR category.name like ?1% )"
            + " and organizations_product_mapping.product_mapping_id=product_mapping.id"
            + " and organizations_product_mapping.organization_id=?2", nativeQuery = true)
    public Long countBySearchParam(String searchParam, long orgId);

    @Query(value = "select count(*) from organizations_product_mapping,product_mapping where "
            + " organizations_product_mapping.product_mapping_id=product_mapping.id"
            + " and organizations_product_mapping.organization_id=?1", nativeQuery = true)
    public long count(long orgId);

    @Transactional
    @Modifying
    @Query(value = "delete from organizations_product_model where organizations_product_model.organization_id =?1 and organizations_product_model.product_model_id = ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long productId);

}
