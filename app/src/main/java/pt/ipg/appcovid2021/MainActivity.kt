package pt.ipg.appcovid2021

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import pt.ipg.appcovid2021.R.menu.*
import pt.ipg.appcovid2021.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var menu: Menu

    var menuAtual = menu_lista_utentes
        set(value) {
            field = value
            invalidateOptionsMenu()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        DadosApp.activity = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        menuInflater.inflate(menuAtual, menu)
        this.menu = menu

        if (menuAtual == menu_lista_utentes ) {
            atualizaMenuPacientes(false)
        }
        if (menuAtual == menu_lista_localizacoes ) {
            atualizaMenuLocais(false)
        }
        if (menuAtual == menu_lista_vacinas ) {
            atualizaMenuVacinas(false)
        }


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val opcaoProcessada = when (item.itemId) {

            R.id.action_settings -> {
                Toast.makeText(this, R.string.versao, Toast.LENGTH_LONG).show()
                true
            }
            else -> when(menuAtual) {
                menu_lista_utentes -> (DadosApp.fragment as FragmentUtentes).processaOpcaoMenu(item)
                menu_lista_localizacoes -> (DadosApp.fragment as FragmentLocalidades2).processaOpcaoMenu(item)
                menu_lista_vacinas -> (DadosApp.fragment as FragmentVacinas2).processaOpcaoMenu(item)
                menu_novo_utente -> (DadosApp.fragment as NovoUtenteFragment).processaOpcaoMenu(item)
                menu_nova_localizacao -> (DadosApp.fragment as NovaLocalidadeFragment).processaOpcaoMenu(item)
                menu_nova_vacina -> (DadosApp.fragment as NovaVacinaFragment2).processaOpcaoMenu(item)
                menu_pagina_inicial -> (DadosApp.fragment as FragmentPaginaInicial).processaOpcaoMenu(item)
                menu_edita_localizacao -> (DadosApp.fragment as EditaLocalidadeFragment).processaOpcaoMenu(item)
                menu_edita_vacina -> (DadosApp.fragment as EditaVacinaFragment).processaOpcaoMenu(item)
                menu_edita_utentes -> (DadosApp.fragment as EditaUtenteFragment2).processaOpcaoMenu(item)
                menu_elimina_localidade -> (DadosApp.fragment as EliminaLocalidadeFragment).processaOpcaoMenu(item)
                else -> false
            }
        }
        return if(opcaoProcessada) true else super.onOptionsItemSelected(item)

        /*return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }*/
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun atualizaMenuPacientes(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_utente).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_utente).setVisible(mostraBotoesAlterarEliminar)
    }
    fun atualizaMenuLocais(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_localizacao).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_localizacao).setVisible(mostraBotoesAlterarEliminar)
    }
    fun atualizaMenuVacinas(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_vacina).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_vacina).setVisible(mostraBotoesAlterarEliminar)
    }
}