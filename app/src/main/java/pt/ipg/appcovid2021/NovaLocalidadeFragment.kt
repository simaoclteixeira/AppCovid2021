package pt.ipg.appcovid2021

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.appcovid2021.databinding.FragmentNovaLocalidadeBinding


class NovaLocalidadeFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentNovaLocalidadeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var editTextNomeNovaLocalidade: EditText
    private lateinit var editTextNovoCodigoPostal: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.novaLocalidadeFragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_nova_localizacao

        _binding = FragmentNovaLocalidadeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextNomeNovaLocalidade = view.findViewById(R.id.editTextNomeNovaLocalidade)
        editTextNovoCodigoPostal = view.findViewById(R.id.editTextCodigoPostalNovo)


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun navegaLocal() {
        findNavController().navigate(R.id.action_fragmentNovoLocal_to_fragmentInicioPage)
    }

    fun guardar() {
        val NovaLocalidade = editTextNomeNovaLocalidade.text.toString()
        if (NovaLocalidade.isEmpty()) {
            editTextNomeNovaLocalidade.setError("Preencha este campo")
            editTextNomeNovaLocalidade.requestFocus()
            return
        }

        val NovoCodigoPostal = editTextNovoCodigoPostal.text.toString()
        if (NovoCodigoPostal.isEmpty()) {
            editTextNovoCodigoPostal.setError("Preencha este campo")
            editTextNovoCodigoPostal.requestFocus()
            return
        }


        val localidade = Localidade(nome = NovaLocalidade, codigoPostal = NovoCodigoPostal)

        val uri = activity?.contentResolver?.insert(
            ContentProviderActivity.ENDEREÇO_LOCALIZACAO,
            localidade.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNomeNovaLocalidade,
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

        navegaLocal()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_nova_localizacao -> guardar()
            R.id.action_cancelar_nova_localizacao -> navegaLocal()
            else -> return false
        }

        return true
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        TODO("Not yet implemented")
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }
}