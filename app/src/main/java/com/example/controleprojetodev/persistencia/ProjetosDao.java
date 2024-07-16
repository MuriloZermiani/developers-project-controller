package com.example.controleprojetodev.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.controleprojetodev.modelo.Projeto;

import java.util.List;

@Dao
public interface ProjetosDao {
    @Insert
    long insert(Projeto projeto);

    @Delete
    int delete (Projeto projeto);

    @Update
    int update(Projeto projeto);

    @Query("SELECT * FROM projeto WHERE id = :id")
    Projeto queryForId(long id);

    @Query("SELECT * FROM projeto ORDER BY nome ASC")
    List<Projeto> queryAllAscending();

    @Query("SELECT * FROM projeto ORDER BY nome DESC")
    List<Projeto> queryAllDownward();

}
