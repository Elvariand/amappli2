package isika.p3.amappli.controllers.amap;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import isika.p3.amappli.dto.amappli.RoleDTO;
import isika.p3.amappli.entities.auth.Permission;
import isika.p3.amappli.entities.auth.Role;
import isika.p3.amappli.entities.tenancy.ColorPalette;
import isika.p3.amappli.entities.tenancy.FontChoice;
import isika.p3.amappli.entities.user.User;
import isika.p3.amappli.repo.amappli.PermissionRepository;
import isika.p3.amappli.security.CustomUserDetails;
import isika.p3.amappli.service.amap.GraphismService;
import isika.p3.amappli.service.amap.RoleService;
import isika.p3.amappli.service.amap.UserService;

@Controller
public class RoleController {

	private final RoleService roleService;

	private final PermissionRepository permissionRepository;

	private final UserService userService;

	private final GraphismService graphismService;


	public RoleController(RoleService roleService, PermissionRepository permissionRepository, UserService userService, GraphismService graphismService) {
		this.roleService = roleService;
		this.permissionRepository = permissionRepository;
		this.userService = userService;
		this.graphismService = graphismService;
	}

	@PreAuthorize("hasAuthority('gestion plateforme')")
	@GetMapping("/amappli/roles/manage")
	public String showGeneralRolesWithPermissions(Model model) {

		// Loading existing roles and permissions
		List<Role> roles = roleService.findAllGenericRoles();
		model.addAttribute("roles", roles);
		Iterable<Permission> permissions = permissionRepository.findAll();
        model.addAttribute("permissions", permissions);

		// Creating role/permission map because we can't check if an item is in a set with the jsp tags
		Map<Long, Set<Long>> rolePermissionsMap = new HashMap<>();
        for (Role role : roles) {
            Set<Long> permissionIds = role.getPermissions().stream()
                .map(Permission::getPermissionId)
                .collect(Collectors.toSet());
            rolePermissionsMap.put(role.getRoleId(), permissionIds);
        }
		model.addAttribute("rolePermissionsMap", rolePermissionsMap);
		// Adding an empty DTO for the form
		model.addAttribute("roleDTO", new RoleDTO());
		model.addAttribute("amappli", true);

		// Graphism
		model.addAttribute("latitude", 42.1880896);
		model.addAttribute("longitude",  9.0684138);
		model.addAttribute("mapStyleLight", "mapbox://styles/tiroirmorgane/cm4sw37wr001301s12frm2l2y");
        model.addAttribute("mapStyleDark", "mapbox://styles/tiroirmorgane/cm52cqefg003101sa878udky6");
        model.addAttribute("cssStyle", ColorPalette.PALETTE1);
        model.addAttribute("font", FontChoice.FUTURA);

		return "/amappli/back/roles/rolesmanagement";
	}

	@PreAuthorize("hasAuthority('gestion plateforme')")
	@PostMapping("/amappli/roles/manage")
	public String editGeneralRolesWithPermissions(RoleDTO roleDTO) {
		
		//Role r = Role.builder().name(name).permissions().build();
		roleService.manageRoleFromRoleManagmentPage(roleDTO,"amappli");
		return "redirect:/amappli/roles/manage";
	}

	@PreAuthorize("hasAuthority('gestion utilisateurs amap') and (hasAuthority(#alias) or hasAuthority('gestion plateforme'))")
	@GetMapping("/amap/{tenancyAlias}/roles/manage")
	public String showRolesWithPermissions(@PathVariable("tenancyAlias") String alias, Model model) {

		// Get logged user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails loggedUserInfo = (CustomUserDetails) authentication.getPrincipal();

		User user = userService.findById((Long) loggedUserInfo.getAdditionalInfoByKey("userId"));

		// The roles that can't be modified
		List<Role> rolesNoModif = roleService.findAllGenericRoles();
		model.addAttribute("rolesNoModif",rolesNoModif);
		// Get applicable roles and permissions
		List<Role> roles = roleService.findAmapExclusiveRoles(alias);
		model.addAttribute("roles", roles);
		// An user can only manage permissions he already has himself
		Collection<Permission> permissionsToManage = user.getPermissions();
	
		model.addAttribute("permissionsToManage", permissionsToManage);

		//Creating role/permission map because we can't check if an item is in a set with the jsp tags
		// for the default roles
		Map<Long, Set<Long>> roleNoModifPermissionsMap = new HashMap<>();
        for (Role role : rolesNoModif) {
            Set<Long> permissionIds = role.getPermissions().stream()
                .map(Permission::getPermissionId)
				.filter( permissionId -> permissionsToManage.stream().anyMatch(p -> p.getPermissionId().equals(permissionId)))
                .collect(Collectors.toSet());
            roleNoModifPermissionsMap.put(role.getRoleId(), permissionIds);
        }
		model.addAttribute("roleNoModifPermissionsMap", roleNoModifPermissionsMap);

		// for the roles to manage
		Map<Long, Set<Long>> rolePermissionsMap = new HashMap<>();
        for (Role role : roles) {
            Set<Long> permissionIds = role.getPermissions().stream()
                .map(Permission::getPermissionId)
				.filter( permissionId -> permissionsToManage.stream().anyMatch(p -> p.getPermissionId().equals(permissionId)))
                .collect(Collectors.toSet());
            rolePermissionsMap.put(role.getRoleId(), permissionIds);
        }
		model.addAttribute("rolePermissionsMap", rolePermissionsMap);

		// Graphisme
		graphismService.setUpModel(alias, model);
		// Adding an empty DTO for the form
		model.addAttribute("roleDTO", new RoleDTO());
		model.addAttribute("amappli", false);
		model.addAttribute("tenancyAlias",alias);
		return "/amappli/back/roles/rolesmanagement";

	}

	@PreAuthorize("hasAuthority('gestion utilisateurs amap') and (hasAuthority(#alias) or hasAuthority('gestion plateforme'))")
	@PostMapping("/amap/{tenancyAlias}/roles/manage")
	public String editRolesWithPermissions(@PathVariable("tenancyAlias") String alias, RoleDTO roleDTO) {
		
		//Role r = Role.builder().name(name).permissions().build();
		roleService.manageRoleFromRoleManagmentPage(roleDTO, alias);
		return "redirect:/amap/"+alias+"/roles/manage";
	}
}
