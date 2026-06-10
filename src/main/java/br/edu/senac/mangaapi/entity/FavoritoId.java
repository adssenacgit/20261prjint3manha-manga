package br.edu.senac.mangaapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavoritoId implements Serializable {

    @Column(name = "favoritos_usuario_id", nullable = false)
    private Integer usuarioId;

    @Column(name = "favoritos_manga_id", nullable = false)
    private Integer mangaId;

    public FavoritoId() {
    }

    public FavoritoId(Integer usuarioId, Integer mangaId) {
        this.usuarioId = usuarioId;
        this.mangaId = mangaId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getMangaId() {
        return mangaId;
    }

    public void setMangaId(Integer mangaId) {
        this.mangaId = mangaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FavoritoId that)) {
            return false;
        }
        return Objects.equals(usuarioId, that.usuarioId) && Objects.equals(mangaId, that.mangaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, mangaId);
    }
}
