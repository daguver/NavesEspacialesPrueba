package com.prueba.navesespaciales.controller;

import com.prueba.navesespaciales.model.SpaceShip;
import com.prueba.navesespaciales.service.SpaceShipService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
public class CRUDControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceShipService spaceShipService;

    @Test
    public void testGetAllProductsPageable() throws Exception {
        List<SpaceShip> spaceShips = new ArrayList<>();
        spaceShips.add(new SpaceShip(1L, "Millenium Falcon"));
        spaceShips.add(new SpaceShip(2L, "HSS Imperium"));

        Mockito.when(spaceShipService.getAllSpaceShips(Mockito.any())).thenReturn(listToPage(spaceShips,0,2));

        mockMvc.perform(MockMvcRequestBuilders.get("/spaceShip/pageable")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllProductsThatContainString() throws Exception {
        List<SpaceShip> spaceShips = new ArrayList<>();
        spaceShips.add(new SpaceShip(3L, "X-wing"));
        spaceShips.add(new SpaceShip(4L, "Y-wing"));

        Mockito.when(spaceShipService.getAllSpaceShipsByName("wing")).thenReturn(spaceShips);

        mockMvc.perform(MockMvcRequestBuilders.get("/spaceShip?name=wing")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));
    }

    @Test
    public void testGetSpaceShipById() throws Exception {
        Optional<SpaceShip> spaceShip = Optional.of(new SpaceShip(3L, "X-wing"));

        Mockito.when(spaceShipService.getSpaceShipById(3L)).thenReturn(spaceShip);

        mockMvc.perform(MockMvcRequestBuilders.get("/spaceShip/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("X-wing"));
    }

    @Test
    public void testCreateSpaceShip() throws Exception {
        SpaceShip spaceShip = new SpaceShip(4L, "SuperStarDestroyer");

        Mockito.when(spaceShipService.createSpaceShip(Mockito.any())).thenReturn(spaceShip);

        mockMvc.perform(MockMvcRequestBuilders.post("/spaceShip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"4\",\"name\":\"SuperStarDestroyer\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testUpdateSpaceShip() throws Exception {
        SpaceShip spaceShip = new SpaceShip(2L, "Y-wing");

        Mockito.when(spaceShipService.updateSpaceShip(Mockito.any(), Mockito.any())).thenReturn(spaceShip);

        mockMvc.perform(MockMvcRequestBuilders.put("/spaceShip/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Y-wing\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Y-wing"));
    }

    @Test
    public void testUpdateSpaceShipNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/spaceShip/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Y-wing\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteSpaceShip() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/spaceShip/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    public static <T> Page<T> listToPage(List<T> list, int pageNumber, int pageSize) {
        int start = pageNumber * pageSize;
        int end = Math.min((start + pageSize), list.size());

        return new PageImpl<>(list.subList(start, end), PageRequest.of(pageNumber, pageSize), list.size());
    }
}
