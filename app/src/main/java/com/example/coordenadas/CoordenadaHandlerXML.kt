package com.example.coordenadas

import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler

class CoordenadaHandlerXML : DefaultHandler() {
    private val cadena = StringBuilder()
    private var coordenada: Coordenada? = null
    var coordenadas: MutableList<Coordenada> = mutableListOf()

    @Throws(SAXException::class)
    override fun startDocument() {
        cadena.clear()
        coordenadas = mutableListOf()
        println("startDocument")
    }

    @Throws(SAXException::class)
    override fun startElement(uri: String, nombreLocal: String, nombre: String, attributes: Attributes) {
        cadena.setLength(0)
        if (nombre == "coordenada") {
            coordenada = Coordenada()
            coordenada?.pais = attributes.getValue("pais")
        }

        println("startElement: $nombreLocal $nombre")
    }

    @Throws(SAXException::class)
    override fun characters(ch: CharArray, start: Int, length: Int) {
        cadena.append(ch, start, length)
        println("dato final: $cadena")
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String, nombreLocal: String, nombre: String) {
        when (nombre) {
            "lugar" -> coordenada?.lugar = cadena.toString()
            "latitud" -> coordenada?.latitud = cadena.toString().toInt()
            "longitud" -> coordenada?.longitud = cadena.toString().toInt()
            "coordenada" -> coordenada?.let { coordenadas.add(it) }
        }

        println("endElement: $nombreLocal $nombre")
    }

    @Throws(SAXException::class)
    override fun endDocument() {
        println("endDocument")
    }
}