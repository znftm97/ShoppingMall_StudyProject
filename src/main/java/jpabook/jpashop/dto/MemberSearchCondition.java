package jpabook.jpashop.dto;

import lombok.Data;

@Data
public class MemberSearchCondition {

    private Long id;
    private String name;
    private String searchWord;
    private String searchOption;
}
