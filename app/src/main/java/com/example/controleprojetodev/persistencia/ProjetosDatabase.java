package com.example.controleprojetodev.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.controleprojetodev.modelo.Projeto;

@Database(entities = {Projeto.class}, version = 6, exportSchema = false)
public abstract class ProjetosDatabase extends RoomDatabase{
    public abstract ProjetosDao getProjetosDao();

    private static ProjetosDatabase instance;

    public static ProjetosDatabase getDatabase(final Context context){
        if(instance == null){
            synchronized (ProjetosDatabase.class){
                if (instance == null){
                    instance = Room.databaseBuilder(context,
                                                    ProjetosDatabase.class,
                                                    "projetos.db")
                                                    .allowMainThreadQueries()
                                                    .build();
                }
            }
        }
        return instance;
    }
}
