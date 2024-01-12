package com.example.howmanyeat.api;

import com.example.howmanyeat.domain.Meal;
import com.example.howmanyeat.domain.Member;
import com.example.howmanyeat.service.MealService;
import com.example.howmanyeat.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class MealApiController {

    // 의존관계는 한방향으로만 가는게 좋다(ex : controller -> service -> repository)
    private final MemberService memberService;
    private final MealService mealService;

    @PostMapping("/api/v1/meal/{id}")
    public CreateMealResponse saveMealV1(@RequestBody @Valid
                                             CreateMealRequest request,
                                         @PathVariable Long id) {

        Member findMember = memberService.findOne(id);

        Meal meal = new Meal();
        meal.setFoodName(request.getFoodName());
        meal.setMealTime(request.getMealTime());
        if (request.getIsNewDay()) {
            Meal findMeal = mealService.findRecentOne();
            if (findMeal == null) {
                meal.setMealType(1);
            } else {
                meal.setMealType(findMeal.getMealType() + 1);
            }
        } else {
            meal.setMealType(1);
        }
        meal.setMemo(request.getMemo());
        meal.setMember(findMember);

        Long returnId = mealService.join(meal);

        return new CreateMealResponse(returnId);
    }

    @Data
    static class CreateMealRequest {
        private String foodName;
        private float calorie;
        private String memo;
        private LocalDateTime mealTime;
        private Boolean isNewDay;
    }

    @Data
    static class CreateMealResponse {
        private Long id;

        public CreateMealResponse(Long id) { this.id = id; }
    }
}
