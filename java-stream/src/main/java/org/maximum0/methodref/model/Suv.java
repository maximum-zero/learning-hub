package org.maximum0.methodref.model;

public class Suv extends Car {

    public Suv(String name, String brand) {
        super(name, brand);
    }

    @Override
    public void drive() {
        System.out.println("Driving a Suv " + name + " from " + brand);
    }
}
