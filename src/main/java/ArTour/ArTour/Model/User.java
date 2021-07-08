package ArTour.ArTour.Model;

public class User {
    private String name;
    private String lastname;
    private String username;
    private String passwordHash;

    public User() {
    }

    public User(String name, String lastname, String username, String passwordHash) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
