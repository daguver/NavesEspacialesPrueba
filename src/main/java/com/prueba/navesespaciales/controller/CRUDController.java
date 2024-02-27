package com.prueba.navesespaciales.controller;

import com.prueba.navesespaciales.model.SpaceShip;
import com.prueba.navesespaciales.service.SpaceShipService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/spaceShip")
public class CRUDController {

    @Autowired
    SpaceShipService spaceShipService;

    @GetMapping("/pageable")
    @Operation(summary = "Get with pagination", description = "Get AllShips with pagination")
    public Page<SpaceShip> getAllShips(@RequestParam(defaultValue = "0") final Integer pageNumber,
                                       @RequestParam(defaultValue = "5") final Integer size){
        return spaceShipService.getAllSpaceShips(PageRequest.of(pageNumber,size));
    }

    @GetMapping
    @Operation(summary = "Get with filter", description = "Get AllShips whose name contain a specific string")
    public List<SpaceShip> getAllShipsWithName(@RequestParam String name){
        return spaceShipService.getAllSpaceShipsByName(name);
    }

    @GetMapping("/{id}")
    @Operation(summary = "getSpaceShipById", description = "Get ship by id")
    public ResponseEntity<SpaceShip> getSpaceShipById(@PathVariable Long id){

        Optional<SpaceShip> spaceShip = spaceShipService.getSpaceShipById(id);
        return spaceShip.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "createSpaceShip", description = "Create a spaceShip")
    public ResponseEntity<SpaceShip> createSpaceShip(@RequestBody SpaceShip spaceShip){
        SpaceShip newSpaceShip = spaceShipService.createSpaceShip(spaceShip);
        return new ResponseEntity<>(newSpaceShip, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "updateSpaceShip", description = "Update a spaceShip")
    public ResponseEntity<SpaceShip> updateSpaceShip(@PathVariable Long id, @RequestBody SpaceShip spaceShip){
        SpaceShip updatedSpaceShip = spaceShipService.updateSpaceShip(id, spaceShip);
        return updatedSpaceShip != null ? new ResponseEntity<>(updatedSpaceShip, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "deleteSpaceShip", description = "Delete a spaceShip")
    public ResponseEntity<SpaceShip> deleteSpaceShip(@PathVariable Long id){
        spaceShipService.deleteSpaceShip(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
