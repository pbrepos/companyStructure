package ru.pb.springstart.service;

import ru.pb.springstart.entity.Office;

import java.util.List;

/**
 * Created by Pavel Barmyonkov on 11.10.18.
 * pbarmenkov@gmail.com
 */
public interface OfficeService {

    void save(Office office);

    List<Office> getAll();

    Office getOfficeById(int id);

    void remove(Office office);

    void update(Office office);

}
