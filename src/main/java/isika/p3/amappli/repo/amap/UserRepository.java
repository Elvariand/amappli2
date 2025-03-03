package isika.p3.amappli.repo.amap;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.entities.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query("SELECT u FROM User u LEFT JOIN FETCH u.roles LEFT JOIN FETCH u.tenancy WHERE u.email=:email")
	User findByEmail(@Param("email")String email);

	boolean existsByEmailAndTenancy(String email, Tenancy tenancy);

	@Query("SELECT u FROM User u LEFT JOIN FETCH u.orders WHERE u.id = :userId")
    User findUserWithOrders(@Param("userId") Long userId);
	
    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.tenancy = :tenancy AND r.name = :roleName")
    List<User> findByTenancyAndRole(@Param("tenancy") Tenancy tenancy, @Param("roleName") String roleName);

	List<User> findByTenancy_TenancyId(Long tenancyId);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.membershipFee WHERE u.id = :userId")
	User findUserWithMembership(@Param("userId") Long userId);
	
    

}
