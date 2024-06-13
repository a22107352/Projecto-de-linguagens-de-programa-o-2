Humano:
Como criar em Java um ArrayList com os seguintes campos:
    String nomeArtista;
    int numMusicas;
Obtendo a informação do ArrayList TemaArtista com o formato:
    String idTema;
    String artista;
No nomeArtista coloca-se o artista do Array TemaArtista e no numMusicas o numero de idTema de um artista no ArrayList TemaArtista que existe no Arry List Tema.
O mesmo artista só pode existir uma vez e só será considerado se o idTema existir no ArrayList Tema.

--------------------------

Em Java, você pode criar uma classe que represente o objeto que será armazenado em seu ArrayList, com os campos "nomeArtista" e "numMusicas". 
Para obter a informação do ArrayList TemaArtista e criar o ArrayList desejado, você pode iterar sobre os elementos do ArrayList TemaArtista, verificar se o artista já foi adicionado ao ArrayList desejado e contar quantas vezes o idTema aparece no ArrayList TemaArtista para o artista atual. 
Aqui está um exemplo de código para fazer isso:

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Artista {
    private String nomeArtista;
    private int numMusicas;

    public Artista(String nomeArtista, int numMusicas) {
        this.nomeArtista = nomeArtista;
        this.numMusicas = numMusicas;
    }

    public String getNomeArtista() {
        return nomeArtista;
    }

    public void setNomeArtista(String nomeArtista) {
        this.nomeArtista = nomeArtista;
    }

    public int getNumMusicas() {
        return numMusicas;
    }

    public void setNumMusicas(int numMusicas) {
        this.numMusicas = numMusicas;
    }
}

public class Main {
    public static void main(String[] args) {
        List<String[]> temaArtista = new ArrayList<>();
        temaArtista.add(new String[]{"1", "Artista1"});
        temaArtista.add(new String[]{"2", "Artista1"});
        temaArtista.add(new String[]{"3", "Artista2"});
        temaArtista.add(new String[]{"4", "Artista2"});
        temaArtista.add(new String[]{"5", "Artista2"});

        Map<String, Integer> artistaNumMusicas = new HashMap<>();
        for (String[] tema : temaArtista) {
            String idTema = tema[0];
            String artista = tema[1];
            if (!artistaNumMusicas.containsKey(artista)) {
                artistaNumMusicas.put(artista, 1);
            } else {
                int numMusicas = artistaNumMusicas.get(artista);
                artistaNumMusicas.put(artista, numMusicas + 1);
            }
        }

        List<Artista> artistas = new ArrayList<>();
        for (String artista : artistaNumMusicas.keySet()) {
            int numMusicas = artistaNumMusicas.get(artista);
            artistas.add(new Artista(artista, numMusicas));
        }

        for (Artista artista : artistas) {
            System.out.println(artista.getNomeArtista() + " - " + artista.getNumMusicas());
        }
    }
}

Nesse exemplo, o ArrayList TemaArtista é representado por uma lista de arrays de String. O primeiro elemento do array é o idTema e o segundo é o artista. 
O código cria um HashMap para armazenar o número de músicas de cada artista, contando quantas vezes o artista aparece na lista TemaArtista. Em seguida, ele cria uma lista de objetos Artista, utilizando o HashMap criado anteriormente para preencher o campo "numMusicas". 
Por fim, o código itera sobre a lista de artistas e imprime o nome e o número de músicas de cada um.

---------------------------------

Humano:
Reformula sem utilizar o HashMap

--------------------------------

Certo, aqui está uma versão do código sem usar o HashMap:

import java.util.ArrayList;
import java.util.List;

public class Artista {
    private String nomeArtista;
    private int numMusicas;

    public Artista(String nomeArtista, int numMusicas) {
        this.nomeArtista = nomeArtista;
        this.numMusicas = numMusicas;
    }

    public String getNomeArtista() {
        return nomeArtista;
    }

    public void setNomeArtista(String nomeArtista) {
        this.nomeArtista = nomeArtista;
    }

    public int getNumMusicas() {
        return numMusicas;
    }

    public void setNumMusicas(int numMusicas) {
        this.numMusicas = numMusicas;
    }
}

public class Main {
    public static void main(String[] args) {
        List<String[]> temaArtista = new ArrayList<>();
        temaArtista.add(new String[]{"1", "Artista1"});
        temaArtista.add(new String[]{"2", "Artista1"});
        temaArtista.add(new String[]{"3", "Artista2"});
        temaArtista.add(new String[]{"4", "Artista2"});
        temaArtista.add(new String[]{"5", "Artista2"});

        List<Artista> artistas = new ArrayList<>();
        for (String[] tema : temaArtista) {
            String idTema = tema[0];
            String artista = tema[1];
            boolean artistaEncontrado = false;
            for (Artista a : artistas) {
                if (a.getNomeArtista().equals(artista)) {
                    artistaEncontrado = true;
                    a.setNumMusicas(a.getNumMusicas() + 1);
                    break;
                }
            }
            if (!artistaEncontrado) {
                artistas.add(new Artista(artista, 1));
            }
        }

        for (Artista artista : artistas) {
            System.out.println(artista.getNomeArtista() + " - " + artista.getNumMusicas());
        }
    }
}

Nesta versão, o código ainda utiliza a classe Artista definida anteriormente para representar os objetos a serem armazenados no ArrayList. A diferença é que agora, em vez de usar um HashMap para contar o número de músicas de cada artista, 
o código percorre a lista de artistas já adicionados ao ArrayList e verifica se o artista atual já está presente. Se sim, incrementa o número de músicas; se não, adiciona um novo objeto Artista com o número de músicas igual a 1.
Por fim, o código itera sobre a lista de artistas e imprime o nome e o número de músicas de cada um.


-----------------------------

CONCLUSÃO DO GRUPO: 

Apesar de não termos uma sollução que seja exatamente o pretendido, serviu como guia para a nossa implementação.
