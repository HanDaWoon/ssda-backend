package com.boseyo.backend.service

import com.boseyo.backend.dto.MakeDto
import com.boseyo.backend.entity.MakeDrawEntity
import com.boseyo.backend.repository.MakeRepository
import com.boseyo.backend.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service


@Service
class MakeService(
    private val makeRepository: MakeRepository,
    private val userRepository: UserRepository,
    private val restTemplateService: RestTemplateService
) {
    fun draw(makeDto: MakeDto): String? {
        val drawer = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val makeDrawEntity = MakeDrawEntity(
            user = userRepository.findOneWithAuthoritiesByUsername(drawer.username).orElse(null)!!,
            fontName = makeDto.fontName,
            image64 = makeDto.imageBase64,
            contentType = makeDto.contentType
        )

        return restTemplateService.sendDrawImage(makeRepository.save(makeDrawEntity))
    }
}