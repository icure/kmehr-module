package org.taktik.icure.errors

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.icure.cardinal.sdk.utils.RequestStatusException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.ServerWebInputException
import org.taktik.couchdb.exception.CouchDbConflictException
import org.taktik.icure.exceptions.ForbiddenRequestException
import org.taktik.icure.exceptions.MissingRequirementsException
import org.taktik.icure.exceptions.NotFoundRequestException
import org.taktik.icure.exceptions.PasswordTooShortException
import org.taktik.icure.exceptions.QuotaExceededException
import org.taktik.icure.exceptions.TooManyResultsException

import reactor.core.publisher.Mono
import java.io.IOException

@Configuration
class GlobalErrorHandler : ErrorWebExceptionHandler {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    private val objectMapper = ObjectMapper().registerKotlinModule()

    override fun handle(exchange: ServerWebExchange, ex: Throwable) = exchange.response.let { r ->

        val bufferFactory = r.bufferFactory()

        r.headers.contentType = MediaType.APPLICATION_JSON
        r.writeWith(
            Mono.just(ex).flatMap { e ->
                when (e) {
                    is RequestStatusException -> bufferFactory.toBuffer(e.message).also {
                        when (e.statusCode) {
                            401 -> r.statusCode = HttpStatus.UNAUTHORIZED
                            403 -> r.statusCode = HttpStatus.FORBIDDEN
                            404 -> r.statusCode = HttpStatus.NOT_FOUND
                            409 -> r.statusCode = HttpStatus.CONFLICT
                            else -> r.statusCode = HttpStatus.BAD_REQUEST
                        }
                    }

                    is IOException -> bufferFactory.toBuffer(e.message).also { r.statusCode = HttpStatus.BAD_REQUEST }
                    is TooManyResultsException -> bufferFactory.toBuffer(
                        e.message,
                        TooManyResultsException.EXCEPTION_DETAIL
                    ).also { r.statusCode = HttpStatus.UNPROCESSABLE_ENTITY }

                    is PasswordTooShortException -> bufferFactory.toBuffer(e.message)
                        .also { r.statusCode = HttpStatus.NOT_ACCEPTABLE }

                    is NotFoundRequestException -> bufferFactory.toBuffer(e.message)
                        .also { r.statusCode = HttpStatus.NOT_FOUND }

                    is ForbiddenRequestException -> bufferFactory.toBuffer(e.message)
                        .also { r.statusCode = HttpStatus.FORBIDDEN }

                    is UnauthorizedException -> bufferFactory.toBuffer(e.message)
                        .also { r.statusCode = HttpStatus.UNAUTHORIZED }

                    is IllegalArgumentException -> bufferFactory.toBuffer(e.message)
                        .also { r.statusCode = HttpStatus.BAD_REQUEST }

                    is CouchDbConflictException -> bufferFactory.toBuffer(e.message)
                        .also { r.statusCode = HttpStatus.CONFLICT }

                    is MissingRequirementsException -> bufferFactory.toBuffer(e.message)
                        .also { r.statusCode = HttpStatus.BAD_REQUEST }

                    is QuotaExceededException -> bufferFactory.toBuffer(e.message)
                        .also { r.statusCode = HttpStatus.PAYMENT_REQUIRED }

                    is org.springframework.security.access.AccessDeniedException -> bufferFactory.toBuffer(e.message)
                        .also { r.statusCode = HttpStatus.UNAUTHORIZED }

                    is ServerWebInputException -> bufferFactory.toBuffer(e.reason)
                        .also { r.statusCode = HttpStatus.BAD_REQUEST }

                    else -> bufferFactory.toBuffer(e.message).also {
                        r.statusCode = HttpStatus.INTERNAL_SERVER_ERROR
                        log.error("${exchange.request.id} - ${e.message}", e)
                    }
                }
            }
        )
    }

    private fun DataBufferFactory.toBuffer(info: String?, exceptionDetail: String? = null) = Mono.just(
        try {
            val error = info?.let { HttpError(it, exceptionDetail) } ?: "Unknown error".toByteArray()
            this.wrap(objectMapper.writeValueAsBytes(error))
        } catch (e: JsonProcessingException) {
            this.wrap("".toByteArray())
        }
    )

    @JsonInclude(JsonInclude.Include.NON_NULL)
    class HttpError internal constructor(val message: String, val exceptionDetail: String? = null)
}
