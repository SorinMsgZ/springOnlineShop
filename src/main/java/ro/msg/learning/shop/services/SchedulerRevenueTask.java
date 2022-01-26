package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.RevenueDTO;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.OrderDetail;
import ro.msg.learning.shop.repositories.OrderDetailRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SchedulerRevenueTask {
    private final OrderDetailRepository orderDetailRepository;
    private final RevenueService revenueService;
    private final List<RevenueDTO> revenueList = new ArrayList<>();
    private final LocalDate dateOfCreatingOrder = LocalDate.now();

    @Scheduled(cron = "${cron.expression.daily}", zone = "${cron.expression.zone}")
    public void aggregateAndStoreSalesRevenues() {
        List<OrderDetail> orderDetailFilteredByDateList = orderDetailRepository.findAll().stream()
                .filter(orderDetail -> orderDetail.getOrder().getCreatedAt().toLocalDate().toString()
                        .equals(dateOfCreatingOrder.toString()))
                .collect(Collectors.toList());
        List<Location> locationWithRevenueForTodayList = orderDetailFilteredByDateList.stream()
                .map(orderDetail -> orderDetail.getOrder().getShippedFrom())
                .distinct()
                .collect(Collectors.toList());

        for (Location location : locationWithRevenueForTodayList) {
            BigDecimal locationTotalRevenue = BigDecimal.ZERO;

            List<BigDecimal> locationRevenueList = new ArrayList<>();

            orderDetailFilteredByDateList.stream()
                    .filter(orderDetail -> orderDetail.getOrder().getShippedFrom() == location)
                    .forEach(orderDetail -> locationRevenueList.add(orderDetail.getProduct().getPrice()
                            .multiply(BigDecimal.valueOf(orderDetail.getQuantity()))));

            for (BigDecimal revenueItem : locationRevenueList) {
                locationTotalRevenue = locationTotalRevenue.add(revenueItem);
            }

            RevenueDTO locationRevenueDTO = RevenueDTO.builder()
                    .location(location)
                    .localDate(dateOfCreatingOrder)
                    .sum(locationTotalRevenue)
                    .build();
            revenueList.add(locationRevenueDTO);
        }

        storeRevenuesIntoDatabase(revenueList);
    }


    public void storeRevenuesIntoDatabase(List<RevenueDTO> revenueList) {
        revenueList.forEach(revenueService::create);
    }

    public List<RevenueDTO> getRevenueList() {
        return revenueList;
    }


}
