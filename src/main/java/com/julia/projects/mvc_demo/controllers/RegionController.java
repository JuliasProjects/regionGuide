package com.julia.projects.mvc_demo.controllers;

import com.julia.projects.mvc_demo.entities.Region;
import com.julia.projects.mvc_demo.mappers.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class RegionController {

    @Autowired
    private RegionMapper regionMapper;

    @GetMapping(value = "/regions")
    @Cacheable(cacheNames = "regions", key = "#id")
    public Region read(@RequestParam int id) {
        return regionMapper.findRegionById(id);
    }

    @PatchMapping(value = "/regions")
    @CachePut(cacheNames = "regions", key = "#id")
    public Region update(@RequestParam int id, @RequestParam Map<String, String> params) {
        Region regionById = regionMapper.findRegionById(id);
        if (params.containsKey("name"))
            regionById.setName(params.get("name"));
        if (params.containsKey("shortname"))
            regionById.setShortname(params.get("shortname"));
        regionMapper.update(regionById);
        return regionById;
    }

    @DeleteMapping(value = "/regions")
    @CacheEvict(cacheNames = "regions", key = "#id")
    public void delete(@RequestParam int id) {
        regionMapper.delete(id);
    }

    @PutMapping(value = "/regions")
    public void add(@RequestParam String name, @RequestParam String shortname) {
        if (name.isEmpty() || shortname.isEmpty())
            throw new IllegalArgumentException("The parameter cannot be empty");
        regionMapper.insert(name, shortname);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String errorHandler(Exception e) {
        return "Error occurred: " + e.getMessage();
    }
}

