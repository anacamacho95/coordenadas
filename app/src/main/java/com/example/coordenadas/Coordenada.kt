package com.example.coordenadas

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "coordenadas")
data class Coordenadas(
    @field:ElementList(inline = true, entry = "coordenada")
    var coordenada: List<Coordenada> = mutableListOf()
)

@Root(name = "coordenada")
data class Coordenada(
    @field:Element(name = "lugar")
    var lugar: String = "",

    @field:Element(name = "latitud")
    var latitud: Int = 0,

    @field:Element(name = "longitud")
    var longitud: Int = 0,


    @field:Attribute(name = "País", required = false)
    var pais: String? = null,

)
