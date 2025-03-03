package isika.p3.amappli.repo.amap;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.entities.workshop.Workshop;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
	@Query("SELECT c FROM Workshop c WHERE c.tenancy.tenancyAlias = :tenancyAlias")
	List<Workshop> findByTenancyAlias(@Param("tenancyAlias") String tenancyAlias);
    List<Workshop> findByTenancy(Tenancy tenancy);
    
    @Query(value = "SELECT id FROM workshops ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Long findRandomIdNative();

    default Workshop findRandom() {
        Long randomId = findRandomIdNative();
        return randomId != null ? findById(randomId).orElse(null) : null;
    }
}
