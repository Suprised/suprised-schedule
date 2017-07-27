package com.suprised.itext;


public class PdfTable {
    /*public static final String DEST = "simple_table.pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        Path path = Paths.get(DEST);
        Files.delete(path);
        Files.createFile(path);
        new PdfTable().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfPTable table = new PdfPTable(8);
        for (int aw = 0; aw < 16; aw++) {
            table.addCell("hi");
        }
        Image image = Image.getInstance("src/resource/p03.gif");
        
        document.add(table);
        document.add(image);
        document.close();
    }*/

}
