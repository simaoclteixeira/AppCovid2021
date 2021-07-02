package pt.ipg.appcovid2021

class DadosApp {
    companion object {
        lateinit var activity: MainActivity
        var listaUtentesFragment: ListaUtentesFragment? = null
        var novoUtenteFragment: NovoUtenteFragment? = null

        var UtenteSelecionado: Utente? = null
    }
}