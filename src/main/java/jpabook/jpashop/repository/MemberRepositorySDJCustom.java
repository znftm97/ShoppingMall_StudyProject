package jpabook.jpashop.repository;

import jpabook.jpashop.dto.MemberDto;
import jpabook.jpashop.dto.MemberSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositorySDJCustom {
    Page<MemberDto> searchMember(MemberSearchCondition condition, Pageable pageable);
    /*Page<MemberDto> searchPageId(MemberSearchCondition condition, Pageable pageable)*/;
}
