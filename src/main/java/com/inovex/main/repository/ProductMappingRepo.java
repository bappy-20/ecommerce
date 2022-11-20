package com.inovex.main.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inovex.main.entity.ProductMapping;

@Repository
public interface ProductMappingRepo extends JpaRepository<ProductMapping, Long> {

    Optional<ProductMapping> findByProductId(Long productId);

    @Query(value = "SELECT a.product_id,a.on_hand, b.product_name,b.sku,b.product_label,"
            + "b.product_category,b.mesuring_type,b.product_price,b.product_mrp_price,"
            + "b.discount,b.discount_type,b.available_discount,b.offer,b.available_offer,"
            + "b.product_image,b.brand_id,b.supplier_id,b.short_description ,b.safety_stock"
            + " ,b.starting_stock FROM " + "secondary_inventory a,product b "
            + "WHERE  a.product_id=b.id", nativeQuery = true)
    List<Object[]> getProductDetails(long disId);

    @Query("SELECT l FROM ProductMapping l where l.productCategory = :productCategory")
    public List<ProductMapping> getproductById(@Param("productCategory") long productCategory);

    @Query(value = "SELECT product.id,product.product_name,category.name "
            + " FROM product inner join category on product.product_category=category.id LIMIT ?1,?2", nativeQuery = true)
    public ArrayList<Object> getPagination(int start, int length);

    @Query(value = "SELECT product.id,product.product_name,category.name FROM product "
            + " inner join category on product.product_category=category.id " + " WHERE " + " product.id like ?1%"
            + " OR product.product_name like ?1%" + " OR category.name like ?1%  " + " LIMIT ?2,?3", nativeQuery = true)
    public ArrayList<Object> getPaginationWithSerachParam(String query, int start, int length);

    @Query(value = "select count(*) from product " + " inner join category on product.product_category=category.id "
            + " WHERE " + " product.id like ?1%" + " OR product.product_name like ?1%"
            + " OR category.name like ?1% ", nativeQuery = true)
    public Long countBySearchParam(String searchParam);

    @Query(value = "select count(*) from product", nativeQuery = true)
    public long count();

    @Transactional
    @Modifying
    @Query(value = "delete FROM organizations_product_mapping where organization_id = ?1 and product_mapping_id = ?2", nativeQuery = true)
    public int deleteFromOrg(long orgId, long ProductMapppingId);
}
