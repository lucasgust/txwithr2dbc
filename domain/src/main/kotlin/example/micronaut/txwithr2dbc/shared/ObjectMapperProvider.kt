package example.micronaut.txwithr2dbc.shared

import com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object ObjectMapperProvider {

    val mapper: ObjectMapper = jacksonObjectMapper().config()

    fun ObjectMapper.config() = apply {
        registerModule(JavaTimeModule())
        setDefaultPropertyInclusion(ALWAYS)
        disable(WRITE_DATES_AS_TIMESTAMPS)
    }
}
