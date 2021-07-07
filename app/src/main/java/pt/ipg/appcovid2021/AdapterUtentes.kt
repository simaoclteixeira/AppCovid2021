package pt.ipg.appcovid2021

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterUtentes(val fragment:FragmentUtentes) : RecyclerView.Adapter<AdapterUtentes.ViewHolderPacientes>(){
    public var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolderPacientes(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val textViewNome = itemView.findViewById<TextView>(R.id.textViewNomeUtenteItem)
        private val textViewNumeroUtente = itemView.findViewById<TextView>(R.id.textViewNumeroUtenteItem)
        private val textViewDataNascimento = itemView.findViewById<TextView>(R.id.textViewDataNascimentoItem)
        private val textViewVacinaUtente = itemView.findViewById<TextView>(R.id.textViewVacinaUtente)



        private lateinit var utente: Utente

        init {
            itemView.setOnClickListener(this)
        }

        fun atualizaPaciente(utente: Utente){
            this.utente = utente

            textViewNome.text = utente.nome
            textViewDataNascimento.text = utente.dnascimento
            textViewNumeroUtente.text = utente.nrpaciente
            textViewVacinaUtente.text = utente.nomeVacina
        }

        override fun onClick(v: View?) {
            selecionado?.desSeleciona()
            seleciona()
        }
        private fun seleciona() {
            selecionado = this
            itemView.setBackgroundResource(R.color.cor_selecao)
            DadosApp.UtenteSelecionado = utente
            DadosApp.activity.atualizaMenuPacientes(true)

        }

        private fun desSeleciona() {
            selecionado = null
            itemView.setBackgroundResource(android.R.color.white)
        }
        companion object {

            var selecionado : ViewHolderPacientes? = null
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPacientes {
        val itemPaciente = fragment.layoutInflater.inflate(R.layout.item_utente, parent, false)

        return ViewHolderPacientes(itemPaciente)
    }

    override fun onBindViewHolder(holder: ViewHolderPacientes, position: Int) {
        cursor!!.moveToPosition(position)
        holder.atualizaPaciente(Utente.fromCursor(cursor!!))
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0

    }

}