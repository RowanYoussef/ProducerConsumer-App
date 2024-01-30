package com.example.demo;

import java.util.ArrayList;

public class CareTaker {
	private ArrayList<SnapShot> snapshots= new ArrayList<>();
	
	
	public void addSnapShot(SnapShot snapshot) {
		this.snapshots.add(snapshot);
	}
	public SnapShot getSnapShot() {
		return this.snapshots.get(this.snapshots.size()-1);
	}
	public void Clear() {
		this.snapshots=new ArrayList<>();
	}
}
