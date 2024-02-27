package com.prueba.navesespaciales.service;

import com.prueba.navesespaciales.model.SpaceShip;
import com.prueba.navesespaciales.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceShipService {

    @Autowired
    SpaceRepository spaceRepository;

    public Page<SpaceShip> getAllSpaceShips(Pageable pageable) {
        return spaceRepository.findAll(pageable);
    }

    public List<SpaceShip> getAllSpaceShipsByName(String name) {
        return spaceRepository.findByNameContaining(name);
    }

    public Optional<SpaceShip> getSpaceShipById(Long id) {
        return spaceRepository.findById(id);
    }

    @CacheEvict("spaceShips")
    public SpaceShip createSpaceShip(SpaceShip item) {
        return spaceRepository.save(item);
    }

    @CacheEvict("spaceShips")
    public SpaceShip updateSpaceShip(Long id, SpaceShip newShip) {
        if (spaceRepository.existsById(id)) {
            newShip.setId(id); // Ensure the ID matches the path variable
            return spaceRepository.save(newShip);
        } else {
            return null; // Handle the case where the item doesn't exist
        }
    }

    @CacheEvict("spaceShips")
    public void deleteSpaceShip(Long id) {
        spaceRepository.deleteById(id);
    }
}
