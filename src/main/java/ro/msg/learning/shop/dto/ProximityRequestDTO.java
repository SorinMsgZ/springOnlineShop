package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProximityRequestDTO {

    private List<String> locations;
    private ProximityRequestOptionDTO options;

}
