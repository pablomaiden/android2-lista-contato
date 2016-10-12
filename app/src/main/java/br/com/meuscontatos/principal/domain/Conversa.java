package br.com.meuscontatos.principal.domain;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Conversa extends RealmObject {

    @PrimaryKey
    private long id;
    private String idFirebase;
    private String name; //Pessoa com quem se esta conversando, quem recebeu a primeira mensagem (para aparecer na lista de conversas)
    private String text; //Texto da última mensagem enviada (para aparecer na lista de conversas)
    private String photoUrl; //Foto do usuário Destinatário da conversa

    //Acrescentar dados do usuário remetente tb, aquele que iniciou a conversa

    public Conversa(){}

    public Conversa(String name, String text, String photoUrl){
        this.name = name;
        this.text = text;
        this.photoUrl = photoUrl;

    }

    //getters and setters
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdFirebase() {
        return idFirebase;
    }

    public void setIdFirebase(String idFirebase) {
        this.idFirebase = idFirebase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
