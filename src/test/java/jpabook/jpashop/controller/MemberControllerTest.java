package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepositorySDJ;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberControllerTest {

    @Autowired
    MemberRepositorySDJ memberRepositorySDJ;

    @Test
    public void list(@PageableDefault(size = 5, sort = "id") Pageable pageable){
        Page<Member> page = memberRepositorySDJ.findAll(pageable);
        System.out.println("page : " + page);
    }


}