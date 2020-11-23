package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public Movie findMovie(Long id){
        return em.find(Movie.class, id);
    }
    public Album findAlbum(Long id){
        return em.find(Album.class, id);
    }

    /*public String selectType(){
        return em.createQuery("select d from Item d", Item.class);
    }*/
}
