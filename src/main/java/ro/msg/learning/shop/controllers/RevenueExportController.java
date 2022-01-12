package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.dto.RevenueDTO;
import ro.msg.learning.shop.services.RevenueService;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RevenueExportController {

    private final RevenueService revenueService;

    @GetMapping(value = "/revenues/export/{localDate}", produces = "text/csv")
    public List<RevenueDTO> exportingRevenueByDate(@PathVariable LocalDate localDate, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=revenues.csv");
        return revenueService.readByDate(localDate);
    }

}
