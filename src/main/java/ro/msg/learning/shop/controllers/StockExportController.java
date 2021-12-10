package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.dto.StockExportDTO;
import ro.msg.learning.shop.services.StockExportService;

import javax.servlet.http.HttpServletResponse;

import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StockExportController {

    private final StockExportService stockExportService;

//    value = "/stocks/{locationId}", produces = "text/plain; charset=utf-8"

    @GetMapping(value = "/stocks/export/{locationId}", produces = "text/csv")
    public List<StockExportDTO> exporting(@PathVariable int locationId, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stocks.csv");
        return stockExportService.exporting(locationId);
    }
}
