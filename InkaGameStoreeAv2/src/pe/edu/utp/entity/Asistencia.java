
package pe.edu.utp.entity;

public class Asistencia {
    private int asistenciaID;
    private int usuarioID;
    private String fecha;
    private String horaIngreso;
    private String horaSalida;

    public Asistencia() {
    }

    public Asistencia(int asistenciaID, int usuarioID, String fecha, String horaIngreso, String horaSalida) {
        this.asistenciaID = asistenciaID;
        this.usuarioID = usuarioID;
        this.fecha = fecha;
        this.horaIngreso = horaIngreso;
        this.horaSalida = horaSalida;
    }

    public int getAsistenciaID() {
        return asistenciaID;
    }

    public void setAsistenciaID(int asistenciaID) {
        this.asistenciaID = asistenciaID;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(String horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    @Override
    public String toString() {
        return "Asistencia{" + "asistenciaID=" + asistenciaID + ", usuarioID=" + usuarioID + ", fecha=" + fecha + ", horaIngreso=" + horaIngreso + ", horaSalida=" + horaSalida + '}';
    }
    
    
}
