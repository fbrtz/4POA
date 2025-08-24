package controller;
import model.Contato;
import java.util.List;
    
    public interface InterfaceAgenda {
    List<Contato> listarContatos();
    void adicionarContato(String nome, String telefone);
    boolean excluirContato(Contato contato);
    boolean alterarContato(Contato contato, String novoNome, String novoTelefone);
}
