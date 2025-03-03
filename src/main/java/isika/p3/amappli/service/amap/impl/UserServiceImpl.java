package isika.p3.amappli.service.amap.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import isika.p3.amappli.dto.amap.NewUserDTO;

import isika.p3.amappli.dto.amap.UpdateProfileDTO;
import isika.p3.amappli.dto.amap.UserDTO;
import isika.p3.amappli.entities.auth.Role;
import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.entities.user.Address;
import isika.p3.amappli.entities.user.ContactInfo;
import isika.p3.amappli.entities.user.User;
import isika.p3.amappli.exceptions.EmailAlreadyExistsException;
import isika.p3.amappli.exceptions.TenancyNotFoundException;
import isika.p3.amappli.repo.amap.RoleRepository;
import isika.p3.amappli.repo.amap.UserRepository;
import isika.p3.amappli.service.amap.UserService;
import isika.p3.amappli.service.amappli.TenancyService;
import jakarta.transaction.Transactional;

@Service
@Primary
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TenancyService tenancyService;
    
    private final RoleRepository roleRepository;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TenancyService tenancyService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tenancyService = tenancyService;
        this.roleRepository = roleRepository;
    }
    
    
    @Transactional
    public User addTenancyUser(UserDTO userDTO, Long tenancyId) {
        // Vérifiez si la tenancy existe
        Tenancy tenancy = tenancyService.getTenancyById(tenancyId);
        if (tenancy == null) {
            throw new TenancyNotFoundException("Tenancy introuvable avec l'ID : " + tenancyId);
        }
        // Vérifiez si l'email existe déjà pour cette tenancy
        if (this.existsByEmailAndTenancy(userDTO.getEmail(), tenancy)) {
            throw new EmailAlreadyExistsException("Un utilisateur avec cet email existe déjà pour cette AMAP.");
        }

        // Convertir UserDTO en entité User
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setCreditBalance(userDTO.getCreditBalance() != null ? userDTO.getCreditBalance() : BigDecimal.ZERO);
        user.setAddress(userDTO.getAddress()); // Si address est déjà une entité
        user.setContactInfo(userDTO.getContactInfo()); // Idem pour contactInfo

        // Encoder le mot de passe
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Associer l'utilisateur à la tenancy
        user.setTenancy(tenancy);
        user.setActive(true); // Activation par défaut

        // Sauvegarder l'utilisateur
        return userRepository.save(user);
    }
    
    public User authenticateUser(String email, String password) throws RuntimeException {
        
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            throw new RuntimeException("Email ou mot de passe incorrect.");
        }
    }

    @Transactional
    public void addPlatformUser(NewUserDTO newUserDTO) throws EmailAlreadyExistsException{
        try {
            // Get info from DTO into new User
            User user = new User();
            BeanUtils.copyProperties(newUserDTO,user);
            // Nested properties are not copied by BeanUtils
            user.setAddress(newUserDTO.getAddress());
            user.setContactInfo(newUserDTO.getContactInfo());

            // Get the role for AMAP Admin
            Role admin = roleRepository.findByName("ADMIN");
            user.getRoles().add(admin);
            // At first, the platform user has no tenancy space
            user.setTenancy(null);
            // The user is active, he's only deactivated if there's a problem
            user.setActive(true);
            // The user has 0 credits by default
            user.setCreditBalance(new BigDecimal(0));
            saveUser(user); //encoding done inside
        }
        catch (RuntimeException e){
            throw new EmailAlreadyExistsException("Cet email est déjà utilisé.");
        }
        
    }
    
    @Override
    public boolean existsByEmailAndTenancy(String email, Tenancy tenancy) {
    	return this.userRepository.existsByEmailAndTenancy(email, tenancy);
    }
    
    @Override
    public User updateUser(User user){
        return userRepository.save(user);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    @Override
    public List<User> findAll() {
    	return (List<User>) userRepository.findAll();
    }

	@Override
	public User findById(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}
	
	public User findUserWithMembership(Long userId) {
		return userRepository.findUserWithMembership(userId);
	}
	
	 public UserDTO getUserProfile(Long userId) {
	        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));
	        UserDTO userDTO = new UserDTO();
	        userDTO.setEmail(user.getEmail());
	        userDTO.setContactInfo(user.getContactInfo());
	        userDTO.setAddress(user.getAddress());
	        return userDTO;
	    }
	 
	 
	 public void updateUserProfile(Long userId, UpdateProfileDTO updateProfileDTO) {
		    // Récupérer l'utilisateur existant
		    User user = userRepository.findById(userId)
		                               .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

		  
		    user.setEmail(updateProfileDTO.getEmail());

		    // Récupérer les objets existants
		    ContactInfo existingContactInfo = user.getContactInfo();
		    Address existingAddress = user.getAddress();

		   
		    if (existingContactInfo != null && updateProfileDTO.getContactInfo() != null) {
		        existingContactInfo.setName(updateProfileDTO.getContactInfo().getName());
		        existingContactInfo.setFirstName(updateProfileDTO.getContactInfo().getFirstName());
		        existingContactInfo.setPhoneNumber(updateProfileDTO.getContactInfo().getPhoneNumber());
		    }

		   
		    if (existingAddress != null && updateProfileDTO.getAddress() != null) {
		        existingAddress.setLine1(updateProfileDTO.getAddress().getLine1());
		        existingAddress.setLine2(updateProfileDTO.getAddress().getLine2());
		        existingAddress.setPostCode(updateProfileDTO.getAddress().getPostCode());
		        existingAddress.setCity(updateProfileDTO.getAddress().getCity());
		    }

		    
		    userRepository.save(user);
		}


	



	
	
	
}
