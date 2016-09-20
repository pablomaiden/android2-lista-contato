package br.com.meuscontatos.principal.domain;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Contato extends RealmObject {

    @PrimaryKey
    private long id;
    private String nome;
    private String sobreNome;
    private String telefone;
    private String email;
    private String urlFoto;

    public Contato(){}

    public Contato(String nome, String sobreNome, String telefone, String email, String urlFoto){
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.telefone = telefone;
        this.email = email;
        this.urlFoto = urlFoto;
    }

    //getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }


}
