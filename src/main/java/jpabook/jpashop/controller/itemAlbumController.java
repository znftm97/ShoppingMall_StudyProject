package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class itemAlbumController {

    private final ItemService itemService;

    @GetMapping("/items/createItemFormAlbum")
    public String CreateAlbum(Model model){
        model.addAttribute("form", new AlbumForm());
        return "items/createItemFormAlbum";
    }

    @PostMapping("/items/new/Album")
    public String create(AlbumForm form){
        Album album = new Album();
        album.setName(form.getName());
        album.setPrice(form.getPrice());
        album.setStockQuantity(form.getStockQuantity());
        album.setArtist(form.getArtist());
        album.setEtc(form.getEtc());
        album.setItemType("album");

        itemService.saveItem(album);
        return "redirect:/items";
    }
}
