package pt.ipg.appcovid2021

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns
import kotlin.reflect.KTypeProjection

class ContentProviderActivity : ContentProvider() {
    private var BdCovidOpenHelper : BdCovidOpenHelper? = null
    override fun onCreate(): Boolean {
        BdCovidOpenHelper = BdCovidOpenHelper(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        seletionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val bd = BdCovidOpenHelper!!.readableDatabase

        return when (getUriMatcher().match(uri)){
            URI_UTENTE -> TabelaUtentes(bd).query(
                projection as Array<String>,
                selection,
                seletionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )
            URI_UTENTE_ESPECIFICO -> TabelaUtentes(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )

            URI_LOCALIZACAO -> TabelaLocalidades(bd).query(
                projection as Array<String>,
                selection,
                seletionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )

            URI_LOCALIZACAO_ESPECIFICA -> TabelaLocalidades(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )

            URI_VACINAS -> TabelaVacinas(bd).query(
                projection as Array<String>,
                selection,
                seletionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )

            URI_VACINA_ESPECIFICA -> TabelaVacinas(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )

            else -> null

        }
    }

    override fun getType(uri: Uri): String? {
        return when (getUriMatcher().match(uri)){
            URI_UTENTE -> "$MULTIPLOS_ITEMS/$UTENTE"
            URI_UTENTE_ESPECIFICO -> "$UNICO_ITEMS/$UTENTE"
            URI_LOCALIZACAO -> "$MULTIPLOS_ITEMS/$LOCALIZACAO"
            URI_LOCALIZACAO_ESPECIFICA -> "$UNICO_ITEMS/$LOCALIZACAO"
            URI_VACINAS -> "$MULTIPLOS_ITEMS/$VACINAS"
            URI_VACINA_ESPECIFICA -> "$UNICO_ITEMS/$VACINAS"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val bd = BdCovidOpenHelper!!.writableDatabase

        val id = when (getUriMatcher().match(uri)){
            URI_UTENTE -> TabelaUtentes(bd).insert(values!!)
            URI_LOCALIZACAO -> TabelaLocalidades(bd).insert(values!!)
            URI_VACINAS -> TabelaVacinas(bd).insert(values!!)
            else -> -1L
        }

        if( id == -1L) return null

        return Uri.withAppendedPath(uri,id.toString())
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val bd = BdCovidOpenHelper!!.writableDatabase

        return when (getUriMatcher().match(uri)){
            URI_UTENTE_ESPECIFICO -> TabelaUtentes(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )
            URI_LOCALIZACAO_ESPECIFICA -> TabelaLocalidades(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )
            URI_VACINA_ESPECIFICA -> TabelaVacinas(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )
            else -> 0

        }
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val bd = BdCovidOpenHelper!!.writableDatabase

        return  when (getUriMatcher().match(uri)){
            URI_UTENTE_ESPECIFICO -> TabelaUtentes(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )
            URI_LOCALIZACAO_ESPECIFICA -> TabelaLocalidades(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )
            URI_VACINA_ESPECIFICA -> TabelaVacinas(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )
            else -> 0
        }
    }

    companion object{
        private const val AUTHORITY= "com.example.covidapp"

        private const val UTENTE = "Utente"
        private const val LOCALIZACAO = "Localizacao"
        private const val VACINAS = "Vacinas"

        private const val URI_UTENTE = 100
        private const val URI_UTENTE_ESPECIFICO = 101
        private const val URI_LOCALIZACAO = 200
        private const val URI_LOCALIZACAO_ESPECIFICA = 201
        private const val URI_VACINAS = 300
        private const val URI_VACINA_ESPECIFICA = 301


        private  const val MULTIPLOS_ITEMS = "vnd.android.cursor.dir"
        private  const val UNICO_ITEMS = "vnd.android.cursor.item"

        private val ENDEREÇO_BASE = Uri.parse("content://$AUTHORITY")
        private val ENDEREÇO_UTENTES = Uri.withAppendedPath(ENDEREÇO_BASE, UTENTE)
        private val ENDEREÇO_LOCALIZACAO = Uri.withAppendedPath(ENDEREÇO_BASE, LOCALIZACAO)
        val ENDEREÇO_VACINAS = Uri.withAppendedPath(ENDEREÇO_BASE, VACINAS)

        private fun getUriMatcher() : UriMatcher{
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTHORITY, UTENTE, URI_UTENTE)
            uriMatcher.addURI(AUTHORITY, "$UTENTE/#", URI_UTENTE_ESPECIFICO)
            uriMatcher.addURI(AUTHORITY, LOCALIZACAO, URI_LOCALIZACAO)
            uriMatcher.addURI(AUTHORITY, "$LOCALIZACAO/#", URI_LOCALIZACAO_ESPECIFICA)
            uriMatcher.addURI(AUTHORITY, VACINAS, URI_VACINAS)
            uriMatcher.addURI(AUTHORITY, "$VACINAS/#", URI_VACINA_ESPECIFICA)

            return uriMatcher
        }
    }

}