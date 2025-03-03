package isika.p3.amappli.entities.tenancy;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomePageContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long homePageContentId;

    private String subTitle;

    private Boolean showSuppliers;

    @OneToMany( targetEntity = ContentBlock.class, mappedBy = "homePageContent", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Builder.Default
    private List<ContentBlock> contents = new ArrayList<ContentBlock>();

    @OneToOne
    @JoinColumn(name = "tenancyId")
    private Tenancy tenancy;
}
