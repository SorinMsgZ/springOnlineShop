package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.services.StockExportService;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpHeaders;
import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StockExportController {

    private final StockExportService stockExportService;

//    value = "/stocks/{locationId}", produces = "text/plain; charset=utf-8"

    @GetMapping(value = "/stocks/{locationId}", produces = "text/csv")
    public List<StockDTO> exporting(@PathVariable int locationId, HttpServletResponse response) {
//        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stocks.csv");
        return stockExportService.exporting(locationId);
    }
}
