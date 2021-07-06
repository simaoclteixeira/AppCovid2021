package pt.ipg.appcovid2021

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterLocalidades (val fragment: FragmentLocalidades2): RecyclerView.Adapter<AdapterLocalidades.ViewHolderLocal>() {
    public var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class ViewHolderLocal(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        private val textViewNome = itemView.findViewById<TextView>(R.id.textViewNomeLocalidadeItem)
        private val textViewCodigoPostal = itemView.findViewById<TextView>(R.id.textViewCodigoPostalItem)


        private lateinit var localidade: Localidade

        init {
            itemView.setOnClickListener(this)
        }

        fun atualizaLocal(localidade: Localidade) {
            this.localidade = localidade

            textViewNome.text = localidade.nome
            textViewCodigoPostal.text = localidade.codigoPostal
        }
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        override fun onClick(v: View?) {
            selecionado?.desSeleciona()
            seleciona()
        }

        private fun seleciona() {
            selecionado = this
            itemView.setBackgroundResource(R.color.cor_selecao)
            DadosApp.localidadeSelecionado = localidade
            DadosApp.activity.atualizaMenuLocais(true)
        }

        private fun desSeleciona() {
            selecionado = null
            itemView.setBackgroundResource(android.R.color.white)
        }

        companion object {
            var selecionado : ViewHolderLocal? = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLocal {
        val itemLocal = fragment.layoutInflater.inflate(R.layout.item_localidade, parent, false)

        return ViewHolderLocal(itemLocal)
    }

    override fun onBindViewHolder(holder: ViewHolderLocal, position: Int) {
        cursor!!.moveToPosition(position)
        holder.atualizaLocal(Localidade.fromCursor(cursor!!))
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }
}


