package isika.p3.amappli.entities.tenancy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import isika.p3.amappli.entities.auth.Role;
import isika.p3.amappli.entities.user.Address;
import isika.p3.amappli.entities.user.ContactInfo;
import isika.p3.amappli.entities.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tenancy {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tenancyId;

    @Column(nullable = false)
    private String tenancyName;

    private String tenancySlogan;
    
    private String email;

    @Column(nullable=false, unique =true ) // For url mapping
    private String tenancyAlias;

    private String tenancyLatitude;

    private String tenancyLongitude;

    @Embedded
    private PickUpSchedule pickUpSchedule;
    
    private BigDecimal membershipFeePrice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId")
    @EqualsAndHashCode.Exclude
    private Address address;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contactInfoId")
    private ContactInfo contactInfo;
    
    private LocalDateTime dateCreated;

    private LocalDateTime dateLastModified;

    @OneToMany(targetEntity = User.class, mappedBy = "tenancy", cascade = CascadeType.ALL)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<User> users = new HashSet<>();
    
    @OneToMany(targetEntity = Role.class, mappedBy = "tenancy", cascade = CascadeType.ALL)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Role> roles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Graphism graphism;

    @Embedded
    @EqualsAndHashCode.Exclude
    private Options options;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "homePageContentId")
    private HomePageContent homePageContent;

    @OneToOne
    @JoinColumn( name = "serviceSubscriptionId")
    private ServiceSubscription serviceSubscription;

}
