package isika.p3.amappli.service.amap;

import java.util.List;

import isika.p3.amappli.dto.amappli.RoleDTO;
import isika.p3.amappli.entities.auth.Role;

public interface RoleService {

	Role findByName(String name);
	
	Role findById(Long roleId);

	List<Role> findAllRoles();

	List<Role> findAllGenericRoles();

	List<Role> findAmapExclusiveRoles(String tenancyAlias);

	List<Role> findAmapRoles(String TenancyAlias);

	Role createRole(Role role);

	void deleteRole(Long roleId);
	
    void addtestRoles(); 

	void manageRoleFromRoleManagmentPage(RoleDTO roleDTO, String alias);
}
