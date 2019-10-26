package br.edu.ifsp.scl.calculadorasdmkt.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.calculadorasdmkt.R
import br.edu.ifsp.scl.calculadorasdmkt.controller.ConfiguracaoController
import br.edu.ifsp.scl.calculadorasdmkt.model.Configuracao
import br.edu.ifsp.scl.calculadorasdmkt.model.PersistencePreference
import br.edu.ifsp.scl.calculadorasdmkt.model.Separador
import kotlinx.android.synthetic.main.activity_configuracao.*
import kotlinx.android.synthetic.main.toolbar.*

class ConfiguracaoActivity: AppCompatActivity() {
    object Constantes {
        // Chave de retorno para a MainActivity
        val CONFIGURACAO = "CONFIGURACAO"
    }

    // Referência para Controller
    lateinit var configuracaoController: ConfiguracaoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracao)

        // Toolbar
        toolbar.title = "Configuração"
        setSupportActionBar(toolbar)

        // Chama controller e atualizar view
        configuracaoController = ConfiguracaoController(this) { (persistence, configuracao) ->
            persistenceSpinner.setSelection(when (persistence) {
                PersistencePreference.SHARED_PREFS -> 0
                PersistencePreference.SQLITE_DB -> 1
            })

            leiauteSpn.setSelection(if (configuracao.leiauteAvancado) 1 else 0)
            separadorRg.check(
                if (configuracao.separador == Separador.PONTO) R.id.pontoRb
                else R.id.virgulaRb
            )
            setResult(RESULT_OK, Intent().putExtra(Constantes.CONFIGURACAO, configuracao))
        }
        configuracaoController.buscaConfiguracao()
    }

    fun onClickSalvaConfiguracao(v: View) {
        configuracaoController.update(
            if (persistenceSpinner.selectedItemPosition == 0) PersistencePreference.SHARED_PREFS
            else PersistencePreference.SQLITE_DB
        )

        // Pega os dados da tela
        val leiauteAvancado = leiauteSpn.selectedItemPosition == 1
        val separador = if (pontoRb.isChecked) Separador.PONTO else Separador.VIRGULA

        // Criar um objeto Configuracao
        val novaConfiguracao: Configuracao = Configuracao(leiauteAvancado, separador)

        // Chamar o Controller para salvar
        configuracaoController.salvaConfiguracao(novaConfiguracao)

        Toast.makeText(this, "Configuração salva!", Toast.LENGTH_SHORT).show()
        finish()
    }

    fun onCancelClick(view: View) {
        Toast.makeText(this, "Alterações descartadas", Toast.LENGTH_SHORT).show()
        finish()
    }
}