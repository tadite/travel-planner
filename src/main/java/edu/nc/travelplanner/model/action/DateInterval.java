package edu.nc.travelplanner.model.action;

import java.util.Date;

public class DateInterval {
    private Date startDate;
    private Date endDate;

    public DateInterval(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
