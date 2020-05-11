package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Compensation addCompensation(Compensation compensation) {
        LOG.debug("Adding compensation [{}]", compensation);

        compensationRepository.insert(compensation);

        return compensation;
    }

    @Override
    public List<Compensation> getCompensation(String id) {
        LOG.debug("Getting compensations for employee id [{}]", id);

        List<Compensation> compensations = compensationRepository.findByEmployee_EmployeeId(id);

        if (compensations == null || compensations.size() == 0) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return compensations;
    }
}
