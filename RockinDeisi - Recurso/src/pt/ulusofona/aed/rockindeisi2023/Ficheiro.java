package pt.ulusofona.aed.rockindeisi2023;

public class Ficheiro {
        String nome;
        int linhasOK;
        int linhasNOK;
        int primeiraLinhaNOK;

        public Ficheiro(String nome, int linhasOK, int linhasNOK, int primeiraLinhaNOK) {
            this.nome = nome;
            this.linhasOK = linhasOK;
            this.linhasNOK = linhasNOK;
            this.primeiraLinhaNOK = primeiraLinhaNOK;
        }

        public String getNome() {
            return nome;
        }

        void setNome(String nomeFicheiro) {
            this.nome = nomeFicheiro;
        }

        void setLinhasOK(int linhasOK) {
            this.linhasOK = linhasOK;
        }

        public int getLinhasOK() {
            return linhasOK;
        }

        public int getLinhasNOK() {
            return linhasNOK;
        }

        public int getPrimeiralinhaNOK() {
            return primeiraLinhaNOK;
        }
        @Override
        public String toString() {
            //NomeFicheiro | LinhasOK | LinhasNOK | PrimeiraLinhaNOK
            return nome + " | " + linhasOK + " | " + linhasNOK  + " | " + primeiraLinhaNOK;
        }
    }
