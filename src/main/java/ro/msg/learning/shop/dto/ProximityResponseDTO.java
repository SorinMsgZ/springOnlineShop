package ro.msg.learning.shop.dto;

import lombok.Data;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class ProximityResponseDTO {
    private String allToAll;
    private String manyToOne;
    private List<Integer> distance;
    private List<Integer> time;
    private List<Object> locations;
    private Object info;

    public ProximityResponseDTO(String allToAll, String manyToOne, List<Integer> distance,
                                List<Integer> time, List<Object> locations, Object info) {
        this.allToAll = allToAll;
        this.manyToOne = manyToOne;
        this.distance = distance;
        this.time = time;
        this.locations = locations;
        this.info = info;
    }

    public static ProximityResponseDTO returnProximityResponse(List<String> listLocationsToBeCompared) {

        ProximityRequestOptionDTO proximityRequestOptionDTO = ProximityRequestOptionDTO.builder()
                .manyToOne("false")
                .build();
        ProximityRequestDTO proximityRequestDTO = ProximityRequestDTO.builder()
                .locations(listLocationsToBeCompared)
                .options(proximityRequestOptionDTO)
                .build();


        HttpHeaders headers = new HttpHeaders();

        headers.set("41rxtk9GH5UBBtaZOo0WDpVyDPWyyslU", "T7fgO25kf7c9dRgz");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<ProximityRequestDTO> request = new HttpEntity<>(proximityRequestDTO, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate
                .postForObject("http://www.mapquestapi.com/directions/v2/routematrix?key=41rxtk9GH5UBBtaZOo0WDpVyDPWyyslU", request, ProximityResponseDTO.class);
    }
}
