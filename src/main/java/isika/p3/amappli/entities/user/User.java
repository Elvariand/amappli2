package isika.p3.amappli.entities.user;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import isika.p3.amappli.entities.auth.Permission;
import isika.p3.amappli.entities.auth.Role;
import isika.p3.amappli.entities.membership.MembershipFee;
import isika.p3.amappli.entities.order.Order;
import isika.p3.amappli.entities.order.ShoppingCart;
import isika.p3.amappli.entities.tenancy.Tenancy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
	
	@NotBlank(message = "L'email est obligatoire.")
	@Size(max=70, message = "L'email doit faire 70 caractères maximum.")
    @Email( message = "L'email est invalide.")
    @Column(nullable = false, unique = true)
    private String email;
	
	@NotBlank(message = "Champ obligatoire.")
    @Column(nullable = false)
    private String password;
    

    private BigDecimal creditBalance;

    @OneToOne(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private ContactInfo contactInfo;

    private boolean isActive;

    @ManyToOne
    @JoinColumn( name = "tenancyId")
    @EqualsAndHashCode.Exclude
    private Tenancy tenancy;
    
    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (
        name = "User_Role_Association",
        joinColumns = @JoinColumn(name = "userId"),
        inverseJoinColumns = @JoinColumn( name = "roleId")
    )

    @EqualsAndHashCode.Exclude
    private Set<Role> roles = new HashSet<Role>();
    
    @OneToOne(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private CompanyDetails companyDetails;
    
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "shoppingCartId")
	@EqualsAndHashCode.Exclude
	private ShoppingCart shoppingCart;
	
	@Builder.Default
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Order> orders = new ArrayList<Order>();
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private MembershipFee membershipFee;
	
    public Set<Permission> getPermissions(){
        Set<Permission> permissions = new HashSet<Permission>();
        for(Role r: roles){
            for(Permission p: r.getPermissions()){
                permissions.add(p);
            }
        }
        return permissions;
    }
    
    public boolean isMembershipFeeValid() {
        LocalDate today = LocalDate.now();
        LocalDate endDate = this.membershipFee.getDateEnd();
        return endDate.isAfter(today) || endDate.isEqual(today);
    }

    
}
