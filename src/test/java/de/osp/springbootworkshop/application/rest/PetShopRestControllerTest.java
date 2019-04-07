package de.osp.springbootworkshop.application.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.osp.springbootworkshop.domain.model.Pet;
import de.osp.springbootworkshop.domain.service.PetShopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * @author Denny
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PetShopRestController.class)
public class PetShopRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @MockBean
    private PetShopService service;

    @Autowired
    private ObjectMapper objectMapper;

    private String toJSON(Object o) throws Exception {
        return objectMapper.writeValueAsString(o);
    }

    @Test
    public void testListPets() throws Exception {
        Pet klaus = Pet.builder()
                .name("Klaus")
                .type("Hamster")
                .birthDay(LocalDate.of(2019, 4, 13))
                .price(BigDecimal.valueOf(20))
                .build();

        Pet rubert = Pet.builder()
                .name("Rubert")
                .type("Hund")
                .birthDay(LocalDate.of(2018, 9, 18))
                .price(BigDecimal.valueOf(550))
                .build();

        Pet blacky = Pet.builder()
                .name("Blacky")
                .type("Katze")
                .birthDay(LocalDate.of(2018, 12, 12))
                .price(BigDecimal.valueOf(350))
                .build();

        when(service.listPets())
                .thenReturn(Arrays.asList(klaus, rubert, blacky));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/petshop/pets")
                .accept(MediaType.APPLICATION_JSON_UTF8);
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testCreatePetWithInvalidRequest() throws Exception {
        Pet rex = Pet.builder()
                .name("Rex")
                .birthDay(LocalDate.of(2018, 10, 13))
                .price(BigDecimal.valueOf(750))
                .build();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/petshop/pets")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(toJSON(rex));
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testCreatePetWithValidRequest() throws Exception {
        Pet rex = Pet.builder()
                .name("Rex")
                .type("Hund")
                .birthDay(LocalDate.of(2018, 10, 13))
                .price(BigDecimal.valueOf(750))
                .build();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/petshop/pets")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(toJSON(rex));
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(toJSON(rex)));
    }

    @Test
    public void testDeletePetWithInvalidRequest() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/petshop/pets/{name}", "Unsinkable")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testDeletePetWithValidRequest() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/petshop/pets/{name}", "Klaus")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}