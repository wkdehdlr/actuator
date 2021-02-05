package com.example.demo;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class MonitoringTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyHealthIndicator myHealthIndicator;

    @Test
    void health() throws Exception {
        given(myHealthIndicator.health()).willReturn(Health.status(Status.UP).build());

        mockMvc.perform(get("/monitor/l7check"))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void up() throws Exception {
        given(myHealthIndicator.up()).willReturn(Health.up().build());

        mockMvc.perform(get("/monitor/l7check/start"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", Matchers.is(Status.UP.toString())))
            .andDo(print());
    }


    @Test
    void down() throws Exception {
        given(myHealthIndicator.down()).willReturn(Health.down().build());

        mockMvc.perform(get("/monitor/l7check/stop"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status",Matchers.is(Status.DOWN.toString())))
            .andDo(print());
    }
}