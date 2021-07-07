package pt.ipg.appcovid2021

import android.database.Cursor
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
import com.google.android.material.snackbar.Snackbar
import pt.ipg.appcovid2021.databinding.FragmentNovoUtenteBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NovoUtenteFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentNovoUtenteBinding? = null

    private lateinit var editTextNomeUtente: EditText
    private lateinit var editTextNumeroUtente: EditText
    private lateinit var editTextDataNascimento: EditText
    private lateinit var spinnerVacinas: Spinner

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_utente

        _binding = FragmentNovoUtenteBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomeUtente = view.findViewById(R.id.TextInputNomeUtente)
        editTextNumeroUtente = view.findViewById(R.id.TextInputNumeroUtente)
        editTextDataNascimento = view.findViewById(R.id.TextInputDataNascimento)
        spinnerVacinas = view.findViewById(R.id.spinnerVacinas)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_VACINAS, null, this)

    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_utente -> guardar()
            R.id.action_cancelar_novo_utente -> navegaListaUtentes()
            else -> return false
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navegaListaUtentes() {
        findNavController().navigate(R.id.action_NovoUtenteFragment_to_UtentesFragment)
    }

    fun guardar() {
        val NovoUtente = editTextNomeUtente.text.toString()
        if (NovoUtente.isEmpty()) {
            editTextNomeUtente.setError("Preencha este campo")
            editTextNomeUtente.requestFocus()
            return
        }

        val numeroUtente = editTextNumeroUtente.text.toString()
        if (numeroUtente.isEmpty()) {
            editTextNumeroUtente.setError("Preencha este campo")
            editTextNumeroUtente.requestFocus()
            return
        }

        val dataNascimento = editTextDataNascimento.text.toString()
        if (dataNascimento.isEmpty()) {
            editTextDataNascimento.setError("Preencha este campo")
            editTextDataNascimento.requestFocus()
            return
        }

        val idVacina = spinnerVacinas.selectedItemId


        val utente = Utente(nome = NovoUtente, nrpaciente = numeroUtente, dnascimento = dataNascimento, idVacina = idVacina)

        val uri = activity?.contentResolver?.insert(
            ContentProviderActivity.ENDEREÇO_LOCALIZACAO,
            utente.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNomeUtente,
                ("erro ao inserir "),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }
        Toast.makeText(
            requireContext(),
            "Localidade gravada com sucesso",
            Toast.LENGTH_LONG
        ).show()

        navegaListaUtentes()
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

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        atualizaSpinnerVacinas(data)
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

    companion object {
        const val ID_LOADER_MANAGER_VACINAS = 0
    }

}