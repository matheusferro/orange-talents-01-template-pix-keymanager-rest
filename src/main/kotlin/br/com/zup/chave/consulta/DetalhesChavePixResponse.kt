package br.com.zup.chave.consulta

import br.com.zup.ConsultaChavePixResponse
import br.com.zup.TipoConta
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Introspected
class DetalhesChavePixResponse(grpcResponse: ConsultaChavePixResponse) {
    val idPix = grpcResponse.idPix
    val clienteId = grpcResponse.clienteId
    val tipoChave = grpcResponse.tipoChave
    val chave = grpcResponse.chave
    val nomeTitular = grpcResponse.nomeTitular
    val cpfTitular = grpcResponse.cpfTitular
    val dadosConta = DadosContaRequest(
        grpcResponse.dadosConta.tipoConta,
        grpcResponse.dadosConta.numero,
        grpcResponse.dadosConta.agencia,
        grpcResponse.dadosConta.instituicao
    )

    val criadaEm = grpcResponse.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }
}

data class DadosContaRequest(val tipoConta: TipoConta, val numero: String, val agencia: String, val instituicao: String)
