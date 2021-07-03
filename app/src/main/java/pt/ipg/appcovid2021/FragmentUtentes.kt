package pt.ipg.appcovid2021

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.ipg.appcovid2021.databinding.FragmentUtentesBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentUtentes : Fragment(), LoaderManager.LoaderCallbacks<Cursor>{

    private var _binding: FragmentUtentesBinding? = null
    private var adapterUtentes: AdapterUtentes? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_pacientes


        _binding = FragmentUtentesBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_PACIENTES, null, this)

        val recyclerViewUtentes = view.findViewById<RecyclerView>(R.id.recyclerViewUtentes)

        adapterUtentes = AdapterUtentes(this)
        recyclerViewUtentes.adapter = adapterUtentes
        recyclerViewUtentes.layoutManager= LinearLayoutManager(requireContext())


    }
    fun navegaNovoPaciente() {
        findNavController().navigate(R.id.action_UtentesFragment_to_NovoUtenteFragment)
    }
    fun navegaAlterarPaciente() {
        //todo: navegar para o fragmento da edição de um livro
    }

    fun navegaEliminarPaciente() {
        //todo: navegar para o fragmento para confirmar eliminação de um livro
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_novo_paciente -> navegaNovoPaciente()
            R.id.action_alterar_paciente-> navegaAlterarPaciente()
            R.id.action_eliminar_paciente -> navegaEliminarPaciente()
            else -> return false
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderActivity.ENDERECO_UTENTE,
            TabelaUtentes.TODAS_COLUNAS,
            null,null,
            TabelaUtentes.CAMPO_NOME_UTENTE
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterUtentes!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterUtentes!!.cursor = null
    }
    companion object{
        const val ID_LOADER_MANAGER_PACIENTES = 0
    }
}