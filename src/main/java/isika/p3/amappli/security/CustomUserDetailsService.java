package isika.p3.amappli.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import isika.p3.amappli.entities.auth.Permission;
import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.entities.user.User;
import isika.p3.amappli.repo.amap.UserRepository;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("Utilisateur non trouvé avec l'email: "+ username);
        }
       
        // If the user is found, get his permissions
        CustomUserDetails securityUser = null;
        securityUser = new CustomUserDetails.Builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .authorities(getGrantedAuthorities(user.getPermissions(), user.getTenancy()))
                    .build();
      
        securityUser.addAdditionalInfo("userId",user.getUserId());
        if(user.getContactInfo() != null){
            securityUser.addAdditionalInfo("firstName",user.getContactInfo().getFirstName());
        }
        if(user.getTenancy() != null){
            securityUser.addAdditionalInfo("tenancyAlias",user.getTenancy().getTenancyAlias());
        }
        return securityUser;

    }

    // Transform our permissions and tenancy membership into spring security authorities
    private List<GrantedAuthority> getGrantedAuthorities(Set<Permission> permissions, Tenancy tenancy){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(Permission p: permissions){
            authorities.add(new SimpleGrantedAuthority(p.getName()));
        }
        if(tenancy != null){
            authorities.add(new SimpleGrantedAuthority(tenancy.getTenancyAlias()));
        }
        return authorities;
    }

    
}
