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


class EliminaLocalidadeFragment : Fragment() {

    private lateinit var textViewLocalidadeEliminar: TextView
    private lateinit var textViewCodigoPostalEliminar: TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_localidade

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_localidade, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewLocalidadeEliminar = view.findViewById(R.id.textViewEliminaNomeLocalizacao2)
        textViewCodigoPostalEliminar = view.findViewById(R.id.textViewEliminaCodigoPostal2)


        val localidade = DadosApp.localidadeSelecionado!!
        textViewLocalidadeEliminar.setText(localidade.nome)
        textViewCodigoPostalEliminar.setText(localidade.codigoPostal)

    }

    fun navegaLocal() {
        findNavController().navigate(R.id.action_eliminaLocalidadeFragment_to_fragmentPaginaInicial)
    }

    fun elimina() {
        val uriLocal = Uri.withAppendedPath(
            ContentProviderActivity.ENDEREÇO_LOCALIZACAO,
            DadosApp.localidadeSelecionado!!.id.toString()
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
            R.id.action_confirma_eliminar_localidade -> elimina()
            R.id.action_cancelar_eliminar_localidade -> navegaLocal()
            else -> return false
        }

        return true
    }
}