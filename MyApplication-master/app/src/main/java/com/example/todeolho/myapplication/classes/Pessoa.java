package com.example.todeolho.myapplication.classes;

/**
 * Created by alan_ on 20/06/2016.
 */
public class Pessoa {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


    private String id;
    private String foto;
    private String nome;
    private String email;

}
