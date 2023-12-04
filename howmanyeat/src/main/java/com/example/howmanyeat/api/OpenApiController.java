package com.example.howmanyeat.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OpenApiController {

    private final RestTemplate restTemplate;

    @Value("${OPEN_API_KEY}")
    private String openApiKey;

    @GetMapping("api/v2/foodInfos")
    public ResultDto foodInfos(@RequestParam("DESC_KOR") @Valid String foodName) {
        String apiUrl = "http://openapi.foodsafetykorea.go.kr/api/" + openApiKey + "/I2790/json/1/5/DESC_KOR=" + foodName;
        ResponseEntity<ResultEntity> responseEntity = restTemplate.getForEntity(apiUrl, ResultEntity.class);
        List<FoodDto> findFood = responseEntity.getBody().I2790.row.stream()
                .map(m -> new FoodDto(m.getDESC_KOR(), m.getNUTR_CONT1()))
                .collect(Collectors.toList());
        return new ResultDto(findFood);
    }


    @Data
    @AllArgsConstructor
    static class ResultEntity {
        private I2790 I2790;
    }

    @Data
    @AllArgsConstructor
    static class I2790 {
        private String total_count;
        private List<NutritionInfos> row;
        private Result result;
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
    @AllArgsConstructor
    static class Result {
        private String MSG;
        private String CODE;
    }

    @Data
    @AllArgsConstructor
    static class ResultDto<T> {
        private T data;
    }
    @Data
    @AllArgsConstructor
    static class FoodDto {
        private String name;
        private String calorie;
    }
}
