package br.com.zup.chave

import br.com.zup.KeyManagerServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcChavePixFactory {

    @Singleton
    fun keyManagerClient(@GrpcChannel("keyManager") channel: ManagedChannel): KeyManagerServiceGrpc.KeyManagerServiceBlockingStub {
        return KeyManagerServiceGrpc.newBlockingStub(channel)
    }
}