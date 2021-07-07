package pt.ipg.appcovid2021

import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBdCovidOpenHelper() = BdCovidOpenHelper(getAppContext())

    private fun insereVacinas(tabela: TabelaVacinas, categoria: Vacina): Long {
        val id = tabela.insert(categoria.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun insereUtente(tabela: TabelaUtentes, utente: Utente): Long {
        val id = tabela.insert(utente.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun getVacinasBaseDados(tabela: TabelaVacinas, id: Long): Vacina {
        val cursor = tabela.query(
            TabelaVacinas.TODAS_COLUNAS,
            "${TabelaVacinas.NOME_TABELA}.${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Vacina().fromCursor(cursor)
    }

    private fun getUtenteBaseDados(tabela: TabelaUtentes, id: Long): Utente {
        val cursor = tabela.query(
            TabelaUtentes.TODAS_COLUNAS,
            "${TabelaUtentes.NOME_TABELA}.${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Utente().fromCursor(cursor)
    }

    @Before
    fun apagaBaseDados() {
        getAppContext().deleteDatabase(BdCovidOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val db = getBdCovidOpenHelper().readableDatabase
        assert(db.isOpen)
        db.close()
    }

    @Test
    fun consegueInserirVacinas() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaVacinas = TabelaVacinas(db)

        val vacina = Vacina(nomeVacina = "Drama")
        vacina.id = insereVacinas(tabelaVacinas, vacina)

        assertEquals(vacina, getVacinasBaseDados(tabelaVacinas, vacina.id))

        db.close()
    }

    @Test
    fun consegueAlterarVacinas() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaCategorias = TabelaVacinas(db)

        val vacina = Vacina(nomeVacina = "scisadas")
        vacina.id = insereVacinas(tabelaCategorias, vacina)

        vacina.nomeVacina = "Ficção científica"

        val registosAlterados = tabelaCategorias.update(
            vacina.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(vacina.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(vacina, getVacinasBaseDados(tabelaCategorias, vacina.id))

        db.close()
    }

    @Test
    fun consegueEliminarVacinas() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaVacina = TabelaVacinas(db)

        val vacina = Vacina(nomeVacina = "teste")
        vacina.id = insereVacinas(tabelaVacina, vacina)

        val registosEliminados = tabelaVacina.delete(
            "${BaseColumns._ID}=?",
            arrayOf(vacina.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerVacinas() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaCategorias = TabelaVacinas(db)

        val categoria = Vacina(nomeVacina = "Aventura")
        categoria.id = insereVacinas(tabelaCategorias, categoria)

        assertEquals(categoria, getVacinasBaseDados(tabelaCategorias, categoria.id))

        db.close()
    }

    @Test
    fun consegueInserirUtentes() {
        val db = getBdCovidOpenHelper().writableDatabase

        val tabelaVacinas = TabelaVacinas(db)
        val vacinas = Vacina(nomeVacina = "Aasdasdasd")
        vacinas.id = insereVacinas(tabelaVacinas, vacinas)

        val tabelaUtente = TabelaUtentes(db)
        val utente = Utente(
            nome = "O Leão que Temos Cá Dentro",
            dnascimento = "12/12/1996",
            nrpaciente = "918689905",
            idVacina = vacinas.id,
            nomeVacina = vacinas.nomeVacina // necessário apenas nos testes
        )
        utente.id = insereUtente(tabelaUtente, utente)

        assertEquals(utente, getUtenteBaseDados(tabelaUtente, utente.id))

        db.close()
    }

    @Test
    fun consegueAlterarUtentes() {
        val db = getBdCovidOpenHelper().writableDatabase

        val tabelaVacinas = TabelaVacinas(db)

        val vacinaPfizer = Vacina(nomeVacina = "Pfizer")
        vacinaPfizer.id = insereVacinas(tabelaVacinas, vacinaPfizer)

        val vacinaJhonson = Vacina(nomeVacina = "Jhonson")
        vacinaJhonson.id = insereVacinas(tabelaVacinas, vacinaJhonson)

        val tabelaUtente = TabelaUtentes(db)
        val utente = Utente(
            nome = "?",
            nrpaciente = "?",
            idVacina = vacinaPfizer.id,
            nomeVacina = vacinaPfizer.nomeVacina // necessário apenas nos testes
        )
        utente.id = insereUtente(tabelaUtente, utente)

        utente.nome = "Simao Teixeira"
        utente.dnascimento = "12/12/1996"
        utente.nrpaciente = "123456789"
        utente.idVacina = vacinaJhonson.id
        utente.nomeVacina = vacinaJhonson.nomeVacina // só é necessário nos testes

        val registosAlterados = tabelaUtente.update(
            utente.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(utente.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(utente, getUtenteBaseDados(tabelaUtente, utente.id))

        db.close()
    }

    @Test
    fun consegueEliminarUtentes() {
        val db = getBdCovidOpenHelper().writableDatabase

        val tabelaVacinas = TabelaVacinas(db)
        val vacina = Vacina(nomeVacina = "Astrazeneca")
        vacina.id = insereVacinas(tabelaVacinas, vacina)

        val tabelaUtentes = TabelaUtentes(db)
        val utente = Utente(
            nome = "?",
            dnascimento = "?",
            nrpaciente = "?",
            idVacina = vacina.id,
            nomeVacina = vacina.nomeVacina // necessário apenas nos testes
        )
        utente.id = insereUtente(tabelaUtentes, utente)

        val registosEliminados = tabelaUtentes.delete(
            "${BaseColumns._ID}=?",
            arrayOf(utente.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerLivros() {
        val db = getBdCovidOpenHelper().writableDatabase

        val tabelaVacinas = TabelaVacinas(db)
        val vacina = Vacina(nomeVacina = "Jhonson",data = "13/12/2021", idLocalidade = vacina.id)
        vacina.id = insereVacinas(tabelaVacinas, vacina)

        val tabelaUtentes = TabelaUtentes(db)
        val utente = Utente(
            nome = "Joao Pereira",
            dnascimento = "12/11/1996",
            nrpaciente = "987654321",
            idVacina = vacina.id,
            nomeVacina = vacina.nomeVacina // necessário apenas nos testes
        )
        utente.id = insereUtente(tabelaUtentes, utente)

        assertEquals(utente, getUtenteBaseDados(tabelaUtentes, utente.id))

        db.close()
    }
}