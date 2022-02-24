/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whatsappserver;

//package com.journaldev.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author space
 */
public class Reader {
    static final String FILE_PATH = "src\\whatsappserver\\db.xml";
    static Document doc;
    static Element root;
    
    static void closeXML() throws FileNotFoundException, IOException{
        File file = new File(FILE_PATH);
        XMLOutputter xout = new XMLOutputter();
        OutputStream out = new FileOutputStream(file);
        xout.output(doc,out);
    }
    
    public static boolean updateIP(String name, String adress) throws JDOMException, IOException {
        File file = new File(FILE_PATH); 
        if (!file.exists()){
            doc = new Document();
            root = new Element("Users");
            doc.setRootElement(root);
            closeXML();
        }
        SAXBuilder builder = new SAXBuilder();
        
        doc = builder.build(file);
        root = (Element) doc.getRootElement();
        
        List<Element> ul = root.getChildren(name);
        if (ul.size() > 0) {
            ul.get(0).setText(adress);
            closeXML();
            return true;
        }
        return false;

    }
    
    public static boolean registerIP(String name, String adress) throws JDOMException, IOException {
        File file = new File(FILE_PATH); 
        if (!file.exists()){
            doc = new Document();
            root = new Element("Users");
            doc.setRootElement(root);
            closeXML();
        }
        SAXBuilder builder = new SAXBuilder();
        
        doc = builder.build(file);
        root = (Element) doc.getRootElement();
        
        List<Element> ul = root.getChildren(name);
        if (ul.size() == 0) {
            Element user = new Element(name);
            user.setText(adress);
            root.addContent(user);
            closeXML();
            return true;
        }
        return false;

    }
    
    public static String getAdress(String name) throws IOException, JDOMException{
        File file = new File(FILE_PATH); 
        SAXBuilder builder = new SAXBuilder();
        
        doc = builder.build(file);
        root = (Element) doc.getRootElement();
        
        return root.getChildText(name);
    }
}
