package com.example.demo;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Machine implements Runnable, Observable{
   private String color;
   private String id;
   private String name;
   private int time;
   private Queue prevQ;
   private Queue nextQ;
   private Product product;
   private boolean isProcessing;
   
   
   
public Machine(String id) {
	this.setColor("palevioletred");
	this.prevQ=new Queue();
	this.nextQ=new Queue();
	this.product=new Product();
	this.id=id;
	this.time=new Random().nextInt(1500) + 500;
	System.out.println(this.time);
}

public boolean isProcessing() {
	return isProcessing;
}

public void setProcessing(boolean isProcessing) {
	this.isProcessing = isProcessing;
}

public String getColor() {
	return color;
}
public void setColor(String color) {
	this.color = color;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getTime() {
	return time;
}
public void setTime(int time) {
	this.time = time;
}
public Queue getPrevQ() {
	return prevQ;
}
public void setPrevQ(Queue prevQ) {
	this.prevQ = prevQ;
}
public Queue getNextQ() {
	return nextQ;
}
public void setNextQ(Queue nextQ) {
	this.nextQ = nextQ;
}
public Product getProduct() {
	return product;
}
public void setProduct(Product product) {
	this.product = product;
}
   
public void update(Queue updatedQueue) {
    if (!updatedQueue.isEmpty() && !isProcessing) {
        Product product = updatedQueue.removeProduct();
        processProduct(product);
    } else if (updatedQueue.isEmpty()) {
       
        isProcessing = false;
        color = "palevioletred"; 
    }
}

private void processProduct(Product product) {
    isProcessing = true;
    color = product.getColor();
    try {
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
    isProcessing = false;
    color = "palevioletred"; 
}

@Override
public void run() {
	while(this.isProcessing) {
		int i=0;
		Product p = new Product();
		if(this.prevQ.getProducts().size()==0) {
			continue;
		}else {
			i++;
			System.out.println("prevQ= "+this.prevQ.getProducts().size());
			System.out.println("nextQ= "+this.nextQ.getProducts().size());
			synchronized ( this.prevQ) {
			if(this.prevQ.getProducts().size()!=0) {
			p=this.prevQ.getProducts().get(0);
			NotifyObservers("prev");
			System.out.println(p.getColor());
			this.color=p.getColor();
			try {
				Thread.sleep(time);
				System.out.println(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			NotifyObservers("next");
			}
			}
			this.color="palevioletred";
			System.out.println("prevQ= "+this.prevQ.getProducts().size());
			System.out.println("nextQ= "+this.nextQ.getProducts().size());
		}
//		if(i!=0&&this.prevQ.getProducts().size()==0) {
//			this.isProcessing=false;
//			break;
//	}
	}
	
}

@Override
public void NotifyObservers(String key) {
	if(key.equals("prev"))
	this.prevQ.update(this, key);
	else
	this.nextQ.update(this, key);	
	
}

@Override
public String toString() {
	return "Machine [color=" + color + ", id=" + id + ", name=" + name + ", time=" + time + ", prevQ=" + prevQ
			+ ", nextQ=" + nextQ + ", product=" + product + ", isProcessing=" + isProcessing + "]";
}

  
}
