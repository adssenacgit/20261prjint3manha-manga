package br.edu.senac.mangaapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagina")
public class Pagina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paginas_id")
    private Integer id;

    @Column(name = "paginas_capitulo_id", nullable = false)
    private Integer capituloId;

    @Column(name = "paginas_numero_ordem", nullable = false)
    private Integer numeroOrdem;

    @Column(name = "paginas_imagem_url", nullable = false, length = 255)
    private String imagemUrl;

    @Column(name = "paginas_status", nullable = false)
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCapituloId() {
        return capituloId;
    }

    public void setCapituloId(Integer capituloId) {
        this.capituloId = capituloId;
    }

    public Integer getNumeroOrdem() {
        return numeroOrdem;
    }

    public void setNumeroOrdem(Integer numeroOrdem) {
        this.numeroOrdem = numeroOrdem;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
