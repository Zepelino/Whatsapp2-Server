/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whatsappserver;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Color;
import javax.swing.UnsupportedLookAndFeelException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.swing.border.*;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import org.jdom2.JDOMException;

/**
 *
 * @author space
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    
    public class Servidor extends Thread {
        private static Hashtable<String, BufferedWriter> clientes;
        private static ServerSocket server;
        private String nome;
        private Socket con;
        private InputStream in;
        private InputStreamReader inr;
        private BufferedReader bfr;
        
        public Servidor(Socket con){
            this.con = con;
            try {
                in  = con.getInputStream();
                inr = new InputStreamReader(in);
                bfr = new BufferedReader(inr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        public void run(){
            try{      
                String msg;
                OutputStream ou =  this.con.getOutputStream();
                Writer ouw = new OutputStreamWriter(ou);
                BufferedWriter bfw = new BufferedWriter(ouw);
                clientes.put(con.getRemoteSocketAddress().toString().split("/")[1], bfw);
                msg = bfr.readLine();
                nome = msg.split("#")[0];
                boolean r;
                String erro_msg;
                if (Boolean.valueOf(msg.split("#")[1])) { 
                    r = Reader.updateIP(nome, con.getRemoteSocketAddress().toString().split("/")[1]);
                    erro_msg = "USUARIO_NAO_ENCONTRADO";
                } else {
                    r = Reader.registerIP(nome, con.getRemoteSocketAddress().toString().split("/")[1]);
                    erro_msg = "USUARIO_JA_REGISTRADO";
                }
                if (!r){
                    bfw.write(erro_msg+"\r\n");
                    bfw.flush();
//                    bfw.write(""+"\r\n");
//                    bfw.flush();
                    appendToPane(jTextPane1, "[SERVER] Erro de reistro com " + con.getRemoteSocketAddress().toString().split("/")[1], Color.RED);
                    appendToPane(jTextPane1, "[SERVER] " + con.getRemoteSocketAddress().toString().split("/")[1] + " desconectado\n", Color.RED);
                    //return;
                } else {
                    bfw.write("SUCCESSFULLY_LOGGED"+"\r\n");
                    bfw.flush();
                }
                
                while(!"CLOSE_SERVER".equals(msg) && msg != null){
//                    System.out.println(Thread.activeCount());
                    String b = bfr.readLine();
                    if (b != null){
                        String[] a = b.split("<lili@hotmail.com>");
                        if (a.length > 1){
                            if (a[1].equals("USER_EXISTS?")){
                                boolean user_exist = Reader.getAdress(a[0]) != null;
                                System.out.println(user_exist);
                                if (user_exist) {
                                    bfw.write("USER_EXISTS"+"\r\n");
                                } else {
                                    bfw.write("USER_DONT_EXISTS"+"\r\n");
                                }
                                bfw.flush();
                                continue;
                            }

                            String addr = Reader.getAdress(a[0]);
                            msg = a[1];
                            sendMsg(nome + "<lili@hotmail.com>" + msg, addr, bfw, a[0]);
                            System.out.println(nome + ": " + msg);
                        } 
                        
                    }                    
                    
                }

            } catch (IOException | JDOMException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        public void sendMsg(String msg, String dest, BufferedWriter sender, String dest_name) throws  IOException {
            BufferedWriter bw = Servidor.clientes.get(dest);
            if (bw == null){
                sender.write(dest_name + "<lili@hotmail.com>Was not possible to send the message"+"\r\n");
                sender.flush();
            }else{
                bw.write(msg+"\r\n");
                bw.flush();
            }             

        }
        
    }
    
    public Main() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        StopBtn = new javax.swing.JButton();
        StartBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("IP:  ");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("PORT:");

        StopBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        StopBtn.setText("STOP");
        StopBtn.setEnabled(false);
        StopBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopBtnActionPerformed(evt);
            }
        });

        StartBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        StartBtn.setText("START");
        StartBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartBtnActionPerformed(evt);
            }
        });

        jTextPane1.setEditable(false);
        jTextPane1.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(jTextPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                        .addGap(90, 90, 90)
                        .addComponent(StartBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(StopBtn)
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(StartBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .addComponent(StopBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public class SearchForConection extends Thread {
        Socket con;
        public void run(){
           while(true){
                try {
                   appendToPane(jTextPane1, "[SERVER] Aguardando conexão...\n", Color.GREEN);
                   con = Servidor.server.accept();
                   System.out.println("BEFORE: "+Thread.activeCount());
                   appendToPane(jTextPane1, "[SERVER] " + con.getRemoteSocketAddress().toString().split("/")[1] + " conectado", Color.GREEN);
                   Thread t = new Servidor(con);
                   t.start();
                   System.out.println("AFTER: " + Thread.activeCount());
                } catch (IOException ex) {
                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    SearchForConection conectionSearch = new SearchForConection();
    private void StartBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartBtnActionPerformed
        StopBtn.setEnabled(true);
        StartBtn.setEnabled(false);
        try{
            //Cria os objetos necessário para instânciar o servidor
            Servidor.server = new ServerSocket(5050);
            Servidor.clientes = new Hashtable<String, BufferedWriter>();
            appendToPane(jTextPane1, "[SERVER] Servidor ativo na porta: 5050", Color.GREEN);
            jLabel1.setText("IP: " + InetAddress.getLocalHost().getHostAddress());
            jLabel2.setText("PORT: " + Servidor.server.getLocalPort());
            
            conectionSearch.start();
        }catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_StartBtnActionPerformed

    private void appendToPane(JTextPane tp, String msg, Color c){
        tp.setEditable(true);
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        //aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg + "\n");
        tp.setEditable(false);
    }
    
    private void StopBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopBtnActionPerformed
        StopBtn.setEnabled(false);
        StartBtn.setEnabled(true);
        try {
            conectionSearch.interrupt();
            Servidor.server.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        appendToPane(jTextPane1, "[SERVER] Servidor Fechado", Color.GREEN);    
        this.dispose();
        
    }//GEN-LAST:event_StopBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            javax.swing.UIManager.setLookAndFeel( new FlatDarkLaf() );
        } catch( UnsupportedLookAndFeelException ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton StartBtn;
    private javax.swing.JButton StopBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
