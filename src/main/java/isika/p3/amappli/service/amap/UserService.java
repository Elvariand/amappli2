package isika.p3.amappli.service.amap;

import java.util.List;

import isika.p3.amappli.dto.amap.NewUserDTO;
import isika.p3.amappli.dto.amap.UpdateProfileDTO;
import isika.p3.amappli.dto.amap.UserDTO;
import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.entities.user.User;
import jakarta.validation.Valid;

public interface UserService {

	void addPlatformUser(NewUserDTO newUserDTO);
	User addTenancyUser(UserDTO userDTO, Long tenancyId);
	User updateUser(User user);
	User saveUser(User user);
	User findById(Long userId);
	List<User> findAll();
	User authenticateUser(String email, String password);
	boolean existsByEmailAndTenancy(String email, Tenancy tenancy);
	UserDTO getUserProfile(Long userId);
	void updateUserProfile(Long userId, @Valid UpdateProfileDTO updateProfileDTO);
	User findUserWithMembership(Long userId);
	
	
}
