package br.com.meuscontatos.principal.domain;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Conversa extends RealmObject {

    @PrimaryKey
    private long id;
    private String nomeDest; //Pessoa com quem se esta conversando, quem recebeu a primeira mensagem (para aparecer na lista de conversas)
    private String sobreNomeDest; // Pessoa com quem se esta conversando (para aparecer na lista de conversas)
    private String lastMsg; //Texto da última mensagem enviada (para aparecer na lista de conversas)
    private String urlFotoDest; //Foto do usuário Destinatário da conversa

    //Acrescentar dados do usuário remetente tb, aquele que iniciou a conversa

    public Conversa(){}

    public Conversa(String nomeDest, String sobreNomeDest, String lastMsg, String urlFotoDest){
        this.nomeDest = nomeDest;
        this.sobreNomeDest = sobreNomeDest;
        this.lastMsg = lastMsg;
        this.urlFotoDest = urlFotoDest;

    }

    //getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeDest() {
        return nomeDest;
    }

    public void setNomeDest(String nomeDest) {
        this.nomeDest = nomeDest;
    }

    public String getSobreNomeDest() {
        return sobreNomeDest;
    }

    public void setSobreNomeDest(String sobreNomeDest) {
        this.sobreNomeDest = sobreNomeDest;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public String getUrlFotoDest() {
        return urlFotoDest;
    }

    public void setUrlFotoDest(String urlFotoDest) {
        this.urlFotoDest = urlFotoDest;
    }
}
