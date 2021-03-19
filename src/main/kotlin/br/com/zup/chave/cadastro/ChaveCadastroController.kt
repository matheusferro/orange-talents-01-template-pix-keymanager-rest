package br.com.zup.chave.cadastro

import br.com.zup.KeyManagerServiceGrpc
import br.com.zup.validacoes.UUIDValido
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import javax.validation.Valid

@Validated
@Controller("/api/v1/clientes/{clienteId}")//id cliente alterado para a url, fazendo mais sentido no padrao REST
class ChaveCadastroController(val grpcClient: KeyManagerServiceGrpc.KeyManagerServiceBlockingStub) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Post("/pix")
    fun cadastroChavePix(
        @PathVariable("clienteId") @UUIDValido clienteId: UUID,
        @Body @Valid request: CadastroChavePixRequest
    ): HttpResponse<Any> {
        logger.info("Iniciando a criacao da chave pix. cliente: ${clienteId}")
        val requestGrpc = request.toRequestGrpc(clienteId)

        logger.info("Inicio comunicacao grpc.")
        val responseGrpc = grpcClient.cadastroChave(requestGrpc)
        logger.info("Comunicacao grpc concluida.")

        val uri = UriBuilder.of("/api/v1/pix/{clienteId}/pix/{idPix}")
            .expand(mutableMapOf(Pair("id", clienteId), Pair("idPix", responseGrpc.idPix)))

        return HttpResponse.created(uri)
    }
}