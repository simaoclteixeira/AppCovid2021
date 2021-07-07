package pt.ipg.appcovid2021

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController


class EliminaUtenteFragment : Fragment() {

    private lateinit var textViewNomeUtenteEliminar: TextView
    private lateinit var textViewNumeroUtenteEliminar: TextView
    private lateinit var textViewDataUtenteEliminar: TextView
    private lateinit var textViewVacinaUtenteEliminar: TextView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_utentes

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_utente, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNomeUtenteEliminar = view.findViewById(R.id.textViewNomeUtenteEliminar2)
        textViewNumeroUtenteEliminar = view.findViewById(R.id.textViewNumeroUtenteEliminar2)
        textViewDataUtenteEliminar = view.findViewById(R.id.textViewDataUtenteEliminar2)
        textViewVacinaUtenteEliminar = view.findViewById(R.id.textViewVacinaUtenteEliminar2)



        val utente = DadosApp.UtenteSelecionado!!
        textViewNomeUtenteEliminar.setText(utente.nome)
        textViewNumeroUtenteEliminar.setText(utente.nrpaciente)
        //textViewDataUtenteEliminar.setText(utente.dnascimento)
        textViewVacinaUtenteEliminar.setText(utente.nomeVacina)

    }

    fun navegaLocal() {
        findNavController().navigate(R.id.action_eliminaUtenteFragment_to_fragmentPaginaInicial)
    }

    fun elimina() {
        val uriLocal = Uri.withAppendedPath(
            ContentProviderActivity.ENDEREÇO_UTENTES,
            DadosApp.UtenteSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriLocal,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                "Erro ao eliminar local",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            "Local eliminado com sucesso",
            Toast.LENGTH_LONG
        ).show()

        navegaLocal()
    }


    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_utentes -> elimina()
            R.id.action_cancelar_eliminar_utentes -> navegaLocal()
            else -> return false
        }

        return true
    }
}