package edu.nc.travelplanner.config;

import edu.nc.travelplanner.dao.TypeOfResidenceDao;
import edu.nc.travelplanner.table.TypeOfResidence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//@Component
public class DataLoader implements ApplicationRunner {

    private TypeOfResidenceDao typeOfResidenceDao;

    @Autowired
    public DataLoader(@Autowired TypeOfResidenceDao typeOfResidenceDao) {

        this.typeOfResidenceDao = typeOfResidenceDao;
    }

    public void run(ApplicationArguments args) {
        initTypesOfResidence();
    }

    private void initTypesOfResidence() {
        typeOfResidenceDao.saveTypeOfResidence(new TypeOfResidence(){{
            setName("Место отправления");
        }});
    }
}
