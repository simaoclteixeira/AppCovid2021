package pt.ipg.appcovid2021

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.ipg.appcovid2021.databinding.FragmentVacinasBinding


class FragmentVacinas : Fragment(),LoaderManager.LoaderCallbacks<Cursor>{

    private var _binding: FragmentVacinasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var adapterVacinas : AdapterVacinas? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentVacinasBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewVacinas = view.findViewById<RecyclerView>(R.id.recyclerViewVacinas)
        adapterVacinas = AdapterVacinas(this)
        recyclerViewVacinas.adapter = adapterVacinas
        recyclerViewVacinas.layoutManager = LinearLayoutManager(requireContext())



        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_VACINAS, null, this)
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderActivity.ENDERECO_VACINA,
            TabelaVacinas.TODAS_COLUNAS,
            null, null,
            TabelaVacinas.CAMPO_ORIGEM
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterVacinas!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterVacinas!!.cursor = null
    }

    companion object{
        const val ID_LOADER_MANAGER_VACINAS = 0
    }
}