package ru.gifservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.gifservice.exception.BadRequestException;
import ru.gifservice.model.dto.ResponseCurrencyJSON;
import ru.gifservice.service.api.CurrencyApiService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class NowRateServiceImpl implements NowRateService {
    private final CurrencyApiService currencyApiService;

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private static final String ZONE_ID = "US/Eastern";

    private static final String BAD_REQUEST_MESSAGE = "This currency is not listed or does not exist";

    @Value("${currency-service.app_id}")
    private String APP_ID;

    public NowRateServiceImpl(CurrencyApiService currencyApiService) {
        this.currencyApiService = currencyApiService;
    }
    @Override
    public Double getNowRate(String currency) {
        String nowDate = LocalDate.now(ZoneId.of(ZONE_ID)).format(DateTimeFormatter.ofPattern(DATE_PATTERN));
        log.info("Date now {}", nowDate);

        ResponseCurrencyJSON currencyNow = currencyApiService.getCurrency(nowDate, APP_ID);

        checkRate(currency, currencyNow);

        Double nowRate = currencyNow.getRates().get(currency);
        log.info("Currency for {} on {} is {} ", currency, nowDate, nowRate);

        return nowRate;
    }


    private void checkRate(String currency, ResponseCurrencyJSON currencyApi) {
        if (!currencyApi.getRates().containsKey(currency)) {
            throw new BadRequestException(BAD_REQUEST_MESSAGE);
        }
    }
}
