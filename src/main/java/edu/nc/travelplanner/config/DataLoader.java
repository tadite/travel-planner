package edu.nc.travelplanner.config;

import edu.nc.travelplanner.service.user.UserService;
import edu.nc.travelplanner.table.Client;
import edu.nc.travelplanner.table.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private UserService userService;


    public void run(ApplicationArguments args) {
        initTypesOfResidence();
    }

    private void initTypesOfResidence() {
        Client admin = userService.find("admin");

        if (admin==null || admin.getBlocked()==null)
        {
            admin=new Client();
            admin.setPassword("admin");
            admin.setLogin("admin");
            admin.setEmail("admin@gmail.com");
            admin.setRole(Roles.ADMIN);

            userService.save(admin);
        }
    }
}
