package br.com.zup.chave.consulta

import br.com.zup.ConsultaChavePixRequest
import br.com.zup.IdPixConsulta
import br.com.zup.KeyManagerConsultaServiceGrpc
import br.com.zup.validacoes.UUIDValido
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Validated
@Controller("/api/v1/clientes/{clienteId}")
class DetalhesChavePixController(private val grpcClient: KeyManagerConsultaServiceGrpc.KeyManagerConsultaServiceBlockingStub) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Get("/pix/{idPix}")
    fun detalhesChavePix(
        @PathVariable("clienteId") @UUIDValido clienteId: String,
        @PathVariable("idPix") @UUIDValido idPix: String
    ): HttpResponse<DetalhesChavePixResponse> {

        val grpcRequest = ConsultaChavePixRequest.newBuilder()
            .setIdPix(
                IdPixConsulta.newBuilder()
                    .setIdPix(idPix)
                    .setClienteId(clienteId)
                    .build()
            )
            .build()

        logger.info("Iniciando chamada grpc.")
        val grpcResponse = grpcClient.consultaChavePix(grpcRequest)
        logger.info("Finalizando chamada grpc.")

        return HttpResponse.ok(DetalhesChavePixResponse(grpcResponse))
    }
}