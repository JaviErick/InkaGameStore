package pe.edu.utp.dto;

public class SesionUsuario {

    private static InicioSesionDTO userLogeado;

    public static void iniciarSesion(InicioSesionDTO user) {
        userLogeado = user;
    }

    public static InicioSesionDTO getUsuarioLogeado() {
        return userLogeado;
    }
    public static int getIdLogeado() {
        if (userLogeado != null) {
            return userLogeado.getId();
        }
        return -1;
    }

    public static void cerrarSesion() {
        userLogeado = null;
    }
}
