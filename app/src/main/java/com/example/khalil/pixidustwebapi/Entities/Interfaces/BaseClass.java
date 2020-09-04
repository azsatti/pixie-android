package com.example.khalil.pixidustwebapi.Entities.Interfaces;


/**
 * Created by Khalil on 1/24/2018.
 */

public interface BaseClass<T extends BaseClass<T>> {
    public int GetId();
    public T[] SetList(T[] lstgeneric);
    public T GetSpecificProperties(T detail);
    public T[] GetList();
    public void SetId(int i);
}
