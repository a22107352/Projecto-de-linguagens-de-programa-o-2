package pt.ulusofona.aed.rockindeisi2023;

public class Detalhe {
    int duracao;
    String duracaoFormatada;
    int letra;
    int popularidade;
    Double dancabilidade;
    Double vivacidade;
    Double volume;

    public Detalhe(int duracao, int letra, int popularidade, Double dancabilidade,
                       Double vivacidade, Double volume, String duracaoFormatada) {
        this.duracao = duracao;
        this.letra = letra;
        this.popularidade = popularidade;
        this.dancabilidade = dancabilidade;
        this.vivacidade = vivacidade;
        this.volume = volume;
        this.duracaoFormatada = duracaoFormatada;
    }
    public int getDuracao() {
        return duracao;
    }

    public int getLetra() {
        return letra;
    }

    public int getPopularidade() {
        return popularidade;
    }

    public Double getDancabilidade() {
        return dancabilidade;
    }

    public Double getVivacidade() {
        return vivacidade;
    }

    public Double getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return  duracao + " | " + letra +  " | " + popularidade +
                "  | " + dancabilidade +  " | " + vivacidade + " | " + volume + " | " + duracaoFormatada;
    }
}
