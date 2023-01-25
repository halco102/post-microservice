package com.reddit.post.controller.category;

import com.reddit.post.dto.category.CategoryRequestDto;
import com.reddit.post.service.category.ICategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategory iCategory;


    @PostMapping
    public ResponseEntity<?> saveCategory(@RequestBody CategoryRequestDto requestDto) {
        return new ResponseEntity<>(iCategory.saveCategory(requestDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        return new ResponseEntity<>(iCategory.getCategoryById(id), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String name) {
        return new ResponseEntity<>(iCategory.getCategoryByName(name), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return new ResponseEntity<>(iCategory.getAllCategories(), HttpStatus.OK);
    }

}
