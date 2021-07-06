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
import pt.ipg.appcovid2021.databinding.FragmentNovaVacina2Binding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NovaVacinaFragment2 : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentNovaVacina2Binding? = null

    private lateinit var editTextNomeVacina: EditText
    private lateinit var editTextDataVacina: EditText
    private lateinit var spinnerLocalidades: Spinner

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.novaVacinaFragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_nova_vacina

        _binding = FragmentNovaVacina2Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomeVacina = view.findViewById(R.id.TextInputNomeVacina)
        editTextDataVacina = view.findViewById(R.id.TextInputDataVacina)
        spinnerLocalidades = view.findViewById(R.id.spinnerLocalidades)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_VACINAS, null, this)

    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_nova_vacina -> guardar()
            R.id.action_cancelar_nova_vacina -> navegaListaVacinas()
            else -> return false
        }

        return true
    }

    fun guardar() {
        val NovaVacina = editTextNomeVacina.text.toString()
        if (NovaVacina.isEmpty()) {
            editTextNomeVacina.setError("Preencha este campo")
            editTextNomeVacina.requestFocus()
            return
        }

        val DataVacina = editTextDataVacina.text.toString()
        if  (DataVacina.isEmpty()) {
            editTextDataVacina.setError("Preencha este campo")
            editTextDataVacina.requestFocus()
            return
        }

        val idLocalidade = spinnerLocalidades.selectedItemId

        val vacina = Vacina(nomeVacina = NovaVacina, data = DataVacina, localidade = idLocalidade.toString())

        val uri = activity?.contentResolver?.insert(
            ContentProviderActivity.ENDEREÇO_LOCALIZACAO,
            vacina.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNomeVacina,
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

        navegaListaVacinas()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navegaListaVacinas() {
        findNavController().navigate(R.id.action_novaVacinaFragment2_to_fragmentVacinas22)
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
        atualizaSpinnerVacinas(data = null)
    }

    private fun atualizaSpinnerVacinas(data: Cursor?) {
        spinnerLocalidades.adapter = SimpleCursorAdapter(
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