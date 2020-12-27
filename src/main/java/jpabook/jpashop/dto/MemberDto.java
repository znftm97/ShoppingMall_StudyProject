package jpabook.jpashop.dto;

import com.querydsl.core.annotations.QueryProjection;
import jpabook.jpashop.domain.Address;
import lombok.Data;

@Data
public class MemberDto {

    private Long id;
    private String name;
    private Address address;

    @QueryProjection
    public MemberDto(Long id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
