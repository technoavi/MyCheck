package com.syntaxtree.agproengg.Test;

import org.eclipse.jdt.internal.compiler.util.Util.Displayable;

public class Newtest {
	public Newtest() {
		// TODO Auto-generated constructor stub
	}
	public static void show(){
	new Newtest().display();
		System.out.println("show");
	}
	public static void main(String[] args) {
		System.out.println("main method to test");
		show();
	}
public void display(){
	System.out.println("scnd method ");
	
}
}
