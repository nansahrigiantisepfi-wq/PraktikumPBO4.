# Praktikum
# Sistem Manajemen Perpustakaan Mini

Aplikasi Java berbasis konsol untuk mengelola perpustakaan mini. Selain operasi CRUD sederhana, aplikasi ini juga menganalisis koleksi buku dan aktivitas peminjaman.

Proyek ini dibuat untuk Praktikum Pemrograman Berorientasi Objek (PBO).

## Fitur

- **Tambah Buku**: menambah buku baru (judul, penulis, tahun terbit, kategori).
- **Daftar Buku**: menampilkan semua buku beserta status ketersediaannya.
- **Cari Buku**: mencari berdasarkan judul atau kategori (tidak peka huruf besar/kecil).
- **Pinjam Buku**: anggota dapat meminjam maksimal 3 buku.
- **Kembalikan Buku**: mengembalikan buku yang dipinjam.
- **Laporan Perpustakaan**: menampilkan total pinjaman, buku paling sering dipinjam, anggota paling aktif, kategori paling populer, dan jumlah buku per kategori.

## Konsep yang Diterapkan

| Konsep | Penerapan |
|---|---|
| OOP | Class, object, method, constructor, variabel, package |
| Tipe data | Primitive (`int`, `boolean`) dan reference (`String`, `ArrayList`, `HashMap`) |
| Struktur kontrol | Kondisional (`if`, `switch`) dan looping (`for`, `while`) |
| Exception | 3 custom exception |
| Assertion | Validasi data anggota sebelum transaksi |
| Character & String | `toLowerCase()`, `contains()`, `Character.toUpperCase()`, `Character.isLetterOrDigit()` |

## Struktur Project

```
library
├── model
│   ├── Book.java
│   └── Member.java
├── service
│   └── LibraryService.java
├── exception
│   ├── BookNotFoundException.java
│   ├── BorrowLimitExceededException.java
│   └── BookAlreadyBorrowedException.java
└── main
    └── MainApp.java
```

| Package | Fungsi |
|---|---|
| `model` | Menyimpan data buku dan anggota |
| `service` | Berisi logika program (pinjam, kembali, cari, laporan) |
| `exception` | Custom exception untuk penanganan error |
| `main` | Menu interaktif dan input pengguna (Scanner) |

## Custom Exception

- `BookNotFoundException`: buku tidak ditemukan.
- `BookAlreadyBorrowedException`: buku sedang dipinjam.
- `BorrowLimitExceededException`: anggota meminjam lebih dari 3 buku.

## Cara Menjalankan

### Persyaratan
- JDK (Java Development Kit)
- Apache NetBeans (atau IDE Java lain)

### Langkah
1. Clone repository ini:
   ```
   git clone https://github.com/nansahrigiantisepfi-wq/Praktikum-PBO.git
   ```
2. Buka project di NetBeans (**File → Open Project**).
3. Aktifkan assertion: **Project Properties → Run → VM Options**, isi dengan:
   ```
   -ea
   ```
4. Klik kanan `MainApp.java` (package `library.main`) lalu pilih **Run File**.

## Contoh Tampilan Menu

```
===== PERPUSTAKAAN MINI =====
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
0. Keluar
Pilih menu:
```

## Rencana Pengembangan

- Penyimpanan data permanen (file atau database)
- Denda keterlambatan
- Login admin dan anggota
- Antarmuka grafis (Swing atau JavaFX)
- Unit test dengan JUnit

## Pembuat

- **Nama**: SEPFINANGSARIGIANTI
- **NIM**:L0325013
- **Kelas**: 3B
