package model;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContatoDoc {
    private final String nome_arquivo = "agenda.txt";

    public void salvarContatos(List<Contato> contatos){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(nome_arquivo))){
            for(Contato contato : contatos){
                writer.write(contato.toFileString());
                writer.newLine();
            }
        }catch(IOException e){
            System.out.println("Falha ao salvar: "+e.getMessage());
        }
    }

    public List<Contato> carregarContatos(){
        ArrayList<Contato> contatos = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(nome_arquivo))){
            String linha;

            while((linha = reader.readLine()) != null){
                try{
                    contatos.add(Contato.fileToString(linha));
                }catch(IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }catch(FileNotFoundException e){
            System.out.println("Ainda não há arquivos de backup, será feito um ao salvar");
        }catch(IOException e){
            System.out.println("Erro ao carregar dados do arquivo: "+e.getMessage());
        }

        return contatos;
    }
}
