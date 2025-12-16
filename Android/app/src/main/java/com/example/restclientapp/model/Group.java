package com.example.restclientapp.model;

public class Group {
    private String id;
    private String nombre;
    private String descripcion;
    private boolean isMember = false;
    public Group() {}

    public boolean isMember() { return isMember; }
    public void setMember(boolean member) { isMember = member; }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
}
