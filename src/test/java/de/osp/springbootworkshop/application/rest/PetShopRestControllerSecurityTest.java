package de.osp.springbootworkshop.application.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.osp.springbootworkshop.domain.model.Pet;
import de.osp.springbootworkshop.domain.model.PetType;
import de.osp.springbootworkshop.domain.service.PetShopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Denny
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PetShopRestController.class)
public class PetShopRestControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @MockBean
    private PetShopService service;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    public static class MockMvcConfig {
        @Bean
        public MockMvc mockMvc(WebApplicationContext applicationContext) {
            return MockMvcBuilders.webAppContextSetup(applicationContext)
                    .apply(springSecurity())
                    .build();
        }
    }

    @Test
    public void testListPetsWithNoCredentials() throws Exception {
        Pet klaus = new Pet("Klaus", new PetType("Hamster"), LocalDate.of(2019, 4, 13), BigDecimal.valueOf(20));
        Pet rubert = new Pet("Rubert",new PetType("Hund"), LocalDate.of(2018, 9, 18), BigDecimal.valueOf(550));
        Pet blacky = new Pet("Blacky",new PetType("Katze"), LocalDate.of(2018, 12, 12),  BigDecimal.valueOf(350));

        when(service.listPets())
                .thenReturn(Arrays.asList(klaus, rubert, blacky));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/petshop/pets")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .with(anonymous());
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testListPetsWithInvalidCredentials() throws Exception {
        Pet klaus = new Pet("Klaus", new PetType("Hamster"), LocalDate.of(2019, 4, 13), BigDecimal.valueOf(20));
        Pet rubert = new Pet("Rubert",new PetType("Hund"), LocalDate.of(2018, 9, 18), BigDecimal.valueOf(550));
        Pet blacky = new Pet("Blacky",new PetType("Katze"), LocalDate.of(2018, 12, 12),  BigDecimal.valueOf(350));

        when(service.listPets())
                .thenReturn(Arrays.asList(klaus, rubert, blacky));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/petshop/pets")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .with(httpBasic("invaliduser", "invalidpassword"));
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testListPetsWithInvalidRole() throws Exception {
        Pet klaus = new Pet("Klaus", new PetType("Hamster"), LocalDate.of(2019, 4, 13), BigDecimal.valueOf(20));
        Pet rubert = new Pet("Rubert",new PetType("Hund"), LocalDate.of(2018, 9, 18), BigDecimal.valueOf(550));
        Pet blacky = new Pet("Blacky",new PetType("Katze"), LocalDate.of(2018, 12, 12),  BigDecimal.valueOf(350));

        when(service.listPets())
                .thenReturn(Arrays.asList(klaus, rubert, blacky));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/petshop/pets")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .with(httpBasic("supplier", "supplier"));
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}