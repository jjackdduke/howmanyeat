package com.example.howmanyeat.service;

import com.example.howmanyeat.domain.Meal;
import com.example.howmanyeat.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;

    @Transactional
    public Long join(Meal meal) {
        mealRepository.save(meal);
        return meal.getMealId();
    }

    public Meal findRecentOne() { return mealRepository.findRecentOne(); }


}
