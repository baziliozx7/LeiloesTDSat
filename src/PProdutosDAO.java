

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.sql.SQLException;
import java.util.List;

public class PProdutosDAO {
    
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
    public static List<ProdutosDTO> listarProdutosVendidos() {
    List<ProdutosDTO> vendidos = new ArrayList<>();

    try {
        conectaDAO conexao = new conectaDAO();
        Connection conn = conexao.connectDB(); // Evite chamar duas vezes

        String sql = "SELECT id, nome, valor, status FROM produtos WHERE status = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "Vendido");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setValor(rs.getInt("valor"));
            produto.setStatus(rs.getString("status"));

            vendidos.add(produto);
        }

        rs.close();
        ps.close();
        conn.close();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getMessage());
    }

    return vendidos;
}
}

     
