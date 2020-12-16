package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

    public Book findBook(Long id){
        return em.find(Book.class, id);
    }
    public Movie findMovie(Long id){ return em.find(Movie.class, id); }
    public Album findAlbum(Long id){
        return em.find(Album.class, id);
    }

    // 주문 수량을 넘겨야되는데 재고를 넘겨버려서 재고가 0이되버림
    /*public void removeStock(Item item){
        item.removeStock(item.getStockQuantity());
    }*/

    public void removeStockCustom(Item item, int count){
        item.removeStock(count);
    }

    public int getStockQuantityCustom(Item item){
        return item.getStockQuantity();
    }

    //재고보다 주문 개수가 크면 안됨
    public void countValidate(Item item, int count){
        if (item.getStockQuantity() < count)
        {
            // 재고 부족
        }

    }

    /*public String selectType(){
        return em.createQuery("select d from Item d", Item.class);
    }*/
}
