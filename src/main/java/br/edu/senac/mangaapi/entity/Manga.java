package br.edu.senac.mangaapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "manga")
public class Manga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mangas_id")
    private Integer id;

    @Column(name = "mangas_titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "mangas_autor", nullable = false, length = 100)
    private String autor;

    @Column(name = "mangas_capa_url", length = 255)
    private String capaUrl;

    @Column(name = "mangas_categoria", nullable = false, length = 50)
    private String categoria;

    @Column(name = "mangas_status", nullable = false)
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCapaUrl() {
        return capaUrl;
    }

    public void setCapaUrl(String capaUrl) {
        this.capaUrl = capaUrl;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
