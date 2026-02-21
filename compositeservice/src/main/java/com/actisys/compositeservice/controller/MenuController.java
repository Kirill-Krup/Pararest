package com.actisys.compositeservice.controller;

import com.actisys.compositeservice.dto.menu.AddMenuItem;
import com.actisys.compositeservice.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/composite/menu")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/addNewDish")
    public Mono<ResponseEntity<Boolean>> addNewDish(@RequestBody AddMenuItem menuItem) {
        return menuService.addNewDish(menuItem);
    }

}
