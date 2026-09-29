package com.example.a24012011117_mad_practical_7

object PersonDbTableData {
    const val TABLE_NAME = "persons"
    const val COL_ID = "id"
    const val COL_NAME = "name"
    const val COL_EMAIL = "email"
    const val COL_PHONE = "phone"
    const val COL_ADDRESS = "address"

    const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "$COL_ID TEXT PRIMARY KEY, " +
            "$COL_NAME TEXT, " +
            "$COL_EMAIL TEXT, " +
            "$COL_PHONE TEXT, " +
            "$COL_ADDRESS TEXT)"
}