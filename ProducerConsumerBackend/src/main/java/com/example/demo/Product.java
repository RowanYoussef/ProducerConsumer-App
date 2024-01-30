package com.example.demo;

public class Product {
   private String color;
   public Product() {
       this.color = RandomColor.generate();
   }
public String getColor() {
	return color;
}
public void setColor(String color) {
	this.color = color;
}
   
}
