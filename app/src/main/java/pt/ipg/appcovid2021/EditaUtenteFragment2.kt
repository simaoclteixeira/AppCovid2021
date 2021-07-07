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
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController


class EditaUtenteFragment2 : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var editTextNomeUtente: EditText
    private lateinit var editTextNrpaciente: EditText
    private lateinit var editTextDataNascimento: EditText
    private lateinit var spinnerVacinas: Spinner


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
        spinnerVacinas = view.findViewById(R.id.spinnerVacinas)


        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_VACINAS, null, this)

        editTextNomeUtente.setText(DadosApp.UtenteSelecionado!!.nome)
        editTextNrpaciente.setText(DadosApp.UtenteSelecionado!!.nrpaciente)
        editTextDataNascimento.setText(DadosApp.UtenteSelecionado!!.dnascimento)


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

        val idVacina = spinnerVacinas.selectedItemId



        val utente = DadosApp.UtenteSelecionado!!
        utente.nome = NomeUtente
        utente.dnascimento = DataNascimento
        utente.nrpaciente = NumeroUtente
        utente.idVacina = idVacina


        val uriLocal = Uri.withAppendedPath(
            ContentProviderActivity.ENDEREÇO_UTENTES,
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

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        atualizaSpinnerVacinas(data)
        atualizaVacinaSelecionada()
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        atualizaSpinnerVacinas(null)
    }

    private fun atualizaSpinnerVacinas(data: Cursor?) {
        spinnerVacinas.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaVacinas.CAMPO_NOME_VACINA),
            intArrayOf(android.R.id.text1),
            0
        )
    }

    private fun atualizaVacinaSelecionada() {
        val idVacina = DadosApp.UtenteSelecionado!!.idVacina

        val ultimaVacina = spinnerVacinas.count - 1
        for (i in 0..ultimaVacina) {
            if (idVacina == spinnerVacinas.getItemIdAtPosition(i)) {
                spinnerVacinas.setSelection(i)
                return
            }
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderActivity.ENDEREÇO_VACINAS,
            TabelaVacinas.TODAS_COLUNAS,
            null, null,
            TabelaVacinas.CAMPO_NOME_VACINA
        )
    }

    companion object {
        const val ID_LOADER_MANAGER_VACINAS = 0
    }
}