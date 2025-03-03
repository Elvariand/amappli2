package isika.p3.amappli.entities.tenancy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentBlock {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentBlockId;

    private boolean isValue;

    private String contentTitle;

    @Column(length = 1000)
    private String contentText;

    private String contentImgName;

    private String contentImgTypeMIME;

    @Lob
    private String contentImg;

    @ManyToOne
    @JoinColumn( name = "homePageContentId")
    private HomePageContent homePageContent;


}
