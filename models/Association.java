package models;

public class Association {
    private int id;
    private String name;
    private String location;
    private User user;

    public Association(int id, String name, String location, User user) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
