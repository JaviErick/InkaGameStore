package pe.edu.utp.service;

import pe.edu.utp.entity.Productos;
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

public class ExcelProductos {

    public static void generarReporteProductos(List<Productos> productos) {
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("Productos");

        try {
            // Inserta el logotipo en el reporte si existe
            InputStream is = new FileInputStream("src/pe/edu/utp/assets/logoinka.png");
            byte[] bytes = IOUtils.toByteArray(is);
            int imgIndex = book.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            is.close();

            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = book.getCreationHelper().createClientAnchor();
            anchor.setCol1(0); // Columna donde inicia la imagen
            anchor.setRow1(0); // Fila donde inicia la imagen
            Picture pict = drawing.createPicture(anchor, imgIndex);
            pict.resize(1, 3); // Ajusta el tamaño de la imagen

            // Crear encabezados para el reporte
            Row header = sheet.createRow(4);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Categoría");
            header.createCell(2).setCellValue("Nombre");
            header.createCell(3).setCellValue("Proveedor");
            header.createCell(4).setCellValue("Precio");
            header.createCell(5).setCellValue("Stock");
            header.createCell(6).setCellValue("Fecha Hora Agregada");
            header.createCell(7).setCellValue("Descripción");
            header.createCell(8).setCellValue("Imagen Ruta");

            // Aplica estilos a los encabezados
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
            for (int i = 0; i < 9; i++) {
                header.getCell(i).setCellStyle(headerStyle);
            }

            // Rellena el reporte con datos de productos
            int numFilaDatos = 5; // Empieza en la fila 5
            CellStyle datosEstilo = book.createCellStyle();
            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);

            for (Productos producto : productos) {
                Row filaDatos = sheet.createRow(numFilaDatos++);
                filaDatos.createCell(0).setCellValue(producto.getProducto_ID());
                filaDatos.createCell(1).setCellValue(producto.getCategoria());
                filaDatos.createCell(2).setCellValue(producto.getNombreProducto());
                filaDatos.createCell(3).setCellValue(producto.getProveedor());
                filaDatos.createCell(4).setCellValue(producto.getPrecio());
                filaDatos.createCell(5).setCellValue(producto.getStock());
                filaDatos.createCell(6).setCellValue(producto.getFechaHoraAgregada());
                filaDatos.createCell(7).setCellValue(producto.getDescripcion());
                filaDatos.createCell(8).setCellValue(producto.getImagenRuta());

                // Aplica el estilo de datos a cada celda
                for (int i = 0; i < 9; i++) {
                    filaDatos.getCell(i).setCellStyle(datosEstilo);
                }
            }

            // Ajusta automáticamente el ancho de las columnas
            for (int i = 0; i < 9; i++) {
                sheet.autoSizeColumn(i);
            }

            // Exporta el archivo Excel a la carpeta de descargas del usuario
            String home = System.getProperty("user.home");
            File file = new File(home + "/Downloads/ReporteProductos.xlsx");
            FileOutputStream fileOut = new FileOutputStream(file);
            book.write(fileOut);
            fileOut.close();
            Desktop.getDesktop().open(file);
            JOptionPane.showMessageDialog(null, "Reporte de Productos Generado");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
