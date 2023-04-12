package com.boseyo.backend.dto

import lombok.Data


@Data
class LoginRequestDto {
    val username: String? = null
    val password: String? = null
}