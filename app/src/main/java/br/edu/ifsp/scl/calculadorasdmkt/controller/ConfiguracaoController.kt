package br.edu.ifsp.scl.calculadorasdmkt.controller

import android.content.Context
import br.edu.ifsp.scl.calculadorasdmkt.model.Configuracao
import br.edu.ifsp.scl.calculadorasdmkt.model.ConfiguracaoService

class ConfiguracaoController(context: Context, var onSetupResult: (Configuracao) -> Unit) {
    private val model: ConfiguracaoService = ConfiguracaoService(context.applicationContext)

    fun salvaConfiguracao(configuracao: Configuracao) {
        model.setConfiguracao(configuracao)
        onSetupResult(configuracao)
    }

    fun buscaConfiguracao() {
        val configuracao = model.getConfiguracao()
        onSetupResult(configuracao)
    }
}