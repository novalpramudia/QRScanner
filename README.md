# QR Scanner

Aplikasi Android untuk memindai QR Code menggunakan kamera atau gambar dari galeri, dibangun dengan Kotlin + Jetpack Compose.

## Fitur

- **Scan lewat kamera** — deteksi QR Code otomatis secara real-time menggunakan CameraX + ML Kit, lengkap dengan frame pemandu dan animasi garis pemindaian.
- **Scan dari galeri** — pilih foto QR Code yang sudah tersimpan lewat Photo Picker bawaan Android (tanpa perlu izin penyimpanan tambahan).
- **Hasil scan** — menampilkan isi & jenis data (URL, email, telepon, WiFi, SMS, lokasi, teks), dengan tombol Copy, Buka Link, Bagikan, dan Scan Lagi.
- **Riwayat scan** — tersimpan otomatis secara lokal (Room/SQLite), lengkap dengan tanggal & waktu, bisa dihapus satu per satu atau semua sekaligus.
- **Dark mode & Light mode** — mengikuti tema sistem, didesain dengan Material 3.
- **Penanganan izin kamera** — meminta izin dengan penjelasan yang jelas, dan tombol ke Settings jika izin ditolak.

## Teknologi

| Komponen | Library / Versi |
|---|---|
| Bahasa | Kotlin 2.0.21 |
| UI | Jetpack Compose (Material 3) |
| Kamera | CameraX 1.3.4 |
| Deteksi QR | Google ML Kit Barcode Scanning 17.3.0 |
| Database lokal | Room 2.6.1 |
| Navigasi | Navigation Compose 2.8.3 |
| Build | Android Gradle Plugin 8.6.1, Gradle 8.9 |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 34 (Android 14) |

## Struktur Proyek

```
app/src/main/java/com/example/qrscanner/
├── MainActivity.kt, QrScannerApplication.kt
├── navigation/      # Definisi rute & NavHost
├── ui/screens/      # Home, Scanner, Result, History
├── ui/components/   # ScannerOverlay, dialog izin, item riwayat
├── ui/theme/        # Warna, tipografi, tema light/dark
├── camera/          # Setup CameraX (preview + analisis frame)
├── scanner/         # Pemroses ML Kit (kamera & galeri)
├── data/            # Room database & DAO
├── model/           # Entity & model data
├── repository/      # Jembatan data ke ViewModel
├── viewmodel/        # State & logika UI
└── utils/           # Permission, clipboard, deteksi jenis konten QR
```

## Menjalankan di Android Studio

1. Buka folder proyek ini lewat **Android Studio → Open**.
2. Klik **Sync Now** saat diminta.
3. Sambungkan HP/emulator (min. Android 7.0), lalu klik **Run ▶**.

## Build APK dari Command Line

Proyek ini menyediakan `build.sh` untuk build APK tanpa perlu membuka Android Studio.

**Tanpa Docker** (butuh JDK 17 terpasang):
```bash
./build.sh            # build APK debug
./build.sh release    # build APK release
```

**Dengan Docker** (tidak perlu install JDK/Android SDK sendiri):
```bash
./build.sh --docker            # build APK debug
./build.sh --docker release    # build APK release
```

Docker image dibangun dari `Dockerfile` yang sudah berisi JDK 17, Gradle, dan Android SDK command-line tools. Detail perintahnya juga bisa dijalankan manual:
```bash
docker build -t qrscanner-builder .
docker run --rm -v "$PWD":/app -w /app qrscanner-builder gradle assembleDebug
```

Hasil APK akan muncul di:
```
app/build/outputs/apk/debug/app-debug.apk
app/build/outputs/apk/release/app-release.apk
```

## Membuat APK Release Tertanda (Signed)

Di Android Studio: **Build → Generate Signed Bundle / APK → APK**, lalu buat atau pilih keystore, dan pilih build variant `release`.

## Catatan Izin

Aplikasi hanya meminta izin **CAMERA**. Fitur scan dari galeri memakai Photo Picker bawaan Android sehingga tidak memerlukan izin akses penyimpanan sama sekali.

## Lisensi

Proyek ini dibuat sebagai contoh/template dan bebas dimodifikasi sesuai kebutuhan.
