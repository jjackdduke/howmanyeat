package com.example.howmanyeat.repository;

import com.example.howmanyeat.domain.Meal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MealRepository {

    private final EntityManager em;

    public void save(Meal meal) { em.persist(meal); }

//    public Meal findRecentOne(Long id) { return em.find(Meal.class, id); }

    public Meal findRecentOne() {
        List<Meal> resultList = em.createQuery("select m from Meal m order by mealTime desc", Meal.class)
                .getResultList();

        if (resultList.isEmpty()) {
            return null; // 또는 다른 기본값을 반환할 수 있음
        }

        return resultList.get(0);
    }

    public List<Meal> findByMember(Long id) {
        return em.createQuery("select m from Meal m where m.member =:id", Meal.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Meal> findByMemberAndLocalDate(Long id, LocalDate localDate) {
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);
        return em.createQuery("select m from Meal m where m.member = :id and m.mealTime >= :startOfDay and m.mealTime <= :endOfDay", Meal.class)
                .setParameter("id", id)
                .setParameter("startOfDay", startOfDay)
                .setParameter("endOfDay", endOfDay)
                .getResultList();
    }

}
