package com.example.howmanyeat.api;

import com.example.howmanyeat.service.OpenApiService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    private final OpenApiService openApiService;

    @GetMapping("api/v2/foodInfos")
    public ResultDto foodInfos(@RequestParam("DESC_KOR") @Valid String foodName) {
        System.out.println(openApiService.useWebClientV2(foodName) + "@@@@2");
        List<FoodDto> foodDtoList = openApiService.useWebClientV2(foodName).getI2790().getRow().stream()
                .map(m -> new FoodDto(m.getDESC_KOR(), m.getNUTR_CONT1()))
                .collect(Collectors.toList());
        return new ResultDto(foodDtoList);
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
