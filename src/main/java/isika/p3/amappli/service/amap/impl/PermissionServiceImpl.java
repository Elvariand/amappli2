package isika.p3.amappli.service.amap.impl;

import org.springframework.stereotype.Service;

import isika.p3.amappli.entities.auth.Permission;
import isika.p3.amappli.repo.amappli.PermissionRepository;
import isika.p3.amappli.service.amap.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;


    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void createPermissions() {
        Permission p1 = Permission.builder()
                        .name("platform_administration")
                        .build();

        Permission p2 = Permission.builder()
                        .name("tenancy_customization")
                        .build();

        Permission p3 = Permission.builder()
                        .name("contract_creation")
                        .build();

        Permission p4 = Permission.builder()
                        .name("tenancy_managment")
                        .build();

        permissionRepository.save(p1);
        permissionRepository.save(p2);
        permissionRepository.save(p3);
        permissionRepository.save(p4);
    }
    
}
