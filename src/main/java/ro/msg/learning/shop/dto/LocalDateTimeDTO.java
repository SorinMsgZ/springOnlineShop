package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.entities.Address;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
public class LocalDateTimeDTO {

    private int year;
    private int month;
    private int dayOfMonth;
    private int hour;
    private int minute;
    private int second;

    public LocalDateTimeDTO(int year, int month, int dayOfMonth, int hour, int minute, int second) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public LocalDateTime toEntity() {
        return LocalDateTime.of(LocalDate.of(year, month, dayOfMonth), LocalTime.of(hour, minute, second));
    }


    public static LocalDateTimeDTO of(LocalDateTime entity) {
        return LocalDateTimeDTO.builder()

                .year(entity.getYear())
                .month(entity.getMonthValue())
                .dayOfMonth(entity.getDayOfMonth())
                .hour(entity.getHour())
                .minute(entity.getMinute())
                .second(entity.getSecond())
                .build();
    }
}

