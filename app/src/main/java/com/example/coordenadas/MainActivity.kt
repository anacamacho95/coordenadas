package com.example.coordenadas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.simpleframework.xml.core.Persister
import java.io.*
import javax.xml.parsers.SAXParserFactory

class MainActivity : AppCompatActivity() {
    var coordenadas = mutableListOf<Coordenada>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        copiarArchivoDesdeAssets()
        procesarArchivoAssetsXML()
        Log.d("prueba", "probando procesado con Simple XML Framework")
        coordenadas.forEach {
            Log.d("prueba1", it.lugar)
        }

        val coordenada1=Coordenada("Almería",48, 50 )
        addCoordenada(coordenada1)
        val coordenada2=Coordenada("Jaén",44, 35 )
        addCoordenada(coordenada2)
        ProcesarArchivoXMLInterno()
        coordenadas.forEach {
            Log.d("prueba2", "Lugar: ${it.lugar} Latitud: ${it.latitud} Longitud: ${it.longitud}")
        }

        procesarArchivoXMLSAX()

    }

    private fun procesarArchivoXMLSAX() {
        try {
            val factory = SAXParserFactory.newInstance()
            val parser = factory.newSAXParser()
            val handler = CoordenadaHandlerXML()

            val inputStream = assets.open("coordenadas.xml")
            parser.parse(inputStream, handler)

            // Accede a la lista de coordenadas desde handler.coordenadas
            handler.coordenadas.forEach {
                Log.d("SAX", "Coordenada: ${it.lugar}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun procesarArchivoAssetsXML() {
        val serializer = Persister()
        var inputStream: InputStream? = null
        var reader: InputStreamReader? = null

        try {
            inputStream = assets.open("coordenadas.xml")
            reader = InputStreamReader(inputStream)
            val coordenadasListType = serializer.read(Coordenadas::class.java, reader, false)
            coordenadas.addAll(coordenadasListType.coordenada)
        } catch (e: Exception) {
            // Manejo de errores
            e.printStackTrace()
        } finally {
            // Cerrar inputStream y reader
            try {
                reader?.close()
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }

    fun addCoordenada(coordenada: Coordenada) {
        try {
            val serializer = Persister()
            coordenadas.add(coordenada)
            val coordenadasList = Coordenadas(coordenadas)
            val outputStream = openFileOutput("coordenadas.xml", MODE_PRIVATE)
            serializer.write(coordenadasList, outputStream)
            outputStream.close() // Asegúrate de cerrar el outputStream después de escribir
        } catch (e: Exception) {
            e.printStackTrace() // Manejo de errores adecuado
        }
    }
    private fun copiarArchivoDesdeAssets() {
        val nombreArchivo = "coordenadas.xml"
        val archivoEnAssets = assets.open(nombreArchivo)
        val archivoInterno = openFileOutput(nombreArchivo, MODE_PRIVATE)

        archivoEnAssets.copyTo(archivoInterno)
        archivoEnAssets.close()
        archivoInterno.close()
    }

    fun ProcesarArchivoXMLInterno() {
        val nombreArchivo = "coordenadas.xml"
        val serializer = Persister()

        try {
            // Abrir el archivo para lectura
            val file = File(filesDir, nombreArchivo)
            val inputStream = FileInputStream(file)
            val coordenadasList = serializer.read(Coordenadas::class.java, inputStream)
            coordenadas.addAll(coordenadasList.coordenada)
            inputStream.close()
        } catch (e: Exception) {

            e.printStackTrace()
        }
    }
}