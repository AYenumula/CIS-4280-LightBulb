package com.example.productive2;

import androidx.annotation.NonNull;

public class SpinnerValues {
    String values;

    public SpinnerValues(String values) {
        this.values = values;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    @NonNull
    @Override
    public String toString() {
        return values;
    }
}
