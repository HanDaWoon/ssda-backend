package com.boseyo.backend.config.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.boseyo.backend.config.auth.PrincipalDetails
import com.boseyo.backend.dto.LoginRequestDto
import com.boseyo.backend.entity.UserEntity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*


@RequiredArgsConstructor
class JwtAuthenticationFilter(authenticationManager: AuthenticationManager?) : UsernamePasswordAuthenticationFilter() {
    // Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
    // 인증 요청시에 실행되는 함수 => /login
    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        println("JwtAuthenticationFilter : 진입")

        // request에 있는 username과 password를 파싱해서 자바 Object로 받기
        val om = jacksonObjectMapper()
        var loginRequestDto: LoginRequestDto? = null
        try {
            loginRequestDto = om.readValue(request.inputStream, LoginRequestDto::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        println("JwtAuthenticationFilter : $loginRequestDto")

        // 유저네임패스워드 토큰 생성
        val authenticationToken = UsernamePasswordAuthenticationToken(
            loginRequestDto?.username,
            loginRequestDto?.password
        )
        println("JwtAuthenticationFilter : 토큰생성완료")

        // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
        // loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
        // UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
        // UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
        // Authentication 객체를 만들어서 필터체인으로 리턴해준다.

        // Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
        // Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
        // 결론은 인증 프로바이더에게 알려줄 필요가 없음.
        val authentication = authenticationManager.authenticate(authenticationToken)

        val principalDetailis = authentication.principal as PrincipalDetails
        println("Authentication : " + principalDetailis.getUser().username)
        return authentication
    }

    // JWT Token 생성해서 response에 담아주기
    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest?, response: HttpServletResponse, chain: FilterChain?,
        authResult: Authentication
    ) {
        val user: PrincipalDetails = authResult.principal as PrincipalDetails
        val jwtToken: String = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
            .withClaim("id", user.getUser().id)
            .withClaim("username", user.getUser().username)
            .sign(Algorithm.HMAC512(JwtProperties.SECRET))
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken)
    }
}