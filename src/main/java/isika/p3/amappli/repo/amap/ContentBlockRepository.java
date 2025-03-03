package isika.p3.amappli.repo.amap;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import isika.p3.amappli.entities.tenancy.ContentBlock;

@Repository
public interface ContentBlockRepository extends CrudRepository<ContentBlock, Long>{

}
