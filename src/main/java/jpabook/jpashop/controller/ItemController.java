package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.repository.ItemRepositorySDJ;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemRepositorySDJ sdj;

    // 아이템 선택 페이지로 매핑
    @GetMapping("/items/new")
    public String createForm(Model model) {
        //model.addAttribute("form", new BookForm());
        return "items/SelectItem";
    }

    // Book 클릭시 등록 페이지로 매핑
    @GetMapping("/items/createItemForm")
    public String CreateBook(Model model){
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    // 등록 페이지에서 버튼 클릭시 Book 생성 후 목록화면으로 매핑
    @PostMapping("items/new/Book")
    public String create(BookForm form) {
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        book.setItemType("Book");

        itemService.saveItem(book);
        return "redirect:/items";
    }

    // 상품 목록 클릭시 리스트 화면으로 매핑 , 페이징
    @GetMapping("/items")
    public String list(@PageableDefault(size = 5, sort = "id") Pageable pageable, Model model) {
        //List<Item> items = itemService.findItems();
        Page<Item> items = sdj.findAll(pageable);
        model.addAttribute("items", items);
        return "items/itemList";
    }

    //상품 수정 화면 렌더링
    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findOne(itemId);

        if(item.getItemType() == "Book"){
            Book itemBook = (Book) itemService.findOne(itemId);
            BookForm form = new BookForm();
            form.setId(item.getId());
            form.setName(item.getName());
            form.setPrice(item.getPrice());
            form.setStockQuantity(item.getStockQuantity());
            form.setIsbn(itemBook.getIsbn());
            form.setAuthor(itemBook.getAuthor());

            model.addAttribute("form", form);
            return "items/updateItemForm";

        }else if(item.getItemType() == "Movie"){
            Movie itemMovie = (Movie) itemService.findOne(itemId);
            MovieForm form = new MovieForm();
            form.setId(item.getId());
            form.setName(item.getName());
            form.setPrice(item.getPrice());
            form.setStockQuantity(item.getStockQuantity());
            form.setDirector(itemMovie.getDirector());
            form.setActor(itemMovie.getDirector());

            model.addAttribute("form", form);
            return "items/updateItemFormMovie";

        }else if(item.getItemType() == "Album"){
            Album itemAlbum = (Album) itemService.findOne(itemId);
            AlbumForm form = new AlbumForm();
            form.setId(item.getId());
            form.setName(item.getName());
            form.setPrice(item.getPrice());
            form.setStockQuantity(item.getStockQuantity());
            form.setArtist(itemAlbum.getArtist());
            form.setEtc(itemAlbum.getEtc());

            model.addAttribute("form", form);
            return "items/updateItemFormAlbum";
        }else {
            //에러처리..
        }

        return null;
    }

    //상품 수정
    @PostMapping("items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") ItemForm form) {
        Item item = itemService.findOne(form.getId());
        if(item.getItemType() == "Book"){
            itemService.updateBook(form.getId(), form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());
        }else if(item.getItemType() == "Movie"){
            itemService.updateMovie(form.getId(), form.getName(), form.getPrice(), form.getStockQuantity(), form.getDirector(), form.getActor());
        }else if(item.getItemType() == "Album"){
            itemService.updateAlbum(form.getId(), form.getName(), form.getPrice(), form.getStockQuantity(), form.getArtist(), form.getEtc());
        }
        return "redirect:/items";
    }

}
