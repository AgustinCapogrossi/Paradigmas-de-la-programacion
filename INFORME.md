# Título: LABORATORIO 3: Actores en Scala

## Grupo 27

Integrantes:
* Capogrossi Agustín (agustin.capogrossi@mi.unc.edu.ar)
* Coria Maria Daiana (mariadaiana.coria@mi.unc.edu.ar)
* Villalba Palmieri Santiago (santiago.villalba@mi.unc.edu.ar)


### Preguntas:

## Si quisieran extender el sistema para soportar el conteo de entidades nombradas del laboratorio 2, ¿qué parte de la arquitectura deberían modificar? Justificar.

Para extender el sistema para soportar el conteo de entidades nombradas se deberia modificar de la estructura:
Crearíamos un nuevo actor que sea un hijo de ParseRSS el cual recibiría los datos del Feed ya parseados, dentro del cual crearíamos una función que los cuente y los ordene, una vez contados y ordenados le enviaríamos al Supervisor.


## Si quisieran exportar los datos (ítems) de las suscripciones a archivos de texto (en lugar de imprimirlas por pantalla):
##¿Qué tipo de patrón de interacción creen que les serviría y por qué? (hint: es mejor acumular todo los items antes de guardar nada).

El tipo de patrón que serviría el "Request-Response with ask between two actors" ya que por su propia definición es útil de utilizar cuando un actor necesita saber que el mensaje fue procesado antes de continuar de ésta manera nos aseguramos que todos los FEEDS están acumulados correctamente antes de exportar los datos.


## ¿Dónde deberían utilizar dicho patrón si quisieran acumular todos los datos totales? ¿Y si lo quisieran hacer por sitio?

Debido a que el Supervisor tiene un alcance global sobre los actores acumularíamos las respuestas totales en el Supervisor. Y si quisieramos hacerlo por sitio no podríamos hacerlo en ParseRSS puesto que éste sólo se encarga de parsear los datos por lo que sólo nos queda la opción de hacerlo en el Feed.


## ¿Qué problema trae implementar este sistema de manera síncrona?

Los problemas que presenta implementarlo de manera síncrona son:
- Reducción en la velocidad del sistema se debe a que supone un mayor uso de recursos.
- Complejidad en la implementación.
- Es necesario asegurarse que los mensajes son recibidos.
- Supone un mayor conocimiento, más complejo de implementar.

## ¿Qué les asegura el sistema de pasaje de mensajes y cómo se diferencia con un semáforo/mutex?

Respuesta en el oral:

Se diferencia del semáforo/mutex porque usa mensajes para la concurrencia.

El modelo de actor opera con el pasaje de mensajes. Los procesos individuales(actores) pueden enviarse mensajes de forma asíncrona entre sí. No existen los estados compartidos ya que cada actor trabaja de manera independiente, por lo que el fallo de uno de ellos en el sistema, no afecta al resto. O sea que el sistema puede seguir funcionando.

