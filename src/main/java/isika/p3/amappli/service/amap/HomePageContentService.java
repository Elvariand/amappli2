package isika.p3.amappli.service.amap;

import java.util.List;
import java.util.Optional;

import isika.p3.amappli.entities.tenancy.HomePageContent;

public interface HomePageContentService {
	

    HomePageContent createHomePageContent(HomePageContent homePageContent);


    Optional<HomePageContent> getHomePageContentById(Long id);

    
    List<HomePageContent> getAllHomePageContents();

    
    HomePageContent updateHomePageContent(Long id, HomePageContent homePageContent);

    
    void deleteHomePageContent(Long id);

    
    HomePageContent getCurrentHomePageContent();

    HomePageContent updateHomePageTitle(Long homePageContentId, String newSubTitle);
    
    
    void updateShowSuppliers(Long id, Boolean showSuppliers);

}
