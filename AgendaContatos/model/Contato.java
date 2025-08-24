package model;

public class Contato {
    private String nome;
    private String telefone;

    public Contato(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    @Override
    public String toString() {
        return ("Nome: " + nome + "| Telefone: " + telefone);
    }

    public String toFileString() {
        return nome + ";" + telefone;
    }
    
    public static Contato fileToString (String line) throws Exception,IllegalArgumentException{
        String[] dados = line.split(";");

        if(dados.length != 2){

            throw new IllegalArgumentException("Linha de dados inválida para criação de Contato: " + line);
        }

        try{
            String nome = dados[0];
            String telefone = dados[1];


            return new Contato(nome,telefone);
        } catch (Exception e) {
            throw new Exception("Erro ao criar contato", e);
        }
        
    }
}