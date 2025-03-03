package isika.p3.amappli.service.amap.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import isika.p3.amappli.dto.amap.ContractDTO;
import isika.p3.amappli.entities.contract.Contract;
import isika.p3.amappli.entities.contract.ContractType;
import isika.p3.amappli.entities.contract.ContractWeight;
import isika.p3.amappli.entities.contract.DeliveryRecurrence;
import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.entities.user.Address;
import isika.p3.amappli.entities.user.User;
import isika.p3.amappli.repo.amap.AddressRepository;
import isika.p3.amappli.repo.amap.ContractRepository;
import isika.p3.amappli.repo.amap.UserRepository;
import isika.p3.amappli.repo.amappli.TenancyRepository;
import isika.p3.amappli.service.amap.ContractService;
import jakarta.transaction.Transactional;

@Service
public class ContractServiceImpl implements ContractService {

	private final ContractRepository contractRepository;
	private final UserRepository userRepository;
	private final TenancyRepository tenancyRepository;

	public ContractServiceImpl(TenancyRepository tenancyRepository, ContractRepository contractRepository, UserRepository userRepository, AddressRepository addressRepository) {
		this.contractRepository = contractRepository;
		this.userRepository = userRepository;
		this.tenancyRepository = tenancyRepository;
	}

	@Override
	public List<Contract> findAll() {
		return contractRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
	    contractRepository.deleteById(id);
	}

	@Override
	public Contract findById(Long id) {
		return contractRepository.findById(id).orElse(null);
	}

	@Override
	public void save(ContractDTO contractDTO, String tenancyAlias) {
		Contract contract = new Contract();
		
        Tenancy tenancy = tenancyRepository.findByTenancyAlias(tenancyAlias)
                .orElseThrow(() -> new IllegalArgumentException("Tenancy not found for alias: " + tenancyAlias));
        contract.setTenancy(tenancy);
        
        // Associer le PickupSchedule depuis la tenancy
        if (tenancy.getPickUpSchedule() != null) {
            contract.setPickUpSchedule(tenancy.getPickUpSchedule());
        } else {
            throw new IllegalArgumentException("Pickup Schedule non défini pour la tenancy.");
        }

        // Récupération de l'utilisateur sélectionné s'il y a un ID d'utilisateur
        if (contractDTO.getUserId() != null) {
            User user = userRepository.findById(contractDTO.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + contractDTO.getUserId()));
            contract.setUser(user);
        } else {
            contract.setUser(null); // Si aucun utilisateur n'est sélectionné
        }
        
        // Récupération de l'adresse associée à la tenancy
        Address tenancyAddress = tenancy.getAddress();
        if (tenancyAddress == null) {
            throw new IllegalArgumentException("No address found for the tenancy.");
        }
        contract.setAddress(tenancyAddress);
	 

		contract.setContractName(contractDTO.getContractName());
		contract.setContractType(ContractType.valueOf(contractDTO.getContractType()));
		contract.setContractDescription(contractDTO.getContractDescription());
		contract.setContractWeight(ContractWeight.valueOf(contractDTO.getContractWeight()));
		contract.setContractPrice(contractDTO.getContractPrice());
		contract.setStartDate(contractDTO.getStartDate());
		contract.setEndDate(contractDTO.getEndDate());
		contract.setDeliveryRecurrence(DeliveryRecurrence.valueOf(contractDTO.getDeliveryRecurrence()));
		contract.setQuantity(contractDTO.getQuantity());
		contract.setShoppable(true);
		contract.setDateCreation(LocalDate.now());

		//Image treatment
		if (!contractDTO.getImage().isEmpty()) {
			if (contractDTO.getImage().getSize() > 20971520) {
				throw new IllegalArgumentException("La taille de l'image dépasse la limite de 5MB.");
			}
			try {
				contract.setImageType(contractDTO.getImage().getContentType());
				byte[] imageBytes = contractDTO.getImage().getBytes();
				contract.setImageData(Base64.getEncoder().encodeToString(imageBytes));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		contractRepository.save(contract);
	}
	
	@Override
	public void updateContract(ContractDTO updatedContractDTO, MultipartFile image, String tenancyAlias) {
	    Contract existingContract = contractRepository.findById(updatedContractDTO.getId())
	            .orElseThrow(() -> new IllegalArgumentException("Le contrat avec l'ID " + updatedContractDTO.getId() + " n'existe pas."));

	    // Mettez à jour uniquement les champs pertinents
	    existingContract.setContractName(updatedContractDTO.getContractName());
	    existingContract.setContractType(ContractType.valueOf(updatedContractDTO.getContractType()));
	    existingContract.setContractDescription(updatedContractDTO.getContractDescription());
	    existingContract.setContractWeight(ContractWeight.valueOf(updatedContractDTO.getContractWeight()));
	    existingContract.setContractPrice(updatedContractDTO.getContractPrice());
	    existingContract.setStartDate(updatedContractDTO.getStartDate());
	    existingContract.setEndDate(updatedContractDTO.getEndDate());
	    existingContract.setDeliveryRecurrence(DeliveryRecurrence.valueOf(updatedContractDTO.getDeliveryRecurrence()));
	    existingContract.setQuantity(updatedContractDTO.getQuantity());


	    if (updatedContractDTO.getUserId() != null) {
	        User newUser = userRepository.findById(updatedContractDTO.getUserId())
	                .orElseThrow(() -> new IllegalArgumentException("Utilisateur avec l'ID " + updatedContractDTO.getUserId() + " n'existe pas."));
	        existingContract.setUser(newUser);
	    }

	    // Gestion de l'image
	    if (image != null && !image.isEmpty()) {
	        try {
	            existingContract.setImageType(image.getContentType());
	            existingContract.setImageData(Base64.getEncoder().encodeToString(image.getBytes()));
	        } catch (IOException e) {
	            throw new RuntimeException("Erreur lors du traitement de l'image", e);
	        }
	    }

	    // Conservez tenancy et date de création
	    existingContract.setTenancy(existingContract.getTenancy());
	    existingContract.setDateCreation(existingContract.getDateCreation());

	    contractRepository.save(existingContract);
	}




	
	@Override
	public List<Contract> findAll(Long tenancyId) {
		return ((List<Contract>) contractRepository.findAll()).stream()
				.filter(u -> u.getTenancy().getTenancyId() == tenancyId).toList();
				
	}

	@Override
	public List<Contract> findAll(String tenancyAlias) {
	    return contractRepository.findByTenancyAlias(tenancyAlias);
	}

	@Override
	public List<Contract> findShoppableContractsByTenancy(Tenancy tenancy) {
	    return contractRepository.findByTenancy(tenancy).stream()
	            .filter(Contract::isShoppable) // Garder uniquement les contrats shoppables
	            .sorted((c1, c2) -> c2.getId().compareTo(c1.getId()))
	            .toList();
	}
	
	@Override
	public List<Contract> findShoppableContractsByTypeAndTenancy(ContractType type, Tenancy tenancy) {
	    return contractRepository.findByTypeAndTenancy(type, tenancy);
	}





}
