package com.boseyo.backend.service

import com.boseyo.backend.entity.MemberEntity
import com.boseyo.backend.repository.MemberRepo
import java.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MemberService(@Autowired val memberRepo: MemberRepo) {
    fun signup(username: String, email: String, password: String): MemberEntity? {
        val member =
                MemberEntity(
                        0,
                        username,
                        email,
                        password,
                        role = 1,
                        status = 1,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                )
        memberRepo.save(member)
        return memberRepo.save(member)
    }
}
