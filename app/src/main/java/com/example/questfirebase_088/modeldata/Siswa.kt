package com.example.questfirebase_088.modeldata

data class Siswa(
    val id: Long = 0,
    val nama: String = "",
    val alamat: String = "",
    val telpon: String = ""
)

data class DetailSiswa(
    val id: Long = 0,
    val nama: String = "",
    val alamat: String = "",
    val telpon: String = ""
)

fun DetailSiswa.toDataSiswa(): Siswa = Siswa(
    id = id,
    nama = nama,
    alamat = alamat,
    telpon = telpon
)