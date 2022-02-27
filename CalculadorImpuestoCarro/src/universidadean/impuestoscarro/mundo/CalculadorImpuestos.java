/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad Ean (Bogotá - Colombia)
 * Programa de Ingeniería de Sistemas
 * Licenciado bajo el esquema Academic Free License version 2.1
 * <p>
 * Bloque de Estudios: Desarrollo de Software
 * Ejercicio: Cálculo de Impuestos de Carros
 * Adaptado de: Proyecto CUPI2 - UNIANDES
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package universidadean.impuestoscarro.mundo;

import java.io.*;
import java.util.*;
import javax.swing.*;
/**
 * Calculador de impuestos.
 */
public class CalculadorImpuestos {
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Porcentaje de descuento por pronto pago.
     */
    public static final double PORC_DESC_PRONTO_PAGO = 10.0;

    /**
     * Valor de descuento por servicio público.
     */
    public static final double VALOR_DESC_SERVICIO_PUBLICO = 50000.0;

    /**
     * Porcentaje de descuento por traslado de cuenta.
     */
    public static final double PORC_DESC_TRASLADO_CUENTA = 5.0;

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Vehículos que maneja el calculador.
     */
    private Vehiculo[] vehiculos;

    /**
     * Vehículo actual mostrado en la aplicación.
     */
    private int posVehiculoActual;

    /**
     *
     * * Rangos de los impuestos.
     */

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Crea un calculador de impuestos, cargando la información de dos archivos. <br>
     * <b>post: </b> Se inicializaron los arreglos de vehículos y rangos.<br>
     * Se cargaron los datos correctamente a partir de los archivos.
     *
     * @throws Exception Error al cargar los archivos.
     */
    public CalculadorImpuestos() throws Exception {
        cargarVehiculos("data/vehiculos.txt");

    }

    // ----------------------------------------------------------------
    // Métodos
    // ----------------------------------------------------------------

    /**
     * Carga los datos de los vehículos que maneja el calculador de impuestos. <br>
     * <b>post: </b> Se cargan todos los vehículos del archivo con sus datos.
     *
     * @param pArchivo Nombre del archivo donde se encuentran los datos de los vehículos. pArchivo != null.
     * @throws Exception Si ocurre algún error cargando los datos.
     */
    private void cargarVehiculos(String pArchivo) throws Exception {
        String texto, valores[], sMarca, sLinea, sModelo, sImagen;
        double precio;
        int cantidad = 0;
        Vehiculo vehiculo;
        try {
            File datos = new File(pArchivo);
            FileReader fr = new FileReader(datos);
            BufferedReader lector = new BufferedReader(fr);
            texto = lector.readLine();

            cantidad = Integer.parseInt(texto);
            vehiculos = new Vehiculo[cantidad];
            posVehiculoActual = 0;

            texto = lector.readLine();
            for (int i = 0; i < vehiculos.length; i++) {
                valores = texto.split(",");

                sMarca = valores[0];
                sLinea = valores[1];
                sModelo = valores[2];
                sImagen = valores[4];
                precio = Double.parseDouble(valores[3]);

                vehiculo = new Vehiculo(sMarca, sLinea, sModelo, precio, sImagen);
                vehiculos[i] = vehiculo;
                // Siguiente línea
                texto = lector.readLine();
            }
            lector.close();
        }
        catch (IOException e) {
            throw new Exception("Error al cargar los datos almacenados de vehículos.");
        }
        catch (NumberFormatException e) {
            throw new Exception("El archivo no tiene el formato esperado.");
        }
    }


    /**
     * Calcula el pago de impuesto que debe hacer el vehículo actual. <br>
     * <b>pre:</b> Las listas de rangos y vehículos están inicializadas.
     *
     * @param descProntoPago      Indica si aplica el descuento por pronto pago.
     * @param descServicioPublico Indica si aplica el descuento por servicio público.
     * @param descTrasladoCuenta  Indica si aplica el descuento por traslado de cuenta.
     * @return Valor a pagar de acuerdo con las características del vehículo y los descuentos que se pueden aplicar.
     */



/**Los vehículos fueron divididos en cuatro rangos de valores para los porcentajes de
 impuestos, de la siguiente manera:

 Entre 0 y 30 millones, pagan el 1,5% (0.015) de impuesto.
 Más de 30 millones y hasta 70 millones, pagan el 2,0%  (0.02) de impuesto.
 Más de 70 millones y hasta 200 millones, pagan el 2,5% (0.025) de impuesto.
 Más de 200 millones, pagan el 4% (0.04) de impuesto. */
    public double calcularPago(boolean descProntoPago, boolean descServicioPublico, boolean descTrasladoCuenta) {
        double pago = 0;
        double precio = darVehiculoActual().darPrecio();
        if (precio <= 30000000){
            pago=precio*1.5/100;
        }
           if (precio <=70000000){
                pago= precio*2/100;
            }

                if(precio <= 200000000) {
                    pago = precio*2.5/100 ;

                {
                    pago = precio * 4/100 ;
                }
            }
 /**hay tres tipos de descuentos que se pueden aplicar:**/
        if (descProntoPago == true){
            pago=pago-(pago*PORC_DESC_PRONTO_PAGO/100);
        }
        if (descServicioPublico == true){
            pago=pago-VALOR_DESC_SERVICIO_PUBLICO;
        }
        if (descTrasladoCuenta == true){
            pago=pago-(pago*PORC_DESC_TRASLADO_CUENTA/100);
        }

        return pago;
    }



    /**
     * Retorna el primer vehículo. <br>
     * <b>post: </b> Se actualizó la posición del vehículo actual.
     *
     * @return El primer vehículo, que ahora es el vehículo actual.
     * @throws Exception Si ya se encuentra en el primer vehículo.
     */
    public Vehiculo darPrimero() throws Exception {
        if (posVehiculoActual == 0) {
            throw new Exception("Ya se encuentra en el primer vehículo.");
        }
        posVehiculoActual = 0;
        return darVehiculoActual();
    }

    /**
     * Retorna el vehículo anterior al actual. <br>
     * <b>post: </b> Se actualizó la posición del vehículo actual.
     *
     * @return El anterior vehículo, que ahora es el vehículo actual.
     * @throws Exception Si ya se encuentra en el primer vehículo.
     */
    public Vehiculo darAnterior() throws Exception {
        if (posVehiculoActual == 0) {
            throw new Exception("Se encuentra en el primer vehículo.");
        }
        posVehiculoActual--;
        return darVehiculoActual();
    }

    /**
     * Retorna el vehículo siguiente al actual. <br>
     * <b>post: </b> Se actualizó la posición del vehículo actual.
     *
     * @return El siguiente vehículo, que ahora es el vehículo actual.
     * @throws Exception Si ya se encuentra en el último vehículo
     */
    public Vehiculo darSiguiente() throws Exception {
        if (posVehiculoActual == vehiculos.length - 1) {
            throw new Exception("Se encuentra en el último vehículo.");
        }
        posVehiculoActual++;
        return darVehiculoActual();
    }

    /**
     * Retorna el último vehículo. <br>
     * <b>post: </b> Se actualizó la posición del vehículo actual.
     *
     * @return El último vehículo, que ahora es el vehículo actual.
     * @throws Exception Si ya se encuentra en el último vehículo
     */
    public Vehiculo darUltimo() throws Exception {
        if (posVehiculoActual == vehiculos.length - 1) {
            throw new Exception("Ya se encuentra en el último vehículo.");
        }
        posVehiculoActual = vehiculos.length - 1;
        return darVehiculoActual();
    }

    /**
     * Retorna el vehículo actual.
     *
     * @return El vehículo actual.
     */
    public Vehiculo darVehiculoActual() {
        return vehiculos[posVehiculoActual];
    }

    /**
     * Busca el vehículo más caro, lo asigna como actual y lo retorna.
     *
     * @return El vehículo más caro.
     */
    public Vehiculo buscarVehiculoMasCaro() {
        Vehiculo masCaro = null;
        double Precio = 0;
        // TODO: Buscar el vehículo más caro del arreglo de vehículos

        for ( int i = 0; i < vehiculos.length; i++){
            Vehiculo vehiculo = vehiculos[i];
            if (vehiculo.darPrecio()>Precio){
                Precio=vehiculo.darPrecio(); /** darprecio vairables que se encuentra en el metodo calcular pago*/
                masCaro=vehiculo;
                posVehiculoActual = i;
            }

        }

        return masCaro;
    }



    /**
     * Busca y retorna el primer vehículo que encuentra con la marca que se lee desde teclado. <br>
     *
     * @return El primer vehículo de la marca. Si no encuentra ninguno retorna null.
     */
    public Vehiculo buscarVehiculoPorMarca() {
        Vehiculo buscado = null;

        // TODO: Usando JOptionPane, leer la marca del vehículo a buscar

        // TODO: Retornar el primer vehículo que tiene la marca dada


        String marca =  JOptionPane.showInputDialog("Ingrese la marca del vehículo a buscar");

        for (int i = vehiculos.length - 1; i>0; i--) {
            Vehiculo vehiculo = vehiculos[i];
            if (vehiculos[i].darMarca().equals(marca)){
                buscado = vehiculos[i];

            }

        }
        return buscado;
    }




    /**
     * Busca y retorna el vehículo de la línea buscada. <br>
     *
     * @return El vehículo de la línea, null si no encuentra ninguno.
     */
    public Vehiculo buscarVehiculoPorLinea() {
        Vehiculo buscado = null;
        String linea = JOptionPane.showInputDialog("Ingrese la línea del vehículo a buscar");
        // TODO: Usando JOptionPane, leer la línea del vehículo a buscar

        // TODO: Buscar el primer vehículo que tiene la línea dada
        for (int i = vehiculos.length - 1; i>0; i--) {
            if (vehiculos[i].darLinea().equals(linea)){
                buscado = vehiculos[i];
            }
        }

        return buscado;
    }

    /**
     * Busca el vehículo más antiguo, lo asigna como actual y lo retorna.
     *
     * @return El vehículo más antiguo.
     */
    public Vehiculo buscarVehiculoMasAntiguo() {
        Vehiculo buscado = vehiculos[0];

        // TODO: Buscar el vehículo más antiguo del sistema
        for (Vehiculo vehi: vehiculos) {
            if (Integer.parseInt(vehi.darAnio()) < Integer.parseInt(buscado.darAnio())) {
                buscado = vehi;
            }
        }

        return buscado;
    }


    /**
     * Calcula el promedio de los precios de todos los automóviles que están en el sistema
     *
     * @return Promedio de precios
     */
    public double promedioPreciosVehiculos() {
        // TODO: Calcular el promedio del precio de todos los automóviles
        double sumaPrecios = 0.0;
        double promedio = 0.0;

        for (int i = 0; i < vehiculos.length; i++){
            Vehiculo vehiculo = vehiculos[i];
            sumaPrecios+=vehiculo.darPrecio();
        }
        promedio = sumaPrecios/vehiculos.length;

        return promedio;
    }



}