package pt.ipg.appcovid2021
import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Utente(var id: Long = -1, var nome: String, var dnascimento: String, var nrpaciente: String, var idVacina: Long, var nomeVacina: String? = null) {
    fun toContentValues(): ContentValues{
        val valores = ContentValues().apply {
            put(TabelaUtentes.CAMPO_NOME_UTENTE,nome)
            put(TabelaUtentes.CAMPO_DATA_NASCIMENTO,dnascimento)
            put(TabelaUtentes.CAMPO_NR_UTENTE,nrpaciente)
            put(TabelaUtentes.CAMPO_ID_VACINA,idVacina)
        }
        return valores
    }


    companion object{
        fun fromCursor(cursor: Cursor): Utente{
            val colunaId = cursor.getColumnIndex(BaseColumns._ID)
            val colunaNomeUtente = cursor.getColumnIndex(TabelaUtentes.CAMPO_NOME_UTENTE)
            val colunaDnascimento = cursor.getColumnIndex(TabelaUtentes.CAMPO_DATA_NASCIMENTO)
            val colunaNrpaciente = cursor.getColumnIndex(TabelaUtentes.CAMPO_NR_UTENTE)
            val colunaIdVacina = cursor.getColumnIndex(TabelaUtentes.CAMPO_ID_VACINA)
            val colunaNomeVacina = cursor.getColumnIndex(TabelaUtentes.CAMPO_EXTERNO_NOME_VACINA)

            val id= cursor.getLong(colunaId)
            val nomeUtente = cursor.getString(colunaNomeUtente)
            val dnascimento = cursor.getString(colunaDnascimento)
            val nrpaciente = cursor.getString(colunaNrpaciente)
            val idVacina = cursor.getLong(colunaIdVacina)
            val nomeVacina = if (colunaNomeVacina != -1) cursor.getString(colunaNomeVacina) else null

            return Utente(id ,nomeUtente ,dnascimento , nrpaciente,idVacina, nomeVacina)

        }
    }
}