package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    /*@Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }*/

    @Transactional
    public void updateBook(Long itemId, String name, int price, int stockQuantity, String author, String isbn){
        Book book = itemRepository.findBook(itemId);
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        book.setAuthor(author);
        book.setIsbn(isbn);
    }

    @Transactional
    public void updateMovie(Long itemId, String name, int price, int stockQuantity, String director, String actor){
        Movie movie = itemRepository.findMovie(itemId);
        movie.setName(name);
        movie.setPrice(price);
        movie.setStockQuantity(stockQuantity);
        movie.setDirector(director);
        movie.setActor(actor);
    }

    @Transactional
    public void updateAlbum(Long itemId, String name, int price, int stockQuantity, String artist, String etc){
        Album album = itemRepository.findAlbum(itemId);
        album.setName(name);
        album.setPrice(price);
        album.setStockQuantity(stockQuantity);
        album.setArtist(artist);
        album.setEtc(etc);
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    @Transactional
    public void removeStock(Item item, int count){
        itemRepository.removeStockCustom(item, count);
    }


}
