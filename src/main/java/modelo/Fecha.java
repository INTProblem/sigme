package modelo;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;

public class Fecha implements Comparable {

    private int dia;
    private int mes;
    private int año;

    // Arreglo con los días de cada mes para validación
    private static final int[] DIAS_POR_MES = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public Fecha() {
        this.dia = 1;
        this.mes = 1;
        this.año = 1970;
    }

    public Fecha(int dia, int mes, int año) {
        if (esFechaValida(dia, mes, año)) {
            this.dia = dia;
            this.mes = mes;
            this.año = año;
        } else {
            throw new IllegalArgumentException("Fecha inválida.");
        }
    }

    /**
     * Verifica que una fecha sea valida.
     *
     * @param dia
     * @param mes
     * @param año
     * @return Retorna verdadero si la fecha es valida.
     */
    public static boolean esFechaValida(int dia, int mes, int año) {
        if (mes < 1 || mes > 12) {
            return false;
        }
        int diasMaximos = DIAS_POR_MES[mes - 1];

        // Ajustar febrero para años bisiestos
        if (mes == 2 && esBisiesto(año)) {
            diasMaximos = 29;
        }

        return dia >= 1 && dia <= diasMaximos;
    }

    /**
     * Este metodo verifica si el año es bisiesto.
     *
     * @param año
     * @return Verdadero si es bisiesto
     */
    public static boolean esBisiesto(int año) {
        return (año % 4 == 0 && año % 100 != 0) || (año % 400 == 0);
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        if (esFechaValida(dia, this.mes, this.año)) {
            this.dia = dia;
        } else {
            throw new IllegalArgumentException("Día inválido.");
        }
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        if (esFechaValida(this.dia, mes, this.año)) {
            this.mes = mes;
        } else {
            throw new IllegalArgumentException("Mes inválido.");
        }
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        if (esFechaValida(this.dia, this.mes, año)) {
            this.año = año;
        } else {
            throw new IllegalArgumentException("Año inválido.");
        }
    }

    /**
     * Método para sumar días a la fecha.
     *
     * @param dias
     */
    public void sumarDias(int dias) {
        while (dias > 0) {
            int diasRestantesMes = obtenerDiasRestantesDelMes();
            if (dias > diasRestantesMes) {
                dias -= diasRestantesMes + 1;
                avanzarAlProximoMes();
            } else {
                this.dia += dias;
                dias = 0;
            }
        }
    }

    /**
     * Obtiene los días restantes en el mes actual
     *
     * @return los dias restantes.
     */
    private int obtenerDiasRestantesDelMes() {
        int diasMaximos = DIAS_POR_MES[this.mes - 1];
        if (this.mes == 2 && esBisiesto(this.año)) {
            diasMaximos = 29;
        }
        return diasMaximos - this.dia;
    }

    /**
     * Avanzar al próximo mes (maneja cambio de año)
     */
    private void avanzarAlProximoMes() {
        if (this.mes == 12) {
            this.mes = 1;
            this.año++;
        } else {
            this.mes++;
        }
        this.dia = 1;
    }

    /**
     * Muestra en formato yyyy-mm-dd
     */
    @Override
    public String toString() {
        LocalDate localDate = LocalDate.of(año, mes, dia);
        return localDate.toString();
    }

    @Override
    public int compareTo(Object o) {
        Fecha otraFecha = (Fecha) o;
        if (this.año != otraFecha.año) {
            return this.año - otraFecha.año;
        } else if (this.mes != otraFecha.mes) {
            return this.mes - otraFecha.mes;
        } else {
            return this.dia - otraFecha.dia;
        }
    }

    /**
     * Valida que la fecha sea correcta luego de cargar por separado el dia, el
     * mes y el año.
     *
     * @return verdadero si se valida la fecha.
     */
    public Fecha cargarDatos() {

        int diaEntrante, mesEntrante, añoEntrante;

        do {
            añoEntrante = cargarAño();
            mesEntrante = cargarMes();
            diaEntrante = cargarDia();
            if (!esFechaValida(diaEntrante, mesEntrante, añoEntrante)) {
                System.out.println("Fecha invalida! Ingrese nuevamente");
                GestorEntradaConsola.pausar();
            }
        } while (!esFechaValida(diaEntrante, mesEntrante, añoEntrante));

        this.dia = diaEntrante;
        this.mes = mesEntrante;
        this.año = añoEntrante;

        return this;
    }

    /**
     * Valida que la fecha sea mayor a 0 y menor a 31.
     *
     * @return verdadero si se valida la fecha.
     */
    private int cargarDia() {
        int diaEntrante;
        boolean esValido;
        do {
            System.out.print("Dia: ");
            diaEntrante = GestorEntradaConsola.leerEntero();
            esValido = diaEntrante > 0 && diaEntrante <= 31;
        } while (!esValido);
        return diaEntrante;
    }

    /**
     * Valida que el mes sea mayor a 0 y menor que 13
     *
     * @return verdadero si se valida el mes.
     */
    private int cargarMes() {
        int mesEntrante;
        boolean esValido;
        do {
            System.out.print("Mes: ");
            mesEntrante = GestorEntradaConsola.leerEntero();
            esValido = mesEntrante >= 1 && mesEntrante <= 12;
        } while (!esValido);
        return mesEntrante;
    }

    /**
     * Valida que el año sea mayor a 1970
     *
     * @return verdadero si se valida el año
     */
    private int cargarAño() {
        int añoEntrante;
        boolean esValido;
        do {
            System.out.print("Año: ");
            añoEntrante = GestorEntradaConsola.leerEntero();
            esValido = añoEntrante >= 1970;
        } while (!esValido);
        return añoEntrante;
    }

    /**
     * Convierte la FechaDTO a java.sql.Date
     *
     * @return Una instancia de java.sql.Date que representa esta fecha.
     */
    public Date toSqlDate() {
        LocalDate localDate = LocalDate.of(this.año, this.mes, this.dia);
        return Date.valueOf(localDate);
    }

    /**
     * Convierte una instancia de java.sql.Date en una instancia de FechaDTO.
     *
     * @param sqlDate Una fecha de tipo java.sql.Date.
     * @return Una instancia de FechaDTO que representa la misma fecha.
     */
    public static Fecha fromSqlDate(Date sqlDate) {
        LocalDate localDate = sqlDate.toLocalDate();
        return new Fecha(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
    }

    public static Fecha fromUtilDate(java.util.Date utilDate) {
        // Si utilDate es una instancia de java.sql.Date (que no soporta toInstant)
        if (utilDate instanceof java.sql.Date) {
            // Convertimos usando toLocalDate() de java.sql.Date
            java.sql.Date sqlDate = (java.sql.Date) utilDate;
            LocalDate localDate = sqlDate.toLocalDate();
            return new Fecha(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
        }

        // Si es java.util.Date normal
        LocalDate localDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new Fecha(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
    }

    public java.util.Date toUtilDate() {
        // Crear un LocalDate con los valores de la instancia de Fecha
        LocalDate localDate = LocalDate.of(this.año, this.mes, this.dia);

        // Convertir LocalDate a java.util.Date
        return java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public LocalDate toLocalDate() {
        return LocalDate.of(año, mes, dia);
    }

    public static Fecha fromLocalDate(LocalDate localDate) {
        return new Fecha(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
    }

    /**
     * Calcula el dia de la semana de una fecha
     *
     * @return el dia de la semana de una fecha.
     */
    public DayOfWeek getDiaSemana() {
        LocalDate localDate = LocalDate.of(año, mes, dia);
        return localDate.getDayOfWeek();
    }

}

