package project.lab6.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class PDFDocument {
    /**
     * Creates a PDF document
     * @param documentName
     * @param period
     * @param text
     */
    public void create(String documentName, String period, List<String> text) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream pageContentStream = new PDPageContentStream(document, page);
            pageContentStream.beginText();
            pageContentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            pageContentStream.newLineAtOffset(25, 700);
            pageContentStream.setLeading(14.5f);
            pageContentStream.showText(period);
            pageContentStream.newLine();
            ;
            text.forEach(show -> {
                try {
                    pageContentStream.showText(show.toString());
                    pageContentStream.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            pageContentStream.endText();
            pageContentStream.close();

            document.save(documentName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createFriendsMessagesPDF(String documentName, String period, List<String> messages, List<String> friends) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream pageContentStream = new PDPageContentStream(document, page);
            pageContentStream.beginText();
            pageContentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            pageContentStream.newLineAtOffset(25, 700);
            pageContentStream.setLeading(14.5f);
            pageContentStream.showText(period);
            pageContentStream.newLine();
            ;
            messages.forEach(show -> {
                try {
                    pageContentStream.showText(show.toString());
                    pageContentStream.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            pageContentStream.newLine();
            pageContentStream.showText("New friends in the specified period: ");
            pageContentStream.newLine();
            pageContentStream.newLine();
            friends.forEach(show -> {
                try {
                    pageContentStream.showText(show.toString());
                    pageContentStream.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            pageContentStream.endText();
            pageContentStream.close();

            document.save(documentName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
