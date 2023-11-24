package com.example.howmanyeat.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController // ResponseBody 랑 Controller 합친 것
@RequiredArgsConstructor
public class MemberApiController {

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveUserV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }
    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
    @Data
    static class Member {
        private Long id;
    }

}
