package collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class Gadget {
    String name;
    int consumption;
    boolean on;

    @Override
    public String toString() {
        return "{ " + name + "(" + consumption + " Вт) }";
    }
}

public class Task3 {

    public static void main(String[] args) {
        List<Gadget> gadgets = new ArrayList<>();
        Gadget toaster = new Gadget();
        toaster.name = "Toaster";
        toaster.consumption = 100;
        Gadget fridge = new Gadget();
        fridge.name = "Fridge";
        fridge.consumption = 500;
        fridge.on = true;
        Gadget tv = new Gadget();
        tv.name = "TV";
        tv.consumption = 250;
        tv.on = true;
        gadgets.add(toaster);
        gadgets.add(fridge);
        gadgets.add(tv);
        AtomicInteger totalConsumption = new AtomicInteger();
        gadgets.forEach(gadget -> {
            if (gadget.on) totalConsumption.addAndGet(gadget.consumption);
        });
        System.out.println("Итоговая потребляемая мощность: " + totalConsumption);
        gadgets.sort(Comparator.comparingInt(gadget -> gadget.consumption));
        System.out.println("Отсортированный по мощности список");
        gadgets.forEach(System.out::println);
        var gadget = gadgets.stream().filter(g -> g.on && g.consumption < 300).findFirst();
        gadget.ifPresent(g -> System.out.println("Прибор с потреблением меньше чем 300: " + g));
    }
}
