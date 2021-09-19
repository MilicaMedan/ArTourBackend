package ArTour.ArTour.Model;

public class User {
    private Integer id;
    private String mail;
    private String name;
    private String lastname;
    private String username;
    private String passwordHash;
    private String salt;
    private Integer settings;

    public User() {
    }

    public User(Integer id, String mail ,String name, String lastname, String username, String passwordHash) {
        this.id = id;
        this.mail = mail;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.passwordHash = passwordHash;

    }

    public User(Integer id, String mail, String name, String lastname, String username, String passwordHash, String salt , Integer settings) {
        this.id = id;
        this.mail = mail;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.settings = settings;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getSettings() {
        return settings;
    }

    public void setSettings(Integer settings) {
        this.settings = settings;
    }
}
