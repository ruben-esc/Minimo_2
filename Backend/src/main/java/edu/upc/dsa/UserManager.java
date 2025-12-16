package edu.upc.dsa;

import edu.upc.dsa.modelos.User;
import edu.upc.dsa.modelos.Group;
import java.util.List;

public interface UserManager {
    User registrarUsuario(String nombre, String email, String password);
    User loginUsuario(String email, String password);
    User getUsuario(String email);
    List<User> getUsuarios();
    boolean enviarCodigoVerificacion(String email);
    boolean verificarCodigo(String email, String codigo);

    //Interfaz actualizada para el mínimo 2:

    public List<Group> getGroups();
    public Group addGroup(String nombre, String descripcion); // Para crear datos de prueba
    public int addUserToGroup(String email, String groupId);
}
