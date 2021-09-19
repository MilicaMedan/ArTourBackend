package ArTour.ArTour.Request;

import java.io.Serializable;

public class SettingsRequest  implements Serializable {
    private Integer settings;

    public SettingsRequest() {
    }

    public SettingsRequest(Integer settings) {
        this.settings = settings;
    }

    public Integer getSettings() {
        return settings;
    }

    public void setSettings(Integer settings) {
        this.settings = settings;
    }
}
