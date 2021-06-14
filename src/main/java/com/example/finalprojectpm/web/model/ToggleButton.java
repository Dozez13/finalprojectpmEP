package com.example.finalprojectpm.web.model;

import java.io.Serializable;

/**
 * Class that will be used as model for button
 * @author Pavlo Manuilenko
 */
public class ToggleButton implements Serializable {
    private static final long serialVersionUID = -4179236910442782245L;
    private String icon;
    private String value;

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    private String slot;
    public ToggleButton(){
        super();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
