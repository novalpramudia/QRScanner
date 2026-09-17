# QR Scanner Android App

<p align="center">
  <img src="path/to/your/app_icon.png" width="128" height="128" alt="QR Scanner Logo">
</p>

<p align="center">
  Aplikasi Android modern untuk memindai QR Code menggunakan kamera secara real-time atau dari gambar galeri, dibangun sepenuhnya menggunakan **Kotlin** dan **Jetpack Compose**.
</p>

---

## 📱 Tampilan Aplikasi

<p align="center">
  <img src="path/to/your/screenshot_home.png" width="250" alt="Home Screen">
  &nbsp;&nbsp;&nbsp;&nbsp;
  <img src="path/to/your/screenshot_scanner.png" width="250" alt="Scanner Screen">
</p>
<p align="center">
  <i>(Kiri) Halaman Utama. (Kanan) Tampilan Kamera dengan Overlay Pemindaian Aktif.</i>
</p>

---

## ✨ Fitur Utama

-   **📸 Scan Lewat Kamera**
    -   Deteksi QR Code otomatis secara real-time menggunakan kolaborasi **CameraX** dan **Google ML Kit**.
    -   Dilengkapi dengan frame pemandu yang intuitif dan animasi garis pemindaian untuk pengalaman pengguna yang lebih baik (seperti terlihat pada gambar).
-   **🖼️ Scan dari Galeri**
    -   Pilih foto berisi QR Code yang sudah tersimpan.
    -   Menggunakan **Photo Picker** bawaan Android modern, sehingga aplikasi **tidak memerlukan izin akses penyimpanan tambahan** apa pun.
-   **📑 Penanganan Hasil Scan**
    -   Menampilkan isi data dan jenis konten QR secara cerdas (URL, email, telepon, WiFi, SMS, lokasi, teks datar).
    -   Aksi cepat sekali klik: **Copy** (Salin), **Buka Link** di browser, **Bagikan** (Share), dan **Scan Lagi**.
-   **📜 Riwayat Scan (History)**
    -   Tersimpan otomatis secara lokal menggunakan **Room/SQLite**.
    -   Mencatat detail lengkap termasuk tanggal & waktu pemindaian.
    -   Manajemen riwayat: hapus item satu per satu atau semua sekaligus.
-   **🎨 UI/UX Modern**
    -   Mendukung penuh **Dark Mode & Light Mode** yang mengikuti tema sistem.
    -   Didesain menggunakan prinsip **Material 3** untuk tampilan yang bersih dan kontemporer.
-   **🔒 Penanganan Izin (Permissions)**
    -   Alur permintaan izin kamera yang ramah pengguna dengan penjelasan yang jelas sebelum meminta.
    -   Menyediakan tombol akses cepat langsung ke Settings jika izin ditolak secara permanen.

---

## 🛠️ Teknologi & Library

Aplikasi ini menggunakan tumpukan teknologi Android terkini:

| Komponen | Library / Versi | Deskripsi |
| :--- | :--- | :--- |
| **Bahasa** | **Kotlin 2.0.21** | Bahasa pemrograman utama. |
| **UI Framework** | **Jetpack Compose (M3)** | Toolkit modern untuk membangun UI deklaratif. |
| **Kamera** | **CameraX 1.3.4** | Library Jetpack untuk integrasi kamera yang konsisten. |
| **Deteksi QR** | **Google ML Kit** | Barcode Scanning API (v17.3.0) untuk deteksi on-device yang cepat. |
| **Database Lokal** | **Room 2.6.1** | Abstraksi SQLite untuk penyimpanan riwayat. |
| **Navigasi** | **Navigation Compose 2.8.3** | Navigasi antar layar di Compose. |
| **Build Tool** | **AGP 8.6.1, Gradle 8.9** | Konfigurasi build proyek. |

### Spesifikasi Minimum
*   **Min SDK:** 24 (Android 7.0 "Nougat")
*   **Target SDK:** 34 (Android 14)

---

## 📂 Struktur Proyek

Berikut adalah gambaran singkat struktur paket sumber dalam folder `app/src/main/java/com/example/qrscanner/`:

```text
├── MainActivity.kt, QrScannerApplication.kt (Entry point)
├── navigation/      # Definisi rute, NavHost, dan argumen navigasi.
├── ui/              
│   ├── screens/     # Layar utama: Home, Scanner (Kamera), Result, History.
│   ├── components/  # Komponen UI reusable: ScannerOverlay, dialog izin, item riwayat.
│   └── theme/       # Konfigurasi Tema Material 3: Warna, tipografi, shapes.
├── camera/          # Setup CameraX, lifecycle owner, preview use case.
├── scanner/         # Logika pemroses ML Kit untuk frame kamera & bitmap galeri.
├── data/            # Room database, Entities, dan DAO (Data Access Object).
├── model/           # Model data internal aplikasi.
├── repository/      # Jembatan data antara database dan ViewModel (Single Source of Truth).
├── viewmodel/       # Pemegang state UI dan logika bisnis yang sadar siklus hidup.
└── utils/           # Helper untuk Permission, clipboard, dan deteksi jenis konten QR.
