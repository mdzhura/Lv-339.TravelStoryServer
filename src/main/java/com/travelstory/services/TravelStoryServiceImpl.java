package com.travelstory.services;

import com.travelstory.dao.TravelStoryDAO;
import com.travelstory.entity.TravelStory;
import com.travelstory.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelStoryServiceImpl implements TravelStoryService {
    @Autowired
    TravelStoryDAO travelStoryDAO;

    @Override
    public TravelStory addTravelStory(TravelStory travelStory) {
        TravelStory travelStory1 = travelStoryDAO.saveAndFlush(travelStory);
        return travelStory;
    }

    @Override
    public void deleteTravelStory(long id) {
        travelStoryDAO.deleteById(id);
    }

    @Override
    public TravelStory getByHead(String head) {
        return travelStoryDAO.findByHead(head);
    }

    @Override
    public TravelStory editTravelStory(TravelStory travelStory) {
        return travelStoryDAO.saveAndFlush(travelStory);
    }

    @Override
    public List<TravelStory> getAll() {
        return travelStoryDAO.findAll();
    }

    @Override
    public TravelStory getById(long id) {
        return null;
        // return travelStoryDAO.findById(id).orElseThrow(() ->
        // new EntityNotFoundException(String.format("TravelStory with id '%s' not found", id,"sdknfiosdv")));
    }

    @Override
    public List<TravelStory> getByUserOwner(User userOwner) {
        return (List<TravelStory>) travelStoryDAO.findByUserOwner(userOwner);
    }
}
