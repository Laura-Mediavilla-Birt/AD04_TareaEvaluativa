package es.activa.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "actividad")
public class Actividad {

    // Identificador de la actividad.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre de la actividad.
    @Column(name = "nombre", nullable = false)
    private String nombre;

    // Localidad donde se realiza.
    @Column(name = "localidad", nullable = false)
    private String localidad;

    // Fecha y hora de la actividad.
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    // Número total de plazas.
    @Column(name = "plazas_totales", nullable = false)
    private int plazasTotales;

    // Número de plazas disponibles.
    @Column(name = "plazas_disponibles", nullable = false)
    private int plazasDisponibles;

    // Cada actividad pertenece a un monitor.
    @ManyToOne
    @JoinColumn(name = "monitor_id", nullable = false)
    private Monitor monitor;

    // Constructor vacío requerido por JPA.
    public Actividad() {
    }

    // Constructor con los datos de la actividad.
    public Actividad(String nombre, String localidad,
                     LocalDateTime fechaHora, int plazasTotales,
                     int plazasDisponibles, Monitor monitor) {
        this.nombre = nombre;
        this.localidad = localidad;
        this.fechaHora = fechaHora;
        this.plazasTotales = plazasTotales;
        this.plazasDisponibles = plazasDisponibles;
        this.monitor = monitor;
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
    public String getLocalidad() {
        return localidad;
    }
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    public int getPlazasTotales() {
        return plazasTotales;
    }
    public void setPlazasTotales(int plazasTotales) {
        this.plazasTotales = plazasTotales;
    }
    public int getPlazasDisponibles() {
        return plazasDisponibles;
    }
    public void setPlazasDisponibles(int plazasDisponibles) {
        this.plazasDisponibles = plazasDisponibles;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }
}