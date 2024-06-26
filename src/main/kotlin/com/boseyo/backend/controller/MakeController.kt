package com.boseyo.backend.controller

import com.boseyo.backend.dto.MakeDto
import com.boseyo.backend.service.MakeService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class ResponseMake(
    val status: String,
    val data: String
)

@RestController
@RequestMapping("/api/make")
class MakeController(private val makeService: MakeService) {
    @PostMapping("/draw")
    @PreAuthorize("hasAnyRole('USER')")
    fun draw(@RequestBody @Valid makeDto: MakeDto): ResponseEntity<ResponseMake> {
        val response = makeService.draw(makeDto)
        return ResponseEntity.ok(response?.let { ResponseMake("success", it) })
    }
}