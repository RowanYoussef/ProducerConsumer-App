package com.example.demo;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class Controller {
	    CareTaker caretaker= new CareTaker();
	   @GetMapping("/addMachine/{id}")
	    public void addMachine(@PathVariable("id") String id){
	        System.out.println("add machine");
	        Network network = Network.getInstance();
	        network.addMachine(new Machine(id));
	    }
	    @GetMapping("/addQueue/{id}")
	    public void addQueue(@PathVariable("id") String id){
	        System.out.println("add queue");
	        Network network = Network.getInstance();
	        network.addQueue(new Queue(id));
	    }
	    @GetMapping("/connectMachineToQueue/{queueid}/{machineid}")
	    public String connectMachineToQueue(@PathVariable("machineid") String machineId,@PathVariable("queueid") String queueId){
	        System.out.println(machineId+" " + " "+queueId);
	        Network network = Network.getInstance();
	        return network.connectMachineToQueue(machineId, queueId);
	    }
	    @GetMapping("/connectQueueToMachine/{queueid}/{machineid}")
	    public String connectQueueToMachine(@PathVariable("queueid") String queueId,@PathVariable("machineid") String machineId){
	        System.out.println(machineId+" " + " "+queueId);
	        Network network = Network.getInstance();
	        return network.connectQueueToMachine(queueId, machineId);
	    }
	    @GetMapping("/Clear")
	    public void Clear() {
	     Network.getInstance().Clear();
	     this.caretaker.Clear();
	     
	    	
	    }
	    @GetMapping("/simulate/{productsNumber}")
	    public void simulate(@PathVariable("productsNumber") int productsNumber) {
	        System.out.println(productsNumber);
	        Network network = Network.getInstance();
	        for (int i=0; i<productsNumber; i++)
	            network.getQueues().get(0).addProduct(new Product());
	            //caretaker.addSnapShot(network.SaveStateToMemento());
	            //System.out.println(caretaker.getSnapShot().getNetwork().getMachines().toString());
	            network.Simulate();
	        
	    }
	    @GetMapping("/replay")
	    public void replay() {
	    	Network network = Network.getInstance();
	    	//network.GetStateFromMemento(caretaker.getSnapShot());
	    	 for(Machine m : network.getMachines()) {
	    		 m.setProcessing(false);
	    	 }
	    	 network.Copy();
	    	 System.out.println(network.getMachines().get(0).getPrevQ().getProducts().size());
	    	 System.out.println(network.getMachines().get(0).getNextQ().getProducts().size());
	    	network.Simulate();
	    	
	    }
	    @GetMapping("/getMachines")
	    public ArrayList<Machine> getMachines(){
	    	Network network = Network.getInstance();
	    	return network.getMachines();
	    }
	    @GetMapping("/getQueues")
	    public ArrayList<Queue> getQueues(){
	    	Network network = Network.getInstance();
	    	return network.getQueues();
	    }
}
