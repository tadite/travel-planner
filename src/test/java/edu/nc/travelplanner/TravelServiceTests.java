package edu.nc.travelplanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import edu.nc.travelplanner.dto.afterPickTree.CheckpointAfterPickTreeDto;
import edu.nc.travelplanner.dto.afterPickTree.TravelAfterPickTreeDto;
import edu.nc.travelplanner.model.factory.DefaultEnumMapper;
import edu.nc.travelplanner.model.factory.PathMapper;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerParseException;
import edu.nc.travelplanner.model.factory.dataproducer.DefaultSenderFactory;
import edu.nc.travelplanner.model.factory.dataproducer.FileDataProducerJsonReader;
import edu.nc.travelplanner.model.factory.dataproducer.JsonDataProducerFactory;
import edu.nc.travelplanner.model.factory.filter.DefaultResponseFilterFactory;
import edu.nc.travelplanner.model.factory.source.FileSourceJsonReader;
import edu.nc.travelplanner.model.factory.source.JsonSourceFactory;
import edu.nc.travelplanner.model.main.DataProducerConfig;
import edu.nc.travelplanner.model.main.DataProducerManager;
import edu.nc.travelplanner.service.travel.TravelSaveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

public class TravelServiceTests {

    DataProducerManager dataProducerManager = new DataProducerManager(
            new JsonDataProducerFactory(new DefaultResponseFilterFactory(new DefaultEnumMapper()),
                    new FileDataProducerJsonReader(new PathMapper()),
                    new JsonSourceFactory(new FileSourceJsonReader(new PathMapper()),
                            new DefaultEnumMapper()),new DefaultSenderFactory(new DefaultEnumMapper())),
            new DataProducerConfig(new PathMapper(), new ObjectMapper()),
            new ObjectMapper());

    public TravelServiceTests() throws IOException {
    }

    @Test
    public void test() throws DataProducerParseException, IOException {
        Map<String, String> cities = dataProducerManager.getAllCitiesByCountryIdAndQuery(Long.valueOf(1), "Vorone");
        Map<String, String> countries = dataProducerManager.getAllCountriesMap();
        return;
    }
}
