package com.mindex.challenge.service;

import java.util.List;

import com.mindex.challenge.data.Compensation;

public interface CompensationService {
    Compensation addCompensation(Compensation compensation);
    List<Compensation> getCompensation(String id);
}