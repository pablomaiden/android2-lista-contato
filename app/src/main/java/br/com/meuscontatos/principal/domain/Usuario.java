package br.com.meuscontatos.principal.domain;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Usuario extends RealmObject{

    @PrimaryKey
    private Long id;
    private String usuario;
    private String senha;
    private String email;
    private String idUserFireBase;
    private String nameUserFireBase;
    private String urlFotoFireBase;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdUserFireBase() {
        return idUserFireBase;
    }

    public void setIdUserFireBase(String idUserFireBase) {
        this.idUserFireBase = idUserFireBase;
    }

    public String getNameUserFireBase() {
        return nameUserFireBase;
    }

    public void setNameUserFireBase(String nameUserFireBase) {
        this.nameUserFireBase = nameUserFireBase;
    }

    public String getUrlFotoFireBase() {
        return urlFotoFireBase;
    }

    public void setUrlFotoFireBase(String urlFotoFireBase) {
        this.urlFotoFireBase = urlFotoFireBase;
    }
}
