package edu.continental.modparticipacionciudadana.clases;

public class Obra {
    private String ID_Obra;
    private String OBR_Nombre;
    private String OBR_Descripcion;
    private String ID_Tipo;
    private String Tipo_Obra;
    private String OBR_Fecha_Inicio;
    private String OBR_Fecha_Fin;
    private String OBR_Monto;
    private String OBR_Coordenanda_X;
    private String OBR_Coordenanda_Y;
    private String OBR_Dias_Calendarios;

    public Obra() {
    }

    public Obra(String ID_Obra, String OBR_Nombre, String OBR_Descripcion, String ID_Tipo, String tipo_Obra, String OBR_Fecha_Inicio, String OBR_Fecha_Fin, String OBR_Monto, String OBR_Coordenanda_X, String OBR_Coordenanda_Y, String OBR_Dias_Calendarios) {
        this.ID_Obra = ID_Obra;
        this.OBR_Nombre = OBR_Nombre;
        this.OBR_Descripcion = OBR_Descripcion;
        this.ID_Tipo = ID_Tipo;
        Tipo_Obra = tipo_Obra;
        this.OBR_Fecha_Inicio = OBR_Fecha_Inicio;
        this.OBR_Fecha_Fin = OBR_Fecha_Fin;
        this.OBR_Monto = OBR_Monto;
        this.OBR_Coordenanda_X = OBR_Coordenanda_X;
        this.OBR_Coordenanda_Y = OBR_Coordenanda_Y;
        this.OBR_Dias_Calendarios = OBR_Dias_Calendarios;
    }

    public String getID_Obra() {
        return ID_Obra;
    }

    public void setID_Obra(String ID_Obra) {
        this.ID_Obra = ID_Obra;
    }

    public String getOBR_Nombre() {
        return OBR_Nombre;
    }

    public void setOBR_Nombre(String OBR_Nombre) {
        this.OBR_Nombre = OBR_Nombre;
    }

    public String getOBR_Descripcion() {
        return OBR_Descripcion;
    }

    public void setOBR_Descripcion(String OBR_Descripcion) {
        this.OBR_Descripcion = OBR_Descripcion;
    }

    public String getID_Tipo() {
        return ID_Tipo;
    }

    public void setID_Tipo(String ID_Tipo) {
        this.ID_Tipo = ID_Tipo;
    }

    public String getTipo_Obra() {
        return Tipo_Obra;
    }

    public void setTipo_Obra(String tipo_Obra) {
        Tipo_Obra = tipo_Obra;
    }

    public String getOBR_Fecha_Inicio() {
        return OBR_Fecha_Inicio;
    }

    public void setOBR_Fecha_Inicio(String OBR_Fecha_Inicio) {
        this.OBR_Fecha_Inicio = OBR_Fecha_Inicio;
    }

    public String getOBR_Fecha_Fin() {
        return OBR_Fecha_Fin;
    }

    public void setOBR_Fecha_Fin(String OBR_Fecha_Fin) {
        this.OBR_Fecha_Fin = OBR_Fecha_Fin;
    }

    public String getOBR_Monto() {
        return OBR_Monto;
    }

    public void setOBR_Monto(String OBR_Monto) {
        this.OBR_Monto = OBR_Monto;
    }

    public String getOBR_Coordenanda_X() {
        return OBR_Coordenanda_X;
    }

    public void setOBR_Coordenanda_X(String OBR_Coordenanda_X) {
        this.OBR_Coordenanda_X = OBR_Coordenanda_X;
    }

    public String getOBR_Coordenanda_Y() {
        return OBR_Coordenanda_Y;
    }

    public void setOBR_Coordenanda_Y(String OBR_Coordenanda_Y) {
        this.OBR_Coordenanda_Y = OBR_Coordenanda_Y;
    }

    public String getOBR_Dias_Calendarios() {
        return OBR_Dias_Calendarios;
    }

    public void setOBR_Dias_Calendarios(String OBR_Dias_Calendarios) {
        this.OBR_Dias_Calendarios = OBR_Dias_Calendarios;
    }
}
