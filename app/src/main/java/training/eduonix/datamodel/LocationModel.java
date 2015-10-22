package training.eduonix.datamodel;

/**
 * Created by AndroDev on 22-10-2015.
 */
public class LocationModel {

    private long id;
    private String name;
    private int autoLocation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAutoLocation() {
        return autoLocation;
    }

    public void setAutoLocation(int autoLocation) {
        this.autoLocation = autoLocation;
    }
}
