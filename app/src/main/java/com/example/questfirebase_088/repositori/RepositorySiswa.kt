package com.example.questfirebase_088.repositori

import com.example.questfirebase_088.modeldata.Siswa
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

interface RepositorySiswa {
    suspend fun getDataSiswa(): List<Siswa>
    suspend fun postDataSiswa(siswa: Siswa)
    suspend fun getSiswaById(id: String): Siswa?     // <-- Ubah ke String, tambahkan nullable '?'
    suspend fun updateSiswa(id: String, siswa: Siswa) // <-- Ubah agar lebih eksplisit
    suspend fun deleteSiswa(id: String)
}

class FirebaseRepositorySiswa : RepositorySiswa {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("siswa")

    override suspend fun getDataSiswa(): List<Siswa> {
        return try {
            collection.get().await().documents.map { doc ->
                Siswa(
                    id = doc.id,
                    nama = doc.getString("nama") ?: "",
                    alamat = doc.getString("alamat") ?: "",
                    telpon = doc.getString("telpon") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    override suspend fun postDataSiswa(siswa: Siswa) {
        val docRef = collection.document(siswa.id.toString())
        val data = hashMapOf(
            "id" to siswa.id,
            "nama" to siswa.nama,
            "alamat" to siswa.alamat,
            "telpon" to siswa.telpon
        )
        docRef.set(data).await()
    }

    // --- TAMBAHAN UNTUK DETAIL & EDIT ---
    override suspend fun getSiswaById(id: String): Siswa? {
        // Menerima String, tidak perlu .toString()
        val snapshot = collection.document(id).get().await()
        // Konversi ke objek Siswa, kembalikan null jika tidak ada
        return snapshot.toObject(Siswa::class.java)?.copy(id = snapshot.id)
    }

    override suspend fun updateSiswa(id: String, siswa: Siswa) {
        // Menerima String, langsung gunakan untuk menimpa dokumen
        collection.document(id).set(siswa).await()
    }

    override suspend fun deleteSiswa(id: String) {
        // Menerima String, langsung gunakan untuk menghapus
        collection.document(id).delete().await()
    }
}