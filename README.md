# Sistema de Gestión Hotelera
Sistema de Gestión Hotelera y Servicios para Huéspedes para la materia Análisis y Diseño Orientado a Objetos.

## Integrantes
- Micaela Cembal
- Agustina Gryner

## Descripción
Sistema desarrollado en Java que permite administrar huéspedes, habitaciones, reservas, estadías y pagos. Le agregamos una interfaz gráfica Swing, como bien vimos en la materia Paradigma Orientado a Objetos e hicimos la persistencia en base de datos MySQL.

## Instrucciones para ejecutar el proyecto
1. Clonar el repositorio: git clone https://github.com/MicaelaCembal/SistemaGestionHotelera-CembalyGryner.git
2. Abrir el proyecto en IntelliJ IDEA
3. Agregar el JAR mysql-connector-j-9.7.0 en File. Ahi entras a Project Structure, despues a Modules y ahi a Dependencies
4. Tener corriendo o PhpMyAdmin como uso Micaela o MySQL como uso Agustina 
5. Ejecutar Main.java para la interfaz gráfica o MainConsola.java para modo consola
6. La base de datos y tablas se crean automáticamente al iniciar
7. Usuarios por defecto: admin / 1234 (ADMIN) y recep / 4321 (RECEPCIONISTA)

## Patrones de diseño aplicados
- **Singleton:** ControladorSistema
- **Facade:** ControladorSistema
- **State:** EstadoHabitacion, EstadoDisponible, EstadoOcupado, EstadoLimpieza, EstadoMantenimiento
- **Strategy:** IMedioPago, PagoTarjeta, PagoEfectivo, PagoTransferencia
- **Composite:** IServicio, ServicioConsumo, ServicioActividad, PaqueteServicios
- **Observer:** IObserver, NotificadorSistema

## Principios SOLID aplicados
- **SRP:** cada clase tiene una única responsabilidad
- **OCP:** las interfaces permiten agregar nuevas implementaciones sin modificar código existente
- **LSP:** las implementaciones son intercambiables entre sí
- **DIP:** las clases dependen de abstracciones, no de implementaciones concretas

## Patrones GRASP aplicados
- **Experto en Información:** Reserva calcula su propio costo total
- **Controlador:** ControladorSistema coordina todas las operaciones
- **Creador:** Hotel crea y administra las habitaciones
- **Bajo Acoplamiento:** uso de interfaces en todo el sistema
- **Alta Cohesión:** cada clase tiene responsabilidades bien definidas
- **Polimorfismo:** comportamiento distinto según la implementación concreta

## Distribución de tareas

**Micaela Cembal:**
- Clases: Hotel, Habitacion, Huesped, Usuario, ControladorSistema
- Patrón State y sus estados concretos
- Enums: RolUsuario, CategoriaHuesped
- Diagramas de secuencia: Crear Reserva y Realizar Check-In
- Identificación de principios SOLID y GRASP
- Implementación de ConexionDB y persistencia en MySQL
- Desarrollo de la interfaz gráfica 
- Funcionalidades de check-out, registro de pago y patrón Observer en el controlador
- Implementación de PaqueteServicios (Composite)
- Corrección de errores de compilación y relaciones entre clases

**Agustina Gryner:**
- Patrones Strategy y Composite
- Clases: Pago, IMedioPago, PagoTarjeta, PagoEfectivo, PagoTransferencia
- Clases: IServicio, ServicioConsumo, ServicioActividad, PaqueteServicios
- Clases: Estadia, ServicioEstadia, Promocion
- Diagramas de secuencia: Realizar Check-Out y Realizar Mantenimiento
- Justificación de patrones de diseño
- Reorganización de paquetes e implementación de DAOs
- Funcionalidades de check-in, reserva y registro de huésped
- Implementación de estados de habitaciones
- Realización de MainConsola para testeo