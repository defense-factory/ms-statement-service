package com.defensefactory.statement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class StatementControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void health() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"));
    }

    @Test
    void statementFromYaml() throws Exception {
        mockMvc.perform(get("/statements/account-1001"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountId").value("account-1001"))
                .andExpect(jsonPath("$.period").value("2026-10"))
                .andExpect(jsonPath("$.currency").value("CAD"))
                .andExpect(jsonPath("$.closingBalance").value(1250.75));
    }

    @Test
    void zeroBalanceStatement() throws Exception {
        mockMvc.perform(get("/statements/account-1002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.closingBalance").value(0.0));
    }

    @Test
    void unknownAccountReturnsNotFound() throws Exception {
        mockMvc.perform(get("/statements/unknown"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }
}
