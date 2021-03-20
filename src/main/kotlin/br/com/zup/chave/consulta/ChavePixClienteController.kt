package br.com.zup.chave.consulta

import br.com.zup.ConsultaChavesPixClienteRequest
import br.com.zup.KeyManagerConsultaServiceGrpc
import br.com.zup.validacoes.UUIDValido
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/api/v1/clientes/{clienteId}")
class ChavePixClienteController(private val grpcClient: KeyManagerConsultaServiceGrpc.KeyManagerConsultaServiceBlockingStub) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Get("/pix")
    fun consultaChavePixPorCliente(@PathVariable("clienteId") @UUIDValido clienteId: String): HttpResponse<ChavePixClienteResponse> {

        logger.info("Iniciando busca de chaves pix cliente: ${clienteId}")
        val responseGrpc = grpcClient.consultaChavesPixCliente(
            ConsultaChavesPixClienteRequest.newBuilder()
                .setClienteId(clienteId)
                .build()
        )
        logger.info("Busca finalizada.")

        return HttpResponse.ok(ChavePixClienteResponse(responseGrpc))
    }

}