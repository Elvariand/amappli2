package isika.p3.amappli.repo.amap;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import isika.p3.amappli.entities.product.Product;
import isika.p3.amappli.entities.tenancy.Tenancy;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("SELECT c FROM Product c WHERE c.tenancy.tenancyAlias = :tenancyAlias")
	List<Product> findByTenancyAlias(@Param("tenancyAlias") String tenancyAlias);
    List<Product> findByTenancy(Tenancy tenancy);
    
    @Query(value = "SELECT id FROM products ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Long findRandomIdNative();

    default Product findRandom() {
        Long randomId = findRandomIdNative();
        return randomId != null ? findById(randomId).orElse(null) : null;
    }

}
