package isika.p3.amappli.repo.amappli;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import isika.p3.amappli.entities.auth.Permission;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long>{

    Iterable<Permission> findAllById(Iterable<Long> ids);
}
