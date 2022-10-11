package ru.gifservice.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.gifservice.model.dto.ResponseGifJSON;
import ru.gifservice.service.api.GifApiService;

import java.util.Objects;

@Component
@Slf4j
public class GifServiceImpl implements GifService {
    private final NowRateService nowRateService;

    private final YesterdayRateService yesterdayRateService;

    private final TagService tagService;

    private final GifApiService gifApiService;

    @Value("${gif-service.api_key}")
    private String API_KEY;

    public GifServiceImpl(NowRateService nowRateService, YesterdayRateService yesterdayRateService, TagService tagService, GifApiService gifApiService) {
        this.nowRateService = nowRateService;
        this.yesterdayRateService = yesterdayRateService;
        this.tagService = tagService;
        this.gifApiService = gifApiService;
    }


    @Override
    public String getUrl(String currency) {
        Double nowRate = nowRateService.getNowRate(currency);
        Double yestRate = yesterdayRateService.getYesterdayRate(currency);

        String tag = tagService.getTag(nowRate, yestRate);
        log.info("Tag is {}", tag);

        ResponseEntity<ResponseGifJSON> gifJSON = gifApiService.getGif(tag, API_KEY);

        String embed_url = Objects.requireNonNull(gifJSON.getBody()).getData().getEmbed_url();
        log.info("Gif url is {}", embed_url);

        return embed_url;
    }
}
