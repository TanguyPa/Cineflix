package fr.etu.miage.projet_android.model;

import com.google.gson.annotations.SerializedName;

public class DateUpcoming {
    @SerializedName("maximum")
    private String maximum;
    @SerializedName("minimum")
    private String minimum;

    public DateUpcoming(String maximum, String minimum) {
        this.maximum = maximum;
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }
}
