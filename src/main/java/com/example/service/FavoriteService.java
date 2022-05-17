package com.example.service;

import java.util.List;

import com.example.domain.Favorite;
import com.example.repository.FavoriteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    public void favorite(Integer id, Integer administratorId) {
        favoriteRepository.insert(id, administratorId);
    }

   public List<Favorite> favoriteList(Integer administratorId) {
        return favoriteRepository.findByFavorites(administratorId);
    }

    public void favoriteDelete(Integer favoriteId) {
         favoriteRepository.delete(favoriteId);
    }

    public Favorite favoriteLoad(Integer favoriteId){
        return favoriteRepository.favoriteLoad(favoriteId);
    }
}
