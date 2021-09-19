package ArTour.ArTour.JWT;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    private final String jwt;
    private final Integer settings;

    public AuthenticationResponse(String jwt, Integer settings ) {
        this.jwt = jwt;
        this.settings = settings;
    }

    public String getJwt() {
        return jwt;
    }

    public Integer getSettings() {
        return settings;
    }
}