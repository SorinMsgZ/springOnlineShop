package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.services.StockExportService;

import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StockExportController {

    private final StockExportService stockExportService;

//    value = "/goods/{locationId}", produces = "text/plain; charset=utf-8"

    @GetMapping(value = "/goods/{locationId}", produces = "text/plain; charset=utf-8")
    public List<StockDTO> exporting(@PathVariable int locationId) {
        return stockExportService.exporting(locationId);
    }
}
