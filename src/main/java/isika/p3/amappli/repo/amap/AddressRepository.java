package isika.p3.amappli.repo.amap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import isika.p3.amappli.entities.user.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
