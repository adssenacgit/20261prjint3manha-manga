package br.edu.senac.mangaapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "capitulo")
public class Capitulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "capitulos_id")
    private Integer id;

    @Column(name = "capitulos_manga_id", nullable = false)
    private Integer mangaId;

    @Column(name = "capitulos_numero", nullable = false)
    private Integer numero;

    @Column(name = "capitulos_titulo", length = 150)
    private String titulo;

    @Column(name = "capitulos_status", nullable = false)
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMangaId() {
        return mangaId;
    }

    public void setMangaId(Integer mangaId) {
        this.mangaId = mangaId;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
