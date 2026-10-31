package com.example.agenda_contatos;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.agenda_contatos.database.AppDatabase;
import com.example.agenda_contatos.service.Contato;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText nome;
    private EditText telefone;
    private Button salvar, excluir; //NÃO GOSTO DESSA SINTAXE
    private AppDatabase db;
    private ListView listViewContatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //INICIALIZAÇÃO DE COMPONENTES DE TELA
        setContentView(R.layout.activity_main);
        nome = findViewById(R.id.editTextNome);
        telefone = findViewById(R.id.editTextTelefone);
        salvar = findViewById(R.id.buttonSalvar);
        excluir = findViewById(R.id.buttonExcluir);
        listViewContatos = findViewById(R.id.listViewContatos);

        //CRIAÇÃO DO BANCO DE DADOS
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "AGENDA.db").build();

        buscarContatos();
        //CAPTURAR OS CLIQUES
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarContato();
            }
        });

        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                excluirContato();
            }
        });
    }

    private void excluirContato() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.requestContatoDAO().excluirTodos();
                buscarContatos();
            }
        }).start();
    }

    private void salvarContato() {
        String nomeContato = nome.getText().toString();
        String telefoneContato = telefone.getText().toString();
        if(nomeContato.isEmpty() || telefoneContato.isEmpty()){
            Toast.makeText(this, "Preencha todas as informações!", Toast.LENGTH_SHORT).show();
            return;
        }
        Contato contato = new Contato(nomeContato, telefoneContato);

        //SAINDO DA THREAD DE UI PARA FAZER ACESSO AO BANCO DE DADOS!
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.requestContatoDAO().inserirContato(contato); //FAZENDO O INSERT NO BANCO DE DADOS
                runOnUiThread(new Runnable() { //VOLTANDO PRA THREAD DE UI PARA ACESSAR MEUS COMPONENTES DE TELA
                    @Override
                    public void run() {
                        nome.setText("");
                        telefone.setText("");
                        Toast.makeText(MainActivity.this, "Contato salvo!", Toast.LENGTH_SHORT).show();
                    }
                });
                buscarContatos();

            }
        }).start();
    }

    private void buscarContatos(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Contato> todosContatos = db.requestContatoDAO().buscarTodosContatos();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<Contato> adaptador = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, todosContatos);
                        listViewContatos.setAdapter(adaptador);
                    }
                });
            }
        }).start();

    }
}