package pt.ulusofona.aed.rockindeisi2023;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static ArrayList<Tema> temas;
    static ArrayList<Ficheiro> ficheiros;
    static ArrayList<Artista> artistas;
    static ArrayList<TemaArtista> temasArtistas;

    static class TemaDetalhe {
        String idTema;
        int duracao;
        int letra;
        int popularidade;
        Double dancabilidade;
        Double vivacidade;
        Double volume;

        public TemaDetalhe(String idTema, int duracao, int letra, int popularidade, Double dancabilidade,
                           Double vivacidade, Double volume) {
            this.idTema = idTema;
            this.duracao = duracao;
            this.letra = letra;
            this.popularidade = popularidade;
            this.dancabilidade = dancabilidade;
            this.vivacidade = vivacidade;
            this.volume = volume;
        }

        public String getIdTema() {
            return idTema;
        }

    }

    static class TemaArtista {
        String idTema;
        String artista;

        public TemaArtista(String idTema, String artista) {
            this.idTema = idTema;
            this.artista = artista;
        }

        public String getIdTema() { return idTema;}
        public String getNome() { return artista;}

    }

    static boolean loadFiles(File folder) {

        File file_Songs = new File(folder,"songs.txt");
        File fileSongs_artists = new File(folder,"song_artists.txt");
        File fileSongs_details = new File(folder,"song_details.txt");

        temasArtistas = new ArrayList<>();
        ficheiros = new ArrayList<>();

        Scanner scanner; //= null;
        //Executar pela ordem: 1º Song_artists, 2º Song_details, 3º Songs

        /*----------------------- Song_Artists -> TemaArtista -----------------------*/
        try{
            scanner = new Scanner(fileSongs_artists);
        } catch (FileNotFoundException e){
            return false;
        }
        artistas = new ArrayList<>();

        boolean flagDuplicado, flagTemDetalhe, flagTemArtista, flagArtistaExistente, flagTemaExistente; //variavél para controlar se o item já existe, se temdetalhes e se tem artistas
        int linhasOK = 0, linhasNOK = 0, primeiralinhaNOK = -1, numLinhas = 0;
        int contaArtistas;
        while (scanner.hasNext()){
            String linha =  scanner.nextLine();
            numLinhas++;
            linha = linha.trim();

            String[] partes = linha.split(" @ ");
            if (partes.length == 2) { //Tem de ter 2 partes
                String idTema = partes[0].trim();
                //String[] nomeArtista = partes[1].trim().replaceAll("[\\[\\]'\"]", "").split(","); // extrai os nomes dos artistas;
                ArrayList<String> nomeArtista = parseMultipleArtists(linha);
                flagDuplicado = false;

                for (String nome : nomeArtista) {// Percorre o array com os nomes dos artista para verificar se são válidos
                    nome = nome.trim();

                    for (TemaArtista temaArtista : temasArtistas) {// Verifica se no Array temaArtista o nome atual já está associado ao idTema, se sim flagDuplicado e saí
                        if (temaArtista.getIdTema().equals(idTema) && temaArtista.getNome().equals(nome)) {
                            flagDuplicado = true;

                            break;
                        }
                    }
                    if (!flagDuplicado) { //Se o nome não está associado ao idTema, cria novo temaArtista.
                        TemaArtista temaArtista = new TemaArtista(idTema, nome);
                        temasArtistas.add(temaArtista);
                    }
                }
                linhasOK++; //Mesmo que seja duplicado contabiliza linha OK
            }
            else{ // Se não tiver 2 partes ignora e contabiliza como linha NOK
                linhasNOK ++;
                if (primeiralinhaNOK ==-1){primeiralinhaNOK = numLinhas;}
            }
        }

        Ficheiro ficheiro = new Ficheiro("songs.txt", linhasOK, linhasNOK, primeiralinhaNOK);
        ficheiros.add(ficheiro);

        scanner.close();

        /*----------------------- song_details.txt -> TemaDetalhe -----------------------*/

        ArrayList<TemaDetalhe> temasDetalhes = new ArrayList<>();

        try{
            scanner = new Scanner(fileSongs_details);
        } catch (FileNotFoundException e){
            return false;
        }
        linhasOK = 0; linhasNOK = 0; primeiralinhaNOK = -1; numLinhas = 0;
        while (scanner.hasNext()){
            String linha =  scanner.nextLine();
            numLinhas++;
            String[] partes = linha.split(" @ ");

            if (partes.length == 7) { //Tem de ter 7 partes
                String idTema = partes[0].trim();
                int duracao = Integer.parseInt(partes[1]);
                int letra = Integer.parseInt(partes[2]);
                int popularidade = Integer.parseInt(partes[3]);
                Double dancabilidade = Double.parseDouble(partes[4]);
                Double vivacidade = Double.parseDouble(partes[5]);
                Double volume = Double.parseDouble(partes[6]);

                TemaDetalhe temaDetalhe = new TemaDetalhe(idTema,duracao,letra,popularidade,dancabilidade,vivacidade,volume);
                temasDetalhes.add(temaDetalhe);

                linhasOK++;
            }
            else{ // Se não tiver 7 partes ignora e contabiliza como linha NOK
                linhasNOK ++;
                if (primeiralinhaNOK ==-1){primeiralinhaNOK = numLinhas;}
            }
        }

        ficheiro = new Ficheiro("song_details.txt", linhasOK, linhasNOK, primeiralinhaNOK);
        ficheiros.add(ficheiro);

        scanner.close();

        /*----------------------- SONGS -> Tema -----------------------*/
        temas = new ArrayList<>();

        try{
            scanner = new Scanner(file_Songs);
        } catch (FileNotFoundException e){
            return false;
        }
        linhasOK = 0; linhasNOK = 0; primeiralinhaNOK = -1; numLinhas = 0;
        while (scanner.hasNext()){
            String linha =  scanner.nextLine();
            numLinhas++;
            flagDuplicado=false;
            String[] partes = linha.split(" @ ");
            if (partes.length == 3) {  //Tem de ter 3 partes
                String id = partes[0].trim();
                String nome = partes[1].trim();
                int anoLancamento = Integer.parseInt(partes[2]);

                //verificar se o tema já existe ignorar mas não contabilizar como NOK
                for (Tema tema : temas) {
                    if (tema.getIdTema().equals(id)) {
                        flagDuplicado = true;
                        break;
                    }
                }
                if (!flagDuplicado) { //Se não existe o Tema
                    ArrayList<Detalhe> detalhes = new ArrayList<>();
                    //verificar se tem detalhes associados, se não tiver ignorar mas não contabilizar NOK
                    flagTemDetalhe = false;

                    for (TemaDetalhe temaDetalhe : temasDetalhes) {
                        if (temaDetalhe.getIdTema().equals(id)) { //Encontra tema no ArrayDetalhes
                            flagTemDetalhe = true;
                            detalhes.add(new Detalhe(
                                    temaDetalhe.duracao,temaDetalhe.letra,temaDetalhe.popularidade,
                                    temaDetalhe.dancabilidade,temaDetalhe.vivacidade,temaDetalhe.volume,
                                    millisecondsToMinutesSeconds(temaDetalhe.duracao).trim()));
                            break;
                        }
                    }
                    if (flagTemDetalhe) { //TemDetalhe
                        flagTemArtista = false; contaArtistas = 0;
                        for (TemaArtista temaArtista : temasArtistas) { //Verificar se tem Artistas e Conta
                            if (temaArtista.getIdTema().equals(id)) {
                                flagTemArtista = true;
                                contaArtistas++;
                            }
                        }
                        if (flagTemArtista) { //Tem artista cria Tema
                            Tema tema = new Tema(id, nome, anoLancamento, contaArtistas, detalhes);
                            temas.add(tema);
                        }
                    }
                }
                linhasOK++;
            }
            else{
                linhasNOK ++;
                if (primeiralinhaNOK ==-1){primeiralinhaNOK = numLinhas;}
            }
        }
        ficheiro = new Ficheiro("song_artists.txt", linhasOK, linhasNOK, primeiralinhaNOK);
        ficheiros.add(ficheiro);

        scanner.close();
        //Depois de ler todos os ficheiros, verificar se o Artista existe, se não criar elemento no array Artista
        for (TemaArtista temaArtista : temasArtistas) {
            flagTemaExistente = false;
            flagArtistaExistente = false;
            for(Tema t :temas) {
                if(t.getIdTema().equals(temaArtista.idTema)){
                    flagTemaExistente = true;
                    for (Artista a : artistas) {
                        if (a.getNomeArtista().equals(temaArtista.artista)) {
                            a.setNumMusicas(a.getNumMusicas() + 1); // incrementa o número de músicas do artista se o idTema existir no ArrayList Tema
                            flagArtistaExistente = true;
                            break;
                        }
                    }
                }
            }
            if (!flagArtistaExistente && flagTemaExistente) {
                artistas.add(new Artista(temaArtista.artista, 1));
            }
        }
        return true;
    }


    public static ArrayList getObjects(TipoEntidade tipo) {
        ArrayList result = null;
        if (tipo == TipoEntidade.TEMA) {
            result = temas;
        } else if (tipo == TipoEntidade.ARTISTA) {
            result = artistas;
        } else if (tipo == TipoEntidade.INPUT_INVALIDO) {
            result = ficheiros;
        }
        return result;
    }

    public static String millisecondsToMinutesSeconds(long milliseconds) {
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000*60)) % 60);
        return String.format("%2d:%02d", minutes, seconds);

    }


//    static Query parseCommand(String command) {
//        String[] parts = command.split(" ",2);
//        if (parts.length < 2) {
//            return null; // Invalid command
//        }
//
//        String name = parts[0];
//        String[] args = new String[parts.length - 1];
//        System.arraycopy(parts, 1, args, 0, parts.length - 1);
//
//        return new Query(name, args);
//    }
static Query parseCommand(String command) {
    String[] parts = command.split(" ", 2);
    if (parts.length < 2) {
        return null; // Invalid command
    }

    String name = parts[0];
    String[] args = parts[1].split(" ");

    return new Query(name, args);
}

static ArrayList<String> parseMultipleArtists(String line) {
    ArrayList<String> parsedArtists = new ArrayList<>();

    line = line.trim();
    line = line.replaceAll("\"\"", "\""); // Substitui aspas duplas por aspas

    int startIndex = line.indexOf("[");
    int endIndex = line.lastIndexOf("]");

    if (startIndex != -1 && endIndex != -1) {
        String artistsStr = line.substring(startIndex + 1, endIndex);

        Pattern pattern = Pattern.compile("'((?<!\\w),|[^'])*'|\"([^\"]+)\"" );
        Matcher matcher = pattern.matcher(artistsStr);

        while (matcher.find()) {
            String artist = matcher.group().trim();

            artist = artist.replaceAll("^['\"]+|['\"]+$", ""); // Remove aspas do início e do fim
            parsedArtists.add(artist);
        }
    } else {
        parsedArtists.add(line);
    }

    return parsedArtists;
}







    /* GET_ARTISTS_WITH_MIN_DURATION
    Parametros : X - ano, N - duração (em segundos)
    Descrição:
    Retorna os nomes dos artistas que participaram em músicas no ano X cuja duração seja maior do que N.
    O resultado deve ser uma String contendo várias linhas separadas por \n e  respeitando a seguinte sintaxe:
            "<Nome artista> | <Nome música> | <Duração em milisegundos>\n"
    A ordem com que aparecem os artistas é indiferente.
    Caso não hajam resultados (por não existirem músicas para aquele artista ou por o artista não existir), deve retornar: “No artists” */

    public static String getArtistsWithMinDuration(int year, int duration) {
        String result = "";
        for (Tema tema : temas) {
            if (tema.getAno() == year) {
                for (Detalhe detalhe : tema.getDetalhes()) {
                    if (detalhe.getDuracao() > duration) {
                        for (TemaArtista temaArtista : temasArtistas) {
                            if (temaArtista.idTema.equals(tema.getIdTema())) {
                                result += temaArtista.artista + " | " + tema.getTitulo() + " | " + detalhe.getDuracao() + "\n";
                            }
                        }
                        break;
                    }
                }
            }
        }
        if (result.isEmpty()) {
            result = "No artists";
        }
        return result;
    }

    /* GET_SONG_TITLES_CONSIDERING_WORDS
    Parametros comando: N - número de resultados, INCLUDE - a palavra a incluir, EXCLUDE - a palavra a excluir
    Descrição:
    Devolve as N músicas cujo título inclua a palavra INCLUDE mas não inclua a palavra EXCLUDE.
    As palavras a devem ser comparadas ignorando se são maiúsculas ou minúsculas e o resultado deve ser apresentado mantendo a ordem original de leitura do ficheiro de músicas (songs.txt).
    O resultado deve ser uma String contendo várias linhas separadas por \n e respeitando a seguinte sintaxe:
    "<Nome música>\n"
    Caso não haja nenhuma música, deve ser devolvida a string: “No results”
    toLowerCase: transforma em mínusculas */

    public static String getSongTitlesConsideringWords(int numResults, String includeWord, String excludeWord) {
        String result = "";
        int count = 0;
        for (Tema tema : temas) {
            String titulo = tema.getTitulo();
            if (titulo.toLowerCase().contains(includeWord.toLowerCase()) && !titulo.toLowerCase().contains(excludeWord.toLowerCase())) {
                result += titulo + "\n";
                count++;
                if (count == numResults) {
                    break;
                }
            }
        }
        if (result.isEmpty()) {
            result = "No results";
        }
        return result;
    }

    /* COUNT_SONGS_YEAR
    Parametros comando: X
    Descrição:
    Retorna o total de temas musicais que foram lançados no ano X.
    */
    public static String countSongsByYear(int year) {
        int count = 0;
        for (Tema tema : temas) {
            if (tema.getAno() == year) {
                count++;
            }
        }
        return  Integer.toString(count);
    }

    /*GET_SONGS_BY_ARTIST
    Parametros comando: N - número de resultados, A - nome do artista
    Descrição:
    Retorna as N primeiras músicas associadas ao artista indicado, pela ordem com que aparecem no ficheiro songs.txt.
    O resultado deve ser uma String contendo várias linhas separadas por \n e respeitando a seguinte sintaxe:
        “<Nome tema> : <Ano>\n”
    Caso não hajam resultados (por não existirem músicas para aquele artista ou por o artista não existir), deve retornar
    “No songs”
    */


    static String getSongsByArtist(int numResults, String artist) {
        String result = "";
        int count = 0;
        for (Tema tema : temas) {
            for (TemaArtista temaArtista : temasArtistas) {
                if (temaArtista.getIdTema().equals(tema.getIdTema()) && temaArtista.getNome().equalsIgnoreCase(artist)) {
                    result += tema.getTitulo() + " : " + tema.getAno() + "\n";
                    count++;
                    if (count == numResults) {
                        return result.toString();
                    }
                    break;
                }
            }
        }
        if (result.isEmpty()) {
            result = "No songs";
        }
        return result;
    }

    /* GET_MOST_DANCEABLE */
    public static String getMostDanceableSongs(int startYear, int endYear, int numResults) {
        ArrayList<Tema> danceableSongs = new ArrayList<>();

        // Filtrar as músicas dentro do intervalo de anos e com detalhes de dancabilidade
        for (Tema tema : temas) {
            if (tema.ano >= startYear && tema.ano <= endYear && !tema.detalhes.isEmpty()) {
                Detalhe detalhe = tema.detalhes.iterator().next();
                if (detalhe.dancabilidade != null) {
                    danceableSongs.add(tema);
                }
            }
        }

        // Ordenar as músicas por dançabilidade em ordem decrescente
        danceableSongs.sort((t1, t2) -> {
            Detalhe detalhe1 = t1.detalhes.iterator().next();
            Detalhe detalhe2 = t2.detalhes.iterator().next();
            return Double.compare(detalhe2.dancabilidade, detalhe1.dancabilidade);
        });

        // Obter as N músicas mais dançáveis
        String result = "";
        int count = 0;
        for (Tema tema : danceableSongs) {
            Detalhe detalhe = tema.detalhes.iterator().next();
            result += tema.titulo + " : " + tema.ano + " : " + detalhe.dancabilidade + "\n";
            count++;
            if (count == numResults) {
                break;
            }
        }
        return result;
    }

    public static Artista getArtistByName(String name) {
        for (Artista artist : artistas) {
            if (artist.getNomeArtista().equalsIgnoreCase(name)) {
                return artist;
            }
        }
        return null;
    }

    /*ADD_TAGS */
    public static String addTags(String artist, String[] tags) {
        Artista existingArtist = getArtistByName(artist);
        if (existingArtist == null) {
            return "Inexistent artist";
        }

        Set<String> artistTags = existingArtist.getTags();
        // Adicionar as novas tags ao novo array
        for (String tag : tags) {
            artistTags.add(tag.toUpperCase());
        }
        StringBuilder result = new StringBuilder();
        result.append(existingArtist.getNomeArtista()).append(" | ");
        for (String tag : artistTags) {
            result.append(tag).append(",");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
    /* REMOVE_TAGS  */
    public static String removeTags(String artist, String[] tags) {

        Artista existingArtist = getArtistByName(artist);
        if (existingArtist == null) {
            return "Inexistent artist";
        }
        Set<String> artistTags = existingArtist.getTags();
        for (String tag : tags) {
            artistTags.remove(tag.toUpperCase());
        }
        StringBuilder result = new StringBuilder();
        result.append(existingArtist.getNomeArtista()).append(" | ");
        if (artistTags.isEmpty()) {
            result.append("No tags");
        } else {
            for (String tag : artistTags) {
                result.append(tag).append(",");
            }
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }

    /* GET_ARTISTS_FOR_TAG */
    public static String getArtistsForTag(String tag) {
        StringBuilder result = new StringBuilder();
        for (Artista artist : artistas) {
            Set<String> tags = artist.getTags();
            if (tags.contains(tag.toUpperCase())) {
                result.append(artist.getNomeArtista()).append(";");
            }
        }
        if (result.length() > 0) {
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }

    static QueryResult execute(String command) {
        Query query = parseCommand(command);
        if (query == null) {
            return null; // Invalid command
        }
        long startTime = System.currentTimeMillis();
        String result = "";
        if(query.getName().equals("COUNT_DUPLICATE_SONGS_YEAR")){

            Map<String, Integer> titleCounts = new HashMap<>();
            for (int tema = 0; tema < temas.size(); tema++) {
                if(temas.get(tema).ano==Integer.parseInt(query.getArgs()[0])){
                   String title = temas.get(tema).titulo;
                    titleCounts.put(title, titleCounts.getOrDefault(title, 0) + 1);
                }
            }

            int duplicateCount = 0;
            for (int count : titleCounts.values()) {
                if (count > 1) {
                    duplicateCount += count - 1;
                }
            }
            result = String.valueOf(duplicateCount);
         }else if (query.getName().equals("GET_ARTISTS_WITH_MIN_DURATION")) {
            if (query.getArgs().length != 2) {
                return null; // Invalid command arguments
            }
            int year = Integer.parseInt(query.getArgs()[0]);
            int duration = Integer.parseInt(query.getArgs()[1]);
            result = getArtistsWithMinDuration(year, duration);
        } else if (query.getName().equals("GET_SONG_TITLES_CONSIDERING_WORDS")) {
            if (query.getArgs().length != 3) {
                return null; // Invalid command arguments
            }
            int numResults = Integer.parseInt(query.getArgs()[0]);
            String includeWord = query.getArgs()[1];
            String excludeWord = query.getArgs()[2];
            result = getSongTitlesConsideringWords(numResults, includeWord, excludeWord);
        } else if (query.getName().equals("COUNT_SONGS_YEAR")) {
            if (query.getArgs().length != 1) {
                return null; // Invalid command arguments
            }
            int year = Integer.parseInt(query.getArgs()[0]);
            result = countSongsByYear(year);
        } else if (query.getName().equals("GET_SONGS_BY_ARTIST")) {
            if (query.getArgs().length != 2) {
                return null; // Invalid command arguments
            }
            int numResults = Integer.parseInt(query.getArgs()[0]);
            String artist = query.getArgs()[1];
            result = getSongsByArtist(numResults, artist);
        } else if (query.getName().equals("GET_MOST_DANCEABLE")) {
            if (query.getArgs().length != 3) {
                return null; // Invalid command arguments
            }
            int startYear = Integer.parseInt(query.getArgs()[0]);
            int endYear = Integer.parseInt(query.getArgs()[1]);
            int numResults = Integer.parseInt(query.getArgs()[2]);
           result = getMostDanceableSongs(startYear, endYear, numResults);
        } else if (query.getName().equals("ADD_TAGS")) {
            if (query.getArgs().length < 1) {
                return null;
            }
            String parametro = query.getArgs()[0];
            String[] parts = parametro.split(";");
            String artist = parts[0];
            String[] tags = Arrays.copyOfRange(parts, 1, parts.length);
            result = addTags(artist, tags);
        } else if (query.getName().equals("REMOVE_TAGS")) {
            if (query.getArgs().length < 2) {
                return null; // Invalid command arguments
            }
            String artist = query.getArgs()[0];
            String[] tags = Arrays.copyOfRange(query.getArgs(), 1, query.getArgs().length);
            result = removeTags(artist, tags);
        } else if (query.getName().equals("GET_ARTISTS_FOR_TAG")) {
            if (query.getArgs().length != 1) {
                return null; // Invalid command arguments
            }
            String tag = query.getArgs()[0];
            result = getArtistsForTag(tag);
        } else {
            return null; // Invalid command
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        return new QueryResult(result, executionTime);
    }

    public static void main(String[] args) {

        File file = new File(".");
        boolean leuBem = loadFiles(file);
        if (!leuBem){
            System.out.println("Ficheiro não existe");
        }

        System.out.println("Welcome to DEISI Rockstar!");

        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        while (line != null && !line.equals("EXIT")) {
            QueryResult result = execute(line);
            if (result == null) {
                System.out.println("Illegal command. Try again");
            } else {
                System.out.println(result.result);
                System.out.println("(took " + result.time + " ms)");
            }
            line = in.nextLine();
        }
    }
}