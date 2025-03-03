package isika.p3.amappli.repo.amap;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import isika.p3.amappli.entities.order.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long>{
	
	@Query("SELECT sc FROM ShoppingCart sc WHERE sc.user.id = :userId")
    ShoppingCart findByUserId(@Param("userId") Long userId);

}
