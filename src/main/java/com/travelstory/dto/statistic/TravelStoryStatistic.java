package com.travelstory.dto.statistic;

import com.travelstory.entity.TravelStory;
import com.travelstory.entity.TravelStoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface TravelStoryStatistic extends JpaRepository<TravelStory, Long> {
    @Query(value = "SELECT count(id) FROM travelstory.travel_story WHERE created_date between ?1 and ?2", nativeQuery = true)
    Integer countTravelStoriesCreatedDateBeetween(LocalDate begin, LocalDate end);

    Integer countTravelStoriesByTravelStoryStatusEquals(TravelStoryStatus storyStatus);

    Integer countTravelStoriesByCreatedDateAfter(LocalDateTime lastUpdateDate);
}
