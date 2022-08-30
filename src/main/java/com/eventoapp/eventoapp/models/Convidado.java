package com.eventoapp.eventoapp.models;

import com.sun.istack.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
@Entity
public class Convidado implements Serializable {

    private static long serialVersionUID = 1L;

    @Id
    @NotEmpty
    private String rg;
    @Column(nullable = false)
    @NotEmpty
    private String nome;
    @ManyToOne(optional = false)
    private Evento evento;

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Convidado convidado = (Convidado) o;
        return Objects.equals(rg, convidado.rg) &&
                Objects.equals(nome, convidado.nome) &&
                Objects.equals(evento, convidado.evento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rg, nome, evento);
    }

    @Override
    public String toString() {
        return rg.toString();
    }
}
