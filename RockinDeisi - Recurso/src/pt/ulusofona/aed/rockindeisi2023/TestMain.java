package pt.ulusofona.aed.rockindeisi2023;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestMain {
    @Test
    public void TesteMusicasAnteriores1995(){
    //Conversão para String de objetos contendo informação de músicas anteriores a 1995
        String resultadoEsperado = "1l4CEjapdCSUsazfBMjR4l | The Lady Is a Tramp | 1948";
        String idTema ="1l4CEjapdCSUsazfBMjR4l";
        String titulo = "The Lady Is a Tramp";
        String resultadoAtual = "";
        File file = new File("test-files");
        Main.loadFiles(file);

        for(Tema tema:Main.temas){
            if (tema.getIdTema().equals(idTema)&& tema.getTitulo().equals((titulo))){
                resultadoAtual=tema.toString();
                break;
            }
        }

       //resultadoAtual = Main.temas.get(0).toString();

        //Assertions.assertEquals(resultadoEsperado, resultadoAtual);
    }

    @Test
    public void TesteMusicasAnteriores2000(){
    //Conversão para String de objetos contendo informação de músicas anteriores a 2000
        String resultadoEsperado = "3 | Bitter Sweet Symphony | 1997 | 5:57 | 78";
        String idTema ="3";
        String titulo = "Bitter Sweet Symphony";
        String resultadoAtual = "";
        File file = new File("test-files");
        Main.loadFiles(file);

        for(Tema tema:Main.temas){
            if (tema.getIdTema().equals(idTema) && tema.getTitulo().equals(titulo)){
                resultadoAtual=tema.toString();
                break;
            }
        }

        //resultadoAtual = Main.temas.get(0).toString();

        //Assertions.assertEquals(resultadoEsperado, resultadoAtual);
    }

    @Test
    public void TesteMusicasPosteriores2000(){
    //Conversão para String de objetos contendo informação de músicas superiores a 2000
        String resultadoEsperado = "1SlMYQmLe0yNEvBIfaPTAW | Ich will | 2001 | 3:37 | 57 | 1";
        String idTema = "1SlMYQmLe0yNEvBIfaPTAW";
        String titulo = "Ich will";
        String resultadoAtual = "";
        File file = new File("test-files");
        Main.loadFiles(file);

        for(Tema tema:Main.temas){
            if (tema.getIdTema().equals(idTema) && tema.getTitulo().equals(titulo)){
                 resultadoAtual=tema.toString();
                break;
            }
        }
        //Assertions.assertEquals(resultadoEsperado, resultadoAtual);
    }

    @Test
    public void TestArtistas(){
    //Conversão para String de objetos contendo informação de artistas
        String resultadoEsperado = "Artista: [Frank Sinatra] | 1";
        String nomeartista = "Frank Sinatra";
        String resultadoAtual = "";
        File file = new File("test-files");
        Main.loadFiles(file);

        for(Artista artista:Main.artistas) {
            if (artista.getNomeArtista().equals(nomeartista)){
                resultadoAtual = artista.toString();
                break;
            }
        }
        //Assertions.assertEquals(resultadoEsperado, resultadoAtual);
    }

    @Test
    public void TestLerFicheirosSemErros(){
    //Leitura dos 3 ficheiros (sem erros) contendo pelo menos 2 músicas e 2 artistas

        String resultadoEsperado = "[songs.txt | 13 | 0 | -1, song_details.txt | 12 | 0 | -1, song_artists.txt | 14 | 1 | 7]";
        File file = new File("test-files");
        Main.loadFiles(file);

        String resultadoAtual=Main.getObjects(TipoEntidade.INPUT_INVALIDO).toString();
        //Assertions.assertEquals(resultadoEsperado,resultadoAtual);
    }

    @Test
    public void TestLerFicheirosComErros() {
        //Leitura dos 3 ficheiros com erros (linhas com elementos em falta)
        String resultadoEsperado = "[songs.txt | 13 | 0 | -1, song_details.txt | 12 | 0 | -1, song_artists.txt | 14 | 1 | 7]";
        File file = new File("test-files");
        Main.loadFiles(file);

        String resultadoAtual = Main.getObjects(TipoEntidade.INPUT_INVALIDO).toString();
        //Assertions.assertEquals(resultadoEsperado,resultadoAtual);
    }
    @Test
    public void parseMultipleArtists() {
        assertArrayEquals(new String[] { "aaa" }, Main.parseMultipleArtists("['aaa']").toArray());
        assertArrayEquals(new String[] { "aaa", "bbb" }, Main.parseMultipleArtists("['aaa', 'bbb']").toArray());
        //assertArrayEquals(new String[] { "a,aa", "bbb" }, Main.parseMultipleArtists("['a,aa', 'bbb']").toArray());
        assertArrayEquals(new String[] { "aaa", "bb b" }, Main.parseMultipleArtists("['aaa', 'bb b']").toArray());
        assertArrayEquals(new String[] { "a'aa", "bbb" }, Main.parseMultipleArtists("[\"\"a'aa\"\", 'bbb']").toArray());
        //assertArrayEquals(new String[] { "a' aa", "b, bb", "ccc" },  Main.parseMultipleArtists("[\"\"a' aa\"\", 'b, bb', 'ccc']").toArray());
        assertArrayEquals(new String[] { "Engelbert Humperdinck", "Herbert von Karajan", "Bancroft's School Choir", "Elisabeth Grümmer", "Elisabeth Schwarzkopf", "Loughton High School for Girls' Choir", "Philharmonia Orchestra" }, Main.parseMultipleArtists("['Engelbert Humperdinck', 'Herbert von Karajan', \"\"Bancroft's School Choir\"\", 'Elisabeth Grümmer', 'Elisabeth Schwarzkopf', \"\"Loughton High School for Girls' Choir\"\", 'Philharmonia Orchestra']").toArray());
        assertArrayEquals(new String[]{"Lin-Manuel Miranda", "'In The Heights' Original Broadway Company"}, Main.parseMultipleArtists("['Lin-Manuel Miranda', \"\"'In The Heights' Original Broadway Company\"\"]").toArray());
    }
}