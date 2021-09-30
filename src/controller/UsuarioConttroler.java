/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.Conexao;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import model.Usuario;

/**
 *
 * @author guilherme.w1
 */
public class UsuarioConttroler {

    //Usuario objUsuario;
    //JTable jtbUsuarios = null;
    //public UsuarioDAO(Usuario objUsuario, JTable jtbUsuarios) {
    //    this.objUsuario = objUsuario;
    //    this.jtbUsuarios = jtbUsuarios;
    //}
    public boolean login(String user, String pass) {
        try {
            Connection con = Conexao.getConnection();
            ResultSet rs = null;
            PreparedStatement smt = null;
            
            
            String wSql = "select from usuarios where login = ? and senha = md5(encode(?::bytea, 'base64'))";
            //stm.executeQuery(wSql);
            
            smt = con.prepareStatement(wSql);
            smt.setString(1, user);
            smt.setString(2, pass);
            
            rs = smt.executeQuery();

            //objUsuario = new Usuario();
            return rs.next();

        } catch (SQLException ex) {
            System.out.println("ERRO de SQL: " + ex.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return false;
        }

    }
    
    public boolean incluir(Usuario objeto){
        try{
            Connection con = Conexao.getConnection();
            PreparedStatement stmt = null;
            
            if(verificarExistencia(objeto) == true){
                return false;
            }else{
            
            String wSQL = "INSERT INTO usuarios values(default, ?, ?, md5(md5(encode(?::bytea, 'base64'))))";
            stmt = con.prepareStatement(wSQL);
            stmt.setString(1, objeto.getNome());
            stmt.setString(2, objeto.getLogin());
            stmt.setString(3, objeto.getSenha());
            
            
            stmt.executeUpdate();
        }  
        }catch(SQLException e){
            System.out.println("erro sql: "+e.getMessage() );
            return false;
        }catch(Exception e){
            System.out.println("Erro " +e.getMessage());
            return false;
        }finally{
            return true;
        }
    }
    
    private boolean verificarExistencia(Usuario objeto){
        try{
            Connection con = Conexao.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String sql = "select id from usuarios where login = ?" ;
            stmt = con.prepareStatement(sql);
            stmt.setString(1, objeto.getLogin());
            rs = stmt.executeQuery();
            if(rs.next()){
                return true;
            }
            return false;
        }catch(SQLException e){
            System.out.println("ERRO SQL: "+e.getMessage());
        }catch(Exception e){
            System.out.println("ERRO: " + e.getMessage());
        } 
        return false;
    }
    
    public void excluir(int id){
        try{
            Connection con = Conexao.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String sql = "delete";
                    
            
        
        
        }catch(SQLException e){
            System.out.println("erro sql: "+e.getMessage());
        }catch(Exception e ){
            System.out.println("Erro" +e.getMessage());
        }
        
    }
    

}
