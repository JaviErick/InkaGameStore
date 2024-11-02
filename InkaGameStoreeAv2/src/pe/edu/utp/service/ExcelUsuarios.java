package pe.edu.utp.service;

import pe.edu.utp.entity.Usuario;
import pe.edu.utp.config.Conexion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.Desktop;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ExcelUsuarios {

    public static void generarReporteUsuarios(List<Usuario> usuarios) {
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("Usuarios");

        try {
            // Inserta el logotipo en el reporte si existe
            InputStream is = new FileInputStream("src/pe/edu/utp/assets/logoinka.png");
            byte[] bytes = IOUtils.toByteArray(is);
            int imgIndex = book.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            is.close();

            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = book.getCreationHelper().createClientAnchor();
            anchor.setCol1(0);
            anchor.setRow1(0);
            Picture pict = drawing.createPicture(anchor, imgIndex);
            pict.resize(1, 3);

            // Crear encabezados para el reporte de usuarios
            Row header = sheet.createRow(4);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Rol");
            header.createCell(2).setCellValue("DNI");
            header.createCell(3).setCellValue("Nombres y Apellidos");
            header.createCell(4).setCellValue("Teléfono");
            header.createCell(5).setCellValue("Correo");
            header.createCell(6).setCellValue("Contraseña");

            // Estilo de encabezado
            CellStyle headerStyle = book.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            Font font = book.createFont();
            font.setBold(true);
            headerStyle.setFont(font);
            for (int i = 0; i < 7; i++) {
                header.getCell(i).setCellStyle(headerStyle);
            }

            // Llenar reporte con datos de usuarios
            int numFilaDatos = 5;
            CellStyle datosEstilo = book.createCellStyle();
            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);

            for (Usuario usuario : usuarios) {
                Row filaDatos = sheet.createRow(numFilaDatos++);
                filaDatos.createCell(0).setCellValue(usuario.getUsuario_ID());
                filaDatos.createCell(1).setCellValue(usuario.getRol());
                filaDatos.createCell(2).setCellValue(usuario.getDNI());
                filaDatos.createCell(3).setCellValue(usuario.getNombreCompleto());
                filaDatos.createCell(4).setCellValue(usuario.getTelefono());
                filaDatos.createCell(5).setCellValue(usuario.getCorreo());
                filaDatos.createCell(6).setCellValue(usuario.getContraseña());

                for (int i = 0; i < 7; i++) {
                    filaDatos.getCell(i).setCellStyle(datosEstilo);
                }
            }

            // Ajuste automático de ancho de columnas
            for (int i = 0; i < 7; i++) {
                sheet.autoSizeColumn(i);
            }

            // Guardar el archivo Excel en la carpeta de descargas
            String home = System.getProperty("user.home");
            File file = new File(home + "/Downloads/ReporteUsuarios.xlsx");
            FileOutputStream fileOut = new FileOutputStream(file);
            book.write(fileOut);
            fileOut.close();
            Desktop.getDesktop().open(file);
            JOptionPane.showMessageDialog(null, "Reporte Generado");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
