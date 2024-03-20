package com.mark.springbootmultipledatasource.clazz.controller;

import com.mark.springbootmultipledatasource.clazz.entity.Clazz;
import com.mark.springbootmultipledatasource.clazz.repository.ClazzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class")
public class ClazzController {

    @Autowired
    ClazzRepository clazzRepository;

    @PostMapping
    public ResponseEntity<Clazz> saveClazz(@RequestBody Clazz clazz) {
        Clazz savedClazz = clazzRepository.save(clazz);
        return new ResponseEntity<>(savedClazz, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clazz> findById(@RequestBody Long id) {
        Clazz clazz = clazzRepository.findById(id).orElseThrow(() -> new RuntimeException("Class not found by id " + id));
        return new ResponseEntity<>(clazz, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Clazz>> findAllClasses() {
        List<Clazz> clazzes = clazzRepository.findAll();
        return new ResponseEntity<>(clazzes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clazz> update(@PathVariable Long id, @RequestBody Clazz clazz) {
        Clazz updateClazz = clazzRepository.findById(id).orElseThrow(() -> new RuntimeException("Class not found by id " + id));
        updateClazz.setName(clazz.getName());
        Clazz savedClazz = clazzRepository.save(updateClazz);
        return new ResponseEntity<>(savedClazz, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        clazzRepository.deleteById(id);
        return new ResponseEntity<>("Class has been deleted!", HttpStatus.OK);
    }

}
