package com.suprised.itext;


public class HtmlParse {
    /*public static final String DEST = "html.pdf";
    public static final String HTML = "src/resource/resume.html";
 
    // public static final String FONT = "src/resource/SIMSUN.TTC";
    // public static final String FONT = "src/resource/FreeSans.ttf";
    public static final String FONT = "src/resource/simsunb.ttf";
 
    public static void main(String[] args) throws IOException, DocumentException {
        Path path = Paths.get(DEST);
        if (Files.exists(path)) {
            Files.delete(path);
        }
        Files.createFile(path);
        new HtmlParse().createPdf(DEST);
        // new HtmlParse().createPdf2(DEST);
        // new HtmlParse().createPdf3(DEST);
    }
 
    *//**
     * Creates a PDF with the words "Hello World"
     * @param file
     * @throws IOException
     * @throws DocumentException
     *//*
    public void createPdf(String file) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        // step 3
        document.open();
        // step 4
        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        // fontImp.register(FONT);
        fontImp.register("src/resource/SIMYOU.TTF");
        for (String s : fontImp.getRegisteredFamilies()) {
            System.out.println(s);
        }
        
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(HTML), null, Charset.forName("UTF-8"), fontImp);
        // step 5
        document.close();
    }
    
    public void createPdf2(String file) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        // step 3
        document.open();
        // step 4
        // String str = null;
 
        XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
        // InputStream is = new ByteArrayInputStream(Files.readAllBytes(Paths.get(HTML)));
        InputStream is = new ByteArrayInputStream("<html><body><h1 style='font-family: Arial Unicode MS, FreeSans; color:red; font-size:20px; font-weight: normal; '>hello 中文  World</h1></body></html>".getBytes("UTF-8"));
        worker.parseXHtml(writer, document, is, Charset.forName("UTF-8"), new XMLWorkerFontProvider("src/resource/"));
        // step 5
        document.close();
    }
    
    public void createPdf3(String file) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        // step 3
        document.open();
        // step 4
        // CSS
        CSSResolver cssResolver = new StyleAttrCSSResolver();
        CssFile cssFile = XMLWorkerHelper.getCSS(new ByteArrayInputStream("body {font-family:tsc fming s tt}".getBytes()));
        cssResolver.addCss(cssFile);
 
        // HTML
        XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontProvider.register("src/resource/simsunb.ttf");//cfmingeb.ttf
        fontProvider.register("src/resource/SIMYOU.TTF");
        
        for (String s : fontProvider.getRegisteredFamilies()) {
            System.out.println(s);
        }
        
        CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
 
        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
 
        // XML Worker
        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(new FileInputStream(HTML), Charset.forName("UTF-8"));
        // step 5
        document.close();
    }*/
}
