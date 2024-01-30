package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Network implements Cloneable{
	
	 private static Network obj = new Network();
	 private ArrayList<Queue> queues = new ArrayList<>();
	 private ArrayList<Machine> machines = new ArrayList<>();
	 private ArrayList<Thread> threads = new ArrayList<>(); 
	 
	 public static Network getInstance(){
	        return obj;
   }
//	 protected Object clone()throws CloneNotSupportedException{
//		 Network clonedObject = (Network) super.clone();
//		 return clonedObject;
//	    }
	 private Network() {}
	 
	 public void Copy() {
		
		for(Queue q:this.queues) {
			if(q.getProducts().size()!=0) {
				this.queues.get(0).setProducts(q.getProducts());
				q.setProducts(new ArrayList<>());
				break;
			}
			
		}
		this.threads=new ArrayList<>();
		
		
	 }
    
	public ArrayList<Thread> getThreads() {
		return threads;
	}
	public void setThreads(ArrayList<Thread> threads) {
		this.threads = threads;
	}
	public ArrayList<Queue> getQueues() {
		return queues;
	}
	public void setQueues(ArrayList<Queue> queues) {
		this.queues = queues;
	}
	public ArrayList<Machine> getMachines() {
		return machines;
	}
	public void setMachines(ArrayList<Machine> machines) {
		this.machines = machines;
	}
	public void addMachine(Machine machine) {
		this.machines.add(machine);
	}
	public void addQueue(Queue queue) {
		this.queues.add(queue);
	}
    public String connectQueueToMachine(String queueId, String machineId){
        Queue Q = null;
        Machine M=null;
        for(Queue q : queues){
            if(q.getId().equals(queueId)){
                Q = q;
            }
        }
        if(Q==null)
        	return "queue not exist";
        for(Machine m : machines){
            if(m.getId().equals(machineId)){
                m.setPrevQ(Q);
                M=m;
            }
        }
        if(M==null)
        	return "machine not exist";
        return "Success";
    }

    public String connectMachineToQueue(String machineId, String queueId){
        Queue Q = null;
        Machine M=null;
        for(Queue q : queues){
            if(q.getId().equals(queueId)){
                Q = q;
            }
        }
        if(Q==null)
        	return "queue not exist";
        for(Machine m : machines){
			if(m.getId().equals(machineId)){
                m.setNextQ(Q);
                M=m;
            }
        }
        if(M==null)
        	return "machine not exist";
        return "Success";
    }
//    public SnapShot SaveStateToMemento()  {
//    	return new SnapShot((Network) this.Copy());
//    }
//    public void GetStateFromMemento(SnapShot snapshot) {
////    	 this.obj=new Network();
////    	this.obj=snapshot.getNetwork();
//    	this.Copy();
//    }
    public void Clear(){
        this.queues=new ArrayList<>();
        this.machines=new ArrayList<>();
        this.threads = new ArrayList<>(); 
        this.obj=new Network();
    }
    public void Simulate() {
    	for(Machine m : machines) {
    		Thread t = new Thread(m);
    		m.setProcessing(true);
    		this.threads.add(t);
    		
    	}
    	for(Thread t : threads) {
    		t.start();
    	}
    }
}
