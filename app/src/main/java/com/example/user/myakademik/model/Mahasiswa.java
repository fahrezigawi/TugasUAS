package com.example.user.myakademik.model;


//TODO 3 Buat kelas model Mahasiswa

public class Mahasiswa {
    private String nim, nama, alamat, nik, kode_mk, nama_matkul, sks;

    public Mahasiswa() {

    }

    public Mahasiswa(String nim, String nama, String alamat, String nik, String kode_mk, String nama_matkul, String sks) {
        this.nim = nim;
        this.nama = nama;
        this.alamat = alamat;
        this.nik = nik;
        this.kode_mk = kode_mk;
        this.nama_matkul = nama_matkul;
        this.sks = sks;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getKode_mk() {
        return kode_mk;
    }

    public void setKode_mk(String kode_mk) {
        this.kode_mk = kode_mk;
    }

    public String getNama_matkul() {
        return nama_matkul;
    }

    public void setNama_matkul(String nama_matkul) {
        this.nama_matkul = nama_matkul;
    }

    public String getSks() {
        return sks;
    }

    public void setSks(String sks) {
        this.sks = sks;
    }
}

