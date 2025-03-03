package isika.p3.amappli.service.amap.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isika.p3.amappli.entities.tenancy.HomePageContent;
import isika.p3.amappli.repo.amap.HomePageContentRepository;
import isika.p3.amappli.service.amap.HomePageContentService;

@Service
public class HomePageContentServiceImpl implements HomePageContentService {
	
	@Autowired
    private HomePageContentRepository homePageContentRepository;

    @Override
    public HomePageContent createHomePageContent(HomePageContent homePageContent) {
        return homePageContentRepository.save(homePageContent);
    }

    
    @Override
    public Optional<HomePageContent> getHomePageContentById(Long id) {
        return homePageContentRepository.findById(id);
        
    }


    @Override
    public List<HomePageContent> getAllHomePageContents() {
        return (List<HomePageContent>) homePageContentRepository.findAll();
    }

   
    @Override
    public HomePageContent updateHomePageContent(Long id, HomePageContent homePageContent) {
        if (homePageContentRepository.existsById(id)) {
            homePageContent.setHomePageContentId(id);  
            return homePageContentRepository.save(homePageContent);
        }
        return null; 
    }

  
    @Override
    public void deleteHomePageContent(Long id) {
        if (homePageContentRepository.existsById(id)) {
            homePageContentRepository.deleteById(id);
        }
    }

   
    @Override
    public HomePageContent getCurrentHomePageContent() {
       
        return ((Collection<HomePageContent>) homePageContentRepository.findAll()).stream().findFirst().orElse(null); 
    }

    
    @Override
    public HomePageContent updateHomePageTitle(Long homePageContentId, String newSubTitle) {
        HomePageContent homePageContent = homePageContentRepository.findById(homePageContentId).orElse(null);

        if (homePageContent != null) {
            homePageContent.setSubTitle(newSubTitle);
            return homePageContentRepository.save(homePageContent);  
        } else {
            
            return null; 
        }
    }
    
    
    // Mettre à jour le statut d'affichage des fournisseurs
    @Override
    public void updateShowSuppliers(Long id, Boolean showSuppliers) {
        Optional<HomePageContent> homePageContentOpt = homePageContentRepository.findById(id);
        if (homePageContentOpt.isPresent()) {
            HomePageContent homePageContent = homePageContentOpt.get();
            homePageContent.setShowSuppliers(showSuppliers);
            homePageContentRepository.save(homePageContent);
        }
    }

	
	

}
