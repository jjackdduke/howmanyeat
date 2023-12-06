package com.example.howmanyeat.service;
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

    public Object useWebClientV1(String foodName) {

        Optional<Object> optionalDto = Optional.of(webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(String.format("/%s/I2790/json/1/5/DESC_KOR=%s", openApiKey, foodName))
                        .build())
                .retrieve()
                .bodyToMono(Object.class)
                .block());
        if (optionalDto.isPresent()) {

            LinkedHashMap<String, Object> i2790HashMap = (LinkedHashMap<String, Object>) optionalDto.get();
            LinkedHashMap<String, Object> resultEntityHashMap = (LinkedHashMap<String, Object>) i2790HashMap.get("I2790");

            ArrayList<FoodDto> foodDtoArrayList = new ArrayList<>();

            for (Object nutritionInfos : (ArrayList<Object>) resultEntityHashMap.get("row")) {
                LinkedHashMap<Object, String> nutritionInfosHashMap = (LinkedHashMap<Object, String>) nutritionInfos;
                FoodDto foodDto =  new FoodDto(
                        nutritionInfosHashMap.get("NUTR_CONT8"),
                        nutritionInfosHashMap.get("DESC_KOR")
                        );
                foodDtoArrayList.add(foodDto);
            }
            return new ResultDto((String) resultEntityHashMap.get("total_count"), foodDtoArrayList);
        } else {
            return null; // 좋지않은 방법
        }
    }
    @Data
    @AllArgsConstructor
    static class ResultDto<T> {
        private String count;
        private T data;
    }
    @Data
    @AllArgsConstructor
    static class FoodDto {
        private String name;
        private String calorie;
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
    static class ResultEntity {
        @JsonProperty("I2790")
        private I2790 I2790;
    }

    @Data
    static class I2790 {
        private String total_count;
        private List<NutritionInfos> row;
        private Result RESULT;
    }

    @Data
    @AllArgsConstructor
    static class NutritionInfos {
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
    static class Result {
        private String MSG;
        private String CODE;
    }


}
