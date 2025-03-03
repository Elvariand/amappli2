package isika.p3.amappli.service.amap;

import java.util.List;

import isika.p3.amappli.entities.user.CompanyDetails;

public interface CompanyDetailsService {

	List<CompanyDetails> findAll();
	
	CompanyDetails save(CompanyDetails companyDetails);
	
	CompanyDetails findById(Long companyDetailsId);
	
	void deleteById(Long companyDetailsId);
}
