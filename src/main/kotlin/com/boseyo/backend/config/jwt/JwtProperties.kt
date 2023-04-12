package com.boseyo.backend.config.jwt

interface JwtProperties {
    companion object {
        const val SECRET = "secret_key" // 우리 서버만 알고 있는 비밀값
        const val EXPIRATION_TIME = 864000000 // 10일 (1/1000초)
        const val TOKEN_PREFIX = "Bearer "
        const val HEADER_STRING = "Authorization"
    }
}