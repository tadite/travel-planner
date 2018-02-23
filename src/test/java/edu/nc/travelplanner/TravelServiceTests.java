package edu.nc.travelplanner;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import edu.nc.travelplanner.dao.TravelDao;
import edu.nc.travelplanner.dto.afterPickTree.CheckpointAfterPickTreeDto;
import edu.nc.travelplanner.dto.afterPickTree.TravelAfterPickTreeDto;
import edu.nc.travelplanner.service.travel.TravelService;
import edu.nc.travelplanner.table.Country;
import edu.nc.travelplanner.table.Travel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TravelplannerApplication.class)
@EnableTransactionManagement*/
public class TravelServiceTests {

    //@Autowired
    TravelService travelService;


    //@Test
    public void canSave() throws ClientException, ApiException {
        travelService.saveTravelAfterPick(new TravelAfterPickTreeDto(){{
            setFrom(new CheckpointAfterPickTreeDto(){{
                setClientId(1L);
                setCityId(2L);
                setCountryId(3);
            }});
            setClientId(5L);
        }});
    }
}
