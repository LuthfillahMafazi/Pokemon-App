package com.code.id.pokemonapp.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.code.id.pokemonapp.domain.model.PokemonItem
import androidx.core.database.sqlite.transaction

class PokemonDbSqLite(context: Context) : SQLiteOpenHelper(context, "pokemon.db", null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(
            "CREATE TABLE pokemon (name TEXT PRIMARY KEY, url TEXT)"
        )
    }

    override fun onUpgrade(
        p0: SQLiteDatabase?,
        p1: Int,
        p2: Int
    ) {
        p0?.execSQL("DROP TABLE IF EXISTS pokemon")
        onCreate(p0)
    }

    fun insertAll(pokemonList: List<PokemonItem>?) {
        val db = writableDatabase
        db.transaction {
            try {
                if (pokemonList != null) {
                    for (pokemon in pokemonList) {
                        val values = ContentValues().apply {
                            put("name", pokemon.name)
                            put("url", pokemon.url)
                        }
                        insertWithOnConflict("pokemon", null, values, SQLiteDatabase.CONFLICT_REPLACE)
                    }
                }
            } finally {
            }
        }
    }

    fun getAllPokemon(): List<PokemonItem> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM pokemon", null)
        val list = mutableListOf<PokemonItem>()
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val url = cursor.getString(cursor.getColumnIndexOrThrow("url"))
            list.add(PokemonItem(name, url))
        }
        cursor.close()
        return list
    }
}