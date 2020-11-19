package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemMovieController {

    private final ItemService itemService;

    @GetMapping("/items/createItemFormMovie")
    public String CreateMovie(Model model){
        model.addAttribute("form", new MovieForm());
        return "items/createItemFormMovie";
    }

    @PostMapping("/items/new/movie")
    public String create(MovieForm form){
        Movie movie = new Movie();
        movie.setName(form.getName());
        movie.setPrice(form.getPrice());
        movie.setStockQuantity(form.getStockQuantity());
        movie.setActor(form.getActor());
        movie.setDirector(form.getDirector());

        itemService.saveItem(movie);
        return "redirect:/items";
    }
}
