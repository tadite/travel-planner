package edu.nc.travelplanner.config;

import edu.nc.travelplanner.dao.TypeOfResidenceDao;
import edu.nc.travelplanner.table.TypeOfResidence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupApplicationListener implements
        ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    }
}
