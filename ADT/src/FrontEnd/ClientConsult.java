/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontEnd;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.UIManager;
import org.apache.commons.dbcp.*;
import org.apache.commons.pool.*;

/**
 *
 * @author Pepe
 */
public class ClientConsult extends javax.swing.JFrame {

    String config[] = {"jdbc:mysql://undercode.com.ar:3306/proyectoADT", "jvidelaolmos", "MoTorp21co"};
    String titulos[] = {"id-cliente", "Fecha de ingreso", "Nombre", "Apellido", "Telefono", "E-mail", "Region", "Estado", "Vendedor"};
    String fila[] = new String[9];
    Statement stmt = null;
    DefaultTableModel modelo;
    BasicDataSource bds = new BasicDataSource();

    public ClientConsult(BasicDataSource bds) {
        initComponents();

        this.setTitle("ADT Alpha");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        try {
            Connection con = bds.getConnection();
            if (con != null) {
                System.out.println("Se ha establecido una conexion a la base de datos" + "\n" + config[0]);
            }
            stmt = con.createStatement();
            Statement st = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT COUNT(*) FROM Solicitud");
            resultSet.next();

            ResultSet rs = stmt.executeQuery("select* from Solicitud");
            modelo = new DefaultTableModel(null, titulos);
            while (rs.next()) {

                fila[0] = rs.getString("idSolicitud");
                fila[1] = rs.getString("fechaIngreso");
                fila[2] = rs.getString("nombre");
                fila[3] = rs.getString("apellido");
                fila[4] = rs.getString("telefono");
                fila[5] = rs.getString("email");
                fila[6] = rs.getString("region");
//              Query del ultimo estado de la solicitud, si no existe query para ese id es dato nuevo
                ResultSet nrs = st.executeQuery("SELECT* from Estado WHERE Solicitud_idSolicitud='" + rs.getString("idSolicitud") + "' ORDER BY fechaCambio DESC LIMIT 1");
                if (!nrs.next()) {
                    fila[7] = "Dato nuevo";
                } else {
                    fila[7] = nrs.getString("nombreEstado");
                }
                fila[8] = rs.getString("Usuario_idUsuario");

                modelo.addRow(fila);
            }
            table.setModel(modelo);

            table.setModel(modelo);
            TableColumn ci = table.getColumn("id-cliente");
            ci.setMaxWidth(35);
            TableColumn cf = table.getColumn("Fecha de ingreso");
            cf.setMaxWidth(200);
            TableColumn cn = table.getColumn("Nombre");
            cn.setMaxWidth(150);
            TableColumn ca = table.getColumn("Apellido");
            ca.setMaxWidth(210);
            TableColumn ct = table.getColumn("Telefono");
            ct.setMaxWidth(210);
            TableColumn cem = table.getColumn("E-mail");
            cem.setMaxWidth(210);
            TableColumn cr = table.getColumn("Region");
            cr.setMaxWidth(210);
            TableColumn cst = table.getColumn("Estado");
            cst.setMaxWidth(120);
            TableColumn csl = table.getColumn("Vendedor");
            csl.setMaxWidth(120);
            resultSet.close();
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al extraer los datos de la tabla");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addGap(44, 44, 44))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
