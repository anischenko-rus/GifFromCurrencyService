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
public class YesterdayRateServiceImpl implements YesterdayRateService {
    private final CurrencyApiService currencyApiService;

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private static final String ZONE_ID = "US/Eastern";

    private static final String BAD_REQUEST_MESSAGE = "This currency is not listed or does not exist";

    @Value("${currency-service.app_id}")
    private String APP_ID;

    public YesterdayRateServiceImpl(CurrencyApiService currencyApiService) {
        this.currencyApiService = currencyApiService;
    }


    @Override
    public Double getYesterdayRate(String currency) {
        String yestDate = LocalDate.now(ZoneId.of(ZONE_ID)).minusDays(1L).format(DateTimeFormatter.ofPattern(DATE_PATTERN));
        log.info("Date yesterday {}", yestDate);

        ResponseCurrencyJSON currencyYest = currencyApiService.getCurrency(yestDate, APP_ID);

        checkRate(currency, currencyYest);

        Double yestRate = currencyYest.getRates().get(currency);
        log.info("Currency for {} on {} is {} ", currency, yestDate, yestRate);

        return yestRate;
    }


    private void checkRate(String currency, ResponseCurrencyJSON currencyApi) {
        if (!currencyApi.getRates().containsKey(currency)) {
            throw new BadRequestException(BAD_REQUEST_MESSAGE);
        }
    }
}
