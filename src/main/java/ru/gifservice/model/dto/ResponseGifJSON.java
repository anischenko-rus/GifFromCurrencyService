package ru.gifservice.model.dto;

import lombok.Data;

@Data
public class ResponseGifJSON {

    private GifJSON data;

    private Object meta;

}
