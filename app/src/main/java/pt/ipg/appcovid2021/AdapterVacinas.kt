package pt.ipg.appcovid2021

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterVacinas(val fragment: FragmentVacinas): RecyclerView.Adapter<AdapterVacinas.ViewHolderVacina>() {

    public var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolderVacina(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewNome = itemView.findViewById<TextView>(R.id.textViewNomeLocalidadeItem)
        private val textViewDataVacina = itemView.findViewById<TextView>(R.id.textViewDataVacina)
        private val textViewLocalidadeVacina = itemView.findViewById<TextView>(R.id.textViewCodigoPostalItem)


        fun atualizaVacina(vacina: Vacina) {
            textViewNome.text = vacina.nomeVacina
            textViewDataVacina.text = vacina.data.toString()
            textViewLocalidadeVacina.text = vacina.localidade.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVacina {
        val itemVacina = fragment.layoutInflater.inflate(R.layout.item_vacina, parent, false)

        return ViewHolderVacina(itemVacina)
    }

    override fun onBindViewHolder(holder: ViewHolderVacina, position: Int) {
        cursor!!.moveToPosition(position)
        holder.atualizaVacina(Vacina.fromCursor(cursor!!))

    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }


}