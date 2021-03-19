package br.com.zup.chave


import br.com.caelum.stella.validation.CPFValidator
import io.micronaut.validation.validator.constraints.EmailValidator

enum class TipoChaveRequest {
    NAO_IDENTIFICADO {
        override fun valida(chave: String): Boolean {
            return false
        }

    },
    CPF {
        override fun valida(chave: String): Boolean {
            if (!chave.matches("^[0-9]{11}$".toRegex()) || chave.isBlank()) {
                return false
            }

            return CPFValidator().invalidMessagesFor(chave).isEmpty()

        }
    },
    EMAIL {
        override fun valida(chave: String): Boolean {
            if (chave.isBlank()) {
                return false
            }
            return EmailValidator().run {
                initialize(null)
                isValid(chave, null)
            }
        }
    },
    TELEFONE_CELULAR {
        override fun valida(chave: String): Boolean {
            //validacao alterada - Sugestao do alefh sousa
            return (chave.matches("^\\+[1-9]{3}[0-9]{9}\\d\$".toRegex()) && chave.isNotBlank())
        }
    },
    ALEATORIA {
        override fun valida(chave: String): Boolean {
            return chave.isBlank()
        }
    };

    /**
     * Facilitando quando temos 1 comportamento semelhante para
     * diferentes atributos, e vai ser executado uma diferente lógica.
     */
    abstract fun valida(chave: String): Boolean
}