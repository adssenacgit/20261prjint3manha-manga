package br.edu.senac.mangaapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "favoritos")
public class Favorito {

    @EmbeddedId
    private FavoritoId id;

    @CreationTimestamp
    @Column(name = "favoritos_data_adicao", nullable = false, updatable = false)
    private LocalDateTime dataAdicao;

    @Column(name = "favoritos_status", nullable = false)
    private Integer status;

    public FavoritoId getId() {
        return id;
    }

    public void setId(FavoritoId id) {
        this.id = id;
    }

    public LocalDateTime getDataAdicao() {
        return dataAdicao;
    }

    public void setDataAdicao(LocalDateTime dataAdicao) {
        this.dataAdicao = dataAdicao;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
