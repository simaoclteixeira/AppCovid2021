package pt.ipg.appcovid2021
import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.time.LocalDate
import java.util.*

data class Localidade(var id: Long = -1, var nome: String, var codigoPostal: String) {
    fun toContentValues() : ContentValues{
        val valores = ContentValues()
        valores.put(TabelaLocalidades.NOME_LOCALIDADE,nome)
        valores.put(TabelaLocalidades.CODIGO_POSTAL,codigoPostal)


        return valores
    }

    companion object{
        fun fromCursor(cursor: Cursor) : Localidade{
            val colunaId = cursor.getColumnIndex(BaseColumns._ID)
            val colunaNome = cursor.getColumnIndex(TabelaLocalidades.NOME_LOCALIDADE)
            val colunaCodigoPostal = cursor.getColumnIndex(TabelaLocalidades.CODIGO_POSTAL)

            val id = cursor.getLong(colunaId)
            val nome = cursor.getString(colunaNome)
            val codigoPostal = cursor.getString(colunaCodigoPostal)


            return Localidade( id, nome, codigoPostal)
        }
    }
}