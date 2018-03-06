package edu.nc.travelplanner;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import edu.nc.travelplanner.dto.afterPickTree.CheckpointAfterPickTreeDto;
import edu.nc.travelplanner.dto.afterPickTree.TravelAfterPickTreeDto;
import edu.nc.travelplanner.service.travel.TravelSaveService;

/*@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TravelplannerApplication.class)*/
public class TravelServiceTests {

   // @Autowired
    TravelSaveService travelSaveService;


   // @Test
    public void canSave() throws ClientException, ApiException {
        travelSaveService.saveTravelAfterPick(new TravelAfterPickTreeDto(){{
            setFrom(new CheckpointAfterPickTreeDto(){{
                setClientId(1L);
                setCityId(2L);
                setCountryId(3);
            }});
            setClientId(5L);
        }});
    }
}
