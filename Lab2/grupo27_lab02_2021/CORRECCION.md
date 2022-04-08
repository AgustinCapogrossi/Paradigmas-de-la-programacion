# Grupo 27		
## Corrección		
	Tag o commit corregido:	1.0
		
### Entrega		100,00%
	Commits de cada integrante	100,00%
	En tiempo y con tag correcto	100,00%
	Commits frecuentes y con nombres significativos	100,00%
### Informe		78,50%
	Parte 1: ¿Como es la interfaz de la clase u objeto que implementaron para el primer componente?	80,00%
	Parte 1: ¿Utilizaron una clase o un objeto? ¿Por qué?	100,00%
	Parte 2: ¿Por qué es una mala opción usar literales?	50,00%
	Parte 2: ¿Qué concepto(s) de la Programación Orientada a Objetos utilizaron?	100,00%
	Parte 3: ¿Cómo funciona el polimorfismo de clases en su implementación?	50,00%
	Parte 3: ¿Qué clase almacena las URLs?	100,00%
### Funcionalidad		76,00%
	Manejan el caso de error en la consulta HTTP devolviendo una lista vacía	0,00%
	Parsean correctamente los artículos de Reddit y de RSS	100,00%
	Reemplazan las url en los artículos de Reddit	100,00%
	Realizan consultas a múltiples URLs subscriptas	100,00%
	Aceptan correctamente los distintos parámetros de las URLs	80,00%
### Modularización y diseño		100,00%
	Evitan repetir código que realiza el request HTTP y maneja los errores aprovechando la herencia.	100,00%
	Utilizan clases distintas para parsear los feed RSS y Reddit	100,00%
	Utilizan una clase distinta de los parsers para administrar las subscripciones	100,00%
	La clase que administra las subscripciones no está acoplada a los distintos tipos de url. Es decir, si se agrega un nuevo tipo de url, no es necesario cambiar esta clase.	100,00%
### Calidad de código		75,00%
	Utilizan el paradigma funcional cuando es posible.	100,00%
	Estructuras de código simples	100,00%
	Reutilizan funciones de librería, por ejemplo para serializar y deserializar Json	100,00%
	Estilo: no tienen errores ni warnings al hacer `sbt scalastyle`, indentación consistente, líneas de menos de 80 caracteres. Nombre de variable en camelCase	0,00%
### Opcionales		
	Punto estrella 1: Elección de campos	0,00%
	Punto estrella 2: Normalización de conteos	0,00%
	Punto estrella 3: StackExchange feed	0,00%
		
# Nota Final		7,91875
		
		
# Comentarios		
	Las respuestas del informe que estaban un poco incompletas las profundizaron durante el oral.	
	En la pregunta 1, tenían que responder qué hacen los métodos y atributos de cada clase, no sólo que existen.	
	En la pregunta 3, no explican la relación entre el acoplamiento y lo "costoso" de agregar nuevos casos.	
	En la pregunta 4 responden "herencia, al definir una función que es subtipo de otra". 	
	En la pregunta sobre cómo funciona el polimorfismo de clase, no explican cómo funciona, sino para qué lo usan.	
	Faltó implementar la parte 4	
	Replace URL debería estar después de parsear el json.	
	Falta que los parámetros de la URL sean opcionales para el usuario	
	Líneas de más de 80 caracteres, indentación inconsistente. Scala se indenta con 2 espacios, no 4. 	
