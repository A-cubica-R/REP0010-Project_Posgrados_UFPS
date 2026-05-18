package ufps.edu.co.services;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import ufps.edu.co.records.output.entity.AspiranteOutput;

@Service
public class PdfGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(PdfGeneratorService.class);

    private static final Color HEADER_BG  = new Color(185, 28, 28);
    private static final Color ROW_ALT_BG = new Color(252, 220, 220);

    public byte[] generarListaAdmitidos(
            String cohorteNombre,
            LocalDateTime fechaGeneracion,
            List<AspiranteOutput> aspirantesAdmitidos,
            String directorNombre) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 50f, 50f, 60f, 60f);

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            // --- Encabezado imagen ---
            try (InputStream is = getClass().getClassLoader().getResourceAsStream("EncabezadoPDF.png")) {
                if (is != null) {
                    Image headerImg = Image.getInstance(is.readAllBytes());
                    headerImg.setAlignment(Element.ALIGN_CENTER);
                    headerImg.scaleToFit(PageSize.A4.getWidth() - 100f, 120f);
                    headerImg.setSpacingAfter(12f);
                    document.add(headerImg);
                } else {
                    logger.warn("EncabezadoPDF.png no encontrado en el classpath");
                }
            }

            Font titleFont    = new Font(Font.HELVETICA, 16, Font.BOLD,   Color.BLACK);
            Font subtitleFont = new Font(Font.HELVETICA, 11, Font.NORMAL, Color.DARK_GRAY);
            Font colHeaderFont = new Font(Font.HELVETICA, 10, Font.BOLD,  Color.WHITE);
            Font boldFont     = new Font(Font.HELVETICA, 10, Font.BOLD,   Color.BLACK);
            Font normalFont   = new Font(Font.HELVETICA, 10, Font.NORMAL, Color.BLACK);
            Font footerFont   = new Font(Font.HELVETICA, 10, Font.NORMAL, Color.BLACK);
            Font footerBold   = new Font(Font.HELVETICA, 11, Font.BOLD,   Color.BLACK);

            // --- Título y fecha ---
            Paragraph title = new Paragraph("Lista de Aspirantes Admitidos en " + cohorteNombre, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph fechaPar = new Paragraph(
                    "Fecha: " + fechaGeneracion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    subtitleFont);
            fechaPar.setAlignment(Element.ALIGN_CENTER);
            fechaPar.setSpacingAfter(20f);
            document.add(fechaPar);

            // --- Tabla ---
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{0.5f, 3f, 3f, 1.5f});
            table.setSpacingBefore(5f);
            table.setSpacingAfter(30f);

            for (String col : new String[]{"#", "Nombre Completo", "Correo / Celular", "Puntaje"}) {
                PdfPCell hCell = new PdfPCell(new Phrase(col, colHeaderFont));
                hCell.setBackgroundColor(HEADER_BG);
                hCell.setPadding(6f);
                hCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(hCell);
            }

            for (int i = 0; i < aspirantesAdmitidos.size(); i++) {
                AspiranteOutput a = aspirantesAdmitidos.get(i);
                Color rowBg = (i % 2 == 0) ? Color.WHITE : ROW_ALT_BG;

                String nombre   = "N/A";
                String contacto = "N/A";
                String puntaje  = a.puntuacion() != null ? a.puntuacion().toPlainString() : "N/A";

                if (a.persona() != null) {
                    String n  = a.persona().nombres()   != null ? a.persona().nombres()   : "";
                    String ap = a.persona().apellidos() != null ? a.persona().apellidos() : "";
                    nombre = (n + " " + ap).trim();

                    String correo  = a.persona().correo()  != null ? a.persona().correo()  : "";
                    String celular = a.persona().celular() != null ? a.persona().celular() : "";
                    contacto = correo + (celular.isBlank() ? "" : "\n" + celular);
                }

                PdfPCell numCell = new PdfPCell(new Phrase(String.valueOf(i + 1), normalFont));
                numCell.setBackgroundColor(rowBg);
                numCell.setPadding(5f);
                numCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(numCell);

                PdfPCell nameCell = new PdfPCell(new Phrase(nombre, boldFont));
                nameCell.setBackgroundColor(rowBg);
                nameCell.setPadding(5f);
                table.addCell(nameCell);

                PdfPCell contactCell = new PdfPCell(new Phrase(contacto, normalFont));
                contactCell.setBackgroundColor(rowBg);
                contactCell.setPadding(5f);
                table.addCell(contactCell);

                PdfPCell scoreCell = new PdfPCell(new Phrase(puntaje, normalFont));
                scoreCell.setBackgroundColor(rowBg);
                scoreCell.setPadding(5f);
                scoreCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(scoreCell);
            }

            document.add(table);

            // --- Pie de página ---
            Paragraph footerLabel = new Paragraph("Generado y firmado", footerFont);
            footerLabel.setAlignment(Element.ALIGN_CENTER);
            document.add(footerLabel);

            Paragraph footerName = new Paragraph(directorNombre, footerBold);
            footerName.setAlignment(Element.ALIGN_CENTER);
            document.add(footerName);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            logger.error("Error generando PDF de lista de admitidos para cohorte '{}'", cohorteNombre, e);
            throw new RuntimeException("Error generando PDF de admitidos: " + e.getMessage(), e);
        }
    }
}
