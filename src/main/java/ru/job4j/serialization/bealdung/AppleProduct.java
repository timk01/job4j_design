package ru.job4j.serialization.bealdung;

import java.io.Serial;
import java.io.Serializable;

public class AppleProduct implements Serializable {

    private static final long serialVersionUID = 1234567L;

    public String headphonePort;
    public String thunderboltPort;
    private String lightningPort;

    public String getHeadphonePort() {
        return headphonePort;
    }

    public void setHeadphonePort(String headphonePort) {
        this.headphonePort = headphonePort;
    }

    public String getThunderboltPort() {
        return thunderboltPort;
    }

    public void setThunderboltPort(String thunderboltPort) {
        this.thunderboltPort = thunderboltPort;
    }

    public String getLightningPort() {
        return lightningPort;
    }

    public void setLightningPort(String lightningPort) {
        this.lightningPort = lightningPort;
    }
}