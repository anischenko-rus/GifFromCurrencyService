package ru.gifservice.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.gifservice.model.dto.ResponseGifJSON;

@FeignClient(name = "${gif-service.name}", url = "${gif-service.base.url}")
public interface GifApiService {
    @GetMapping("/random?api_key={api_key}&tag={tag}")
    ResponseEntity<ResponseGifJSON> getGif(@PathVariable("tag") String tag, @PathVariable("api_key") String api_key);

}
