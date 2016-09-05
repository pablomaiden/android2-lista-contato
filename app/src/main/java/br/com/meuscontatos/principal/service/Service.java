package br.com.meuscontatos.principal.service;


import android.content.Context;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

public class Service {

    private static Service sInstace;
    public Service(){
    }
    public static synchronized Service getInstace(){
        if(sInstace==null){
            sInstace= new Service();
        }
        return sInstace;
    }

    public Realm getRealm(Context context){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        try {
            return Realm.getInstance(realmConfiguration);
        } catch (RealmMigrationNeededException e){
            try {
                Realm.deleteRealm(realmConfiguration);
                return Realm.getInstance(realmConfiguration);
            } catch (Exception ex){
                throw ex;
            }
        }
    }
}
