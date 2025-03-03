package isika.p3.amappli.repo.amappli;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import isika.p3.amappli.entities.tenancy.ServiceSubscription;

@Repository
public interface ServiceSubscriptionRepository extends CrudRepository<ServiceSubscription, Long> {

}
