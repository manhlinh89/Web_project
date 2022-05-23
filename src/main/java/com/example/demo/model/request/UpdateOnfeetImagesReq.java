package com.example.demo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateOnfeetImagesReq {
    @JsonProperty("onfeet_images")
    private ArrayList<String> onfeetImages;
}
