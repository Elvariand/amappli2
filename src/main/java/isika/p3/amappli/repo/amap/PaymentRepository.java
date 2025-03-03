package isika.p3.amappli.repo.amap;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import isika.p3.amappli.entities.payment.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

	@Query("SELECT p FROM Payment p JOIN p.order o WHERE o.user.userId = :userId")
	Payment findPaymentsByUser(@Param("userId") Long userId);
}
