package controller;
import java.util.ArrayList;
import java.util.List;
import model.*;



public class AgendaController{
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Contato> contatos;
    @SuppressWarnings("FieldMayBeFinal")
    private ContatoDoc contatodoc;
    
    

    public AgendaController() {
        this.contatodoc = new ContatoDoc();
        this.contatos = new ArrayList<>(contatodoc.carregarContatos());
    }


    public List<Contato> buscarPorNome(String nome){
        List<Contato> resultado = new ArrayList<>();
        
        for(Contato contato : contatos){
            if(contato.getNome().equalsIgnoreCase(nome)){
                resultado.add(contato);
            }
        }

        return resultado;
    }

 
    public List<Contato> listarContatos(){
        return List.copyOf(contatos);
    }


    public void adicionarContato(String nome, String telefone){
        Contato contato = new Contato(nome,telefone);
        contatos.add(contato);
        contatodoc.salvarContatos(contatos);

    }
 
    public boolean excluirContato(Contato contato){
        contatos.remove(contato);
        contatodoc.salvarContatos(contatos);
        return true;
    }
                                                                            
    public boolean alterarContato(Contato contato, String novoNome, String novoTelefone) {
        
        if(contato != null){
            contato.setNome(novoNome);
            contato.setTelefone(novoTelefone);
            contatodoc.salvarContatos(contatos);

            return true;
        }

        return false;
    }


}
