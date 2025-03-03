package isika.p3.amappli.repo.amap;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import isika.p3.amappli.entities.auth.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{

	Role findByName(String name);

	@Query("SELECT r FROM Role r WHERE r.tenancy IS NULL AND r.roleId <> 1")
	List<Role> findByTenancyIsNullExceptRoleOne();

	List<Role> findByTenancyTenancyAlias(String tenancyAlias);
}
