package com.boseyo.backend.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "member")
data class MemberEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "member_id")
        val id: Long? = null,
        @Column(name = "member_username") val username: String,
        @Column(name = "member_email") val email: String,
        @Column(name = "member_password") val password: String,
        @Column(name = "member_role") val role: Int,
        @Column(name = "member_status") val status: Int,
        @Column(name = "member_created_at") val createdAt: LocalDateTime,
        @Column(name = "member_updated_at") val updatedAt: LocalDateTime,
        @Column(name = "member_deleted_at") val deletedAt: LocalDateTime? = null
)
