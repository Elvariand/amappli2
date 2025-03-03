package isika.p3.amappli.service.amap.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import isika.p3.amappli.entities.user.Address;
import isika.p3.amappli.entities.user.CompanyDetails;
import isika.p3.amappli.entities.user.ContactInfo;
import isika.p3.amappli.entities.user.User;
import isika.p3.amappli.exceptions.EmailAlreadyExistsException;
import isika.p3.amappli.exceptions.TenancyNotFoundException;
import isika.p3.amappli.dto.amap.UpdateUserDTO;
import isika.p3.amappli.dto.amap.UserDTO;
import isika.p3.amappli.entities.auth.Role;
import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.service.amap.AddressService;
import isika.p3.amappli.service.amap.AmapAdminUserService;
import isika.p3.amappli.service.amap.CompanyDetailsService;
import isika.p3.amappli.service.amap.ContactInfoService;
import isika.p3.amappli.service.amap.RoleService;
import isika.p3.amappli.service.amap.UserService;
import isika.p3.amappli.service.amappli.TenancyService;

@Service
public class AmapAdminUserServiceImpl implements AmapAdminUserService {

	@Autowired
	private final UserService userService;
	
	@Autowired
	private final AddressService addressService;
	
	@Autowired
	private final ContactInfoService contactInfoService;
	
	@Autowired
	private final CompanyDetailsService companyDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final TenancyService tenancyService;
    
    @Autowired
    private final RoleService roleService;
    
	public AmapAdminUserServiceImpl(UserService userService, AddressService addressService,
			ContactInfoService contactInfoService, CompanyDetailsService companyDetailsService,
			TenancyService tenancyService, RoleService roleService) {
		this.userService = userService;
		this.addressService = addressService;
		this.contactInfoService = contactInfoService;
		this.companyDetailsService = companyDetailsService;
		this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		this.tenancyService = tenancyService;
		this.roleService = roleService;
	}
	
	@Override
	public User addTenancyUser(UserDTO userDTO, String tenancyAlias) {
		// Vérifiez si la tenancy existe
		Tenancy tenancy = tenancyService.getTenancyByAlias(tenancyAlias);
		if (tenancy == null) {
			throw new TenancyNotFoundException("Tenancy introuvable avec l'Alias : " + tenancyAlias);
		}
		// Vérifiez si l'email existe déjà pour cette tenancy
		if (userService.existsByEmailAndTenancy(userDTO.getEmail(), tenancy)) {
			throw new EmailAlreadyExistsException("Un utilisateur avec cet email existe déjà pour cette AMAP.");
		}
		
		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setCreditBalance(BigDecimal.ZERO);
		
		if (userDTO.getRoles() != null) {
			Set<Role> newRoles = new HashSet<>();
			for (Long roleID : userDTO.getRoles()) {
				newRoles.add(roleService.findById(roleID));
			}
			user.setRoles(newRoles);
		}
		
		// Encoder le mot de passe
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		
		// Associer l'utilisateur à la tenancy
		user.setTenancy(tenancy);
		user.setActive(true); // Activation par défaut
		
		this.saveUser(user);
		
		Address address = new Address();
		address.setLine1(userDTO.getAddress().getLine1());
		address.setLine2(userDTO.getAddress().getLine2());
		address.setPostCode(userDTO.getAddress().getPostCode());
		address.setCity(userDTO.getAddress().getCity());
		address.setUser(user);
		address = this.addressService.save(address);
		
		CompanyDetails cd = new CompanyDetails();
		cd.setCompanyName(userDTO.getCompanyDetails().getCompanyName());
		cd.setSiretNumber(userDTO.getCompanyDetails().getSiretNumber());
		cd.setUser(user);
		cd = this.companyDetailsService.save(cd);
		
		ContactInfo ci = new ContactInfo();
		ci.setFirstName(userDTO.getContactInfo().getFirstName());
		ci.setName(userDTO.getContactInfo().getName());
		ci.setPhoneNumber(userDTO.getContactInfo().getPhoneNumber());
		ci.setUser(user);
		ci = this.contactInfoService.save(ci);
		
		// Convertir UserDTO en entité User
		user.setAddress(address);
		user.setContactInfo(ci);
		user.setCompanyDetails(cd);
		
		
		// Sauvegarder l'utilisateur
		return this.saveUser(user);
	}
	
    @Override
    public User saveUser(User user) {
        userService.saveUser(user);
        return user;
    }

	@Override
	public void hideUser(User user) {
		user.setActive(false);
		userService.saveUser(user);
	}

	@Override
	public void hideById(Long userId) {
		this.hideUser(this.findById(userId));
	}

	@Override
	public List<User> findAll(Long tenancyId) {
		return ((List<User>) userService.findAll()).stream()
				.filter(u -> tenancyId == (u.getTenancy() != null ? u.getTenancy().getTenancyId() : 0)
				&& u.isActive())
				.sorted((u1,u2)->u2.getUserId().compareTo(u1.getUserId()))
				.toList();
	}

	@Override
	public List<User> findAll(String tenancyAlias) {
		return ((List<User>) userService.findAll()).stream()
				.filter(u -> tenancyAlias.equals(u.getTenancy() != null ? u.getTenancy().getTenancyAlias() : null)
						&& u.isActive())
				.sorted((u1,u2)->u2.getUserId().compareTo(u1.getUserId()))
				.toList();
	}

	@Override
	public List<User> findSuppliers(String tenancyAlias) {
		return ((List<User>) userService.findAll()).stream()
				.filter(u -> tenancyAlias.equals(u.getTenancy() != null ? u.getTenancy().getTenancyAlias() : null) 
						&& u.getRoles().contains(roleService.findByName("Producteur"))
						&& u.isActive())
				.sorted((u1,u2)->u2.getUserId().compareTo(u1.getUserId()))
				.toList();
	}

	@Override
	public List<User> findSuppliers(Long tenancyId) {
		return ((List<User>) userService.findAll()).stream()
				.filter(u -> tenancyId == (u.getTenancy() != null ? u.getTenancy().getTenancyId() : 0) 
						&& u.getRoles().contains(roleService.findByName("Producteur"))
						&& u.isActive()
						&& u.getCompanyDetails() != null
						&& u.getCompanyDetails().getCompanyName() != null)
				.sorted((u1,u2)->u2.getUserId().compareTo(u1.getUserId()))
				.toList();
	}

	@Override
	public User findById(Long userId) {
		return userService.findById(userId);
	}


	@Override
	public User updateUser(UpdateUserDTO updatedUserDTO) {
		User oldUser = this.findById(updatedUserDTO.getUserId());
		if (oldUser == null) {
			throw new IllegalArgumentException("Le user avec l'ID " + updatedUserDTO.getUserId() + " n'existe pas.");
		}
		
		oldUser.getAddress().setLine1((updatedUserDTO.getAddress().getLine1()));
		oldUser.getAddress().setLine2((updatedUserDTO.getAddress().getLine2()));
		oldUser.getAddress().setPostCode((updatedUserDTO.getAddress().getPostCode()));
		oldUser.getAddress().setCity((updatedUserDTO.getAddress().getCity()));
		oldUser.getContactInfo().setName((updatedUserDTO.getContactInfo().getName()));
		oldUser.getContactInfo().setFirstName((updatedUserDTO.getContactInfo().getFirstName()));
		oldUser.getContactInfo().setPhoneNumber((updatedUserDTO.getContactInfo().getPhoneNumber()));
		oldUser.getCompanyDetails().setCompanyName((updatedUserDTO.getCompanyDetails().getCompanyName()));
		oldUser.getCompanyDetails().setSiretNumber((updatedUserDTO.getCompanyDetails().getSiretNumber()));
		oldUser.setCreditBalance(updatedUserDTO.getCreditBalance());
		oldUser.setEmail(updatedUserDTO.getEmail());
		oldUser.setCreditBalance(updatedUserDTO.getCreditBalance());

		if (updatedUserDTO.getRoles() != null) {
			Set<Role> newRoles = new HashSet<>();
			for (Long roleID : updatedUserDTO.getRoles()) {
				newRoles.add(roleService.findById(roleID));
			}
			oldUser.setRoles(newRoles);
		}
		
		this.saveUser(oldUser);
		
		return oldUser;
	}

}
