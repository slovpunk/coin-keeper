package ru.didyk.coinkeeper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.didyk.coinkeeper.model.Account;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/product-category-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProductCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    /*
    TODO: java.lang.AssertionError: No value at JSON path "$.sum"
    @TestContainers
    поднять контейнер в докере
     */
    @Test
    void getCategoryById() throws Exception {
        mockMvc.perform(get(AccountController.PATH + "{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.sum", is(10000)))
                .andExpect(jsonPath("$.title", is("FirstCategory")))
                .andExpect(jsonPath("$.account_id", is(1L)));

    }

    @Test
    void addProductCategory() {
    }

    @Test
    void addPurchasesInCategory() {
    }

    @Test
    void deleteProductCategory() {
    }
}