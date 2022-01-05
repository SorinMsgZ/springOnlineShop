package ro.msg.learning.shop.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProximityResponseDTO {
    private String allToAll;
    private String manyToOne;
    private List<Integer> distance;
    private List<Integer> time;
    private List<Object> locations;
    private Object info;
}
