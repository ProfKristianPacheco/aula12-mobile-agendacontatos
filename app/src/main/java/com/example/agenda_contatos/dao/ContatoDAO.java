package com.example.agenda_contatos.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.agenda_contatos.service.Contato;

import java.util.List;

//NA CAMADA DAO EU FAÇO OS CRUDS
@Dao
public interface ContatoDAO {
    @Insert
    public void inserirContato(Contato contato);
    @Delete
    public void excluirContato(Contato contato);
    @Update
    public void atualizarContato(Contato contato);
    @Query("SELECT * FROM CONTATOS")
    public List<Contato> buscarTodosContatos();
    @Query("DELETE FROM CONTATOS WHERE id_contato = (SELECT MIN(id_contato) FROM contatos)")
    public void excluirPrimeiroContato();
    @Query("DELETE FROM CONTATOS WHERE id_contato = (SELECT MAX(id_contato) FROM contatos)")
    public void excluirUltimoContato();
    @Query("DELETE FROM CONTATOS")
    public void excluirTodos();
}
