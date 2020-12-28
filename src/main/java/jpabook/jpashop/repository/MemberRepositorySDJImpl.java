package jpabook.jpashop.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.dto.MemberDto;
import jpabook.jpashop.dto.MemberSearchCondition;
import jpabook.jpashop.dto.QMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static jpabook.jpashop.domain.QMember.member;

@RequiredArgsConstructor
public class MemberRepositorySDJImpl implements MemberRepositorySDJCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MemberDto> searchMember(MemberSearchCondition condition, Pageable pageable) {
        QueryResults<MemberDto> results = queryFactory
                .select(new QMemberDto(
                        member.id,
                        member.name,
                        member.address))
                .from(member)
                .where(
                        usernameEq(condition.getName()),
                        idEq(condition.getId())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MemberDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression usernameEq(String usernameParam){
        return usernameParam != null ? member.name.eq(usernameParam) : null;
    }

    private BooleanExpression idEq(Long idParam){
        return idParam != null ? member.id.eq(idParam) : null;
    }


}
