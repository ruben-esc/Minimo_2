package com.example.restclientapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restclientapp.api.AuthService;
import com.example.restclientapp.api.RetrofitClient;
import com.example.restclientapp.model.Group;
import com.example.restclientapp.model.JoinGroupRequest;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GroupsAdapter adapter;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        SessionManager session = new SessionManager(getApplicationContext());
        userEmail = session.getEmail();

        recyclerView = findViewById(R.id.recyclerGroups);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(userEmail != null) {
            cargarGrupos();
        } else {
            Toast.makeText(this, "Error: No hay usuario logueado", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarGrupos() {
        AuthService service = RetrofitClient.getApiService();
        Call<List<Group>> call = service.getGroups();

        call.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if (response.isSuccessful()) {
                    List<Group> groups = response.body();

                    adapter = new GroupsAdapter(groups, new GroupsAdapter.OnItemClickListener() {
                        @Override
                        public void onJoinClick(String groupId, int position) {
                            unirseAlGrupo(groupId, position);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(GroupsActivity.this, "Error al cargar grupos: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                Toast.makeText(GroupsActivity.this, "Fallo de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void unirseAlGrupo(String groupId, int position) {
        AuthService service = RetrofitClient.getApiService();
        JoinGroupRequest request = new JoinGroupRequest(userEmail, groupId);

        Call<Void> call = service.joinGroup(request);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(GroupsActivity.this, "¡Te has unido al grupo!", Toast.LENGTH_LONG).show();
                    if (adapter != null) {
                        adapter.setJoined(position);
                    }
                }
                else if (response.code() == 409) {
                    Toast.makeText(GroupsActivity.this, "¡Ya estás en un grupo!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(GroupsActivity.this, "Error al unirse (" + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(GroupsActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }
}