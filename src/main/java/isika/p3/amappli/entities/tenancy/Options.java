package isika.p3.amappli.entities.tenancy;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Options {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long optionsId;

    private Boolean option1Active;

    private Boolean option2Active;

    // @OneToMany(targetEntity = Tenancy.class, mappedBy = "options", cascade = CascadeType.ALL)
    // private Set<Tenancy> tenancies;

    // public void addTenancy(Tenancy tenancy) {
    // 	this.tenancies.add(tenancy);
    // }
}
