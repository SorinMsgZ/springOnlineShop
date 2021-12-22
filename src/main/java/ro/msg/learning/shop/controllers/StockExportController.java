package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;

import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.StockExportDTO;

import ro.msg.learning.shop.services.CsvTranslator;
import ro.msg.learning.shop.services.StockExportService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StockExportController {

    private final StockExportService stockExportService;

    @GetMapping(value = "/stocks/export/{locationId}", produces = "text/csv")
    public List<StockExportDTO> exportingStockByLocationId(@PathVariable int locationId, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stocks.csv");
        return stockExportService.exportingStockByLocationId(locationId);
    }

    /*  @PostMapping(value = "/stocks/export", consumes = "text/csv")
      public List<StockExportDTO> importStocks(@RequestBody InputStream body) throws IOException {
          CsvTranslator<StockExportDTO> csvTranslator = new CsvTranslator <>();
          List<StockExportDTO> inputStockExportDTOList = csvTranslator.fromCsv(StockExportDTO.class, body);
          return stockExportService.importStocks(inputStockExportDTOList);
      }*/
    @PostMapping(value = "/stocks/export", consumes = "text/csv")
    public List<StockExportDTO> importStocks(@RequestBody List<StockExportDTO> body) throws IOException {
        return stockExportService.importStocks(body);
    }

   /* @PostMapping(value = "/stocks/export", consumes = "text/csv")
    public List<StockExportDTO> importStocks(@RequestBody List<StockExportDTO> body) throws IOException {

        return stockExportService.importStocks(body);
    }*/

}
