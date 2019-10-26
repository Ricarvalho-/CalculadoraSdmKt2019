package br.edu.ifsp.scl.calculadorasdmkt.model

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class ConfiguracaoService(context: Context) {
    companion object{
        const val persistenceTag = "persistence"
    }

    private val sharedPrefsDao = ConfiguracaoSharedPreferences(context)
    private val sqliteDao = ConfiguracaoSqlite(context)
    private val gson: Gson by lazy { GsonBuilder().create() }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("persistence", Context.MODE_PRIVATE)

    var persistencePreference: PersistencePreference = let {
        val json = sharedPreferences.getString(persistenceTag, "")
        gson.fromJson(json, PersistencePreference::class.java) ?: PersistencePreference.SHARED_PREFS
    }
        set(newPersistence) {
            if (field != newPersistence)
                newPersistence.updateDaoWith(configuracaoDao())
            field = newPersistence
            sharedPreferences.edit()
                .putString(persistenceTag, gson.toJson(newPersistence))
                .apply()
        }

    private fun PersistencePreference.updateDaoWith(otherDao: ConfiguracaoDao) {
        val otherSetup = otherDao.readConfiguracao()
        configuracaoDao(this).createOrUpdateConfiguracao(otherSetup)
    }

    private fun configuracaoDao(persistence: PersistencePreference = persistencePreference) = when (persistence) {
        PersistencePreference.SHARED_PREFS -> sharedPrefsDao
        PersistencePreference.SQLITE_DB -> sqliteDao
    }

    fun setConfiguracao(configuracao: Configuracao) {
        /* Qualquer tratamento necessário aos dados antes de salvá-los
        na fonte de dados escolhida deve ser feita no Service.
        As classes que implementam o DAO devem esconder as peculiaridades
        para acesso a cada fonte de dados diferente e executar apenas as funções de CRUD.*/
        // Tratamento de dados aqui!
        // Delegando ao modelo
        configuracaoDao().createOrUpdateConfiguracao(configuracao)
    }

    fun getConfiguracao(): Configuracao {
        // Tratamento de dados aqui!
        // Delegando ao modelo
        return configuracaoDao().readConfiguracao()
    }
}