package com.example.agenda_contatos.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.agenda_contatos.dao.ContatoDAO;
import com.example.agenda_contatos.service.Contato;

@Database(entities = {Contato.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContatoDAO requestContatoDAO();
}
