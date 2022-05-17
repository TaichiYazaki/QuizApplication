package com.example.controller;



import java.util.List;

import com.example.domain.Favorite;
import com.example.domain.LoginUser;
import com.example.domain.Quiz;
import com.example.service.FavoriteService;
import com.example.service.QuizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    public FavoriteService favoriteService;

    @Autowired
    public QuizService quizService;

    /**
     * お気に入り追加
     */
    @RequestMapping("/favoriteAdd")
    public String favorite(Integer id, Model model,@AuthenticationPrincipal LoginUser loginUser) { 
        Integer registerId=  loginUser.getRegister().getId();
        favoriteService.favorite(id, registerId);
        return "redirect:/quiz/list";
    }

    /**
     * お気に入り表示
    */
    @RequestMapping("/favoriteList")
    public String favoriteList(Model model,@AuthenticationPrincipal LoginUser loginUser) {
        List<Favorite> favoriteList = favoriteService.favoriteList(loginUser.getRegister().getId());
        model.addAttribute("favoriteList", favoriteList);
        return "favorite";
    } 

    /**
     * お気に入り削除
     */
    @RequestMapping("/favoriteDelete")
    public String favoriteDelete(Integer favoriteId) {
        favoriteService.favoriteDelete(favoriteId);
        return "redirect:/favorite/favoriteList";
    }
}
