package es.activa.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Table(name = "cliente")
public class Cliente {

    // Identificador del cliente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del cliente.
    @Column(name = "nombre", nullable = false)
    private String nombre;

    // Correo electrónico único.
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Constructor vacío requerido por JPA.
    public Cliente() {
    }

    // Constructor con los datos del cliente.
    public Cliente(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    // Getters y setters.
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}