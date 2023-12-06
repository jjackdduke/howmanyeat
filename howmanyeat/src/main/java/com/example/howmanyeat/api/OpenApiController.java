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
    public Object foodInfos(@RequestParam("DESC_KOR") @Valid String foodName) {
        Object o = openApiService.useWebClientV1(foodName);
        return o;
    }

}
