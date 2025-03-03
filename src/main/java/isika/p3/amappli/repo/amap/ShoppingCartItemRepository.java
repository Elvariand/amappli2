package isika.p3.amappli.repo.amap;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import isika.p3.amappli.entities.order.Shoppable;
import isika.p3.amappli.entities.order.ShoppingCartItem;
import jakarta.transaction.Transactional;

@Repository
public interface ShoppingCartItemRepository extends CrudRepository<ShoppingCartItem, Long>{

	@Modifying
	@Transactional
	@Query("DELETE FROM ShoppingCartItem i WHERE i.shoppable = :shoppable")
	void deleteByShoppable(@Param("shoppable") Shoppable shoppable);
	void deleteByShoppableId(Long shoppableId);

}
