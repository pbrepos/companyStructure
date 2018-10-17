package ru.pb.springstart.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pb.springstart.dao.SubdivisionDao;
import ru.pb.springstart.entity.Subdivision;

import java.util.List;

/**
 * Created by Pavel Barmyonkov on 11.10.18.
 * pbarmenkov@gmail.com
 */

@Service
public class SubdivisionServiceImpl implements SubdivisionService {

    @Autowired
    private SubdivisionDao subdivisionDao;

    @Transactional
    @Override
    public void save(Subdivision subdivision) throws ConstraintViolationException {
        subdivisionDao.save(subdivision);
    }

    @Transactional
    @Override
    public List<Subdivision> getAll() {
        return subdivisionDao.getAll();
    }

    @Override
    public Subdivision getSubdivisionById(int id) {
        return subdivisionDao.getSubdivisionById(id);
    }

    @Transactional
    @Override
    public void remove(Subdivision subdivision) {
        subdivisionDao.remove(subdivision);
    }

    @Transactional
    @Override
    public void update(Subdivision subdivision) throws DataIntegrityViolationException {
        subdivisionDao.update(subdivision);
    }
}
