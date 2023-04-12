package com.boseyo.backend.entity

import jakarta.persistence.*
import lombok.Data
import java.time.LocalDateTime
import java.util.*


@Entity
@Data
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var email: String,
    var password: String,
    var username: String,
    var roles: String,
    var created: LocalDateTime,
    var updated: LocalDateTime,
    var deleted: LocalDateTime? = null,
    var enabled: Boolean
) {
    fun getRoleList(): MutableList<Array<String>> {
        return if (roles.isNotEmpty()) mutableListOf(roles.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) else ArrayList()
    }
}