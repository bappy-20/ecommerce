package com.inovex.main.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.ProductSubCategory;

@Repository
public interface ProductSubCategoryRepo extends JpaRepository<ProductSubCategory, Long> {

    Optional<ProductSubCategory> findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "delete from organizations_product_sub_category where organizations_product_sub_category.organization_id = ?1 "
            + "and organizations_product_sub_category.product_sub_category_id= ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long catId);

    @Transactional
    @Modifying
    @Query(value = "delete from category_product_sub_category where category_product_sub_category.product_sub_category_id = ?1 ", nativeQuery = true)
    public int deleteFromCategory(long subCatId);
}
