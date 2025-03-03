package isika.p3.amappli.entities.tenancy;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
public class Graphism {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long graphismId;

    @Enumerated(EnumType.STRING)
    private ColorPalette colorPalette;

    @Enumerated(EnumType.STRING)
    private FontChoice fontChoice;

    private String logoImgType;

    @Lob
    private String logoImg;

    @OneToOne
    @JoinColumn(name = "tenancyId")
    private Tenancy tenancy;
}
