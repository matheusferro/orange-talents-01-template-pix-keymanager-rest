package br.com.zup.exception

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class GlobalExceptionHandler : ExceptionHandler<StatusRuntimeException, HttpResponse<Any>> {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun handle(request: HttpRequest<*>?, exception: StatusRuntimeException?): HttpResponse<Any> {

        logger.error("Uma exception foi lançada: ${exception?.status?.description}")

        val statusCode = exception?.status?.code
        val statusDescription = exception?.status?.description

        val (statusResponse, message) = when (statusCode) {
            Status.NOT_FOUND.code -> Pair(HttpStatus.NOT_FOUND, statusDescription)
            Status.INVALID_ARGUMENT.code -> Pair(HttpStatus.BAD_REQUEST, "Verifique os dados da requisição.")
            Status.ALREADY_EXISTS.code -> Pair(HttpStatus.UNPROCESSABLE_ENTITY, statusDescription)
            Status.UNAVAILABLE.code -> Pair(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro de disponibilidade.")
            else -> {
                logger.error("Ocorreu um erro inesperado na requisição.")
                Pair(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possivel completar a requisicao.")
            }
        }

        return HttpResponse.status<JsonError>(statusResponse).body(JsonError(message))
    }
}