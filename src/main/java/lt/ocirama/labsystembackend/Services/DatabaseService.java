package lt.ocirama.labsystembackend.Services;

public class DatabaseService {
    /*int year = Calendar.getInstance().get(Calendar.YEAR);
    int month = Calendar.getInstance().get(Calendar.MONTH);
    public Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/kuro_bandymu_sistema?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                connection = DriverManager.getConnection(url, "lei", "pass");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to resolve connection");
            }
        }
        return connection;
    }

    public void createTable(Connection connection, String bandymas) {
        try {
            Statement statement = connection.createStatement();
            String a, b, c, d, e, f, g, h, i, j, k, l, m;
            a = "id int PRIMARY KEY AUTO_INCREMENT,";
            b = "Meginio_nr varchar(30),";
            c = "Padeklo_nr varchar(30),";
            d = "Uzsakymo_nr varchar(30),";
            e = "Svoris double,";
            f = "Tuscio_indo_mase double,";
            g = "Tuscio_indo_ir_eminio_mase_PRIES_bandyma double,";
            h = "Tuscio_indo_ir_eminio_mase_PO_bandymo double,";
            i = "Tuscio_indo_ir_eminio_mase_PO_bandymo_plius_n_val double,";
            j = "Pamatinio_padeklo_nr varchar(30)";
            k = "Pamatinio_padeklo_masė_PRIEŠ_bandyma double";
            l = "Pamatinio_padeklo_masė_PO_bandymo double";
            m = "Data date";
            String pattern = "CREATE TABLE IF NOT EXISTS " + bandymas + "_" + year + "_" + month;
            String tableSql = null;
            switch (bandymas) {
                case "Visumine_dregme":
                case "Bendroji_dregme":
                    tableSql = (pattern +
                            "(" + a + d + b + c + f + g + h + i + m + ")");
                    break;
                case "Padeklo_priskyrimas":
                    tableSql = (pattern
                            + "(" + a + d + b + c + m + ")");
                    break;
                case "Meginiu_svoriai":
                    tableSql = (pattern
                            + "(" + d + b + e + m + ")");
                    break;
                case "Peleningumas":
                    tableSql = (pattern
                            + "(" + a + d + b + c + f + g + h + m + ")");
                    break;
                case "Pamatinis_padeklas":
                    tableSql = (pattern
                            + "(" + a + j + k + l + m + ")");
            }
            statement.execute(tableSql);
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSvoriai(Connection con, String uzsakymo_nr, String meginio_nr, Double svoris, Date data) {
        try {
            Statement stmt = con.createStatement();
            //Peleningumas
            String insertSql = "INSERT INTO " + "Meginiu_svoriai" + "_" + year + "_" + month + "(meginio_nr, uzsakymo_nr,svoris,data)"
                    + " VALUES('" + meginio_nr + "','" + uzsakymo_nr + "','" + svoris + "','" + data + "')";
            stmt.executeUpdate(insertSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePamatinis(Connection con, String Pamatinio_padeklo_nr, Double Pamatinio_padeklo_masė_PRIEŠ_bandyma, Date data) {
        try {
            Statement stmt = con.createStatement();
            //Pamatinis
            String insertSql = "INSERT INTO " + "Pamatinis_padeklas" + "_" + year + "_" + month + "(Pamatinio_padeklo_nr, Pamatinio_padeklo_masė_PRIEŠ_bandyma,Pamatinio_padeklo_masė_PO_bandymo,data)"
                    + " VALUES('" + Pamatinio_padeklo_nr + "','" + Pamatinio_padeklo_masė_PRIEŠ_bandyma + "','" + data + "')";
            stmt.executeUpdate(insertSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTable(Connection con, String bandymas, String meginio_nr, String indo_nr, String uzsakymo_nr, String tuscio_indo_mase_nr, LocalDate data) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        try {
            Statement stmt = con.createStatement();
            String insertSql = "INSERT INTO " + bandymas + "_" + year + "_" + month + "(meginio_nr, padeklo_nr, uzsakymo_nr,tuscio_indo_mase,data)"
                    + " VALUES('" + meginio_nr + "', '" + indo_nr + "','" + uzsakymo_nr + "','" + tuscio_indo_mase_nr + "','" + data + "')";
            stmt.executeUpdate(insertSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void PelPoDziovinimo(Connection con, String sg, String indukas) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        try {
            Statement stmt = con.createStatement();
            String sq1 = "UPDATE " + "peleningumas" + "_" + year + "_" + month + " set tuscio_indo_ir_eminio_mase_PO_bandymo =" + sg + " where padeklo_nr = " + indukas + ";";
            stmt.executeUpdate(sq1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void PilnasIndas(Connection con, String bandymas, String sg, String indukas) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        try {
            Statement stmt = con.createStatement();
            String sq1 = "UPDATE " + bandymas + "_" + year + "_" + month + " set tuscio_indo_ir_eminio_mase_PRIES_bandyma =" + sg + " where meginio_nr = " + indukas + ";";
            stmt.executeUpdate(sq1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*void PamatinisPadeklas(Connection con, String bandymas, String trayId2, String sg) {
       int year = Calendar.getInstance().get(Calendar.YEAR);
       int month = Calendar.getInstance().get(Calendar.MONTH);
       LocalDate date2 = LocalDate.now();
       try {
           Statement stmt = con.createStatement();
           String sq1 = "UPDATE " + bandymas + "_" + year + "_" + month + " set pamatinio_padeklo_nr =" + trayId2 + " where Data = " + date2 + ";";
           String sq2 = "UPDATE " + bandymas + "_" + year + "_" + month + " set pamatinio_padeklo_mase_PRIES_bandyma =" + sg + " where Data = " + date2 + ";";
           stmt.executeUpdate(sq1);
           stmt.executeUpdate(sq2);

       } catch (SQLException e) {
           e.printStackTrace();
       }

   }
    public void SvorisPoDziovinimo(Connection con, String bandymas, String sg, Double sum, String indukas) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        try {
            Statement stmt = con.createStatement();
            String sq1 = "UPDATE " + bandymas + "_" + year + "_" + month + " set tuscio_indo_ir_eminio_mase_PO_bandymo =" + sg + " where padeklo_nr = " + indukas + ";";
            String sq2 = "UPDATE " + bandymas + "_" + year + "_" + month + " set tuscio_indo_ir_eminio_mase_PO_bandymo_plius_n_val =" + sum + " where padeklo_nr = " + indukas + ";";
            stmt.executeUpdate(sq1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void PamatinioPadekloSverimas(Connection con, String bandymas, String sg) {
       int year = Calendar.getInstance().get(Calendar.YEAR);
       int month = Calendar.getInstance().get(Calendar.MONTH);
       try {
           Statement stmt = con.createStatement();
           String sq2 = "UPDATE " + bandymas + "_" + year + "_" + month + " set pamatinio_padeklo_mase_PO_bandymo =" + sg + " where Data = " + LocalDate.now() + ";";
           stmt.executeUpdate(sq2);
       } catch (SQLException e) {
           e.printStackTrace();
       }

   }
    public String getUzsakymo_nr(Connection con, String bandymas, String meginys) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        String uzsakymas = null;
        try {
            String sq2 = "select uzsakymo_nr from " + bandymas + "_" + year + "_" + month + " where meginio_nr =" + meginys + ";";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sq2);
            while (rs.next()) {
                uzsakymas = rs.getString("uzsakymo_nr");
            }
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return uzsakymas;
    }

    public String getMeginio_nr(Connection con, String bandymas, String padeklas) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        String uzsakymas = null;
        try {
            String sq2 = "select meginio_nr from " + bandymas + "_" + year + "_" + month + " where padeklo_nr =" + padeklas + " && Data =" + Date.valueOf(LocalDate.now()) + ";";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sq2);
            while (rs.next()) {
                padeklas = rs.getString("meginio_nr");
            }
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return padeklas;
    }


    public void priskyrimas(Connection con, String uzsakymo_nr, String meginio_nr, String padeklas, Date data) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        try {
            Statement stmt = con.createStatement();
            //Peleningumas
            String insertSql = "INSERT INTO " + "Padeklo_priskyrimas" + "_" + year + "_" + month + "(uzsakymo_nr, meginio_nr,padeklo_nr,Data)"
                    + " VALUES('" + uzsakymo_nr + "','" + meginio_nr + "','" + padeklas + "','" + data + "')";
            stmt.executeUpdate(insertSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     public void getData(Connection con,String bandymas,String sg) {
      int year = Calendar.getInstance().get(Calendar.YEAR);
      int month = Calendar.getInstance().get(Calendar.MONTH);
      try {
          String query = "SELECT*from " + bandymas + "_" + year + "_" + month;
          Statement stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery(query);
          while(rs.next())
      }catch (SQLException e) {
          e.printStackTrace();
      }

  }
   String selectSql = "SELECT * FROM employees";
    List<Employee> employees = new ArrayList<>();
    ResultSet resultSet = stmt.executeQuery(selectSql);
    String updatePositionSql = "UPDATE employees SET position=? WHERE emp_id=?";
    PreparedStatement pstmt = con.prepareStatement(updatePositionSql);
        pstmt.setString(1,"lead developer");
        pstmt.setInt(2,1);
    int rowsAffected = pstmt.executeUpdate();

    String preparedSql = "{call insertEmployee(?,?,?,?)}";
    CallableStatement cstmt = con.prepareCall(preparedSql);
        cstmt.setString(2,"ana");
        cstmt.setString(3,"tester");
        cstmt.setDouble(4,2000);
        cstmt.registerOutParameter(1,Types.INTEGER);
        cstmt.execute();
    int new_id = cstmt.getInt(1);

    List<Employee> Employees = new ArrayList<>();
        while(resultSet.next())

    {
        Employee emp = new Employee();
        emp.setId(resultSet.getInt("emp_id"));
        emp.setName(resultSet.getString("name"));
        emp.setPosition(resultSet.getString("position"));
        emp.setSalary(resultSet.getDouble("salary"));
        employees.add(emp);
    }

    stmt =con.createStatement(
    ResultSet.TYPE_SCROLL_INSENSITIVE,
    ResultSet.CONCUR_UPDATABLE
        );
        con.close();

} catch(
        ClassNotFoundException e)

        {
        e.printStackTrace();
        }catch(
        SQLException e)

        {
        e.printStackTrace();
        }
        }*/
}
