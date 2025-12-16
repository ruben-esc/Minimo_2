package edu.upc.dsa;

import edu.upc.dsa.modelos.User;
import edu.upc.dsa.modelos.Group;
import java.util.*;

public class UserManagerImpl implements UserManager {
    private static UserManagerImpl instance;
    private List<User> usuarios;
    private List<Group> groups;

    private UserManagerImpl() {
        usuarios = new ArrayList<>();
        groups = new LinkedList<>();

        this.addGroup("Grupo de Prueba 1", "Confirmación de que funciona");
        this.addGroup("Grupo de Prueba 2", "Este también funciona");

    }

    public static UserManagerImpl getInstance() {
        if (instance == null) instance = new UserManagerImpl();
        return instance;
    }

    @Override
    public User registrarUsuario(String nombre, String email, String password) {
        for (User u : usuarios) {
            if (u.getEmail().equals(email))
                return null;
        }

        User nuevo = new User(UUID.randomUUID().toString(), nombre, email, password);
        nuevo.setEmailVerificado(false);
        usuarios.add(nuevo);
        return nuevo;
    }

    @Override
    public User loginUsuario(String email, String password) {
        // Buscamos al usuario por email
        User u = this.getUsuario(email);

        if (u != null) {
            // Si el usuario existe, se comprueba la contraseña
            if (u.getPassword().equals(password)) {
                return u; //Login correcto
            } else {
                return null; //Contraseña incorrecta
            }
        }
        return null; // Usuario no encontrado
    }

    @Override
    public User getUsuario(String email) {
        for (User u : usuarios) {
            if (u.getEmail().equals(email)) return u;
        }
        return null;
    }

    @Override
    public List<User> getUsuarios() {
        return usuarios;
    }

    public boolean enviarCodigoVerificacion(String email) {
        User user = this.getUsuario(email);

        if (user == null) {
            return false;
        }

        Random random = new Random();
        String codigo = String.format("%06d", random.nextInt(999999));

        user.setCodigoVerificacion(codigo);


        System.out.println("═══════════════════════════════════════");
        System.out.println("📧 CÓDIGO DE VERIFICACIÓN");
        System.out.println("Email: " + email);
        System.out.println("Código: " + codigo);
        System.out.println("═══════════════════════════════════════");

        return true;
    }

    public boolean verificarCodigo(String email, String codigo) {
        User user = this.getUsuario(email);

        if (user == null) {
            return false;
        }

        if (codigo.equals(user.getCodigoVerificacion())) {
            user.setEmailVerificado(true);
            user.setCodigoVerificacion(null);
            System.out.println("Email verificado: " + email);
            return true;
        }

        System.out.println("Código incorrecto para: " + email);
        return false;
    }

    //Implementación actualizada para el mínimo 2:

    @Override
    public List<Group> getGroups() {
        return this.groups;
    }

    @Override
    public Group addGroup(String nombre, String descripcion) {
        String id = UUID.randomUUID().toString();
        Group g = new Group(id, nombre, descripcion);
        this.groups.add(g);
        return g;
    }

    @Override
    public int addUserToGroup(String email, String groupId) {
        User u = this.getUsuario(email);
        if (u == null) return 1; // Error: Usuario no encontrado

        if (u.getGrupos() != null && !u.getGrupos().isEmpty()) {
            return 3; // Error: El usuario ya tiene grupo
        }

        Group targetGroup = null;
        for(Group g : this.groups) {
            if(g.getId().equals(groupId)) {
                targetGroup = g;
                break;
            }
        }

        if (targetGroup == null) return 2; // Error: Grupo no existe

        u.addGrupo(targetGroup.getId());
        return 0; // Éxito
    }
}
