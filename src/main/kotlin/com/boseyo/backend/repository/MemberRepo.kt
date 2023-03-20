package com.boseyo.backend.repository

import com.boseyo.backend.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepo : JpaRepository<MemberEntity, Long> {
    fun findByEmail(email: String): MemberEntity?
    fun findByUsername(username: String): MemberEntity?
}
