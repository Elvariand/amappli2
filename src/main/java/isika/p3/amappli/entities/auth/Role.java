package isika.p3.amappli.entities.auth;

import java.util.HashSet;
import java.util.Set;

import isika.p3.amappli.entities.tenancy.Tenancy;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@EqualsAndHashCode
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    
    private String name;
    
    private boolean hidden;
    
    @ManyToOne
    @JoinColumn( name = "tenancyId")
    private Tenancy tenancy;

    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable (
        name = "Role_Permission_Association",
        joinColumns = @JoinColumn(name = "roleId"),
        inverseJoinColumns = @JoinColumn( name = "permissionId")
    )
    @Builder.Default
    private Set<Permission> permissions = new HashSet<Permission>();
    
}
