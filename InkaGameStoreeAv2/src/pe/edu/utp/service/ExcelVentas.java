package pe.edu.utp.service;

import pe.edu.utp.config.Conexion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.Desktop;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import pe.edu.utp.entity.DetalleVenta;
import pe.edu.utp.entity.Venta;

public class ExcelVentas {

    public static void generarReporteVentas(List<Venta> ventas, List<DetalleVenta> detalles) {
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("Ventas");

        try {
            // Crear encabezados para la primera tabla (Ventas)
            Row headerVentas = sheet.createRow(0);
            headerVentas.createCell(0).setCellValue("ID_Venta");
            headerVentas.createCell(1).setCellValue("Cliente");
            headerVentas.createCell(2).setCellValue("Empleado");
            headerVentas.createCell(3).setCellValue("Total");
            headerVentas.createCell(4).setCellValue("Fecha de Venta");
            headerVentas.createCell(5).setCellValue("Hora de Venta");

            // Aplicar estilo a los encabezados de ventas
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

            for (int i = 0; i < 6; i++) {
                headerVentas.getCell(i).setCellStyle(headerStyle);
            }

            // Llenar datos de la primera tabla (Ventas)
            int numFilaDatos = 1;
            for (Venta venta : ventas) {
                Row filaDatos = sheet.createRow(numFilaDatos++);
                filaDatos.createCell(0).setCellValue(venta.getVentaID());
                filaDatos.createCell(1).setCellValue(venta.getClienteID());
                filaDatos.createCell(2).setCellValue(venta.getUsuarioID());
                filaDatos.createCell(3).setCellValue(venta.getTotal());
                filaDatos.createCell(4).setCellValue(venta.getFecha());
                filaDatos.createCell(5).setCellValue(venta.getHora());
            }

            // Crear encabezados para la segunda tabla (Detalles de Venta)
            Row headerDetalles = sheet.createRow(numFilaDatos + 2);
            headerDetalles.createCell(0).setCellValue("NombreProducto");
            headerDetalles.createCell(1).setCellValue("Cantidad");
            headerDetalles.createCell(2).setCellValue("Precio");
            headerDetalles.createCell(3).setCellValue("Subtotal");
            headerDetalles.createCell(4).setCellValue("FechaHora");

            // Aplicar estilo a los encabezados de detalles
            for (int i = 0; i < 5; i++) {
                headerDetalles.getCell(i).setCellStyle(headerStyle);
            }

            // Llenar datos de la segunda tabla (Detalles de Venta)
            int numFilaDetalle = numFilaDatos + 3;
            for (DetalleVenta detalle : detalles) {
                Row filaDetalle = sheet.createRow(numFilaDetalle++);
                filaDetalle.createCell(0).setCellValue(detalle.getProductoID());
                filaDetalle.createCell(1).setCellValue(detalle.getCantidad());
                filaDetalle.createCell(2).setCellValue(detalle.getPrecio());
                filaDetalle.createCell(3).setCellValue(detalle.getSubtotal());
                filaDetalle.createCell(4).setCellValue(detalle.getFechHoraAgreCarrito());
            }

            // Ajustar automáticamente el ancho de las columnas
            for (int i = 0; i < 6; i++) {
                sheet.autoSizeColumn(i);
            }

            // Exportar el archivo Excel a la carpeta de descargas del usuario
            String home = System.getProperty("user.home");
            File file = new File(home + "/Downloads/ReporteVentas.xlsx");
            FileOutputStream fileOut = new FileOutputStream(file);
            book.write(fileOut);
            fileOut.close();
            Desktop.getDesktop().open(file);
            JOptionPane.showMessageDialog(null, "Reporte de Ventas Generado");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
