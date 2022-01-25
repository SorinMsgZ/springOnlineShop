package ro.msg.learning.shop.dto;

import lombok.Data;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ro.msg.learning.shop.services.MapquestKeyUrl;

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

    public static ProximityResponseDTO returnProximityResponse(List<String> listLocationsToBeCompared,
                                                               MapquestKeyUrl mapquestKeyUrl) {

        ProximityRequestOptionDTO proximityRequestOptionDTO = ProximityRequestOptionDTO.builder()
                .manyToOne("false")
                .build();
        ProximityRequestDTO proximityRequestDTO = ProximityRequestDTO.builder()
                .locations(listLocationsToBeCompared)
                .options(proximityRequestOptionDTO)
                .build();


        HttpHeaders headers = new HttpHeaders();

        headers.set(mapquestKeyUrl.getConsumerKey(), mapquestKeyUrl.getConsumerSecret());
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<ProximityRequestDTO> request = new HttpEntity<>(proximityRequestDTO, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate
                .postForObject(
                        mapquestKeyUrl.getResourceUrl() + "?key=" +
                                mapquestKeyUrl.getConsumerKey(), request, ProximityResponseDTO.class);
    }
}
