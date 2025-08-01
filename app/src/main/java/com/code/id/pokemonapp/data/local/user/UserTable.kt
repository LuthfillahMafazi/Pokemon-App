package com.code.id.pokemonapp.data.local.user

object UserTable {
    const val TABLE_NAME = "user"
    const val COLUMN_ID = "id"
    const val COLUMN_NAME = "userName"
    const val COLUMN_PASSWORD = "password"

    const val CREATE_TABLE = """
        CREATE TABLE $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_NAME TEXT NOT NULL,
            $COLUMN_PASSWORD TEXT NOT NULL
        )
    """
}