package edu.nc.travelplanner.dto.afterPickTree;

import java.util.LinkedList;
import java.util.List;

public class TravelAfterPickTreeDto {
    private CheckpointAfterPickTreeDto from = new CheckpointAfterPickTreeDto();
    private List<CheckpointAfterPickTreeDto> checkpoints = new LinkedList<>();
    private String travelName;
    private String travelDescription;
    private Long clientId;
    private String dateIntervalStart;
    private String dateIntervalEnd;

    public TravelAfterPickTreeDto() {
    }

    public TravelAfterPickTreeDto(CheckpointAfterPickTreeDto from, List<CheckpointAfterPickTreeDto> checkpoints, String travelName, String travelDescription) {
        this.from = from;
        this.checkpoints = checkpoints;
        this.travelName = travelName;
        this.travelDescription = travelDescription;
    }

    public String getDateIntervalStart() {
        return dateIntervalStart;
    }

    public void setDateIntervalStart(String dateIntervalStart) {
        this.dateIntervalStart = dateIntervalStart;
    }

    public String getDateIntervalEnd() {
        return dateIntervalEnd;
    }

    public void setDateIntervalEnd(String dateIntervalEnd) {
        this.dateIntervalEnd = dateIntervalEnd;
    }

    public CheckpointAfterPickTreeDto getFrom() {
        return from;
    }

    public void setFrom(CheckpointAfterPickTreeDto from) {
        this.from = from;
    }

    public List<CheckpointAfterPickTreeDto> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(List<CheckpointAfterPickTreeDto> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public String getTravelName() {
        return travelName;
    }

    public void setTravelName(String travelName) {
        this.travelName = travelName;
    }

    public String getTravelDescription() {
        return travelDescription;
    }

    public void setTravelDescription(String travelDescription) {
        this.travelDescription = travelDescription;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
