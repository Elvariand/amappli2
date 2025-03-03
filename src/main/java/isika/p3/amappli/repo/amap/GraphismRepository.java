package isika.p3.amappli.repo.amap;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import isika.p3.amappli.entities.tenancy.Graphism;

@Repository
public interface GraphismRepository extends CrudRepository<Graphism, Long>{

}
