package com.example.howmanyeat.service;
import com.example.howmanyeat.api.OpenApiController;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final WebClient webClient;

    @Value("${OPEN_API_KEY}")
    private String openApiKey;

    public Optional<?> useWebClientV1(String foodName) {

        Optional<?> optionalDto = Optional.of(webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(String.format("/%s/I2790/json/1/5/DESC_KOR=%s", openApiKey, foodName))
                        .build())
                .retrieve()
                .bodyToMono(Optional.class)
                .block());
        return optionalDto;
    }

        public ResultEntity useWebClientV2(String foodName) {

        ResultEntity resultEntity = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(String.format("/%s/I2790/json/1/5/DESC_KOR=%s", openApiKey, foodName))
                        .build())
                .retrieve()
                .bodyToMono(ResultEntity.class)
                .block();
            System.out.println(resultEntity + "@@@");
        return resultEntity;
    }
    @Data
    @NoArgsConstructor
    public class ResultEntity {
        @JsonProperty("I2790")
        private I2790 I2790;
    }

    @Data
    public class I2790 {
        private String total_count;
        private List<NutritionInfos> row;
        private Result RESULT;
    }

    @Data
    public class NutritionInfos {
        private String NUTR_CONT8;
        private String NUTR_CONT9;
        private String NUTR_CONT4;
        private String NUTR_CONT5;
        private String NUTR_CONT6;
        private String NUM;
        private String NUTR_CONT7;
        private String NUTR_CONT1;
        private String NUTR_CONT2;
        private String SUB_REF_NAME;
        private String NUTR_CONT3;
        private String RESEARCH_YEAR;
        private String MAKER_NAME;
        private String GROUP_NAME;
        private String SERVING_SIZE;
        private String SERVING_UNIT;
        private String SAMPLING_REGION_NAME;
        private String SAMPLING_MONTH_CD;
        private String SAMPLING_MONTH_NAME;
        private String DESC_KOR;
        private String SAMPLING_REGION_CD;
        private String FOOD_CD;
    }
    @Data
    public class Result {
        private String MSG;
        private String CODE;
    }


}
