package ru.pb.springstart.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pb.springstart.dao.OfficeDao;
import ru.pb.springstart.entity.Office;

import java.util.List;

/**
 * Created by Pavel Barmyonkov on 11.10.18.
 * pbarmenkov@gmail.com
 */

@Service
public class OfficeServiceImpl implements OfficeService {

    @Autowired
    private OfficeDao officeDao;

    @Transactional
    @Override
    public void save(Office office) throws ConstraintViolationException {
        officeDao.save(office);
    }

    @Transactional
    @Override
    public List<Office> getAll() {
        return officeDao.getAll();
    }

    @Transactional
    @Override
    public Office getOfficeById(int id) {
        return officeDao.getOfficeById(id);
    }

    @Transactional
    @Override
    public void remove(Office office) {
        officeDao.remove(office);
    }

    @Transactional
    @Override
    public void update(Office office) throws DataIntegrityViolationException {
        officeDao.update(office);
    }
}
