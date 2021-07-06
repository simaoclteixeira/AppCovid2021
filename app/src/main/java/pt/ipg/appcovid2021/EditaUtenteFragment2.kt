package pt.ipg.appcovid2021

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController


class EditaUtenteFragment2 : Fragment()  {

    private lateinit var editTextNomeUtente: EditText
    private lateinit var editTextNrpaciente: EditText
    private lateinit var editTextDataNascimento: EditText
    private lateinit var spinnerVacinas: Spinner


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_utentes

        return inflater.inflate(R.layout.fragment_edita_utente2, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomeUtente= view.findViewById(R.id.editTextEditaNomeUtente)
        editTextNrpaciente = view.findViewById(R.id.editTextEditaNumeroPaciente)
        editTextDataNascimento = view.findViewById(R.id.editTextEditaDataNascimento)



        editTextNomeUtente.setText(DadosApp.UtenteSelecionado!!.nome)
        editTextNrpaciente.setText(DadosApp.UtenteSelecionado!!.nrpaciente)
        //editTextDataNascimento.setText(DadosApp.UtenteSelecionado!!.dnascimento)


    }
    fun navegaLocal() {
        findNavController().navigate(R.id.action_editaUtenteFragment22_to_fragmentPaginaInicial)
    }

    fun guardar() {
        val NomeUtente = editTextNomeUtente.text.toString()
        if (NomeUtente.isEmpty()) {
            editTextNomeUtente.setError("Preenche este campo")
            editTextNomeUtente.requestFocus()
            return
        }

        val NumeroUtente = editTextNrpaciente.text.toString()
        if (NomeUtente.isEmpty()) {
            editTextNrpaciente.setError("Preencha este campo")
            editTextNrpaciente.requestFocus()
            return
        }

        val DataNascimento = editTextDataNascimento.text.toString()
        if (DataNascimento.isEmpty()) {
            editTextDataNascimento.setError("Preencha este campo")
            editTextDataNascimento.requestFocus()
            return
        }



        val utente = DadosApp.UtenteSelecionado!!
        utente.nome = NomeUtente
       // utente.dnascimento = DataNascimento
        utente.nrpaciente = NumeroUtente


        val uriLocal = Uri.withAppendedPath(
            ContentProviderActivity.ENDEREÇO_LOCALIZACAO,
            utente.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriLocal,
            utente.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                "Erro ao alterar",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            "Alterado com sucesso",
            Toast.LENGTH_LONG
        ).show()
        navegaLocal()
    }
    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_edita_localizacao -> guardar()
            R.id.action_cancelar_edita_localizacao -> navegaLocal()
            else -> return false
        }

        return true
    }
}