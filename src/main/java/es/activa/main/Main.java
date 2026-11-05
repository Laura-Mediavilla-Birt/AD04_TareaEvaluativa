package es.activa.main;

// Importaciones necesarias para trabajar con fechas, listas y teclado.
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

// Importación de las clases de servicios.
import es.activa.service.MonitorService;
import es.activa.service.ActividadService;
import es.activa.service.ClienteService;
import es.activa.service.ReservaService;

// Importación de las clases del dominio.
import es.activa.domain.Actividad;
import es.activa.domain.Reserva;

// Importación de la clase de configuración.
import es.activa.config.JPAUtil;

public class Main {

    public static void main(String[] args) {

        // Scanner permite leer los datos introducidos por teclado.
        Scanner sc = new Scanner(System.in);

        // Creamos los objetos de los servicios que gestionan las operaciones.
        MonitorService servicioMonitor = new MonitorService();
        ActividadService servicio = new ActividadService();
        ClienteService servicioCliente = new ClienteService();
        ReservaService servicioReserva = new ReservaService();

        // Variable que almacena la opción elegida en el menú.
        int opcion;

        try {

            // El menú se repite mientras el usuario no elija salir.
            do {

                System.out.println("\n=== ACTIVA · Gestión de actividades ===");
                System.out.println("1. Crear monitor");
                System.out.println("2. Crear actividad");
                System.out.println("3. Buscar actividades disponibles");
                System.out.println("4. Crear cliente");
                System.out.println("5. Crear reserva");
                System.out.println("6. Cancelar reserva");
                System.out.println("7. Listar actividades");
                System.out.println("8. Salir");

                // Leemos la opción seleccionada.
                opcion = leerEntero(sc, "Elige una opción: ");

                // Según la opción elegida, ejecutamos una operación diferente.
                switch (opcion) {

                    // OPCIÓN 1: Crear un monitor.
                    case 1: {

                        System.out.print("Introduce el nombre del monitor: ");
                        String nombreMonitor = sc.nextLine();

                        System.out.print("Introduce la especialidad: ");
                        String especialidad = sc.nextLine();

                        try {

                            // Llamamos al servicio para guardar el nuevo monitor.
                            Long idMonitor = servicioMonitor.crearMonitor(
                                    nombreMonitor, especialidad);

                            System.out.println("Monitor creado correctamente.");
                            System.out.println("ID del monitor: " + idMonitor);

                        } catch (Exception e) {

                            // Mostramos el error si ocurre algún problema.
                            System.out.println("Error al crear el monitor: "
                                    + e.getMessage());
                        }

                        break;
                    }

                    // OPCIÓN 2: Crear una actividad.
                    case 2: {

                        System.out.print("Introduce el nombre de la actividad: ");
                        String nombreActividad = sc.nextLine();

                        System.out.print("Introduce la localidad: ");
                        String localidadActividad = sc.nextLine();

                        // Solicitamos la fecha y hora con el formato indicado.
                        System.out.println("Introduce la fecha y hora.");
                        System.out.println("Formato: dd/MM/yyyy HH:mm");
                        System.out.print("Fecha y hora: ");
                        String fechaTexto = sc.nextLine();

                        int plazasTotales = leerEntero(sc,
                                "Introduce el número de plazas totales: ");

                        Long idMonitor = leerLong(sc,
                                "Introduce el ID del monitor: ");

                        try {

                            // Definimos el formato para convertir el texto en fecha.
                            DateTimeFormatter formato =
                                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

                            // Convertimos el texto introducido a LocalDateTime.
                            LocalDateTime fechaHora =
                                    LocalDateTime.parse(fechaTexto, formato);

                            // Llamamos al servicio para crear la actividad.
                            Long idActividad = servicio.crearActividad(
                                    nombreActividad,
                                    localidadActividad,
                                    fechaHora,
                                    plazasTotales,
                                    idMonitor
                            );

                            System.out.println("Actividad creada correctamente.");
                            System.out.println("ID de la actividad: " + idActividad);

                        } catch (DateTimeParseException e) {

                            // Informamos si la fecha no tiene el formato correcto.
                            System.out.println("Fecha no válida. Utiliza el formato dd/MM/yyyy HH:mm.");

                        } catch (Exception e) {

                            // Controlamos los posibles errores al crear la actividad.
                            System.out.println("Error al crear la actividad: "
                                    + e.getMessage());
                        }

                        break;
                    }

                    // OPCIÓN 3: Buscar actividades disponibles.
                    case 3: {

                        System.out.print("Introduce la localidad: ");
                        String localidad = sc.nextLine();

                        System.out.print("Introduce el día (AAAA-MM-DD): ");
                        String fechaTexto = sc.nextLine();

                        int plazas = leerEntero(sc,
                                "Número de plazas necesarias: ");

                        try {

                            // Convertimos el texto introducido en una fecha.
                            LocalDate dia = LocalDate.parse(fechaTexto);

                            // Buscamos actividades que cumplan los criterios indicados.
                            List<Actividad> disponibles =
                                    servicio.buscarDisponibles(localidad, dia, plazas);

                            // Comprobamos si se han encontrado actividades.
                            if (disponibles.isEmpty()) {

                                System.out.println("No hay actividades disponibles.");

                            } else {

                                // Recorremos la lista y mostramos los datos de cada actividad.
                                for (Actividad a : disponibles) {

                                    System.out.println("ID: " + a.getId());
                                    System.out.println("Nombre: " + a.getNombre());
                                    System.out.println("Fecha y hora: " + a.getFechaHora());
                                    System.out.println("Monitor: "
                                            + a.getMonitor().getNombre());
                                    System.out.println("Plazas disponibles: "
                                            + a.getPlazasDisponibles());
                                    System.out.println("-------------------------");
                                }
                            }

                        } catch (DateTimeParseException e) {

                            // Informamos si la fecha no es válida.
                            System.out.println("Fecha no válida. Utiliza el formato AAAA-MM-DD.");

                        } catch (IllegalArgumentException e) {

                            // Controlamos los errores de validación de la búsqueda.
                            System.out.println("No se pudo realizar la búsqueda: "
                                    + e.getMessage());

                        } catch (Exception e) {

                            // Capturamos otros posibles errores.
                            System.out.println("Error al buscar actividades: "
                                    + e.getMessage());
                        }

                        break;
                    }

                    // OPCIÓN 4: Crear un cliente.
                    case 4: {

                        System.out.print("Introduce el nombre del cliente: ");
                        String nombreCliente = sc.nextLine();

                        System.out.print("Introduce el email del cliente: ");
                        String email = sc.nextLine();

                        try {

                            // Llamamos al servicio para registrar el cliente.
                            Long idCliente = servicioCliente.crearCliente(
                                    nombreCliente, email);

                            System.out.println("Cliente creado correctamente.");
                            System.out.println("ID del cliente: " + idCliente);

                        } catch (Exception e) {

                            // Mostramos el error si no se puede crear el cliente.
                            System.out.println("Error al crear el cliente: "
                                    + e.getMessage());
                        }

                        break;
                    }

                    // OPCIÓN 5: Crear una reserva.
                    case 5: {

                        Long idCliente = leerLong(sc,
                                "Introduce el ID del cliente: ");

                        Long idActividad = leerLong(sc,
                                "Introduce el ID de la actividad: ");

                        int numeroPlazas = leerEntero(sc,
                                "Número de plazas que deseas reservar: ");

                        try {

                            // Creamos la reserva utilizando el servicio correspondiente.
                            Reserva reserva = servicioReserva.crearReserva(
                                    idCliente, idActividad, numeroPlazas);

                            System.out.println("Reserva creada correctamente.");
                            System.out.println("ID de la reserva: " + reserva.getId());

                            // Mostramos las plazas que quedan disponibles.
                            System.out.println("Plazas restantes: "
                                    + reserva.getActividad().getPlazasDisponibles());

                        } catch (IllegalArgumentException e) {

                            // Controlamos errores relacionados con los datos de la reserva.
                            System.out.println("No se pudo crear la reserva: "
                                    + e.getMessage());

                        } catch (Exception e) {

                            // Capturamos cualquier otro error.
                            System.out.println("Error al crear la reserva: "
                                    + e.getMessage());
                        }

                        break;
                    }

                    // OPCIÓN 6: Cancelar una reserva.
                    case 6: {

                        Long idReserva = leerLong(sc,
                                "Introduce el ID de la reserva que quieres cancelar: ");

                        try {

                            // Buscamos y cancelamos la reserva mediante el servicio.
                            Reserva reservaCancelada =
                                    servicioReserva.cancelarReserva(idReserva);

                            System.out.println("Reserva cancelada correctamente.");
                            System.out.println("ID de la reserva: "
                                    + reservaCancelada.getId());

                            // Mostramos las plazas que se han recuperado.
                            System.out.println("Plazas recuperadas: "
                                    + reservaCancelada.getNumeroPlazas());

                            System.out.println("Plazas disponibles actualmente: "
                                    + reservaCancelada.getActividad().getPlazasDisponibles());

                        } catch (IllegalArgumentException e) {

                            // Mostramos los errores relacionados con la cancelación.
                            System.out.println("No se pudo cancelar la reserva: "
                                    + e.getMessage());

                        } catch (Exception e) {

                            // Capturamos cualquier otro error.
                            System.out.println("Error al cancelar la reserva: "
                                    + e.getMessage());
                        }

                        break;
                    }

                    // OPCIÓN 7: Listar todas las actividades.
                    case 7: {

                        try {

                            // Recuperamos todas las actividades registradas.
                            List<Actividad> actividades = servicio.listarActividades();

                            // Comprobamos si existen actividades.
                            if (actividades.isEmpty()) {

                                System.out.println("No hay actividades registradas.");

                            } else {

                                // Recorremos la lista y mostramos los datos de cada actividad.
                                for (Actividad a : actividades) {

                                    System.out.println("ID: " + a.getId());
                                    System.out.println("Nombre: " + a.getNombre());
                                    System.out.println("Localidad: " + a.getLocalidad());
                                    System.out.println("Fecha y hora: " + a.getFechaHora());
                                    System.out.println("Monitor: "
                                            + a.getMonitor().getNombre());
                                    System.out.println("Plazas totales: "
                                            + a.getPlazasTotales());
                                    System.out.println("Plazas disponibles: "
                                            + a.getPlazasDisponibles());
                                    System.out.println("-------------------------");
                                }
                            }

                        } catch (Exception e) {

                            // Informamos si ocurre algún error durante la consulta.
                            System.out.println("Error al listar las actividades: "
                                    + e.getMessage());
                        }

                        break;
                    }

                    // OPCIÓN 8: Salir del programa.
                    case 8:

                        System.out.println("Programa finalizado.");
                        break;

                    // Si se introduce una opción distinta de las anteriores.
                    default:

                        System.out.println("Opción no válida.");
                }

            } while (opcion != 8);

        } finally {

            // Cerramos el Scanner al finalizar el programa.
            sc.close();

            // Cerramos la fábrica de persistencia y liberamos sus recursos.
            JPAUtil.cerrar();
        }
    }

    // Método para leer números enteros y controlar entradas incorrectas.
    private static int leerEntero(Scanner sc, String mensaje) {

        while (true) {

            System.out.print(mensaje);
            String entrada = sc.nextLine().trim();

            try {

                return Integer.parseInt(entrada);

            } catch (NumberFormatException e) {

                System.out.println("Introduce un número entero válido.");
            }
        }
    }

    // Método para leer identificadores de tipo Long.
    private static Long leerLong(Scanner sc, String mensaje) {

        while (true) {

            System.out.print(mensaje);
            String entrada = sc.nextLine().trim();

            try {

                return Long.parseLong(entrada);

            } catch (NumberFormatException e) {

                System.out.println("Introduce un identificador válido.");
            }
        }
    }
}