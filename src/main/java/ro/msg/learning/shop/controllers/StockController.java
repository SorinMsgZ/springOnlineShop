package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entities.StockId;
import ro.msg.learning.shop.services.StockService;

import java.util.List;
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StockController {
    private final StockService service;

    @GetMapping("/stocks")
    public List<StockDTO> listAll() {
        return service.listAll();
    }

    @GetMapping("/stocks/{id}")
    public StockDTO readById(@PathVariable StockId id) {
        return service.readById(id);
    }

    @PostMapping("/stocks")
    public StockDTO create(@RequestBody StockDTO body) {
        return service.create(body);
    }

    @DeleteMapping("/stocks/{id}")
    public void deleteById(@PathVariable StockId id) {
        service.deleteById(id);
    }

    @PutMapping("/stocks/{id}")
    public StockDTO updateById(@PathVariable StockId id, @RequestBody StockDTO body) {
        return service.updateById(id, body);
    }
}
