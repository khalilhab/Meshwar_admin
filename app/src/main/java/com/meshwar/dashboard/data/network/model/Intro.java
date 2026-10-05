package com.meshwar.dashboard.data.network.model;

import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Intro {

    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("uuid")
    private String uuid;
    private Uri newImageUri = null;
    private TextWatcher textWatcher = null;

    public TextWatcher getTextWatcher() {
        return textWatcher;
    }

    public void setTextWatcher(TextInputEditText textInputEditText) {
        if (getTextWatcher()!=null)
             return;

            textInputEditText.addTextChangedListener(this.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public Uri getNewImageUri() {
        return newImageUri;
    }

    public void setNewImageUri(Uri newImageUri) {
        this.newImageUri = newImageUri;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
