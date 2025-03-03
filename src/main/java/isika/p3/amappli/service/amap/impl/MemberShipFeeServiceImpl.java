package isika.p3.amappli.service.amap.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import isika.p3.amappli.dto.amap.MembershipFeeDTO;
import isika.p3.amappli.entities.membership.MembershipFee;
import isika.p3.amappli.repo.amap.MembershipFeeRepository;
import isika.p3.amappli.service.amap.MemberShipService;

public class MemberShipFeeServiceImpl implements MemberShipService {

	
	 @Autowired
	    private MembershipFeeRepository membershipFeeRepository;
	 
	 
	 
	 public MembershipFeeDTO mapToDTO(MembershipFee membershipFee) {
	     MembershipFeeDTO dto = new MembershipFeeDTO();
	     dto.setId(membershipFee.getId());
	     dto.setInfo(membershipFee.getInfo());
	     dto.setPrice(membershipFee.getPrice());
	     dto.setDateBeginning(membershipFee.getDateBeginning());
	     dto.setDateEnd(membershipFee.getDateEnd());

	     // Détermine le statut en fonction des dates
	     LocalDate today = LocalDate.now();
	     if (today.isAfter(membershipFee.getDateEnd())) {
	         dto.setStatus("Inactive");
	     } else {
	         dto.setStatus("Active");
	     }

	   

	     return dto;
	 }
	 
	 

	    public MembershipFeeDTO getMembershipForUser(Long userId) {
	        MembershipFee membershipFee = membershipFeeRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("Aucune adhésion trouvée pour cet utilisateur."));

	        return mapToDTO(membershipFee);
	    }
	
}
