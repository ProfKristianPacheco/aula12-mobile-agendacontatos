package com.example.agenda_contatos.service;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "CONTATOS")
public class Contato {
    @ColumnInfo(name = "id_contato")
    @PrimaryKey(autoGenerate = true)
    private int idContato;
    @ColumnInfo(name = "nome_contato")
    private String nomeContato;
    @ColumnInfo(name = "telefone_contato")
    private String telefoneContato;

    public Contato(String nomeContato, String telefoneContato) {
        this.nomeContato = nomeContato;
        this.telefoneContato = telefoneContato;
    }

    @Override
    public String toString() {
        return nomeContato + " - " + telefoneContato;
    }

    public int getIdContato() {
        return idContato;
    }

    public void setIdContato(int idContato) {
        this.idContato = idContato;
    }

    public String getNomeContato() {
        return nomeContato;
    }

    public void setNomeContato(String nomeContato) {
        this.nomeContato = nomeContato;
    }

    public String getTelefoneContato() {
        return telefoneContato;
    }

    public void setTelefoneContato(String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }
}
