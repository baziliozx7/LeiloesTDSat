

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.sql.SQLException;
import java.util.List;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> age = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        
         String sql = "INSERT INTO produtos ( nome, valor, status) VALUES (?, ?, ?)";

        try {
            conn = new conectaDAO().connectDB(); 
            prep = conn.prepareStatement(sql);
            
          
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate(); 
            prep.executeQuery();

            prep.close(); 
            conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    
    
    public static List<ProdutosDTO> listarTodos(){
        
         List<ProdutosDTO> pro = new ArrayList();
        
        try{
            conectaDAO conexao = new conectaDAO();
            conexao.connectDB(); 
            
            String sql = "SELECT id, nome, valor, status FROM produtos";
            
            
            PreparedStatement ps = conexao.connectDB().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
            
               ProdutosDTO prod = new ProdutosDTO();
               
               prod.setId(rs.getInt("id"));
               prod.setNome(rs.getString("Nome"));
               prod.setValor(rs.getInt("valor"));
               prod.setStatus(rs.getString("status"));
                 
              
               
                
                
                pro.add(prod);
            }
            
         }catch (SQLException se) {
             System.out.println("erro ao listar o agendamento: " + se.getMessage());
         }
       
     return pro;
    }
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos(){
        ArrayList<ProdutosDTO> lista = new ArrayList<>();
    String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";

    try (Connection conn = new conectaDAO().connectDB();
         PreparedStatement pstm = conn.prepareStatement(sql);
         ResultSet rs = pstm.executeQuery()) {

        while (rs.next()) {
            ProdutosDTO obj = new ProdutosDTO();
            obj.setId(rs.getInt("id"));
            obj.setNome(rs.getString("nome"));
            obj.setValor(rs.getInt("valor"));
            obj.setStatus(rs.getString("status"));
            lista.add(obj);
        }

    } catch (SQLException erro) {
        JOptionPane.showMessageDialog(null, "Erro ao listar vendidos: " + erro.getMessage());
    }

    return lista;
    }
    
    public void venderProduto(int id) {
    String sql = "UPDATE produtos SET status = ? WHERE id = ?";

    try {
        conn = new conectaDAO().connectDB(); 
        prep = conn.prepareStatement(sql);

        prep.setString(1, "Vendido");
        prep.setInt(2, id);

        int linhasAfetadas = prep.executeUpdate();

        if (linhasAfetadas > 0) {
            JOptionPane.showMessageDialog(null, "Produto marcado como vendido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Produto com ID " + id + " não encontrado.");
        }

        prep.close();
        conn.close();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
    }
}
}    

    
        


