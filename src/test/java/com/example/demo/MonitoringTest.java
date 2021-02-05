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
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(Monitoring.class)
class MonitoringTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyHealthIndicator myHealthIndicator;

    @Test
    void health() throws Exception {
        //given
        given(myHealthIndicator.health()).willReturn(Health.status(Status.UP).build());

        //when
        final ResultActions actions = mockMvc.perform(get("/monitor/l7check"))
            .andDo(print());

        //then
        actions.andExpect(status().isOk());
    }

    @Test
    void up() throws Exception {
        //given
        given(myHealthIndicator.up()).willReturn(Health.up().build());

        //when
        final ResultActions actions = mockMvc.perform(get("/monitor/l7check/start"))
            .andDo(print());

        //then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.status", Matchers.is(Status.UP.toString())));
    }


    @Test
    void down() throws Exception {
        //given
        given(myHealthIndicator.down()).willReturn(Health.down().build());

        //when
        final ResultActions actions = mockMvc.perform(get("/monitor/l7check/stop"))
            .andDo(print());

        //then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.status", Matchers.is(Status.DOWN.toString())));
    }
}