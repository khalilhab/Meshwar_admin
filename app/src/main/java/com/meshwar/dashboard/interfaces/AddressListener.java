package com.meshwar.dashboard.interfaces;

public interface AddressListener<T> {
    void onAddClick(T item, int position);

    void onEditClick(T item, int position);

    void onClick(T item, int position);
}
