package isika.p3.amappli.service.amap;

import isika.p3.amappli.entities.tenancy.ContentBlock;

public interface ContentBlockService {
    
    public void save(ContentBlock cb);
    public ContentBlock findById(Long id);
}
