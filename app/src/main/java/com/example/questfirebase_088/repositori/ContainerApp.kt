package com.example.questfirebase_088.repositori

import android.app.Application

interface ContainerApp {
    val repositorySiswa: RepositorySiswa
}

class DefaultContainerApp : ContainerApp {
    override val repositorySiswa: RepositorySiswa by lazy {
        // Pastikan class FirebaseRepositorySiswa sudah kamu buat sebelumnya
        FirebaseRepositorySiswa()
    }
}