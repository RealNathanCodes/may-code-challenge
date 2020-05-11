package com.mindex.challenge.controller;

import java.util.List;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/compensation")
    public Compensation add(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation add request for [{}]", compensation);

        return compensationService.addCompensation(compensation);
    }

    @GetMapping("/compensation/{id}")
    public List<Compensation> get(@PathVariable String id) {
        LOG.debug("Received compensation get request for id [{}]", id);

        return compensationService.getCompensation(id);
    }
}
