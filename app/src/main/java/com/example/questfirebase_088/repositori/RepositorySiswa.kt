package com.example.questfirebase_088.repositori

import com.example.questfirebase_088.modeldata.Siswa
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

interface RepositorySiswa {
    suspend fun getDataSiswa(): List<Siswa>
    suspend fun postDataSiswa(siswa: Siswa)
    // Fungsi sesuai dengan Gambar ViewModel
    suspend fun getSatuSiswa(id: Long): Siswa?
    suspend fun editSatuSiswa(id: Long, siswa: Siswa)
    suspend fun hapusSatuSiswa(id: Long)
}

class FirebaseRepositorySiswa : RepositorySiswa {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("siswa")

    override suspend fun getDataSiswa(): List<Siswa> {
        return try {
            collection.get().await().documents.map { doc ->
                Siswa(
                    id = doc.getLong("id") ?: 0L,
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
        val docRef = collection.document()

        val data = hashMapOf(
            "id" to docRef.id.hashCode().toLong(),
            "nama" to siswa.nama,
            "alamat" to siswa.alamat,
            "telpon" to siswa.telpon
        )

        docRef.set(data).await()
    }


    override suspend fun getSatuSiswa(id: Long): Siswa? {
        return try {
            val querySnapshot = collection.whereEqualTo("id", id).get().await()
            val document = querySnapshot.documents.firstOrNull()

            document?.let {
                Siswa(
                    id = it.getLong("id") ?: 0L,
                    nama = it.getString("nama") ?: "",
                    alamat = it.getString("alamat") ?: "",
                    telpon = it.getString("telpon") ?: ""
                )
            }
        } catch (e: Exception) {
            null
        }
    }


    override suspend fun hapusSatuSiswa(id: Long) {
        val querySnapshot = collection.whereEqualTo("id", id).get().await()
        for (document in querySnapshot.documents) {
            document.reference.delete().await()
        }
    }
}