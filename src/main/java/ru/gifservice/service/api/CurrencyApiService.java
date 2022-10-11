package ru.gifservice.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.gifservice.model.dto.ResponseCurrencyJSON;

@FeignClient(name = "${currency-service.name}", url = "${currency-service.base.url}")
public interface CurrencyApiService {
    @GetMapping("/historical/{date}.json?app_id={app_id}")
    ResponseCurrencyJSON getCurrency(@PathVariable("date") String date, @PathVariable("app_id") String app_id);
}
