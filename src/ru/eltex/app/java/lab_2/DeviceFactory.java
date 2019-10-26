package ru.eltex.app.java.lab_2;

import ru.eltex.app.java.lab_1.Device;
import ru.eltex.app.java.lab_1.GameConsole;
import ru.eltex.app.java.lab_1.SetTopBox;
import ru.eltex.app.java.lab_1.TVSet;

import java.util.Random;

public class DeviceFactory {

    private static DeviceFactory instance;
    private DeviceFactory(){}

    // TODO
    // Нужен ли synchronized здесь???
    public synchronized static DeviceFactory getInstance(){
        if(instance == null){
            instance = new DeviceFactory();
        }
        return instance;
    }

    private String[] arrayName = { "Hi", "Love", "Tree", "Stone" };
    private String[] arrayBrand = { "LG", "Samsung", "Sony", "Huawei", "Apple" };
    private String[] arrayModel = { "FIG-01", "Kl-1", "5", "One" };
    private Random random = new Random();

    private GameConsole getGameConsole() {
        return new GameConsole(
                 1024,
                 100,
                 arrayName[ random.nextInt(arrayName.length) ],
                 arrayBrand[ random.nextInt(arrayBrand.length) ],
                 arrayModel[ random.nextInt(arrayModel.length) ]
        );
    }

    private SetTopBox getSetTopBox() {
        return new SetTopBox(
                26,
                17,
                5,
                100,
                arrayName[ random.nextInt(arrayName.length) ],
                arrayBrand[ random.nextInt(arrayBrand.length) ],
                arrayModel[ random.nextInt(arrayModel.length) ]
        );
    }

    private TVSet getTVSet() {
        return new TVSet(
                103,
                100,
                arrayName[ random.nextInt(arrayName.length) ],
                arrayBrand[ random.nextInt(arrayBrand.length) ],
                arrayModel[ random.nextInt(arrayModel.length) ]
        );
    }

    public Device getDevice() {
        int tmp = random.nextInt(3);
        switch (tmp) {
            case  (0):
                return getGameConsole();
            case (1):
                return getSetTopBox();
            case (2):
                return getTVSet();
       }
       return getTVSet();
    }
}
