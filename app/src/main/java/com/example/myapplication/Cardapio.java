package com.example.myapplication;

public class Cardapio {
    private String lanche1;
    private String almoco;
    private String lanche2;
    private String jantar;
    private String keyCardapio;

    public String getKeyCardapio() {
        return keyCardapio;
    }

    public void setKeyCardapio(String keyCardapio) {
        this.keyCardapio = keyCardapio;
    }

    public String getLanche1() {
        return lanche1;
    }

    public void setLanche1(String lanche1) {
        this.lanche1 = lanche1;
    }

    public String getAlmoco() {
        return almoco;
    }

    public void setAlmoco(String almoco) {
        this.almoco = almoco;
    }

    public String getLanche2() {
        return lanche2;
    }

    public void setLanche2(String lanche2) {
        this.lanche2 = lanche2;
    }

    public String getJantar() {
        return jantar;
    }

    public void setJantar(String jantar) {
        this.jantar = jantar;
    }

    @Override
    public String toString() {
        return "Cardapio{" +
                "lanche1='" + lanche1 + '\'' +
                ", almoco='" + almoco + '\'' +
                ", lanche2='" + lanche2 + '\'' +
                ", jantar='" + jantar + '\'' +
                ", keyCardapio='" + keyCardapio + '\'' +
                '}';
    }
}
