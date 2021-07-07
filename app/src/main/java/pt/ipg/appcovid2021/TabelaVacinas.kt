package pt.ipg.appcovid2021

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import pt.ipg.appcovid2021.TabelaLocalidades

class TabelaVacinas(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME_VACINA TEXT NOT NULL, $CAMPO_DATA INTEGER NOT NULL, $CAMPO_ID_LOCALIDADE NUMERIC NOT NULL, FOREIGN KEY (${CAMPO_ID_LOCALIDADE}) REFERENCES ${TabelaLocalidades.NOME_TABELA})")
    }

    fun insert(values: ContentValues): Long {
        return db.insert(NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(NOME_TABELA, whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        val ultimaColuna = columns.size - 1

        var posColunaNomeLocalidade = -1 // -1 indica que a coluna não foi pedida
        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_LOCALIDADE) {
                posColunaNomeLocalidade = i
                break
            }
        }

        if (posColunaNomeLocalidade == -1) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
        }

        var colunas = ""
        for (i in 0..ultimaColuna) {
            if (i > 0) colunas += ","

            colunas += if (i == posColunaNomeLocalidade) {
                "${TabelaLocalidades.NOME_TABELA}.${TabelaLocalidades.NOME_LOCALIDADE} AS $CAMPO_EXTERNO_NOME_LOCALIDADE"
            } else {
                "${NOME_TABELA}.${columns[i]}"
            }
        }

        val tabelas = "$NOME_TABELA INNER JOIN ${TabelaLocalidades.NOME_TABELA} ON ${TabelaLocalidades.NOME_TABELA}.${BaseColumns._ID}=$CAMPO_ID_LOCALIDADE"

        var sql = "SELECT $colunas FROM $tabelas"

        if (selection != null) sql += " WHERE $selection"

        if (groupBy != null) {
            sql += " GROUP BY $groupBy"
            if (having != null) " HAVING $having"
        }

        if (orderBy != null) sql += " ORDER BY $orderBy"

        return db.rawQuery(sql, selectionArgs)

    }

    companion object {
        const val NOME_TABELA = "Vacina"
        const val CAMPO_NOME_VACINA = "NomeVacina"
        const val CAMPO_DATA = "Data"
        const val CAMPO_ID_LOCALIDADE = "Localidade"
        const val CAMPO_EXTERNO_NOME_LOCALIDADE = "nome_categoria"




        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, CAMPO_NOME_VACINA, CAMPO_DATA, CAMPO_ID_LOCALIDADE, CAMPO_EXTERNO_NOME_LOCALIDADE)
    }
}