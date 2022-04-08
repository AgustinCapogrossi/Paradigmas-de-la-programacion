
--Ejercicio 1
--a
generalMap:: (Int -> Int) -> [Int] -> [Int]
generalMap f [] = []
generalMap f (x:xs) = f x : generalMap f xs

polGeneralMap :: (a->a) -> [a] -> [a]
polGeneralMap f [] = []
polGeneralMap f (x:xs) = f x : polGeneralMap f xs

gduplica = generalMap (*2)
gpduplica = polGeneralMap (*2)

gmas1 = generalMap (+1)
gpmas1 = polGeneralMap (+1)
--b

morePolGeneralMap :: (a -> b) -> [a] -> [b]
morePolGeneralMap f [] = []
morePolGeneralMap f (x:xs) = f x : morePolGeneralMap f xs

esPar = morePolGeneralMap even

--Ejercicio 2
--a
generalFilter:: (Int -> Bool) -> [Int] -> [Int]
generalFilter f [] = []
generalFilter f (x:xs) | f x == True = x : generalFilter f xs
                       | f x == False = generalFilter f xs

--b
polGeneralFilter :: (a->Bool) -> [a] -> [a]
polGeneralFilter f [] = []
polGeneralFilter f (x:xs) | f x == True = x : polGeneralFilter f xs 
                          | f x == False = polGeneralFilter f xs 
--c
gsoloPares = generalFilter even

pgsoloPares = polGeneralFilter even
--Ejercicio 3

--a
generalFold:: (Int->Int->Int) -> Int -> [Int] -> Int
generalFold f base [] = base
generalFold f base (x:xs) = f x (generalFold f base xs)

--b 

polGeneralFold:: (a->b->b) -> b -> [a] -> b
polGeneralFold f base [] = base
polGeneralFold f base (x:xs) = f x (polGeneralFold f base xs)

--c
gsumatoria = generalFold (+) 0  
gpsumatoria = polGeneralFold (+) 0

gpproductoria = polGeneralFold (*) 1


--Ejercicio 4

data Figura = Circulo Float       --Cada uno de estos es un _constructor_
            | Cuadrado Float        --define el constructor de un "Cuadrado"
            | Rectangulo Float Float --define el constructor de un "Rectangulo"
            | Punto                --define el constructor de un "Punto"
              deriving (Eq, Show)
              
perimetro :: Figura -> Float
perimetro (Circulo radio) = 2 * pi * radio
perimetro (Cuadrado lado) = 4 * lado
perimetro (Rectangulo ancho alto) = 2 * ancho + 2 * alto
perimetro (Punto) = error "no se puede calcular el perimetro del punto"
--a
superficie :: Figura -> Float
superficie (Circulo radio) = pi * radio * radio
superficie (Cuadrado lado) = lado * lado
superficie (Rectangulo ancho alto) = ancho * alto
superficie (Punto) = error "no se puede calcular la superficie del punto"


--Ejercicio 5

data Expr a = Valor a
          | Expr a :+: Expr a
          | Expr a :-: Expr a
          | Expr a :*: Expr a
           deriving (Eq, Show)
           
semantica :: Int a => Expr a -> Int
semantica (Valor x) = x
semantica (x :+: y) = semantica x + semantica y
semantica (x :-: y) = semantica x - semantica y
semantica (x :*: y) = semantica x * semantica y

data BinTree a = Hoja a
               | Nodo a (BinTree a) (BinTree a) 
               deriving (Show,Eq)

recorrido :: BinTree a -> [a]
recorrido (Hoja x) = [x]
recorrido (Nodo x izquierda derecha) = x : (recorrido izquierda ++ recorrido derecha)

profundidad :: BinTree a -> Int
profundidad (Hoja _) = 0
profundidad (Nodo x izquierda derecha) = 1+ max (profundidad izquierda) (profundidad derecha)
