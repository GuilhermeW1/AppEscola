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
import tools.CaixaDeDialogo;

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

            String wSql = "select from usuarios where coalesce(excluido, false) = false and login = ? and senha = md5(encode(?::bytea, 'base64'))";
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

    public boolean incluir(Usuario objeto) {
        try {
            Connection con = Conexao.getConnection();
            PreparedStatement stmt = null;

            if (verificarExistencia(objeto) == true) {
                return false;
            } else {
                /*
                String wSQL = "INSERT INTO usuarios values(default, ?, ?, md5(md5(encode(?::bytea, 'base64'))), false, ?)";
                stmt = con.prepareStatement(wSQL);
                stmt.setString(1, objeto.getNome());
                stmt.setString(2, objeto.getLogin());
                stmt.setString(3, objeto.getSenha());
                stmt.setInt(4, objeto.getId_Bairro());
                

                stmt.executeUpdate();
                */
                
                String wSQL = " INSERT INTO usuarios VALUES(DEFAULT, ?, ?, md5(encode(?::bytea, 'base64')), false, ?) ";
                stmt = con.prepareStatement(wSQL);
                stmt.setString(1, objeto.getNome());    
                stmt.setString(2, objeto.getLogin());            
                stmt.setString(3, objeto.getSenha());            
                stmt.setInt(4, objeto.getId_bairros());   

                stmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("erro sql: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return false;
        } 
            
        
    }
    
    
    
    public boolean alterar(Usuario objeto) {
        try {
            Connection con = Conexao.getConnection();
            PreparedStatement stmt = null;

            if (!verificarExistencia(objeto) == true) {
                return false;
            } else {
                
                 String wSQL = " UPDATE usuarios ";
                wSQL += " SET nome = ? ";
                if(!objeto.getSenha().equals("")){ //senha está preenchida?
                    wSQL += " ,senha = md5(encode(?::bytea, 'base64')) ";
                }
                wSQL += " ,id_bairros = ? ";
                wSQL += " WHERE id = ? ";
                
                stmt = con.prepareStatement(wSQL);
                stmt.setString(1, objeto.getNome()); 
                
                if(!objeto.getSenha().equals("")){//senha está preenchida?
                    stmt.setString(2, objeto.getSenha());                   
                    stmt.setInt(3, objeto.getId_bairros());               
                    stmt.setInt(4, objeto.getId()); 
                    System.out.println(" Nome  "+ objeto.getNome());
                    System.out.println(" Login  "+ objeto.getLogin());
                    System.out.println(" Senha  "+ objeto.getSenha());
                    System.out.println(" Id bairro  "+ objeto.getId_bairros());
                    System.out.println(" Id  "+ objeto.getId());
                }else{
                    stmt.setInt(2, objeto.getId_bairros()); 
                    stmt.setInt(3, objeto.getId()); 
                     System.out.println(" Nome  "+ objeto.getNome());
                    System.out.println(" Login  "+ objeto.getLogin());
                    //System.out.println(" Senha  "+ objeto.getSenha());
                    System.out.println(" Id bairro  "+ objeto.getId_bairros());
                    System.out.println(" Id  "+ objeto.getId());
                }
                
                stmt.executeUpdate();
                return true;
            }
                
            
                
                
                
                
                /*
                
                String wSQL = "update usuarios set nome = ?, senha = md5(md5(encode(?::bytea, 'base64'))), id_bairros = ?, where id = ?";
                stmt = con.prepareStatement(wSQL);
                stmt.setString(1, objeto.getNome());
                stmt.setString(2, objeto.getSenha());
                stmt.setInt(3, objeto.getId_bairros());
                stmt.setInt(4, objeto.getId());
                

                stmt.executeUpdate();
                return true;
            
            }
            */
        } catch (SQLException e) {
            System.out.println("erro sql: " + e.getMessage() +" ====== "+ e.getSQLState() + e.getLocalizedMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return false;
        } 
            
        
    }

    private boolean verificarExistencia(Usuario objeto) {
        try {
            Connection con = Conexao.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String sql = "select id from usuarios where login = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, objeto.getLogin());
            rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("ERRO SQL: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        return false;
    }

    /*
    private boolean verificarExistenciById(Usuario objeto){
        try{
            Connection con = Conexao.getConnection();
            PreparedStatement stmt =null;
            ResultSet rs =null;
            String sql = "select from usuarios where id = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, objeto.getId());
            
            
            
            
        }catch(SQLException e){
            System.out.println("ERRO SQL: "+e.getMessage());
        }catch(Exception e){
            System.out.println("ERRO: " + e.getMessage());
        } 
    }
     */
    public boolean excluir(String codigo) {
        try {
            Connection con = Conexao.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String sql = "update usuarios set excluido = true where id = ?";

            stmt = con.prepareStatement(sql);
            //se pa tem que muda pra
            stmt.setInt(1, Integer.parseInt(codigo));
            
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("erro sql: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Erro" + e.getMessage());
            return false;
        }

    }
    
    //////////////////////////////////////////////////////////////////////
    
    public void preencher(JTable jtbUsuarios) {

        Conexao.abreConexao();

        Vector<String> cabecalhos = new Vector<String>();
        Vector dadosTabela = new Vector(); //receber os dados do banco

        cabecalhos.add("Id");
        cabecalhos.add("Nome");
        cabecalhos.add("Exc");

        ResultSet result = null;

        try {

            String wSql = " SELECT id, nome FROM usuarios WHERE COALESCE(excluido,false) = false ORDER BY nome ";

            result = Conexao.stmt.executeQuery(wSql);

            Vector<Object> linha;
            while (result.next()) {
                linha = new Vector<Object>();

                linha.add(result.getInt(1));
                linha.add(result.getString(2));
                linha.add("X");

                dadosTabela.add(linha);
            }

        } catch (Exception e) {
            System.out.println("problemas para popular tabela...");
            System.out.println(e);
        }

        jtbUsuarios.setModel(new DefaultTableModel(dadosTabela, cabecalhos) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            // permite seleção de apenas uma linha da tabela
        });

        // permite seleção de apenas uma linha da tabela
        jtbUsuarios.setSelectionMode(0);

        // redimensiona as colunas de uma tabela
        TableColumn column = null;
        for (int i = 0; i <= 2; i++) {
            column
                    = jtbUsuarios.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(60);
                    break;
                case 1:
                    column.setPreferredWidth(200);
                    break;
                case 3:
                    column.setPreferredWidth(10);
                    break;
            }
        }

        //função para deixar a tabela zebrada
        jtbUsuarios.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected,
                        hasFocus, row, column);
                if (row % 2 == 0) {
                    setBackground(Color.LIGHT_GRAY);
                } else {
                    setBackground(Color.WHITE);
                }

                return this;
            }
        });
        //return (true);
    }
    
     public Usuario buscar(String codigo) {
         Usuario objUsuario = null;
        try {
            Connection con = Conexao.getConnection();
            ResultSet rs = null;
            PreparedStatement smt = null;

            String wSql = "select * from usuarios where id = ?";
            //stm.executeQuery(wSql);

            smt = con.prepareStatement(wSql);
            smt.setInt(1, Integer.parseInt(codigo));
            

            rs = smt.executeQuery();

            
            if(rs.next()){
                objUsuario = new Usuario();
                objUsuario.setId(rs.getInt("id"));
                objUsuario.setNome(rs.getString("nome"));
                objUsuario.setLogin(rs.getString("login"));
                objUsuario.setSenha(rs.getString("senha"));
                objUsuario.setExcluir(rs.getBoolean("excluido"));
                objUsuario.setId_bairros(rs.getInt("id_bairros"));
                
            }
            
            

        } catch (SQLException ex) {
            System.out.println("ERRO de SQL: " + ex.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return null;
        }
        
        return objUsuario;

    }

}
