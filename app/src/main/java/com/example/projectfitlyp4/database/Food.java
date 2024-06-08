package com.example.projectfitlyp4.database;

public class Food {

    private String namaMenu, KALORIMAKANAN, menuTime, description, ingredients, foto;

    public Food() {

    }

    public Food(String namaMenu, String KALORIMAKANAN, String menuTime, String description, String ingredients, String foto) {
        this.namaMenu = namaMenu;
        this.KALORIMAKANAN = KALORIMAKANAN;
        this.menuTime = menuTime;
        this.description = description;
        this.ingredients = ingredients;
        this.foto = foto;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }

    public String getKALORIMAKANAN() {
        return KALORIMAKANAN;
    }

    public void setKalori(String KALORIMAKANAN) {
        this.KALORIMAKANAN = KALORIMAKANAN;
    }

    public String getMenuTime() {
        return menuTime;
    }
    public void setMenuTime(String menuTime) {
        this.menuTime = menuTime;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
