package pt.ipg.appcovid2021

import androidx.fragment.app.Fragment

class DadosApp {
    companion object {

        lateinit var activity: MainActivity
        lateinit var fragment: Fragment

        var novoUtenteFragment: NovoUtenteFragment? = null
        var localidadeSelecionado : Localidade? = null


        // var listaUtentesFragment: ListaUtentesFragment? = null




        var UtenteSelecionado: Utente? = null
    }
}