package isika.p3.amappli.service.amap.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import isika.p3.amappli.dto.amap.WorkshopDTO;
import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.entities.user.User;
import isika.p3.amappli.entities.workshop.Workshop;
import isika.p3.amappli.repo.amap.UserRepository;
import isika.p3.amappli.repo.amap.WorkshopRepository;
import isika.p3.amappli.repo.amappli.TenancyRepository;
import isika.p3.amappli.service.amap.WorkshopService;

@Service
public class WorkshopServiceImpl implements WorkshopService {

    private final WorkshopRepository workshopRepository;
	private final UserRepository userRepository;
	private final TenancyRepository tenancyRepository;

    public WorkshopServiceImpl(WorkshopRepository workshopRepository, UserRepository userRepository, TenancyRepository tenancyRepository) {
        this.workshopRepository = workshopRepository;
        this.userRepository = userRepository;
        this.tenancyRepository = tenancyRepository;
    }
    
	@Override
	public void save(WorkshopDTO workshopDTO, String tenancyAlias) {
        Workshop workshop = new Workshop();
        
        Tenancy tenancy = tenancyRepository.findByTenancyAlias(tenancyAlias)
                .orElseThrow(() -> new IllegalArgumentException("Tenancy not found for alias: " + tenancyAlias));
        workshop.setTenancy(tenancy);
        
        // Récupération de l'utilisateur sélectionné
        if (workshopDTO.getUserId() != null) {
            User user = userRepository.findById(workshopDTO.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + workshopDTO.getUserId()));
            workshop.setUser(user);

            // Assigner l'adresse de l'utilisateur au workshop
            if (user.getAddress() != null) {
                workshop.setAddress(user.getAddress());
            } else {
                throw new IllegalArgumentException("No address found for the user.");
            }
        } else {
            workshop.setUser(null); // Si aucun utilisateur n'est sélectionné
        }

        workshop.setWorkshopName(workshopDTO.getWorkshopName());
        workshop.setWorkshopDescription(workshopDTO.getWorkshopDescription());
        workshop.setWorkshopDateTime(workshopDTO.getWorkshopDateTime());
        workshop.setWorkshopPrice(workshopDTO.getWorkshopPrice());
        workshop.setWorkshopDuration(workshopDTO.getWorkshopDuration());
        workshop.setMinimumParticipants(workshopDTO.getMinimumParticipants());
        workshop.setMaximumParticipants(workshopDTO.getMaximumParticipants());
        workshop.setDateCreation(LocalDate.now());

		if (!workshopDTO.getImage().isEmpty()) {
			if (workshopDTO.getImage().getSize() > 20971520) {
				throw new IllegalArgumentException("La taille de l'image dépasse la limite de 5MB.");
			}
			try {
				workshop.setImageType(workshopDTO.getImage().getContentType());
				byte[] imageBytes = workshopDTO.getImage().getBytes();
				workshop.setImageData(Base64.getEncoder().encodeToString(imageBytes));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

        workshopRepository.save(workshop);
	}

	@Override
	public List<Workshop> findAll() {
		return workshopRepository.findAll();
	}

	@Override
	public void deleteById(Long id) {
		workshopRepository.deleteById(id);
	}

	@Override
	public Workshop findById(Long id) {
		return workshopRepository.findById(id).orElse(null);
	}

	@Override
	public void updateWorkshop(WorkshopDTO updatedWorkshopDTO, MultipartFile image, String tenancyAlias) {

		 Workshop existingWorkshop = workshopRepository.findById(updatedWorkshopDTO.getId()).orElse(null);

		    if (existingWorkshop == null) {
		        throw new IllegalArgumentException("L'atelier avec l'ID " + updatedWorkshopDTO.getId() + " n'existe pas.");
		    }

		    // Mise à jour des champs non liés à l'image
		    existingWorkshop.setWorkshopName(updatedWorkshopDTO.getWorkshopName() != null ? updatedWorkshopDTO.getWorkshopName() : existingWorkshop.getWorkshopName());
		    existingWorkshop.setWorkshopDescription(updatedWorkshopDTO.getWorkshopDescription() != null ? updatedWorkshopDTO.getWorkshopDescription() : existingWorkshop.getWorkshopDescription());
		    existingWorkshop.setWorkshopPrice(updatedWorkshopDTO.getWorkshopPrice() != null ? updatedWorkshopDTO.getWorkshopPrice() : existingWorkshop.getWorkshopPrice());
		    existingWorkshop.setWorkshopDuration(updatedWorkshopDTO.getWorkshopDuration() != null ? updatedWorkshopDTO.getWorkshopDuration() : existingWorkshop.getWorkshopDuration());
		    existingWorkshop.setMinimumParticipants(updatedWorkshopDTO.getMinimumParticipants() != null ? updatedWorkshopDTO.getMinimumParticipants() : existingWorkshop.getMinimumParticipants());
		    existingWorkshop.setMaximumParticipants(updatedWorkshopDTO.getMaximumParticipants() != null ? updatedWorkshopDTO.getMaximumParticipants() : existingWorkshop.getMaximumParticipants());

		    // Vérification si l'utilisateur a changé
		    if (updatedWorkshopDTO.getUserId() != null) {
		        User newUser = userRepository.findById(updatedWorkshopDTO.getUserId())
		                .orElseThrow(() -> new IllegalArgumentException("Utilisateur avec l'ID " + updatedWorkshopDTO.getUserId() + " n'existe pas."));

		        // Si l'utilisateur change, mettez à jour l'adresse
		        if (!newUser.equals(existingWorkshop.getUser())) {
		            existingWorkshop.setUser(newUser);
		            existingWorkshop.setAddress(newUser.getAddress());
		        }
		    }
		    
		    if (updatedWorkshopDTO.getWorkshopDateTime() != null) {
		        existingWorkshop.setWorkshopDateTime(updatedWorkshopDTO.getWorkshopDateTime());
		    }

		    
		    // Gestion de l'image
		    if (image != null && !image.isEmpty()) {
		        try {
		            existingWorkshop.setImageType(image.getContentType());
		            byte[] imageBytes = image.getBytes();
		            existingWorkshop.setImageData(Base64.getEncoder().encodeToString(imageBytes));
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }

		    // Sauvegarde de l'atelier mis à jour
		    workshopRepository.save(existingWorkshop);
	}

	@Override
	public List<Workshop> findAll(Long tenancyId) {
		return ((List<Workshop>) workshopRepository.findAll()).stream()
				.filter(u -> u.getTenancy().getTenancyId() == tenancyId).toList();
	}

	@Override
	public List<Workshop> findAll(String tenancyAlias) {
		return workshopRepository.findByTenancyAlias(tenancyAlias);
	}

	@Override
	public List<Workshop> findShoppableWorkshopsByTenancy(Tenancy tenancy) {
	    return workshopRepository.findByTenancy(tenancy).stream()
	            .filter(Workshop::isShoppable) // Garder uniquement les contrats shoppables
	            .toList();
	}

}
