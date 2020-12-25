package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.MemberDto;
import jpabook.jpashop.dto.MemberSearchCondition;
import jpabook.jpashop.repository.MemberRepositorySDJ;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepositorySDJ sdj;

    // 회원가입 페이지 매핑
    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    // 회원가입 버튼 클릭
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){

        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/members";
    }

    //회원 목록 , 페이징
    @GetMapping("/members")
    public String list(@PageableDefault(size = 5, sort = "id") Pageable pageable, Model model){
        Page<Member> page = sdj.findAll(pageable);
        model.addAttribute("members", page);
        return "members/memberList";
    }

    //이름으로 검색
    @GetMapping("/test")
    public Page<MemberDto> searchMemeber(MemberSearchCondition condition, Pageable pageable){
        return sdj.searchPageUsername(condition, pageable);
    }

}
