package pt.ulusofona.aed.rockindeisi2023;

import java.util.HashSet;
import java.util.Set;

public class Artista {
    String nomeArtista;
    int numMusicas;
    private Set<String> tags;

    public Artista(String nomeArtista, int numMusicas) {
        this.nomeArtista = nomeArtista;
        this.numMusicas = numMusicas;
        this.tags = new HashSet<>();
    }

    public String getNomeArtista() {
        return nomeArtista;
    }

    public int getNumMusicas() {
        return numMusicas;
    }

    public void setNumMusicas(int numMusicas) {
        this.numMusicas = numMusicas;
    }

    public Set<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        if (nomeArtista.matches("[A-D].*")){
            return "Artista: [" + nomeArtista + "]";
        }
        else{
            return "Artista: [" + nomeArtista + "] | " + numMusicas;
        }
    }
}
