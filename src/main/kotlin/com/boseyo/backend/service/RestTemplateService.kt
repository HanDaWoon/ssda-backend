package com.boseyo.backend.service

import com.boseyo.backend.dto.SvgDto
import com.boseyo.backend.entity.MakeDrawEntity
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder


@Service
class RestTemplateService {
    val ML_API = "117.16.94.218"
    private fun createUri(): UriComponentsBuilder {
        return UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(ML_API)
            .port(8000)
    }

    fun sendDrawImage(save: MakeDrawEntity): String? {
        val uri = createUri().path("/font_generation/images")
            .pathSegment(save.user!!.username, save.fontName)
            .build()
            .toUriString()

        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val reqFontGen = save.image64?.let { ReqFontGen(it) }
        val entity = HttpEntity(reqFontGen, headers)
        return try {
            restTemplate.postForObject(
                uri, entity, String::class.java
            )
        } catch (e: Exception) {
            e.message
        }
    }

    fun getSVG(svgDto: SvgDto): String? {
        val uri = createUri().path("/sentence_request")
            .pathSegment(svgDto.fontOwner, svgDto.fontName)
            .queryParam("sentence", svgDto.text)
            .build()
            .toUriString()

        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        return try {
            val om = ObjectMapper()
            om.readValue(restTemplate.postForObject(
                uri, headers, String::class.java
            ), ResSvg::class.java).sentence
        } catch (e: Exception) {
            println(e.message)
            e.message
        }
    }
}

data class ReqFontGen(
    val imagebase64: String,
)

data class ResSvg(
    val sentence: String,
)