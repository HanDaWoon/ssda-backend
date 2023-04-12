package com.boseyo.backend.config.auth

import com.boseyo.backend.entity.UserEntity
import com.boseyo.backend.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
@RequiredArgsConstructor
class PrincipalDetailsService(@Autowired val userRepository: UserRepository) : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        println("PrincipalDetailsService : 진입")
        val user: UserEntity? = userRepository.findByUsername(username)

        // session.setAttribute("loginUser", user);
        return PrincipalDetails(user)
    }

    fun join(email: String, password: String, username: String, role: String): UserEntity {
        val user = UserEntity(
            email = email,
            password = password,
            username = username,
            roles = role,
            created = LocalDateTime.now(),
            updated = LocalDateTime.now(),
            enabled = true
        )
        return userRepository.save(user)
    }
}