package pt.ipg.appcovid2021

import androidx.fragment.app.Fragment

class DadosApp {
    companion object {

        lateinit var activity: MainActivity
        lateinit var fragment: Fragment

        var novoUtenteFragment: NovoUtenteFragment? = null
        var novaLocalidadeFragment: NovaLocalidadeFragment? = null
        var novaVacinaFragment: NovaVacinaFragment2? = null

        var localidadeSelecionado : Localidade? = null
        var UtenteSelecionado: Utente? = null
        var vacinaSelecionado: Vacina? = null
    }
}