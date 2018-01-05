package edu.nc.travelplanner.model.source;

public class HttpSource implements Source{
    private String name;
    private String description;
    private String url;

    public HttpSource(String name, String description, String url) {
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

    @Override
    public SourceType getType() {
        return SourceType.HTTP;
    }
}
