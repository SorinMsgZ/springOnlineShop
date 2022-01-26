package ro.msg.learning.shop.service.integrationTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import ro.msg.learning.shop.dto.*;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class MapQuestTest {

    @Test
    public void testGenerateAutomatedId() {

        List<String> listLocationsToBeCompared = new ArrayList<>();

        listLocationsToBeCompared.add("Denver, CO");
        listLocationsToBeCompared.add("Westminster, CO");
        listLocationsToBeCompared.add("Boulder, CO");

        ProximityRequestOptionDTO proximityRequestOptionDTO = ProximityRequestOptionDTO.builder()
                .manyToOne("true")
                .build();
        ProximityRequestDTO proximityRequestDTO = ProximityRequestDTO.builder()
                .locations(listLocationsToBeCompared)
                .options(proximityRequestOptionDTO)
                .build();

        HttpHeaders headers = new HttpHeaders();

        headers.set("41rxtk9GH5UBBtaZOo0WDpVyDPWyyslU", "T7fgO25kf7c9dRgz");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<ProximityRequestDTO> request = new HttpEntity<>(proximityRequestDTO,headers);

        RestTemplate restTemplate = new RestTemplate();
        ProximityResponseDTO proximityResponseDTO = restTemplate
                .postForObject("http://www.mapquestapi.com/directions/v2/routematrix?key=41rxtk9GH5UBBtaZOo0WDpVyDPWyyslU", request, ProximityResponseDTO.class);

        System.out.println(proximityResponseDTO);

        Integer [] expectedDistanceArrayFromNearestToFarthest = {0, 11, 28};
        List<Integer>expectedDistanceList=Arrays.asList(expectedDistanceArrayFromNearestToFarthest);
        assert proximityResponseDTO != null;
        Assert.assertEquals(expectedDistanceList,proximityResponseDTO.getDistance());
    }

}