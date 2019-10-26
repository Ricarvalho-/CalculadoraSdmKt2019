package br.edu.ifsp.scl.calculadorasdmkt.controller

import android.content.Context
import br.edu.ifsp.scl.calculadorasdmkt.model.Configuracao
import br.edu.ifsp.scl.calculadorasdmkt.model.ConfiguracaoService
import br.edu.ifsp.scl.calculadorasdmkt.model.PersistencePreference

typealias Setup = Pair<PersistencePreference, Configuracao>

class ConfiguracaoController(context: Context, var onSetupResult: (Setup) -> Unit) {
    private val model: ConfiguracaoService = ConfiguracaoService(context.applicationContext)

    fun update(persistencePreference: PersistencePreference) {
        model.persistencePreference = persistencePreference
    }

    fun salvaConfiguracao(configuracao: Configuracao) {
        model.setConfiguracao(configuracao)
        onSetupResult(Setup(model.persistencePreference, configuracao))
    }

    fun buscaConfiguracao() {
        val configuracao = model.getConfiguracao()
        onSetupResult(Setup(model.persistencePreference, configuracao))
    }
}