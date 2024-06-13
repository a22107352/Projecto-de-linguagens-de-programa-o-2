package pt.ulusofona.aed.rockindeisi2023;

import java.util.ArrayList;

class Tema {
    String idTema;
    String titulo;
    int ano;
    int numArtistas;
    ArrayList<Detalhe> detalhes;
    //ArrayList<Artista> artistas;

    public Tema(String idTema, String titulo, int ano, int numArtistas, ArrayList <Detalhe> detalhes) {
        this.idTema = idTema;
        this.titulo = titulo;
        this.ano = ano;
        this.numArtistas = numArtistas;
        this.detalhes = detalhes;
        //this.artistas = artistas;
    }

    public String getIdTema() {
        return idTema;
    }
    public String getTitulo() {
        return titulo;
    }
    public int getAno() {
        return ano;
    }

    public int getNumArtistas() {
        return numArtistas;
    }

    public ArrayList<Detalhe> getDetalhes() {
        return detalhes;
    }

    //public ArrayList<Artista> getArtistas() {
    //    return artistas;
    //}

    @Override
    public String toString() {
        //return idTema + " | " + titulo + " | " + ano;
        if(ano < 1995) { //ID | Título | Ano
            return idTema + " | " + titulo + " | " + ano ;
        }
        else if(ano >= 1995 && ano < 2000) { //ID | Título | Ano | Duração | Popularidade
            return idTema + " | " + titulo + " | " + ano + " | " + detalhes.get(0).duracaoFormatada +
                    " | " + detalhes.get(0).popularidade;
        }
        else { //ID | Título | Ano | Duração | Popularidade | NumArtistas
                return  idTema + " | " + titulo + " | " + ano + " | " + detalhes.get(0).duracaoFormatada +
                        " | " + detalhes.get(0).popularidade + " | " + numArtistas;
            }
        }
    }