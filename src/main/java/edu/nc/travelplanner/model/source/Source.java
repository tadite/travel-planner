package edu.nc.travelplanner.model.source;

public class Source {
    private String name;
    private String description;
    private String url;

    public Source(String name, String description, String url) {
        this.name = name;
        this.description = description;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
