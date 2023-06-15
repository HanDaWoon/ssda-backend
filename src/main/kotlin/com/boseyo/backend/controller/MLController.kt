package com.boseyo.backend.controller

import com.boseyo.backend.dto.SvgDto
import com.boseyo.backend.service.RestTemplateService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class ResponseML(
    val status: String,
    val data: String
)
@RestController
@RequestMapping("/api/ml")
class MLController(private val restTemplateService: RestTemplateService) {
    @PostMapping("/svg")
    @PreAuthorize("hasAnyRole('USER')")
    fun getSVG(@RequestBody @Valid svgDto: SvgDto): ResponseEntity<ResponseML> {
        val test = restTemplateService.getSVG(svgDto)
        return ResponseEntity.ok(test?.let { ResponseML("success", it) })
    }
}