package modelo;

public class Serie {

    private int numeroInicial;
    private int cantidadTerminos;
    private String serieGenerada;
    private int sumaTotal;

    public Serie() {

    }

    public Serie(
            int numeroInicial,
            int cantidadTerminos,
            String serieGenerada,
            int sumaTotal) {

        this.numeroInicial = numeroInicial;
        this.cantidadTerminos = cantidadTerminos;
        this.serieGenerada = serieGenerada;
        this.sumaTotal = sumaTotal;

    }

    public int getNumeroInicial() {
        return numeroInicial;
    }

    public void setNumeroInicial(int numeroInicial) {
        this.numeroInicial = numeroInicial;
    }

    public int getCantidadTerminos() {
        return cantidadTerminos;
    }

    public void setCantidadTerminos(int cantidadTerminos) {
        this.cantidadTerminos = cantidadTerminos;
    }

    public String getSerieGenerada() {
        return serieGenerada;
    }

    public void setSerieGenerada(String serieGenerada) {
        this.serieGenerada = serieGenerada;
    }

    public int getSumaTotal() {
        return sumaTotal;
    }

    public void setSumaTotal(int sumaTotal) {
        this.sumaTotal = sumaTotal;
    }
}
