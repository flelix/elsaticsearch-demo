package com.elasticsearch.demo.controller;

import com.elasticsearch.demo.models.Product;
import com.elasticsearch.demo.services.ProductSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
public class SearchController {

    @Autowired
    private ProductSearchService searchService;

    @ResponseBody
    @GetMapping("/products")
    public List<Product> fetchByNameOrDesc(@RequestParam(value = "q", required = false) String query) {
        log.info("searching by name {}",query);
        List<Product> products = searchService.processSearch(query) ;
        log.info("products {}",products);
        return products;
    }

    @GetMapping("/suggestions")
    @ResponseBody
    public List<String> fetchSuggestions(@RequestParam(value = "q", required = false) String query) {
        log.info("fetch suggests {}",query);
        List<String> suggests = searchService.fetchSuggestions(query);
        log.info("suggests {}",suggests);
        return suggests;
    }
}
