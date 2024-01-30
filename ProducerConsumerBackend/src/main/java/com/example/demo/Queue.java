package com.example.demo;

import java.util.ArrayList;

public class Queue implements Observer{
	   private String color;
	   private String id;
	   private String name;
	   private int size;
	   private ArrayList <Product> products;
       private ArrayList <Machine> machines;
       
	   public Queue(String id) {
			this.setColor("blue");
			this.products=new ArrayList<>();
			this.machines=new ArrayList<>();
			this.id=id;
		}
	   public Queue() {
			this.setColor("blue");
			this.products=new ArrayList<>();
			this.machines=new ArrayList<>();
		}
	   
	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public ArrayList<Machine> getMachines() {
		return machines;
	}

	public void setMachines(ArrayList<Machine> machines) {
		this.machines = machines;
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
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
    public void addMachine(Machine machine) {
        machines.add(machine);
    }

    public void removeMachine(Machine machine) {
        machines.remove(machine);
    }


    public void addProduct(Product product) {
        products.add(product);
    }

    public Product removeProduct() {
        if (!products.isEmpty()) {
            Product removedProduct = products.remove(0);
            return removedProduct;
        }
        return null;
   }


    public boolean isEmpty() {
        return products.isEmpty();
    }
	@Override
	public  synchronized void update(Machine machine, String key) {
		if(key.equals("prev")) {
			machine.setProduct(this.getProducts().remove(0));
		}else {
			this.addProduct(machine.getProduct());
		}
	}

//    public void registerMachine(Machine machine) {
//        addObserver(machine);
//        notifyObservers();
//    }
	   
}
