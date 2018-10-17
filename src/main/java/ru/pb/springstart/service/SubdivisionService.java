package ru.pb.springstart.service;

import ru.pb.springstart.entity.Subdivision;

import java.util.List;

/**
 * Created by Pavel Barmyonkov on 11.10.18.
 * pbarmenkov@gmail.com
 */
public interface SubdivisionService {

    void save(Subdivision subdivision);

    List<Subdivision> getAll();

    Subdivision getSubdivisionById(int id);

    void remove(Subdivision subdivision);

    void update(Subdivision subdivision);
}
