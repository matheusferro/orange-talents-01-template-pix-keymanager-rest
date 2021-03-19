package br.com.zup.chave.cadastro

import br.com.zup.KeyManagerRequest
import br.com.zup.TipoChave
import br.com.zup.TipoConta
import br.com.zup.chave.TipoChaveRequest
import br.com.zup.chave.TipoContaRequest
import br.com.zup.validacoes.ValidaChave
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank

@ValidaChave
@Introspected
data class CadastroChavePixRequest(

    @field:NotBlank
    val tipoChave: TipoChaveRequest,

    val chave: String,

    @field:NotBlank
    val tipoConta: TipoContaRequest
) {
    fun toRequestGrpc(clienteId: UUID): KeyManagerRequest {
        return KeyManagerRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setTipoChave(TipoChave.valueOf(this.tipoChave.name))
            .setChave(this.chave)
            .setTipoConta(TipoConta.valueOf(this.tipoConta.name))
            .build()
    }
}
