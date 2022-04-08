def queryURL(url: String, urlType: String): Seq[String] = {
    val response: HttpResponse[String] = Http(url).timeout(connTimeoutMs = 2000, readTimeoutMs = 5000).asString.body
    urlType match {
        case "rss" => {
            // parse RSS in XML
             val xml = XML.loadString(response)
        // Extract text from title and description
        (xml \\ "item").map { item =>
    ((item \ "title").text +" " + (item \ "description").text)}

        }
        case "reddit" => {
            // parse Reddit feed in JSON
            val pattern ="(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]".r //Patron
             //parse Reddit feed in JSON
            val jsonString = pattern.replaceAllIn(response,"").trim() //Cuerpo de la respuesta sin urls
            val result = (parse(jsonString) \ "data" \ "children" \ "data")
    .extract[List[Map[String, Any]]] //Parses the jsonString as a List of Maps //Cuerpo de la funcion sin urls parseado en Json dentro de una [List[Map[String,Any]]]
            val filteredmap = result.map((_.filter((t) => t._1 == "title" || t._1 == "selftext").mapValues(_.asInstanceOf[String]).values)) //Filters the map and returns only "titles" and "selftexts"
            filteredmap.flatten.toSeq  //List[Seq[String,Any]] -(1)-> List[Seq[String,Any]] -(2)-> List[Seq[String,String]] -(3)-> List[Seq[String]] -(4)-> Seq[String]
 
        }
    }
}


class ParseRSS {
    def request(url: String): String={
        val response: HttpResponse[String] = Http(url).timeout(connTimeoutMs = 2000, readTimeoutMs = 5000).asString
        response.body
    }
    def parseURL(responsebody : String): Seq[String]={
        val xml = XML.loadString(responsebody)
        // Extract text from title and description
        (xml \\ "item").map { item =>
    ((item \ "title").text +" " + (item \ "description").text)}
        
    }
    
}


//Para crear un modelo hacemos val rssmodel = new ParseRSS, luego podemos usar el modelo para acceder a las funciones



class FeedService[T <: ParseRSS]{
    var urls: Seq[(String,T)] = Seq[(String,T)]()
    def subscribe(urlTemplate: String,params: Seq[String],parser: T){ //ParseJSON
        val pat = "%s".r
        val urlparam = params.map {s => pat replaceAllIn(urlTemplate,s)}.map{url => (url,parser)}
        urls = urls ++ urlparam
    }
    def getText(): Seq[String] = {
        val texts = urls.map { url =>
            val parser = url._2 //parser = ParseJSON
            parser.parseURL(url._1)
            
        }
        texts.map(_.mkString(","))
    }
}
