package isika.p3.amappli.repo.amap;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import isika.p3.amappli.entities.contract.Contract;
import isika.p3.amappli.entities.contract.ContractType;
import isika.p3.amappli.entities.tenancy.Tenancy;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
	@Query("SELECT c FROM Contract c WHERE c.tenancy.tenancyAlias = :tenancyAlias")
	List<Contract> findByTenancyAlias(@Param("tenancyAlias") String tenancyAlias);
    List<Contract> findByTenancy(Tenancy tenancy);
    
    @Query(value = "SELECT id FROM contracts ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Long findRandomIdNative();

    default Contract findRandom() {
        Long randomId = findRandomIdNative();
        return randomId != null ? findById(randomId).orElse(null) : null;
    }
    
    @Query("SELECT c FROM Contract c WHERE c.contractType = :type AND c.tenancy = :tenancy")
    List<Contract> findByTypeAndTenancy(@Param("type") ContractType type, @Param("tenancy") Tenancy tenancy);

}
