package ru.job4j.ood.icp.device;

/**
 * Server поддерживает не все операции, а в качестве затычек - вообще выкидывает исключения
 * Причем (!) с открытым контрактом - хвостом наружу
 * выход - делить на 3 интерфейса
 */


public interface DeviceSadExample {
    void in(String data);
    void calculate();
    void output();
}
