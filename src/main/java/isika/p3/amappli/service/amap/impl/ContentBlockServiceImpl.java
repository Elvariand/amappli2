package isika.p3.amappli.service.amap.impl;

import org.springframework.stereotype.Service;

import isika.p3.amappli.entities.tenancy.ContentBlock;
import isika.p3.amappli.repo.amap.ContentBlockRepository;
import isika.p3.amappli.service.amap.ContentBlockService;

@Service
public class ContentBlockServiceImpl implements ContentBlockService {
    
    private final ContentBlockRepository repo;


    public ContentBlockServiceImpl(ContentBlockRepository repo) {
        this.repo = repo;
    }

    public void save(ContentBlock cb){
        repo.save(cb);
    }

    public ContentBlock findById(Long id){
        return repo.findById(id).orElse(null);
    }
}
