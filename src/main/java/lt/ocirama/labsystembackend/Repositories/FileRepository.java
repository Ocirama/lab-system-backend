package lt.ocirama.labsystembackend.Repositories;

public class FileRepository {


    /*private final static Scanner sc = new Scanner(System.in);
    String data;
    XSSFSheet sheet;
    XSSFWorkbook workbook;
    //DatabaseService ds = new DatabaseService();
    // Connection connection = ds.getConnection();
    LocalDate date = LocalDate.now();
    ScaleService svarstykles = new ScaleService();
    MeginysEntity m = new MeginysEntity();
    TrayEntity t = new TrayEntity();


    public void WeightLogGenerate() {
        //ds.createTable(connection, "Meginiu_svoriai");
        String path = "C:\\Users\\lei12\\Desktop\\New folder\\" + date + "(Svoriai).xlsx";
        try {
            File file = new File(path);
            System.out.println("Protokolas ?");
            m.setProtocolId(sc.nextLine());
            if (file.exists()) {
                FileInputStream fsip = new FileInputStream(path);
                workbook = new XSSFWorkbook(fsip);
                sheet = workbook.createSheet(m.getProtocolId());
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet(m.getProtocolId());
            }
            Row rowhead = sheet.createRow(0);
            rowhead.createCell(0).setCellValue("Mėginio Nr.");
            rowhead.createCell(1).setCellValue("Svoris, g");
            rowhead.createCell(2).setCellValue("Data");
            for (int i = 1; i < 50; i++) {
                System.out.println("Sekantis mėginys? Taip/Ne");
                String TaipNe = sc.nextLine();
                if (TaipNe.equals("Taip")) {
                    System.out.println("Skenuokite mėginio ID:");
                    m.setSampleId(sc.nextLine());
                    Row row1 = sheet.createRow(i);
                    row1.createCell(0).setCellValue(m.getSampleId());
                    System.out.println("Mėginio masės svėrimas:");
                    SerialPort serialPort = svarstykles.SvarstykliuJungtis();
                    m.setSampleWeight(Double.valueOf(svarstykles.Pasverti(serialPort)));
                    row1.createCell(1).setCellValue(FileControllers.changeToComma(String.valueOf(m.getSampleWeight())));
                    svarstykles.ClosePort(serialPort);
                    row1.createCell(2).setCellValue(String.valueOf(date));
                    //ds.updateSvoriai(connection, m.getProtocolId(), meginys, sgDouble, Date.valueOf(date));
                } else if (TaipNe.equals("Ne")) {
                    break;
                }
            }
            FileOutputStream fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();

            //connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void trayRegistryLogGenerte() {
        //ds.createTable(connection, "Padeklo_priskyrimas");
        String path = "C:\\Users\\lei12\\Desktop\\New folder\\" + date + "(Padeklo_priskyrimas).xlsx";
        try {
            File file = new File(path);
            if (file.exists()) {
                FileInputStream fsip = new FileInputStream("C:\\Users\\lei12\\Desktop\\New folder\\" + data + ".xlsx");
                workbook = new XSSFWorkbook(fsip);
            } else {
                workbook = new XSSFWorkbook();
            }
            for (int i = 1; i < 50; ++i) {
                System.out.println("Sekantis meginys? Taip/Ne");
                String TaipNe = sc.nextLine();
                if (TaipNe.equals("Taip")) {
                    System.out.println("Skenuokite mėginio ID:");
                    String meginys = sc.nextLine();
                    //protocolName = ds.getUzsakymo_nr(connection, "meginiu_svoriai", meginys);
                    if (workbook.getNumberOfSheets() != 0) {
                        if (findSheetByProtocol(workbook, protocolName) == null) {
                            sheet = workbook.createSheet(protocolName + "-" + year + "-8b");
                        } else {
                            sheet = workbook.getSheet(protocolName + "-" + year + "-8b");
                        }
                    } else {
                        sheet = workbook.createSheet(protocolName + "-" + year + "-8b");
                    }
                    Row rowhead = sheet.createRow(0);
                    rowhead.createCell(0).setCellValue("Protokolo Nr.");
                    rowhead.createCell(1).setCellValue("Mėginio Nr.");
                    rowhead.createCell(2).setCellValue("Padėklo Nr.");
                    rowhead.createCell(3).setCellValue("Data");
                    Row row1 = sheet.createRow(sheet.getLastRowNum() + 1);
                    row1.createCell(0).setCellValue(m.getProtocolId());
                    row1.createCell(1).setCellValue(m.getSampleId());
                    System.out.println("Skenuokite padėklą");
                    t.setTrayId(Integer.valueOf(sc.nextLine()));
                    row1.createCell(2).setCellValue(t.getTrayId());
                    row1.createCell(3).setCellValue(String.valueOf(date));

                    //ds.priskyrimas(connection, protocolName, meginys, padeklas, Date.valueOf(date));
                } else if (TaipNe.equals("Ne")) {
                    break;
                }
            }
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\lei12\\Desktop\\New folder\\" + data + ".xlsx");
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
            //connection.close();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void totalMoistureLogGenerate() {

        String path = "C:\\Users\\lei12\\Desktop\\New folder\\" + date + "(VisumineDregme).xlsx";
        File file = new File(path);
        //ds.createTable(connection, "Visumine_dregme");
        try {
            if (file.exists()) {
                FileInputStream fsip = new FileInputStream("C:\\Users\\lei12\\Desktop\\New folder\\" + data + ".xlsx");
                workbook = new XSSFWorkbook(fsip);
            } else {
                workbook = new XSSFWorkbook();
            }
            for (int i = 1; i < 50; i++) {
                System.out.println("Sekantis meginys? Taip/Ne");
                String TaipNe = sc.nextLine();
                if (TaipNe.equals("Taip")) {
                    System.out.println("Skenuokite mėginio ID:");
                    String meginys = sc.nextLine();
                    protocolName = ds.getUzsakymo_nr(connection, "meginiu_svoriai", meginys);
                    if (workbook.getNumberOfSheets() != 0) {
                        if (findSheetByProtocol(workbook, protocolName) == null) {
                            sheet = workbook.createSheet(protocolName + "-" + year + "-8b");
                        } else {
                            sheet = workbook.getSheet(protocolName + "-" + year + "-8b");
                        }
                    } else {
                        sheet = workbook.createSheet(protocolName + "-" + year + "-8b");
                    }
                    Row rowhead = sheet.createRow(0);
                    rowhead.createCell(0).setCellValue("Užsakymo Nr.");
                    rowhead.createCell(1).setCellValue("Mėginio Nr.");
                    rowhead.createCell(2).setCellValue("Padėklo Nr.");
                    rowhead.createCell(3).setCellValue("Tuščio padėklo masė, g");
                    rowhead.createCell(4).setCellValue("Tuščio padėklo ir ėminio masė PRIEŠ bandymą, g");
                    rowhead.createCell(5).setCellValue("Tuščio padėklo ir ėminio masė PO bandymo, g");
                    rowhead.createCell(6).setCellValue("Tuščio padėklo ir ėminio masė PO bandymo+n val, g");
                    rowhead.createCell(7).setCellValue("Pamatinio padėklo Nr.");
                    rowhead.createCell(8).setCellValue("Pamatinio padėklo masė PRIEŠ bandymą, g");
                    rowhead.createCell(9).setCellValue("Pamatinio padėklo masė PO bandymo, g");
                    rowhead.createCell(10).setCellValue("Data");
                    String sg;
                    String sg1;
                    String trayId;
                    int sheetNumber = sheet.getLastRowNum() + 1;
                    for (int j = 0; j < 2; j++) {
                        Row row = sheet.createRow(sheetNumber + j);
                        m.setSampleId(meginys + "-" + j);
                        row.createCell(1).setCellValue(m.getSampleId());
                        System.out.println("Skenuokite tuščio padėklo ID:");
                        t.setTrayId(Integer.valueOf(sc.nextLine()));
                        row.createCell(2).setCellValue(t.getTrayId());
                        System.out.println("Tuščio padėklo svėrimas:");
                        SerialPort serialPort = svarstykles.SvarstykliuJungtis();
                        t.setTrayAndSampleBefore(Double.valueOf(svarstykles.Pasverti(serialPort)));
                        row.createCell(3).setCellValue(t.getTrayAndSampleBefore());
                        //ds.updateTable(connection, "Visumine_dregme", meginys + "-" + j, trayId, protocolName, sg, LocalDate.now());
                    }
                    for (int j = 0; j < 2; j++) {
                        Row row = sheet.getRow(sheetNumber + j);
                        String meg = meginys + "-" + j;
                        row.getCell(1).setCellValue(meg);
                        System.out.println("Pilno padėklo svėrimas");
                        sg1 = SverimoPrograma();
                        row.createCell(4).setCellValue(sg1);
                        double sgdouble = Double.parseDouble(sg1);
                        ds.PilnasIndas(connection, "visumine_dregme", sg1, meginys + "-" + j);
                    }
                } else if (TaipNe.equals("Ne")) {
                    break;
                }
                Row rowd = sheet.getRow(1);
                rowd.createCell(0).setCellValue(protocolName + "/19-8b");
                rowd.createCell(10).setCellValue(Date.valueOf(date));

                FileOutputStream fileOut = new FileOutputStream("C:\\Users\\lei12\\Desktop\\New folder\\" + data + ".xlsx");
                workbook.write(fileOut);
                fileOut.flush();
                fileOut.close();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void totalMoistureLogUpdate() {
        data = date + "(VisumineDregme)";
        FileInputStream excelFile = null;
        try {
            excelFile = new FileInputStream(new File("C:\\Users\\lei12\\Desktop\\New folder\\" + data + ".xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //ds.createTable(connection, "Visumine_dregme");
        try {
            workbook = new XSSFWorkbook(excelFile);
            sheet = workbook.getSheetAt(0);
            for (int i = 1; i < 50; i++) {
                System.out.println("Sekantis meginys? Taip/Ne");
                if (sc.nextLine().equals("Taip")) {
                    System.out.println("Skenuokite padėklo ID:");
                    String indukas = sc.nextLine();
                    int row = findRow(workbook, indukas);
                    System.out.println(row);
                    String sheet1 = findSheet(workbook, indukas);
                    XSSFSheet sheetx = workbook.getSheet(sheet1);
                    XSSFRow rowx = sheetx.getRow(row);

                    double x = getRandomNumberInRange(0.00005, 0.00030);
                    System.out.println("Induko svėrimas po džiovinimo:");
                    String sg1 = SverimoPrograma();
                    double a = Double.parseDouble(sg1);
                    double sum = round(a + x, 5);
                    rowx.createCell(5).setCellValue(sg1);
                    rowx.createCell(6).setCellValue(String.valueOf(sum));
                   // ds.SvorisPoDziovinimo(connection, "Visumine_dregme", sg1, sum, indukas);
                } else {
                    break;
                }
            }
            Row row = sheet.createRow(5);
            FileOutputStream outFile = new FileOutputStream(new File("C:\\Users\\lei12\\Desktop\\New folder\\" + data + ".xlsx"));
            workbook.write(outFile);
            outFile.flush();
            outFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ashLogGenerate() {
        year = year % 100;
        String data = date + "(Peleningumas)";
        File file = new File("C:\\Users\\lei12\\Desktop\\New folder\\" + data + ".xlsx");
       // ds.createTable(connection, "Peleningumas");
        try {
            for (int i = 1; i < 50; i++) {
                System.out.println("Sekantis meginys? Taip/Ne");
                String TaipNe = sc.nextLine();
                if (TaipNe.equals("Taip")) {
                    System.out.println("Skenuokite mėginio ID:");
                    String meginys = sc.nextLine();
                   // protocolName = ds.getUzsakymo_nr(connection, "meginiu_svoriai", meginys);
                    System.out.println("Protokolas: " + protocolName);
                    if (file.exists()) {
                        FileInputStream fsip = new FileInputStream("C:\\Users\\lei12\\Desktop\\New folder\\" + data + ".xlsx");
                        workbook = new XSSFWorkbook(fsip);
                    } else {
                        workbook = new XSSFWorkbook();
                    }
                    for (int l = 0; l < workbook.getNumberOfSheets(); l++) {
                        if (workbook.getSheetAt(l).getSheetName().startsWith(protocolName)) {
                            sheet = workbook.getSheet(protocolName + "-" + year + "-8b");
                        } else if (!workbook.getSheetAt(l).getSheetName().startsWith(protocolName)) {
                            continue;

                        } else {
                            sheet = workbook.createSheet(protocolName + "-" + year + "-8b");
                        }
                    }
                    Row rowhead1 = sheet.createRow(0);
                    rowhead1.createCell(0).setCellValue("Užsakymo Nr.");
                    rowhead1.createCell(1).setCellValue("Mėginio Nr.");
                    rowhead1.createCell(2).setCellValue("Indelio Nr.");
                    rowhead1.createCell(3).setCellValue("Tuščio indelio su dangteliu masė, g");
                    rowhead1.createCell(4).setCellValue("Tuščio indelio su dangteliu ir ėminio masė PRIEŠ bandymą, g");
                    rowhead1.createCell(5).setCellValue("Tuščio indelio su dangteliu ir ėminio masė PO bandymo, g");
                    rowhead1.createCell(6).setCellValue("Data");
                    String sg = null;
                    String sg1 = null;
                    String trayId = null;
                    for (int j = 0; j < 2; j++) {
                        Row row1 = sheet.createRow(i + j);
                        String meg = meginys + "-" + j;
                        row1.createCell(1).setCellValue(meg);
                        System.out.println("Skenuokite tuščio induko ID:");
                        trayId = sc.nextLine();

                        row1.createCell(2).setCellValue(trayId);
                        System.out.println("Tuščio induko svėrimas:");
                        sg = SverimoPrograma();
                        row1.createCell(3).setCellValue(changeToComma(sg));
                        //ds.updateTable(connection, "peleningumas", meginys + "-" + j, trayId, protocolName, sg, LocalDate.now());
                    }
                    for (int j = 0; j < 2; j++) {
                        Row row1 = sheet.getRow(i + j);
                        String meg = meginys + "-" + j;
                        row1.getCell(1).setCellValue(meg);
                        System.out.println("Pilno induko svėrimas");
                        sg1 = SverimoPrograma();
                        row1.createCell(4).setCellValue(changeToComma(sg1));
                        //ds.PilnasIndas(connection, "peleningumas", sg1, meginys + "-" + j);

                    }
                } else if (TaipNe.equals("Ne")) {
                    break;
                }
                Row row1 = sheet.getRow(1);
                row1.createCell(0).setCellValue(protocolName + "/19-8b");
                row1.createCell(10).setCellValue(Date.valueOf(LocalDate.now()));
                FileOutputStream fileOut1 = new FileOutputStream("C:\\Users\\lei12\\Desktop\\New folder\\" + data + ".xlsx");
                workbook.write(fileOut1);
                fileOut1.flush();
                fileOut1.close();
            }
           // connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void ashLogUpdate() {
        data = date + "(VisumineDregme)";
        FileInputStream excelFile = null;
        try {
            excelFile = new FileInputStream(new File("C:\\Users\\lei12\\Desktop\\New folder\\" + data + ".xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook = new XSSFWorkbook(excelFile);
            for (int i = 1; i < 5000; i++) {
                System.out.println("Sekantis meginys? Taip/Ne");
                if (sc.nextLine().equals("Taip")) {
                    System.out.println("Skenuokite induko ID:");
                    String indukas2 = sc.nextLine();
                    int row2 = findRow(workbook, indukas2);
                    System.out.println(row2);
                    String sheet1 = findSheet(workbook, indukas2);
                    XSSFSheet sheetx = workbook.getSheet(sheet1);
                    XSSFRow rowz = sheetx.getRow(row2);
                    System.out.println("Induko svėrimas po džiovinimo:");
                    String sg2 = SverimoPrograma();
                    rowz.createCell(5).setCellValue(changeToComma(sg2));
                    double x = Double.parseDouble(sg2);
                    //ds.PelPoDziovinimo(connection, sg2, indukas2);
                } else {
                    break;
                }
            }
            FileOutputStream outFile = new FileOutputStream(new File("C:\\Users\\lei12\\Desktop\\New folder\\" + data + ".xlsx"));
            workbook.write(outFile);
            outFile.flush();
            outFile.close();
           // connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void generalMoistureLogGenerate() {
        year = year % 100;
        String data = date + "(BendrojiDregme)";
        File file = new File("C:\\Users\\lei12\\Desktop\\New folder\\" + data + ".xlsx");
        //ds.createTable(connection, "bendroji_dregme");
        try {
            for (int i = 1; i < 50; i++) {
                System.out.println("Sekantis meginys? Taip/Ne");
                String TaipNe = sc.nextLine();
                if (TaipNe.equals("Taip")) {
                    System.out.println("Skenuokite mėginio ID:");
                    String meginys = sc.nextLine();
                    //protocolName = ds.getUzsakymo_nr(connection, "meginiu_svoriai", meginys);
                    System.out.println("Protokolas: " + protocolName);
                    if (file.exists()) {
                        FileInputStream fsip = new FileInputStream("C:\\Users\\lei12\\Desktop\\New folder\\" + data + ".xlsx");
                        workbook = new XSSFWorkbook(fsip);
                    } else {
                        workbook = new XSSFWorkbook();
                    }
                    for (int l = 0; l < workbook.getNumberOfSheets(); l++) {
                        if (workbook.getSheetAt(l).getSheetName().startsWith(protocolName)) {
                            sheet = workbook.getSheet(protocolName + "-" + year + "-8b");
                        } else if (!workbook.getSheetAt(l).getSheetName().startsWith(protocolName)) {
                            continue;

                        } else {
                            sheet = workbook.createSheet(protocolName + "-" + year + "-8b");
                        }
                    }

                    Row rowhead1 = sheet.createRow(0);
                    rowhead1.createCell(0).setCellValue("Užsakymo Nr.");
                    rowhead1.createCell(1).setCellValue("Mėginio Nr.");
                    rowhead1.createCell(2).setCellValue("Indelio Nr.");
                    rowhead1.createCell(3).setCellValue("Tuščio indelio su dangteliu masė, g");
                    rowhead1.createCell(4).setCellValue("Tuščio indelio su dangteliu ir ėminio masė PRIEŠ bandymą, g");
                    rowhead1.createCell(5).setCellValue("Tuščio indelio su dangteliu ir ėminio masė PO bandymo, g");
                    rowhead1.createCell(6).setCellValue("Tuščio indelio su dangteliu ir ėminio masė PO bandymo+n val, g");
                    rowhead1.createCell(7).setCellValue("Data");
                    String sg = null;
                    String sg1 = null;
                    String trayId = null;
                    if (TaipNe.equals("Taip")) {
                        System.out.println("Skenuokite mėginio ID:");
                        meginys = sc.nextLine();
                        for (int j = 0; j < 2; j++) {
                            Row row1 = sheet.createRow(i + j);
                            String meg = meginys + "-" + j;
                            row1.createCell(1).setCellValue(meg);
                            System.out.println("Skenuokite tuščio induko ID:");
                            trayId = sc.nextLine();
                            row1.createCell(2).setCellValue(trayId);
                            System.out.println("Tuščio induko svėrimas:");
                            sg = SverimoPrograma();
                            row1.createCell(3).setCellValue(changeToComma(sg));

                            //ds.updateTable(connection, "bendroji_dregme", meginys + "-" + j, trayId, protocolName, sg, LocalDate.now());
                        }
                        for (int j = 0; j < 2; j++) {
                            Row row1 = sheet.getRow(i + j);
                            String meg = meginys + "-" + j;
                            row1.getCell(1).setCellValue(meg);
                            System.out.println("Pilno induko svėrimas");
                            sg1 = SverimoPrograma();
                            row1.createCell(4).setCellValue(changeToComma(sg1));
                           //ds.PilnasIndas(connection, "bendroji_dregme", sg1, meginys + "-" + j);
                        }
                    } else if (TaipNe.equals("Ne")) {
                        break;
                    }
                    Row row1 = sheet.getRow(1);
                    row1.createCell(0).setCellValue(protocolName + "/19-8b");
                    row1.createCell(10).setCellValue(Date.valueOf(date));
                    FileOutputStream fileOut1 = new FileOutputStream("C:\\Users\\lei12\\Desktop\\New folder\\" + data + ".xlsx");
                    workbook.write(fileOut1);
                    fileOut1.flush();
                    fileOut1.close();
                }
            }
           //connection.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public void generalMoistureLogUpdate() {
        data = date + "(VisumineDregme)";
        FileInputStream excelFile = null;
        try {
            excelFile = new FileInputStream(new File("C:\\Users\\lei12\\Desktop\\New folder\\" + data + ".xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook = new XSSFWorkbook(excelFile);
            for (int i = 1; i < 5000; i++) {
                System.out.println("Sekantis meginys? Taip/Ne");
                if (sc.nextLine().equals("Taip")) {
                    System.out.println("Skenuokite induko ID:");
                    String indukas = sc.nextLine();
                    int row = findRow(workbook, indukas);
                    System.out.println(row);
                    String sheet1 = findSheet(workbook, indukas);
                    XSSFSheet sheetx = workbook.getSheet(sheet1);
                    XSSFRow rowx = sheetx.getRow(row);

                    double x = getRandomNumberInRange(0.00005, 0.00020);
                    System.out.println("Induko svėrimas po džiovinimo:");
                    String sg1 = SverimoPrograma();
                    double sgn = Double.parseDouble(sg1);
                    double sum = round(sgn + x, 5);
                    rowx.createCell(5).setCellValue(changeToComma(sg1));
                    rowx.createCell(6).setCellValue(changeToComma(String.valueOf(sum)));
                    //ds.SvorisPoDziovinimo(connection, "bendroji_dregme", sg1, sum, indukas);
                } else {
                    break;
                }

            }
            FileOutputStream outFile = new FileOutputStream(new File("C:\\Users\\lei12\\Desktop\\New folder\\" + data + ".xlsx"));
            workbook.write(outFile);
            outFile.flush();
            outFile.close();
            //connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    /*public void SvoriaiCheck() {
        Svoriai svoriai = new Svoriai();
        svoriai.SvoriuFailoKurimas();
        System.out.println("Sekantis protokolas? Taip/Ne");
        String TaipNe = sc.nextLine();
        if (TaipNe.equals("Taip")) {
            svoriai.SvoriaiCheck();
        }
    }*/
}
