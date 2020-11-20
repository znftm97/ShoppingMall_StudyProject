package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
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

    // 아이템 선택 페이지로 매핑
    @GetMapping("/items/new")
    public String createForm(Model model) {
        //model.addAttribute("form", new BookForm());
        return "items/createItemFormAll";
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

    // 상품 목록 클릭시 리스트 화면으로 매핑
    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    //상품 수정 화면 렌더링
    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findOne(itemId);

        if(item.getItemType() == "Book"){
            BookForm form = new BookForm();
            form.setId(item.getId());
            form.setName(item.getName());
            form.setPrice(item.getPrice());
            form.setStockQuantity(item.getStockQuantity());
            model.addAttribute("form", form);
            return "items/updateItemForm";

        }else if(item.getItemType() == "Movie"){
            MovieForm form = new MovieForm();
            form.setId(item.getId());
            form.setName(item.getName());
            form.setPrice(item.getPrice());
            form.setStockQuantity(item.getStockQuantity());

            model.addAttribute("form", form);
            return "items/updateItemFormMovie";

        }else if(item.getItemType() == "Album"){
            AlbumForm form = new AlbumForm();
            form.setId(item.getId());
            form.setName(item.getName());
            form.setPrice(item.getPrice());
            form.setStockQuantity(item.getStockQuantity());

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

        itemService.updateItem(form.getId(), form.getName(), form.getPrice(), form.getStockQuantity());
        return "redirect:/items";
    }
}
