package ru.gifservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gifservice.exception.BadRequestException;
import ru.gifservice.service.GifService;

import java.net.URI;

@RestController
public class MainController {
    private final GifService gifService;


    public MainController(GifService gifService) {
        this.gifService = gifService;
    }

    private static final String GET_GIF = "/api/v1/gif";

    @GetMapping(GET_GIF)
    public ResponseEntity<String> getGif(@RequestParam String currency) {
        try {
            String gif = gifService.getUrl(currency);
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(gif)).build();
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This currency is not listed or does not exist. Try again.");
        }
    }
}
