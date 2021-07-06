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


class EditaVacinaFragment : Fragment()  {

    private lateinit var editTextNomeVacina: EditText
    private lateinit var editTextDataVacina: EditText
    //private lateinit var spinnerLocalidades: Spinner


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_vacina

        return inflater.inflate(R.layout.fragment_edita_vacina, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomeVacina= view.findViewById(R.id.editTextEditaNomeUtente)
        editTextDataVacina = view.findViewById(R.id.editTextEditaNumeroPaciente)
        // spinner editTextDataNascimento = view.findViewById(R.id.editTextEditaDataNascimento)



        editTextNomeVacina.setText(DadosApp.vacinaSelecionado!!.nomeVacina)
        //editTextDataVacina.setText(DadosApp.vacinaSelecionado!!.data)
        //editTextDataNascimento.setText(DadosApp.UtenteSelecionado!!.dnascimento)


    }
    fun navegaLocal() {
        findNavController().navigate(R.id.action_editaVacinaFragment_to_fragmentPaginaInicial)
    }

    fun guardar() {
        val NomeVacina = editTextNomeVacina.text.toString()
        if (NomeVacina.isEmpty()) {
            editTextNomeVacina.setError("Preenche este campo")
            editTextNomeVacina.requestFocus()
            return
        }

        /*val NumeroUtente = editTextDataVacina.text.toString()
        if (NomeVacina.isEmpty()) {
            editTextDataVacina.setError("Preencha este campo")
            editTextDataVacina.requestFocus()
            return
        }*/

        val DataVacina = editTextDataVacina.text.toString()
        if (DataVacina.isEmpty()) {
            editTextDataVacina.setError("Preencha este campo")
            editTextDataVacina.requestFocus()
            return
        }



        val vacina = DadosApp.vacinaSelecionado!!
        vacina.nomeVacina = NomeVacina
        // utente.dnascimento = DataNascimento
        //vacina.data = DataVacina


        val uriLocal = Uri.withAppendedPath(
            ContentProviderActivity.ENDEREÇO_LOCALIZACAO,
            vacina.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriLocal,
            vacina.toContentValues(),
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
            R.id.action_guardar_edita_vacina -> guardar()
            R.id.action_cancelar_edita_vacina -> navegaLocal()
            else -> return false
        }

        return true
    }
}