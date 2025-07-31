package com.code.id.pokemonapp.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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

}