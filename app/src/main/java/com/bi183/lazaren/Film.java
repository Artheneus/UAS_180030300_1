package com.bi183.lazaren;

import java.util.Date;

public class Film {

    private int idFilm;
    private String judul;
    private String gambar;
    private Date tanggal;
    private String sinopsis;
    private String linkTrailer;

    public Film(int idFilm, String judul, String gambar, Date tanggal, String sinopsis, String linkTrailer) {
        this.idFilm = idFilm;
        this.judul = judul;
        this.gambar = gambar;
        this.tanggal = tanggal;
        this.sinopsis = sinopsis;
        this.linkTrailer = linkTrailer;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getLinkTrailer() {
        return linkTrailer;
    }

    public void setLinkTrailer(String linkTrailer) {
        this.linkTrailer = linkTrailer;
    }
}
