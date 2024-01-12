package com.example.howmanyeat.api;

import com.example.howmanyeat.domain.Member;
import com.example.howmanyeat.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController // ResponseBody 랑 Controller 합친 것
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;


    // v1의 문제점 : 너무 날것 그대로의 결과가 나간다.
    // 엔티티를 외부에 노출하게되면 엔티티에 프레젠테이션 계층을 위한 로직이 추가될 수 있어
    // entity list의 array를 반환하기때문에 확장성이 매우 떨어진다.(count라던지 추가기능이 필요할 시)
    // 결론 : API 응답 스펙에 맞추어 별도의 DTO를 반환한다.
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    // Result 라는 껍데기를 씌워서 유연성을 고려함
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    // v1이 주는 유일한 장점 : 클래스를 안만들어도 된다(잘못 건들면 망한다. 장점이라 할수도 없다.)
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    // v2가 주는 장점
    // 1. 엔티티에 변화가 있을 때 컴파일에러로 어느부분을 수정해줘야할지 알려줘서 서비스를 안정적으로 운영 가능
    // 2. API 문서를 까보지 않아도 멤버에서 어느 값이 파라미터로 넘어오는지 알 수 있다.
    // 3. validation 도 entity 에 직접 설정해주는 것이 아니라 dto 에 설정해줘서 다양성 및 유지보수에 큰 이점이 있다.
    // 4. entity 를 변경해도 외부에서 쏴주는 API 스펙에는 변함이 없어도 된다.(협업에 용이)

    // 5. v1과 v2 모두 지연로딩이 발생하는 문제가 있다.
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setName((request.getName()));

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName()); // 얘는 커맨드
        Member findMember = memberService.findOne(id); // 얘는 쿼리
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }
    @Data
    @AllArgsConstructor // 모든 파라미터를 다 넘기는 생성자가 필요하게 된다.
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

}
