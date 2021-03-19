package br.com.zup.chave.delecao

import br.com.zup.DeleteKeyRequest
import br.com.zup.KeyManagerServiceGrpc
import br.com.zup.validacoes.UUIDValido
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@Validated
@Controller("/api/v1/clientes/{clienteId}")
class DeletarChavePixController(private val grpcCLient: KeyManagerServiceGrpc.KeyManagerServiceBlockingStub) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Delete("/pix")
    fun deletarChave(
        @PathVariable("clienteId") @UUIDValido clienteId: UUID,
        @UUIDValido idPix: UUID
    ): HttpResponse<Any> {
        logger.info("Iniciando delecao da chave pix de id: $idPix")
        val grpcRequest = DeleteKeyRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setIdPix(idPix.toString())
            .build()

        logger.info("Iniciando chamada grpc para delecao.")
        grpcCLient.deletarChave(grpcRequest)
        logger.info("Delecao finalizada.")

        return HttpResponse.ok(mapOf(Pair("clienteId", clienteId), Pair("idPix", idPix)))
    }
}