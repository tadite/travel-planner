package edu.nc.travelplanner.config;

import edu.nc.travelplanner.dao.TypeOfResidenceDao;
import edu.nc.travelplanner.dto.afterPickTree.CheckpointAfterPickTreeDto;
import edu.nc.travelplanner.dto.afterPickTree.TravelAfterPickTreeDto;
import edu.nc.travelplanner.service.travel.TravelSaveService;
import edu.nc.travelplanner.table.TypeOfResidence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    TravelSaveService travelSaveService;

    public void run(ApplicationArguments args) {
        initTypesOfResidence();
    }

    private void initTypesOfResidence() {
        TravelAfterPickTreeDto travelAfterPickTreeDto = new TravelAfterPickTreeDto();
        travelAfterPickTreeDto.setTravelName("test-travel-name");
        travelAfterPickTreeDto.setClientId(Long.valueOf(1));
        travelAfterPickTreeDto.setFrom(new CheckpointAfterPickTreeDto() {{
            this.setCountryId(1);
            this.setCityId(Long.valueOf(1));
        }});
        travelAfterPickTreeDto.getCheckpoints().add(new CheckpointAfterPickTreeDto() {{
            this.setCountryId(1);
            this.setCityId(Long.valueOf(2));
        }});

        travelSaveService.saveTravelAfterPick(travelAfterPickTreeDto);
    }
}
