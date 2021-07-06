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


class EditaLocalidadeFragment : Fragment()  {
    private lateinit var editTextNomeLocalidade: EditText
    private lateinit var editTextCodigoPostal: EditText


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_localizacao

        return inflater.inflate(R.layout.fragment_edita_localidade, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomeLocalidade= view.findViewById(R.id.editTextNomeLocalidade)
        editTextCodigoPostal = view.findViewById(R.id.editTextCodigoPostal)


        editTextNomeLocalidade.setText(DadosApp.localidadeSelecionado!!.nome)
        editTextCodigoPostal.setText(DadosApp.localidadeSelecionado!!.codigoPostal)

    }
    fun navegaLocal() {
        findNavController().navigate(R.id.action_fragmentEditaLocal_to_fragmentInicioPage)
    }

    fun guardar() {
        val NomeCidade = editTextNomeLocalidade.text.toString()
        if (NomeCidade.isEmpty()) {
            editTextNomeLocalidade.setError("Preenche este campo")
            editTextNomeLocalidade.requestFocus()
            return
        }

        val CodigoPostal = editTextCodigoPostal.text.toString()
        if (CodigoPostal.isEmpty()) {
            editTextCodigoPostal.setError("Preencha este campo")
            editTextCodigoPostal.requestFocus()
            return
        }



        val localidade = DadosApp.localidadeSelecionado!!
        localidade.nome = NomeCidade
        localidade.codigoPostal = CodigoPostal


        val uriLocal = Uri.withAppendedPath(
            ContentProviderActivity.ENDEREÇO_LOCALIZACAO,
            localidade.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriLocal,
            localidade.toContentValues(),
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
            R.id.action_guardar_edita_local -> guardar()
            R.id.action_cancelar_edita_local -> navegaLocal()
            else -> return false
        }

        return true
    }
}