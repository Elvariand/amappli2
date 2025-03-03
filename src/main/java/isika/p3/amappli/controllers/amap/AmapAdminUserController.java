package isika.p3.amappli.controllers.amap;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import isika.p3.amappli.dto.amap.UpdateUserDTO;
import isika.p3.amappli.dto.amap.UserDTO;
import isika.p3.amappli.entities.user.User;
import isika.p3.amappli.service.amap.AmapAdminUserService;
import isika.p3.amappli.service.amap.GraphismService;
import isika.p3.amappli.service.amap.RoleService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@PreAuthorize("hasAuthority('gestion utilisateurs amap') and (hasAuthority(#tenancyAlias) or hasAuthority('gestion plateforme'))")
@Controller
@RequestMapping("amap/{tenancyAlias}/admin")
public class AmapAdminUserController {
    
	@Autowired
	private AmapAdminUserService adminUserService;
	
    @Autowired
    private final RoleService roleService;
    
    @Autowired
    private final GraphismService graphismService;
	
	public AmapAdminUserController(AmapAdminUserService adminUserService, RoleService roleService, GraphismService graphismService) {
		this.adminUserService = adminUserService;
		this.roleService = roleService;
		this.graphismService = graphismService;
	}

	@GetMapping("/users/list")
	public String usersList(Model model, @PathVariable("tenancyAlias") String tenancyAlias) {
		List<User> users = adminUserService.findAll(tenancyAlias);
		model.addAttribute("users", users);
		model.addAttribute("tenancyAlias", tenancyAlias);
		graphismService.setUpModel(tenancyAlias, model);
		return "amap/back/users/users-list";
	}
	
	@GetMapping("/users/details/{userId}")
	public String usersDetails(@PathVariable("userId") Long userId, Model model, @PathVariable("tenancyAlias") String tenancyAlias, @ModelAttribute("message") String message) {
		User user = adminUserService.findById(userId);
		model.addAttribute("user", model.containsAttribute("userDTO")? model.getAttribute("userDTO") : user);
		model.addAttribute("tenancyAlias", tenancyAlias);
		model.addAttribute("dateEnd", user.getMembershipFee() != null ? user.getMembershipFee().getDateEnd().format(DateTimeFormatter.ofPattern("dd MMM yyyy")) : "Aucune cotisation effectuée");
		model.addAttribute("allRoles" , this.roleService.findAmapRoles(tenancyAlias));
		graphismService.setUpModel(tenancyAlias, model);
		return "amap/back/users/users-details";
	}
	
	@PostMapping("/users/delete/{userId}")
	public String usersHide(@PathVariable("userId") Long userId) {
		adminUserService.hideById(userId);
		return "redirect:../list";
	}
	
	@GetMapping("/suppliers/list")
	public String suppliersList(Model model, @PathVariable("tenancyAlias") String tenancyAlias) {
		List<User> suppliers = adminUserService.findSuppliers(tenancyAlias);
		model.addAttribute("suppliers", suppliers);
		model.addAttribute("tenancyAlias", tenancyAlias);
		graphismService.setUpModel(tenancyAlias, model);
		return "amap/back/users/suppliers-list";
	}
	

	@PostMapping("/users/update")
	public String usersUpdate(@Valid @ModelAttribute("user") UpdateUserDTO user, BindingResult result, Model model, RedirectAttributes ra, @PathVariable("tenancyAlias") String tenancyAlias) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<UpdateUserDTO>> violations = validator.validate(user);
		for (ConstraintViolation<UpdateUserDTO> violation : violations) 
		{
			String path = violation.getPropertyPath() + "";
			if (path.contains(".")) {
				ra.addFlashAttribute(path.substring(path.indexOf('.') +1), violation.getMessage());
			} else {
				ra.addFlashAttribute(path, violation.getMessage());
			}
		} 
		
		if (result.hasErrors()) {
			ra.addFlashAttribute("message", "Le formulaire n'a pas été correctement rempli");
			ra.addFlashAttribute("userDTO", user);
			return "redirect:details/" + user.getUserId();
		}
		
		adminUserService.updateUser(user);
		return "redirect:list";
	}
	
	@GetMapping("/users/form")
	public String usersForm(Model model, @PathVariable("tenancyAlias") String tenancyAlias) {
		model.addAttribute("user", model.containsAttribute("userDTO")? model.getAttribute("userDTO") : new UserDTO());
		model.addAttribute("tenancyAlias", tenancyAlias);
		model.addAttribute("allRoles" , this.roleService.findAmapRoles(tenancyAlias));
		graphismService.setUpModel(tenancyAlias, model);
		return "amap/back/users/users-form";
	}


	@GetMapping("/suppliers/form")
	public String suppliersForm(Model model, @PathVariable("tenancyAlias") String tenancyAlias) {
		model.addAttribute("user", model.containsAttribute("userDTO")? model.getAttribute("userDTO") : new UserDTO());
		model.addAttribute("tenancyAlias", tenancyAlias);
		model.addAttribute("allRoles" , this.roleService.findAmapRoles(tenancyAlias));
		graphismService.setUpModel(tenancyAlias, model);
		return "amap/back/users/suppliers-form";
	}
	
	@PostMapping("/users/add")
	public String UsersAdd(@ModelAttribute("user") UserDTO user, @PathVariable("tenancyAlias") String tenancyAlias, BindingResult result, Model model, RedirectAttributes ra) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);
		for (ConstraintViolation<UserDTO> violation : violations) 
        {
			String path = violation.getPropertyPath() + "Error";
            if (path.contains(".")) {
            	ra.addFlashAttribute(path.substring(path.indexOf('.') +1), violation.getMessage());
			} else {
				ra.addFlashAttribute(path, violation.getMessage());
			}
        }
			
		if (violations.size() > 0) {
			ra.addFlashAttribute("message", "Le formulaire n'a pas été correctement rempli");
			ra.addFlashAttribute("userDTO", user);
			return "redirect:form";
		}
	    adminUserService.addTenancyUser(user, tenancyAlias);
	    return "redirect:list";
	}
	
	@PostMapping("/suppliers/add")
	public String SuppliersAdd(@ModelAttribute("user") UserDTO user, @PathVariable("tenancyAlias") String tenancyAlias, BindingResult result, Model model, RedirectAttributes ra) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);
		for (ConstraintViolation<UserDTO> violation : violations) 
		{
			String path = violation.getPropertyPath() + "Error";
			if (path.contains(".")) {
				ra.addFlashAttribute(path.substring(path.indexOf('.') +1), violation.getMessage());
			} else {
				ra.addFlashAttribute(path, violation.getMessage());
			}
		}
		
		if (violations.size() > 0) {
			ra.addFlashAttribute("message", "Le formulaire n'a pas été correctement rempli");
			ra.addFlashAttribute("userDTO", user);
			return "redirect:form";
		}
		adminUserService.addTenancyUser(user, tenancyAlias);
		return "redirect:list";
	}
	

	@GetMapping("/suppliers/delete/{userId}")
	public String suppliersHide(@PathVariable("userId") Long userId, @PathVariable("tenancyAlias") String tenancyAlias) {
		adminUserService.hideById(userId);
		return "redirect:../list";
	}
	
	@GetMapping("/suppliers/details/{userId}")
	public String suppliersDetails(@PathVariable("userId") Long userId, Model model, @PathVariable("tenancyAlias") String tenancyAlias) {
	    User supplier = adminUserService.findById(userId);
	    model.addAttribute("user", supplier);
		model.addAttribute("dateEnd", supplier.getMembershipFee() != null ? supplier.getMembershipFee().getDateEnd().format(DateTimeFormatter.ofPattern("dd MMM yyyy")) : "Aucune cotisation effectuée");
		model.addAttribute("tenancyAlias", tenancyAlias);
		model.addAttribute("allRoles" , this.roleService.findAmapRoles(tenancyAlias));
		graphismService.setUpModel(tenancyAlias, model);
	    return "amap/back/users/suppliers-details";
	}

	@PostMapping("/suppliers/update")
	public String suppliersUpdate(@Valid @ModelAttribute("supplier") UpdateUserDTO supplier, BindingResult result, Model model, RedirectAttributes ra) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<UpdateUserDTO>> violations = validator.validate(supplier);
		for (ConstraintViolation<UpdateUserDTO> violation : violations) 
        {
			String path = violation.getPropertyPath() + "Error";
            if (path.contains(".")) {
            	ra.addFlashAttribute(path.substring(path.indexOf('.') +1), violation.getMessage());
			} else {
				ra.addFlashAttribute(path, violation.getMessage());
			}
        } 
			
		if (result.hasErrors()) {
			ra.addFlashAttribute("message", "Le formulaire n'a pas été correctement rempli");
			ra.addFlashAttribute("userDTO", supplier);
			return "redirect:details/" + supplier.getUserId();
		}
		
		adminUserService.updateUser(supplier);
		return "redirect:list";
	}
}
