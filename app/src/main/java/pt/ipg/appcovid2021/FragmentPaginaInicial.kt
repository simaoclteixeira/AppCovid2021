package pt.ipg.appcovid2021

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pt.ipg.appcovid2021.databinding.FragmentPaginaInicialBinding


class FragmentPaginaInicial : Fragment() {

    private var _binding: FragmentPaginaInicialBinding? = null

    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment= this
        (activity as MainActivity).menuAtual = R.menu.menu_pagina_inicial

        _binding = FragmentPaginaInicialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonUtentes.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentPaginaInicial_to_FragmentUtentes)
        }
        binding.buttonVacinas.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentPaginaInicial_to_fragmentVacinas22)
        }
        binding.buttonLocalidades.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentPaginaInicial_to_fragmentLocalidades2)
        }


    }
    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return false
    }
    companion object {

    }
}