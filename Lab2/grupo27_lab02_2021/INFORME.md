# Título: LABORATORIO 2: Frontend/Backend en Scala

## Grupo 27

Integrantes:
* Capogrossi Agustín
* Coria María Daiana
* Villalba Santiago

## Respuestas

### Pregunta

Respuesta, en no más de tres párrafos. (¬¬)

### ¿Como es la interfaz de la clase u objeto que implementaron para el primer componente?
Son en realidad dos clases y la segunda hereda de la primera.
La superclase posee 2 métodos y la subclase sobreescribe el segundo método.
Es de fácil acceso desde el exterior de la clase, ya que al crear una instancia de la misma se puede interactuar con ambos métodos. Ésto debido a que no hay ningún elemento privado.


### ¿Utilizaron una clase o un objeto? ¿Por qué?
Usamos una clase para definir los métodos para pedir una url y parsearla porque la clase define los métodos comunes a los objetos de cierto tipo.


### ¿Por qué utilizar literales como Strings para diferenciar comportamientos similares crea acoplamiento?
Porque al usar literales como Strings necesitamos trabajar con "CASE" y agregar más funciones al mismo método se vuelve más costodo y en el proceso de hacer esto se pueden producir errores que perjudican al funcionamiento del código.


### ¿Qué concepto(s) de la Programación Orientada a Objetos utilizaron para resolver este problema?
Los principales conceptos de la Programación Orientada a Objetos que se utilizaron fueron: la herencia, al definir una función que es subtipo de otra y el polimorfismo de clase.


### ¿Qué clase almacena las URLs?
La clase que almacena las URLs es: FeedService con el método Subscribe.


### ¿Cómo funciona el polimorfismo de clases en su implementación? ¿Qué desventaja tiene pasar al método subscribe un parámetro de tipo string que pueda tomar los valores “rss” y “reddit”, y dejar que decida qué tipo de parser usar?

El polimorfismo de clase funciona tras recibir una url y parsearlo de manera distinta de si es rss o json.
La desventaja es que no solo que genera acoplamiento sino que también de que el usuario sepa de que tipo es la url que se pasa.

