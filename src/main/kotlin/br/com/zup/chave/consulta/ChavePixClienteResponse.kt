package br.com.zup.chave.consulta

import br.com.zup.ConsultaChavesPixClienteResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class ChavePixClienteResponse(responseGrpc: ConsultaChavesPixClienteResponse?) {

    val clienteId: String = responseGrpc?.clienteId ?: ""
    val listaChavePix = responseGrpc?.listaChavePixList?.map {
        mapOf(
            Pair("idPix", it.idPix),
            Pair("tipoChave", it.tipoChave),
            Pair("chave", it.chave),
            Pair("tipoConta", it.tipoConta),
            Pair("criadoEm", it.criadoEm.let {
                    LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
                })
        )
    }
}
