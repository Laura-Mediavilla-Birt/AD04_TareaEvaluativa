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
@Table(name = "reserva")
public class Reserva {

    // Identificador de la reserva.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fecha y hora de la reserva.
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDateTime fechaReserva;

    // Número de plazas reservadas.
    @Column(name = "numero_plazas", nullable = false)
    private int numeroPlazas;

    // Actividad a la que pertenece la reserva.
    @ManyToOne
    @JoinColumn(name = "actividad_id", nullable = false)
    private Actividad actividad;

    // Cliente que realiza la reserva.
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Constructor vacío requerido por JPA.
    public Reserva() {
    }

    // Constructor con los datos de la reserva.
    public Reserva(LocalDateTime fechaReserva, int numeroPlazas,
                   Actividad actividad, Cliente cliente) {
        this.fechaReserva = fechaReserva;
        this.numeroPlazas = numeroPlazas;
        this.actividad = actividad;
        this.cliente = cliente;
    }

    // Getters y setters.
    public Long getId() {
        return id;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public int getNumeroPlazas() {
        return numeroPlazas;
    }

    public void setNumeroPlazas(int numeroPlazas) {
        this.numeroPlazas = numeroPlazas;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}