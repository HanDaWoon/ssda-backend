package com.boseyo.backend.controller

import com.boseyo.backend.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/member")
class MemberController(@Autowired val memberService: MemberService) {
    @PostMapping("/signup")
    fun signup(@RequestBody member: SignupRequestEntity): String {
        memberService.signup(member.email, member.password, member.username)
        return "redirect:/member/login"
    }
}

data class SignupRequestEntity(
        val email: String,
        val password: String,
        val username: String,
)
