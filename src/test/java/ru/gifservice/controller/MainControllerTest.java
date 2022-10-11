package ru.gifservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.gifservice.service.GifService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MainController.class)
public class MainControllerTest {
    private static final String SOME_URL = "someurl.com";

    private static final String CURRENCY = "RUB";


    @MockBean
    private GifService gifService;


    MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void getSomeUrlTest() throws Exception {
        when(gifService.getUrl(CURRENCY)).thenReturn(SOME_URL);
        this.mockMvc.perform(get("/api/v1/gif?currency={CURRENCY}", CURRENCY))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(SOME_URL));

    }
}
