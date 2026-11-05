package es.activa.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

@Entity
@Table(name = "monitor")
public class Monitor {

    // Identificador del monitor.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del monitor.
    @Column(name = "nombre", nullable = false)
    private String nombre;

    // Especialidad del monitor.
    @Column(name = "especialidad", nullable = false)
    private String especialidad;

    // Un monitor puede dirigir varias actividades.
    @OneToMany(mappedBy = "monitor")
    private List<Actividad> actividades = new ArrayList<>();

    // Constructor vacío requerido por JPA.
    public Monitor() {
    }

    // Constructor con los datos del monitor.
    public Monitor(String nombre, String especialidad) {
        this.nombre = nombre;
        this.especialidad = especialidad;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }
}