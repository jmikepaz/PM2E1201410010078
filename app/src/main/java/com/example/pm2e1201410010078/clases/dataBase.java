package com.example.pm2e1201410010078.clases;

public class dataBase {
    /* Tablas */
    public static final String TABLA_CONTACTOS = "contactos";
    public static final String TABLA_PAISES = "pais";

    /* Campos */
    public static final String id = "id";
    public static final String nombre = "nombre";
    public static final String telefono = "telefono";
    public static final String nota = "nota";
    public static final String codigo_pais = "codigo_pais";

    public static final String id_pais = "id_pais";
    public static final String nombre_pais = "nombre_pais";
    public static final String codigo_area = "codigo_area";



    /* Tablas - CREATE, DROP */
    public static final String CREATE_TABLE_CONTACTOS = "CREATE TABLE " +TABLA_CONTACTOS +"( id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, " +
            "                                                                telefono TEXT, nota TEXT, codigo_pais TEXT)";

    public static final String CREATE_TABLE_PAIS = "CREATE TABLE " +TABLA_PAISES +"( id_pais INTEGER PRIMARY KEY AUTOINCREMENT, nombre_pais TEXT, " +
            "                                                                codigo_area TEXT)";

    public static final String DROP_TABLE_CONTACTOS = "DROP Table IF EXISTS"+ TABLA_CONTACTOS;
    public static final String DROP_TABLE_PAISES = "DROP Table IF EXISTS"+ TABLA_PAISES;

    /* Creacion del nombre de la base de datos */
    public static final String NAME_DATABASE = "db_examen";

}
