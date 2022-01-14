package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.dto.RevenueExportDTO;
import ro.msg.learning.shop.services.RevenueExportService;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RevenueExportController {

    private final RevenueExportService revenueExportService;

    @GetMapping(value ="/revenues/export/csv/{localDateAsString}", produces = "text/csv")
    public List<RevenueExportDTO> exportingRevenueByDate(@PathVariable String localDateAsString, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=revenues.csv");
        LocalDate localDate = LocalDate.parse(localDateAsString);
        return revenueExportService.readByDate(localDate);
    }

    @GetMapping("/revenues/export/json/{localDateAsString}")
    public List<RevenueExportDTO> exportingRevenueByDate(@PathVariable String localDateAsString) {
        LocalDate localDate = LocalDate.parse(localDateAsString);
        return revenueExportService.readByDate(localDate);
    }

}
